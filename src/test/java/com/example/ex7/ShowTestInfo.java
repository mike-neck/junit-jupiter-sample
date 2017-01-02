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
package com.example.ex7;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@Slf4j
@Tag("test-class-tag")
public class ShowTestInfo {

    private static final String NL = System.lineSeparator();

    private static final String EMPTY = "<empty>";

    @NotNull
    @Contract("null,_ -> fail;_,null -> fail")
    private static <T> String showOpt(
            @NotNull Function<? super T, String> show
            , @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @NotNull Optional<T> opt
    ) {
        Objects.requireNonNull(show);
        Objects.requireNonNull(opt);

        return opt.map(show)
                .orElse(EMPTY);
    }

    @NotNull
    @Contract(value = "null,_,_,_ -> fail;_,null,_,_->fail;_,_,null,_->fail;_,_,_,null->fail", pure = true)
    private static StringBuilder append(
            @NotNull StringBuilder sb
            , @NotNull String exec
            , @NotNull String name
            , @NotNull String contents
    ) {
        Objects.requireNonNull(sb);
        Objects.requireNonNull(exec);
        Objects.requireNonNull(name);
        Objects.requireNonNull(contents);

        final StringBuilder b = new StringBuilder();
        b.append(exec);
        for (int i = 0; i < 12 - exec.length(); i++) {
            b.append(' ');
        }

        return sb.append(b.toString()).append(name).append("[").append(contents).append("]").append(NL);
    }

    @Contract("null,_->fail;_,null->fail")
    private static void showInfo(
            @NotNull String method
            , @NotNull TestInfo info
    ) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(info);

        final StringBuilder sb = new StringBuilder(method).append(NL);
        final StringBuilder s1 = append(sb, method, "class        ", showOpt(Class::getSimpleName, info.getTestClass()));
        final StringBuilder s2 = append(s1, method, "method       ", showOpt(Method::getName, info.getTestMethod()));
        final StringBuilder s3 = append(s2, method, "display name ", info.getDisplayName());
        final StringBuilder s4 = append(s3, method, "tags         ", info.getTags().stream().collect(joining(", ")));
        log.info(s4.toString());
    }

    @Contract("null -> fail")
    @BeforeAll
    static void beforeAll(TestInfo info) {
        showInfo("beforeAll", info);
    }

    @BeforeEach
    void beforeEach(TestInfo info) {
        showInfo("beforeEach", info);
    }

    @Test
    @Tag("test-tag")
    @Tag("sample")
    void test(TestInfo info) {
        showInfo("test", info);
    }

    @AfterEach
    void afterEach(TestInfo info) {
        showInfo("afterEach", info);
    }

    @Contract("null -> fail")
    @AfterAll
    static void afterAll(TestInfo info) {
        showInfo("afterAll", info);
    }

    @Nested
    @Tag("inner")
    class Inner {

        @BeforeEach
        void innerBefore(TestInfo info) {
            showInfo("innerBefore", info);
        }

        @Test
        @Tag("sample")
        void innerTest(TestInfo info) {
            showInfo("innerTest", info);
        }

        @AfterEach
        void innerAfter(TestInfo info) {
            showInfo("innerAfter", info);
        }
    }
}
