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
package com.example.ex4;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("outer-class")
public class NestedExecution {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        log.info("beforeAll");
        Thread.sleep(20);
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        log.info("afterAll");
        Thread.sleep(20);
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        log.info("beforeEach");
        Thread.sleep(20);
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        log.info("afterEach");
        Thread.sleep(20);
    }

    @DisplayName("outer-test")
    @Test
    void test() throws InterruptedException {
        log.info("test");
        Thread.sleep(20);
    }

    @DisplayName("inner-class")
    @Nested
    class Inner {

        @BeforeEach
        void innerBeforeEach() throws InterruptedException {
            log.info("innerBeforeEach");
            Thread.sleep(20);
        }

        @AfterEach
        void innerAfterEach() throws InterruptedException {
            log.info("innerAfterEach");
            Thread.sleep(20);
        }

        @DisplayName("inner-test")
        @Test
        void innerTest() throws InterruptedException {
            log.info("innerTest");
            Thread.sleep(20);
        }

        @DisplayName("most-inner-class")
        @Nested
        class MostInner {

            @BeforeEach
            void mostInnerBeforeEach() throws InterruptedException {
                log.info("mostInnerBeforeEach");
                Thread.sleep(20);
            }

            @AfterEach
            void mostInnerAfterEach() throws InterruptedException {
                log.info("mostInnerAfterEach");
                Thread.sleep(20);
            }

            @DisplayName("most-inner-test")
            @Test
            void mostInnerTest() throws InterruptedException {
                log.info("mostInnerTest");
                Thread.sleep(20);
            }
        }
    }
}
