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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("トップ")
public class NestedClasses {

    @Test
    @DisplayName("トップのテスト")
    void test() {
        log.info("トップのテスト");
    }

    @Nested
    @DisplayName("ミドル")
    class MiddleInner {

        @Test
        @DisplayName("ミドルにあるテスト")
        void test() {
            log.info("ミドルにあるテスト");
        }

        @Nested
        @DisplayName("ボトム")
        class MostInner {

            @Test
            @DisplayName("ボトムのテスト")
            void test() {
                log.info("ボトムのテスト");
            }
        }
    }
}
