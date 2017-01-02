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
package com.example.ex7.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@ExtendWith({TestLifecycleCallbacks.class})
public class NestedClasses {

    private static final Logger INNER = LoggerFactory.getLogger("com.example.ex7.lifecycle.NestedClasses$Inner");
    private static final Logger MOST = LoggerFactory.getLogger("com.example.ex7.lifecycle.NestedClasses$Inner$MostInner");

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        log.info("beforeAll");
        Thread.sleep(20);
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        log.info("beforeEach[{}]", TestLifecycleCallbacks.getHash(this));
        Thread.sleep(20);
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        log.info("afterEach[{}]", TestLifecycleCallbacks.getHash(this));
        Thread.sleep(20);
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        log.info("afterAll");
        Thread.sleep(20);
    }

    @Test
    void aTest() throws InterruptedException {
        log.info("aTest[{}]", TestLifecycleCallbacks.getHash(this));
        Thread.sleep(20);
    }

    @Test
    void anotherTest() throws InterruptedException {
        log.info("anotherTest[{}]", TestLifecycleCallbacks.getHash(this));
        Thread.sleep(20);
    }

    @Nested
    class Inner {
        @BeforeEach
        void innerBeforeEach() throws InterruptedException {
            INNER.info("innerBeforeEach[{}]", TestLifecycleCallbacks.getHash(this));
            Thread.sleep(20);
        }

        @Test
        void innerTest() throws InterruptedException {
            INNER.info("innerTest[{}]", TestLifecycleCallbacks.getHash(this));
            Thread.sleep(20);
        }

        @AfterEach
        void innerAfterEach() throws InterruptedException {
            INNER.info("innerAfterEach[{}]", TestLifecycleCallbacks.getHash(this));
            Thread.sleep(20);
        }

        @Nested
        class MostInner {
            @BeforeEach
            void mostInnerBeforeEach() throws InterruptedException {
                MOST.info("mostInnerBeforeEach[{}]", TestLifecycleCallbacks.getHash(this));
                Thread.sleep(20);
            }

            @Test
            void mostInnerTest() throws InterruptedException {
                MOST.info("mostInnerTest[{}]", TestLifecycleCallbacks.getHash(this));
                Thread.sleep(20);
            }

            @AfterEach
            void mostInnerAfterEach() throws InterruptedException {
                MOST.info("mostInnerAfterEach[{}]", TestLifecycleCallbacks.getHash(this));
                Thread.sleep(20);
            }
        }
    }
}
