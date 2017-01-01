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
package com.example.ex6;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Slf4j
public class DynamicTestSample {

    @TestFactory
    Collection<DynamicTest> dynamicTestCollection() {
        return Arrays.asList(
                dynamicTest("1 + 2 = 3", () -> assertEquals(3, 1 + 2))
                , dynamicTest("1 - 2 = -1", () -> assertEquals(-1, 1 - 2))
        );
    }

    @TestFactory
    Iterator<DynamicTest> dynamicTestIterator() {
        return Arrays.asList(
                dynamicTest("1 + 2 = 3", () -> assertEquals(3, 1 + 2))
                , dynamicTest("1 - 2 = -1", () -> assertEquals(-1, 1 - 2))
        ).iterator();
    }

    @TestFactory
    Iterable<DynamicTest> dynamicTestIterable() {
        return Arrays.asList(
                dynamicTest("1 + 2 = 3", () -> assertEquals(3, 1 + 2))
                , dynamicTest("1 - 2 = -1", () -> assertEquals(-1, 1 - 2))
        );
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestStream() {
        return Stream.of(
                new ThreeInts(1, 2, 3)
                , new ThreeInts(1, -2, -1)
        ).map(t -> dynamicTest(t.getTitle(), t.getTest()));
    }

    static class ThreeInts {
        final int left;
        final int right;
        final int result;

        ThreeInts(int left, int right, int result) {
            this.left = left;
            this.right = right;
            this.result = result;
        }

        String getTitle() {
            final String c = right < 0 ? "%d - %d = %d" : "%d + %d = %d";
            return String.format(c, left, Math.abs(right), result);
        }

        Executable getTest() {
            return () -> assertEquals(result, left + right);
        }
    }

    @TestFactory
    @DisplayName("@Testと同じくdisplay nameを付けられる")
    List<DynamicTest> dynamicTestList() {
        return Arrays.asList(
                dynamicTest("1 + 2 = 3", () -> assertEquals(3, 1 + 2))
                , dynamicTest("1 - 2 = -1", () -> assertEquals(-1, 1 - 2))
        );
    }

    @TestFactory
    @DisplayName("応用例 : メソッド/関数の性質を確認するテスト")
    Stream<DynamicTest> propertyTest() {
        // 整数の正負を反転する関数
        final IntUnaryOperator inverse = i -> -i;

        // 絶対値は等しいことのテスト
        final IntFunction<Executable> compareAbs = i -> () ->
                assertTrue(Math.abs(i) == Math.abs(inverse.applyAsInt(i)));

        // ランダムのseed
        final long seed = Date.from(
                now(ZoneOffset.UTC.normalized())
                        .toInstant(ZoneOffset.UTC))
                .getTime();

        // テストタイトル
        final IntFunction<String> title = i -> String.format("test case (seed: %17d) %9d", seed, i);

        // dynamicTest
        final IntFunction<DynamicTest> toDynamicTest = i -> dynamicTest(title.apply(i), compareAbs.apply(i));

        // 100個のテスト
        return new IntRandom(100, seed)
                .stream()
                .mapToObj(toDynamicTest);
    }

    static class IntRandom implements IntSupplier {

        final long size;
        final Random random;
        final int pitch;

        int max;

        IntRandom(long size, long seed) {
            this.size = size;
            this.random = new Random(seed);
            this.max = (Integer.MAX_VALUE / (int) size); //TODO 本当はlongで計算しないとダメ
            this.pitch = max;
        }

        IntStream stream() {
            return IntStream.generate(this).limit(size);
        }

        @Override
        public int getAsInt() {
            final int m = max / 2;
            final int r = random.nextInt(max);
            max += pitch;
            return r - m;
        }
    }

    @Test
    @DisplayName("@TestFactoryと@Testは一つのクラスの中に混在できる")
    void test() {
        assertTrue(true);
    }

    @TestFactory
    @DisplayName("Array type method causes JUnitException")
    @Disabled
    DynamicTest[] dynamicTestArray() {
        return new DynamicTest[]{
                dynamicTest("1 + 2 = 3", () -> assertEquals(3, 1 + 2))
                , dynamicTest("1 - 2 = -1", () -> assertEquals(-1, 1 - 2))
        };
    }
}
