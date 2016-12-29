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
import org.jetbrains.annotations.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DisplayName("表示名を変えるテスト")
public class DisplayNameTest {

    private static final int ONE = 1;

    @Contract(pure = true)
    private static int asInt() {
        return ONE;
    }

    @Contract(pure = true)
    private static double asDouble() {
        return ONE;
    }

    @Test
    @DisplayName("intの値をチェックする")
    void checkingInt() {
        assertEquals(1, asInt());
    } 

    @Test
    @DisplayName("doubleの値をチェックする")
    void checkingDouble() {
        assertEquals(65535/65536.0, asDouble(), 1/128.0);
    }

    @Test
    @DisplayName("このテストでは以下の観点でテストを行う\n\n" +
            "* 文字列が同じであること\n" +
            "* 文字列の長さが同じであること\n")
    void multipleLineDisplayName() {
        String japan = "JAPAN";
        assertAll(
                () -> assertEquals("japan", japan.toLowerCase())
                , () -> assertEquals(5, japan.length())
        );
    }
}
