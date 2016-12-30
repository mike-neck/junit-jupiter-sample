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
package com.example.ex8;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
@DisplayName("Stringとintのランダムを得るテスト")
@ExtendWith(InputStringResolver.class)
public class RandomStringWithIntTest {

    @Test
    @DisplayName("Stringだけ")
    void onlyString(@InputString(value = InputStringType.STATIC, staticValue = "only string") String onlyString) {
        assertEquals("only string", onlyString);
    }

    @Test
    @DisplayName("Stringだけでなくintも受け取る")
    @ExtendWith(RandomIntResolver.class)
    void notOnlyStringButInt(
            @InputString(value = InputStringType.STATIC, staticValue = "not only") String notOnly
            , int random
    ) {
        log.info("string -> {}, int -> {}", notOnly, random);
    }

    @Test
    @DisplayName("intだけ")
    @ExtendWith(RandomIntResolver.class)
    void onlyInt(@IntValue(select = {1, 3, 5}) int onlySpecifiedInt) {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(5);
        }};
        assertTrue(list.contains(onlySpecifiedInt));
    }

    @Nested
    @ExtendWith(RandomIntResolver.class)
    class IntAndString {

        @Test
        @DisplayName("ネストしたクラスにintを適用")
        void nestedTest(
                int random
                , @InputString(value = InputStringType.STATIC, staticValue = "nested") String nested
                , @IntValue(min = 1, max = 100) int ranged
        ) {
            assertTrue(0 < ranged);
            assertTrue(ranged < 101);
            log.info("parameters random: {}, string {}, ranged: {}", random, nested, ranged);
        }

        @TestFactory
        Stream<DynamicTest> dynamicTestStream(
                @IntValue(min = 1, max = 30) int testSize
                , int seed
                , @IntValue(min = 2, max = 10) int streamSize
                , @IntValue(min = 0, max = 0) int min
                , @IntValue(min = 100, max = 1000) int max
        ) {
            final Random random = new Random((long) seed);
            final IntSupplier bounded = () -> min + random.nextInt(max - min + 1);
            final Supplier<IntStream> intStreams = () -> IntStream.generate(bounded).limit(streamSize);
            return Stream.generate(intStreams)
                    .limit(testSize)
                    .map(IntStream::sorted)
                    .map(IntStream::summaryStatistics)
                    .map(s -> dynamicTest(
                            "first element should be smaller than average"
                            , () -> assertTrue(s.getMin() < s.getAverage())));
        }
    }
}
