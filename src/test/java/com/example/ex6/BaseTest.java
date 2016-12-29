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
package com.example.ex6;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public interface BaseTest<T> {

    Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @NotNull
    Optional<T> generateNonEmpty();

    @NotNull
    Optional<T> generateEmpty();

    @Test
    @DisplayName("generateNonEmptyから返されるオブジェクトがemptyでないことを確認")
    default void nonEmptyShouldReturnNonEmptyValue() {
        assertTrue(generateNonEmpty().isPresent());
    }

    @Test
    @DisplayName("generateEmptyから返されるオブジェクトがemptyであることを確認")
    default void emptyShouldReturnEmptyValue() {
        assertFalse(generateEmpty().isPresent());
    }

    void notImpl();

    @Test
    void notImplAnnotated();

    void notImplAnnotateAtImpl();

    @Disabled
    void notImplDisabledAtInterface();

    @Test
    default void implOverrideWithoutAnnotation() {
        LOG.info("default実装内のテスト");
    }

    @Test
    default void implOverrideWithAnnotation() {
        LOG.info("default実装内のテスト");
    }

    @Test
    @Disabled
    default void implDisabled() {
        LOG.info("@Disabledが付与されているので実行されない");
        fail("@Disabledが付与されているので実行されない");
    }

    @Test
    @Disabled
    default void implDisabledAndOverridden() {
        fail("ここの部分は@Disabledが付与されているので実行されない");
    }

    @Slf4j
    class ImplTest implements BaseTest<String> {

        @NotNull
        @Override
        public Optional<String> generateNonEmpty() {
            return Optional.of("foo");
        }

        @NotNull
        @Override
        public Optional<String> generateEmpty() {
            return Optional.empty();
        }

        // @Test を付与された default メソッドは実行される

        @Override
        public void notImpl() {
            log.info("もちろん、これは実行されない");
        }

        @Override
        public void notImplAnnotated() {
            log.info("これも実行されない");
        }

        @Test
        @Override
        @DisplayName("interfaceに@Testがなくても実装クラスに@Testがあれば実行される")
        public void notImplAnnotateAtImpl() {
            log.info("interfaceに@Testがなくても実装クラスに@Testがあれば実行される");
        }

        @Test
        @Override
        @DisplayName("interfaceに@Disabledがあっても実装クラスに@Disabledがなければ実行される")
        public void notImplDisabledAtInterface() {
            log.info("interfaceに@Disabledがあっても実装クラスに@Disabledがなければ実行される");
        }

        @Override
        public void implOverrideWithoutAnnotation() {
            log.info("@Testアノテーションを付与しないと実行されない");
        }

        @Test
        @Override
        public void implOverrideWithAnnotation() {
            log.info("@Testアノテーションを付与すると実行される");
        }

        @Test
        @Override
        public void implDisabledAndOverridden() {
            log.info("実装で@Disabledが付与されていないので実行される");
        }
    }
}
