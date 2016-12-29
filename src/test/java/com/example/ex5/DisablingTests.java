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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("除外するテストの例")
public class DisablingTests {

    @Test
    @Disabled
    @DisplayName("動かさないテスト")
    void thisTestWillFail() {
        fail("動かさないテスト");
    }

    @Test
    @DisplayName("動かすテスト")
    void thisTestCanBeRunnable() {
        assertEquals(1, 1);
    }

    @Nested
    @DisplayName("動かすインナークラス")
    class WorkingInner {

        @Test
        @Disabled
        @DisplayName("動かさないテスト")
        void notWorking() {
            fail("not working now.");
        }

        @Test
        @DisplayName("動かすテスト")
        void working() {
            assertTrue(true);
        }
    }

    @Nested
    @Disabled
    @DisplayName("動かさないインナークラス")
    class NotWorking {

        @Test
        @Disabled
        @DisplayName("動かさないテスト")
        void notWorking() {
            fail("not working now.");
        }

        @Test
        @DisplayName("動かすテスト")
        void working() {
            assertTrue(true);
        }
    }
}
