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
package com.example.ex5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Annotations {

    @Slf4j
    @DisplayName("DisableをBeforeAll/AfterAllにつける")
    static class DisablingBeforeAllMethod {

        @BeforeAll
        @Disabled
        static void beforeAll() {
            log.info("beforeAll");
        }

        @AfterAll
        @Disabled
        static void afterAll() {
            log.info("afterAll");
        }

        @Test
        void test() {
            log.info("test");
        }
    }

    @Slf4j
    @DisplayName("DisableをBeforeEach/AfterEachにつける")
    static class DisablingBeforeEachMethod {

        @BeforeEach
        @Disabled
        void beforeEach() {
            log.info("beforeEach");
        }

        @AfterEach
        @Disabled
        void afterEach() {
            log.info("afterEach");
        }

        @Test
        void test() {
            log.info("test");
        }
    }
}
