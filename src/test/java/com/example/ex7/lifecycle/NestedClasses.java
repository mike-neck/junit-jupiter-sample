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
package com.example.ex7.lifecycle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.example.ex7.lifecycle.TestLifecycleCallbacks.formatName;
import static com.example.ex7.lifecycle.TestLifecycleCallbacks.getHash;
import static com.example.ex7.lifecycle.TestLifecycleCallbacks.getLog;

@ExtendWith({TestLifecycleCallbacks.class})
public class NestedClasses {

    public NestedClasses() {
        getLog().info("          {}[{}]", formatName("NestedClasses"), getHash(this));
    }

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

    @Test
    void aTest(String method, @SuppressWarnings("unused") int param) throws InterruptedException {
        getLog().info("          {}[{}]", method, getHash(this));
        Thread.sleep(20);
    }

    @Test
    void anotherTest(String method) throws InterruptedException {
        getLog().info("          {}[{}]", method, getHash(this));
        Thread.sleep(20);
    }

    @Test
    void throwingTest() throws IgnorableException {
        getLog().info("          {}[{}]", formatName("throwingTest"), getHash(this));
        try {
            Thread.sleep(20);
            throw new IgnorableException();
        } catch (InterruptedException e) {
            throw new IgnorableException(e);
        }
    }

    @Nested
    class Inner {
        Inner() {
            getLog().info("          {}[{}]", formatName("Inner"), getHash(this));
        }

        @BeforeEach
        void innerBeforeEach() throws InterruptedException {
            getLog().info("          {}[{}]", formatName("innerBeforeEach"), getHash(this));
            Thread.sleep(20);
        }

        @Test
        void innerTest(String method, @SuppressWarnings("unused") int param) throws InterruptedException {
            getLog().info("          {}[{}]", method, getHash(this));
            Thread.sleep(20);
        }

        @AfterEach
        void innerAfterEach() throws InterruptedException {
            getLog().info("          {}[{}]", formatName("innerAfterEach"), getHash(this));
            Thread.sleep(20);
        }

        @Nested
        class MostInner {
            MostInner() {
                getLog().info("          {}[{}]", formatName("MostInner"), getHash(this));
            }

            @BeforeEach
            void mostInnerBeforeEach() throws InterruptedException {
                getLog().info("          {}[{}]", formatName("mostInnerBeforeEach"), getHash(this));
                Thread.sleep(20);
            }

            @Test
            void mostInnerTest(String method, @SuppressWarnings("unused") int param) throws InterruptedException {
                getLog().info("          {}[{}]", method, getHash(this));
                Thread.sleep(20);
            }

            @AfterEach
            void mostInnerAfterEach() throws InterruptedException {
                getLog().info("          {}[{}]", formatName("mostInnerAfterEach"), getHash(this));
                Thread.sleep(20);
            }
        }
    }
}
