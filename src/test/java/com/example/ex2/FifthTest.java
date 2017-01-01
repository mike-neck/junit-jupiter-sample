/*
 * Copyright 2017 Shinya Mochida
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
package com.example.ex2;

import com.example.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.time.DayOfWeek.SUNDAY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Slf4j
public class FifthTest {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Tokyo");

    @NotNull
    @Contract(pure = true)
    private static LocalDate today() {
        return LocalDate.now(ZONE_ID);
    }

    @Test
    void thisTestDoNotRunOnSunday() {
        final DayOfWeek dayOfWeek = today().getDayOfWeek();
        assumeFalse(SUNDAY.equals(dayOfWeek));
        log.info("日曜以外");
        assertFalse("日".equals(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN)));
    }

    @Test
    void thisTestRunOnSunday() {
        final DayOfWeek dayOfWeek = today().getDayOfWeek();
        assumeTrue(SUNDAY.equals(dayOfWeek));
        log.info("日曜");
        assertEquals("日", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN));
    }

    @Test
    void doNotRunOnSundaySmartStyle() {
        final DayOfWeek dayOfWeek = today().getDayOfWeek();
        assumingThat(
                () -> !SUNDAY.equals(dayOfWeek)
                , () -> assertFalse("日".equals(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN)))
        );
    }

    @Test
    void runOnSundaySmartStyle() {
        final DayOfWeek dayOfWeek = today().getDayOfWeek();
        assumingThat(
                () -> SUNDAY.equals(dayOfWeek)
                , () -> assertEquals("日", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN))
        );
    }

    @Test
    void runAllAssumptions() {
        final DayOfWeek dayOfWeek = today().getDayOfWeek();
        assertAll(
                IntStream.iterate(1, i -> i + 1)
                        .boxed()
                        .limit(7)
                        .map(Pair.createPair(DayOfWeek::of))
                        .map(Pair.mapPair(createAssumption(dayOfWeek)))
                        .map(Pair::reverse)
                        .map(Pair.mapPair(createAssertion(dayOfWeek)))
                        .map(Pair.transformPair((b, e) -> () -> assumingThat(b, e)))
        );
    }

    @NotNull
    @Contract("null -> fail")
    private static Function<DayOfWeek, BooleanSupplier> createAssumption(
            @NotNull DayOfWeek dayOfWeek
    ) {
        Objects.requireNonNull(dayOfWeek);
        return dow -> () -> dow.equals(dayOfWeek);
    }

    @NotNull
    @Contract("null -> fail")
    private static Function<Integer, Executable> createAssertion(
            @NotNull DayOfWeek dayOfWeek
    ) {
        Objects.requireNonNull(dayOfWeek);
        return i -> () ->
                displayDayOfWeek(i)
                        .equals(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.JAPAN));
    }

    @NotNull
    private static String displayDayOfWeek(int d) {
        switch (d) {
            case 1:
                return "月";
            case 2:
                return "火";
            case 3:
                return "水";
            case 4:
                return "木";
            case 5:
                return "金";
            case 6:
                return "土";
            case 7:
                return "日";
            default:
                throw new IllegalArgumentException("argument should be in 1..7.");    
        }
    }
}
