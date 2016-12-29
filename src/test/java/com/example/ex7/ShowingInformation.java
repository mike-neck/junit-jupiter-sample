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
package com.example.ex7;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ContextInformation.class)
@DisplayName("Context を 表示")
@Tag("tag for class")
public class ShowingInformation {

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void test() {
    }

    @Test
    @DisplayName("名前付き")
    void named() {
    }

    @Test
    @DisplayName("例外あり")
    void exception() {
        throw new RuntimeException("test");
    }

    @Test
    @DisplayName("例外あり(無視)")
    @ExtendWith({IgnoredExceptionHandler.class})
    void ignoredException() throws IgnoredException {
        throw new IgnoredException("この例外は無視");
    }

    @Test
    @Tag("tag for method")
    void tagged() {
    }

    @AfterEach
    void afterEach() {
    }

    @AfterAll
    static void afterAll() {
    }
}
