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

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("ParameterResolverがない場合は、TestInfoのみ渡すことができる")
public class MethodParameter {

    private static final String BR = "]";

    private static final String NL = System.lineSeparator();

    @BeforeAll
    static void beforeAll(TestInfo info) {
        log.info(showInfo("TestInfo(before all)", info));
    }

    @BeforeEach
    void setup(TestInfo info) {
        log.info(showInfo("TestInfo(before each)", info));
    }

    @Test
    void test(TestInfo info) {
        assertNotNull(info);
        log.info(showInfo("TestInfo(test)", info));
    }

    @Test
    @DisplayName("info check")
    void infoCheck(TestInfo info) {
        assertEquals("info check", info.getDisplayName());
        log.info(showInfo("TestInfo(infoCheck)", info));
    }

    @Test
    @Tag("foo")
    @Tag("bar")
    @Tag("baz")
    void manyTags(TestInfo info) {
        assertEquals(3, info.getTags().size());
        log.info(showInfo("TestInfo(manyTags)", info));
    }

    @NotNull
    private static String showInfo(@NotNull final String head, @NotNull TestInfo info) {
        final Appender ap = new Appender(head);
        return ap.append("class", MethodParameter.<Class<?>>showOption(Class::getSimpleName).apply(info.getTestClass()))
                .append("method", showOption(Method::getName).apply(info.getTestMethod()))
                .append("tag", joinCollection(identity()).apply(info.getTags()))
                .append("display-name", info.getDisplayName())
                .get();
    }

    static class Appender {
        final StringBuilder sb;

        Appender(String head) {
            this.sb = new StringBuilder("====").append(head).append("====").append(NL);
        }

        Appender append(String name, String contents) {
            sb.append("  ").append(String.format("%12s", name)).append(" [")
                    .append(contents)
                    .append(BR).append(NL);
            return this;
        }

        String get() {
            return sb.toString();
        }
    }

    @NotNull
    private static <T> Function<Collection<T>, String> joinCollection(@NotNull Function<T, String> fun) {
        final Function<? super T, String> f = Objects.requireNonNull(fun);
        return t -> t.stream().map(f).collect(joining(", "));
    }

    @NotNull
    private static <T> Function<Optional<T>, String> showOption(@NotNull Function<? super T, String> fun) {
        final Function<? super T, String> f = Objects.requireNonNull(fun);
        return o -> o.map(f).orElse("<empty>");
    }
}
