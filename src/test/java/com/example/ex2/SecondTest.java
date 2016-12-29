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
package com.example.ex2;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SecondTest {

    private static final Object something = new Object();

    private static class Something {}

    @NotNull
    @Contract(pure = true)
    private String getText() {
        return "2nd test";
    }

    @Nullable
    @Contract(value = " -> null", pure = true)
    private String returnsNull() {
        return null;
    }

    @Test
    void assertingString() {
        assertEquals("2nd test", getText());
    }

    @Test
    void assertVariation() {
        assertEquals("2nd test", getText(), "メッセージは3つめの引数");
        assertEquals("2nd test", getText(), () -> "メッセージは Supplier でも渡せる");

        assertTrue(true, "これは [true]");
        assertFalse(false); // メッセージなし

        assertNotEquals("いろいろなassertion", getText());
        assertNotSame(new Something(), new Something(), () -> "notSame は reference の比較");
        assertSame(something, something);

        assertNotSame(something, new Something(), () -> "型が違うけど比較できる...");

        assertNull(returnsNull());
        assertNotNull(getText());
    }
}
