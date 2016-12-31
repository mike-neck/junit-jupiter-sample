/*
 * Copyright 2016 Shinya Mochida
 * 
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.guice;

import com.example.guice.annotation.Date;
import com.example.guice.annotation.Lang;
import com.example.guice.annotation.Time;
import com.example.guice.annotation.Zone;
import com.example.guice.api.DateService;
import com.example.util.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.ZoneId.of;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("DateServiceのテスト - 実装はDateServiceImpl")
public class DateServiceTest {

    @Nested
    @DisplayName("ロケールはen日付はMMM/d/uu,時刻はhh:mm,America/Los_Angeles")
    @Lang("en")
    @Date.Format("MMM/d/uu")
    @Time.Format("hh:mm")
    @Zone("America/Los_Angeles")
    @ExtendWith({ DateServiceGuiceExtension.class })
    class LosAngelesTest {

        @Test
        @DisplayName("dateの最初の3文字は英字")
        void first3CharactersIsAlphabet(DateService service) {
            final String today = service.today();
            assertTrue(today.substring(0, 3).matches("\\p{Alpha}{3}"));
        }

        @Test
        @DisplayName("時刻は必ず2文字ある")
        void hourHasTwoCharacters(DateService service) {
            final String now = service.now();
            assertEquals(2, now.split(":")[0].length());
        }

        @TestFactory
        @DisplayName("月の名前")
        Stream<DynamicTest> monthName(DateService service) {
            return Stream.of(
                    new Pair<>("Jan", 1)
                    , new Pair<>("Feb", 2)
                    , new Pair<>("Mar", 3)
                    , new Pair<>("Apr", 4)
                    , new Pair<>("May", 5)
                    , new Pair<>("Jun", 6)
                    , new Pair<>("Jul", 7)
                    , new Pair<>("Aug", 8)
                    , new Pair<>("Sep", 9)
                    , new Pair<>("Oct", 10)
                    , new Pair<>("Nov", 11)
                    , new Pair<>("Dec", 12)
            ).map(Pair.mapPair(m -> service.date(2017, m, 1)))
                    .map(Pair.mapPair(d -> d.substring(0, 3)))
                    .map(Pair.transformPair((e, a) -> dynamicTest(
                            String.format("test of %s", e)
                            , () -> assertEquals(e, a))));
        }

        @Test
        @DisplayName("タイムゾーンはAsia/Tokyoではない")
        void timeZoneIsNotAsiaTokyo(DateService service) {
            final ZoneId zone = service.getZone();
            assertNotEquals(of("Asia/Tokyo"), zone);
        }
    }

    @Nested
    @DisplayName("ロケール指定なし(デフォルト)/Dateの指定なし(uuuu/MM/dd)/Timeの指定なし(HH:mm:ss.SSS)/Zoneの指定なし(UTC)")
    @ExtendWith({ DateServiceGuiceExtension.class })
    class DefaultInjection {

        @Test
        @DisplayName("日付のフォーマットの確認")
        void validateDateFormat(DateService service) {
            assertAll(
                    () -> assertEquals("2017/01/01", service.date(2017, 1, 1))
                    , () -> assertEquals("2016/11/11", service.date(2016, 11, 11))
            );
        }

        @Test
        @DisplayName("時刻のフォーマットの確認")
        void validateTimeFormat(DateService service) {
            assertAll(
                    () -> assertEquals("04:00:00.000", service.time(4, 0))
                    , () -> assertEquals("13:13:00.000", service.time(13, 13))
            );
        }

        @Test
        @DisplayName("タイムゾーンの確認")
        void validateTimeZone(DateService service) {
            final ZoneId zone = service.getZone();
            assertEquals(of("UTC"), zone);
        }

        @Test
        @DisplayName("not null")
        void validateNotNull(DateService service) {
            assertAll(
                    () -> assertNotNull(service.now())
                    , () -> assertNotNull(service.today())
            );
        }
    }

    @Nested
    @DisplayName("DateService以外を引数に渡す")
    @Zone("Asia/Tokyo")
    @Lang("ja")
    @ExtendWith({ DateServiceGuiceExtension.class })
    class OtherTypeParameter {

        @Test
        @DisplayName("ZoneIdを渡す")
        void zoneId(ZoneId zoneId) {
            assertEquals(of("Asia/Tokyo"), zoneId);
        }

        @Nested
        @DisplayName("InnerClassにはOuterClassの設定は引き継がれない(設定がない場合はデフォルトになる)")
        @Date.Format("uu/MM/dd")
        class ShortYear {
            @Test
            @DisplayName("各種引数の確認")
            @Inject
            void parameters(
                    ZoneId zoneId,
                    @Date DateTimeFormatter dateFormat,
                    @Time DateTimeFormatter timeFormatter,
                    Locale locale
            ) {
                final LocalDate date = LocalDate.of(2017, 1, 1);
                final LocalTime time = LocalTime.of(1, 0);
                assertAll(
                        () -> assertEquals(of("UTC"), zoneId)
                        , () -> assertEquals(
                                "17/01/01"
                                , date.format(dateFormat))
                        , () -> assertEquals("01:00:00.000", time.format(timeFormatter))
                        , () -> assertEquals(new Locale("ja", "JP"), locale)
                );
            }
        }
    }
}
