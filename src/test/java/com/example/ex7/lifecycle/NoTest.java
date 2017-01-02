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
package com.example.ex7.lifecycle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.example.ex7.lifecycle.TestLifecycleCallbacks.formatName;
import static com.example.ex7.lifecycle.TestLifecycleCallbacks.getHash;
import static com.example.ex7.lifecycle.TestLifecycleCallbacks.getLog;

@DisplayName("テストがないクラス")
@ExtendWith({ TestLifecycleCallbacks.class })
public class NoTest {

    @BeforeAll
    static void beforeAll() throws InterruptedException {
        getLog().info("          {}", formatName("beforeAll"));
        Thread.sleep(20);
    }

    @BeforeEach
    void beforeEach() throws InterruptedException {
        getLog().info("          {}[{}]", formatName("beforeEach"), getHash(this));
        Thread.sleep(20);
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        getLog().info("          {}[{}]", formatName("afterEach"), getHash(this));
        Thread.sleep(20);
    }

    @AfterAll
    static void afterAll() throws InterruptedException {
        getLog().info("          {}", formatName("afterAll"));
        Thread.sleep(20);
    }

    @Disabled
    @Test
    void disabledTest() {}
}
