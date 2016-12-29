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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("タグをつけたテスト")
public class TaggedTests {

    @Test
    @Tag("one")
    @DisplayName("1st というタグを付けたテスト(タグはIDEで実行する上では特に意味がない)")
    void test1st() {
        log.info("one");
    }

    @Test
    @Tag("two")
    @DisplayName("two という タグ")
    void test2nd() {
        log.info("two");
    }

    @Test
    @Tags({
            @Tag("one")
            , @Tag("two")
            , @Tag("three")
    })
    @DisplayName("Tagsでタグたくさん")
    void test3rd() {
        log.info("one");
        log.info("two");
        log.info("three");
    }

    @Test
    @Tag("one")
    @Tag("three")
    @Tag("this is four")
    @DisplayName("Tagsの中にではなく、Tagをたくさん")
    void test4th() {
        log.info("one");
        log.info("three");
        log.info("this is four");
    }

    @Test
    @Tag("two")
    @Tag("three")
    @Tag("this is five")
    @DisplayName("Tagsの中にではなく、Tagをたくさん")
    void test5th() {
        log.info("two");
        log.info("three");
        log.info("this is five");
    }
}
