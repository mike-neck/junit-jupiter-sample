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
package com.example.ex3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class ExecutionModel {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        log.info("beforeAll");
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        log.info("afterAll");
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        log.info("beforeEach");
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        log.info("afterEach");
    }

    @Test
    void testFirst() throws InterruptedException {
        log.info("testFirst");
        fail("testFirst");
    }

    @Test
    void testSecond() throws InterruptedException {
        log.info("testSecond");
    }

    @Test
    void testThird() throws InterruptedException {
        log.info("testThird");
    }
}
