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
package com.example.ex2;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FourthTest {

    private static final String text = "This is text.";

    @Test
    void assertAllTests() {
        assertAll(
                () -> assertEquals("This is text.", text)
                , () -> assertEquals(13, text.length())
                , () -> log.info("assertしないということもできる...")
        );
    }

    private static final IntUnaryOperator SUCC = i -> i + 1;

    @Test
    void assertAllThatFails() {
        Stream<Executable> tests = IntStream.iterate(1, SUCC).limit(7)
                .mapToObj(i -> () -> {
                    log.info("This is assertion[{}]", i);
                    assertEquals(i, i + (i % 3 == 0? 1 : 0), () -> "assertion" + i);
                });
        assertAll(tests);
    }
}
