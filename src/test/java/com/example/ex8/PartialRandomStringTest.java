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
package com.example.ex8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("部分的にExtensionを適用する")
public class PartialRandomStringTest {

    @Test
    @DisplayName("Extensionを用いない普通のテスト")
    void notApplyExtension() {
    }

    @Test
    @DisplayName("Extensionを適用して引数を取る")
    @ExtendWith({InputStringResolver.class})
    void applyExtension(@InputString(value = InputStringType.STATIC, staticValue = "apply") String str) {
        assertEquals("apply", str);
    }
}
