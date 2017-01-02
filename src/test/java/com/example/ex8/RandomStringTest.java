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

import com.example.ex8.annotation.InputString;
import com.example.ex8.annotation.InputStringType;
import com.example.ex8.resolver.InputStringResolver;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith({InputStringResolver.class})
public class RandomStringTest {

    @BeforeAll
    static void beforeAll(@InputString(value = InputStringType.STATIC, staticValue = "beforeAll") String input) {
        log.info("Before All(static, \"beforeAll\") -> {}", input);
    }

    @BeforeEach
    void beforeEach(@InputString(value = InputStringType.RANDOM) String random) {
        log.info("Before Each(random) -> {}", random);
    }

    @Test
    @DisplayName("randomの値を希望")
    void expectRandom(@InputString(InputStringType.RANDOM) String random) {
        assertNotNull(random);
        log.info("randomの値を希望 -> {}", random);
    }

    @Test
    @DisplayName("staticな値を希望")
    void expectStaticValue(@InputString(value = InputStringType.STATIC, staticValue = "staticな値を希望") String str) {
        assertEquals("staticな値を希望", str);
        log.info("staticな値を希望 -> {}", str);
    }

    @Test
    @DisplayName("複数のパラメーターを希望")
    void expectMultipleValue(
            @InputString(InputStringType.RANDOM)
                    String param1
            , @InputString(value = InputStringType.STATIC, staticValue = "static string")
                    String param2
            , @InputString(InputStringType.RANDOM)
                    String param3
            , @InputString(value = InputStringType.STATIC, staticValue = "static string")
                    String param4
    ) {
        assertNotEquals(param1, param3);
        assertEquals(param2, param4);
        log.info("param1: {}, param2: {}, param3: {}, param4: {}"
                , param1
                , param2
                , param3
                , param4
        );
    }

    @Nested
    @DisplayName("ネストしたクラスにも適用される")
    class NestedClass {

        @Test
        @DisplayName("staticな値を希望")
        void expectingStaticValue(@InputString(value = InputStringType.STATIC, staticValue = "static") String str) {
            assertEquals("static", str);
            log.info("ネストしたクラスにも適用される : {}", str);
        }
    }

    @SuppressWarnings("unused")
    @Test
    @DisplayName("指定してないパラメーターはエラー")
    void unSpecified(String unSpecified) {
    }
}
