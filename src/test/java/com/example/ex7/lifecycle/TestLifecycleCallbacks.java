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

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

@Slf4j
public class TestLifecycleCallbacks implements
        BeforeAllCallback
        , BeforeEachCallback
        , BeforeTestExecutionCallback
        , AfterTestExecutionCallback
        , AfterEachCallback
        , AfterAllCallback
        , TestInstancePostProcessor
{

    @NotNull
    private static String getTestInstanceHash(@NotNull TestExtensionContext context) {
        final Object obj = context.getTestInstance();
        return getHash(obj);
    }

    @NotNull
    static String getHash(Object obj) {
        return Integer.toHexString(obj.hashCode());
    }

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        log.info("=== beforeAll ===");
        Thread.sleep(5);
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        final String hash = getTestInstanceHash(context);
        log.info("=== beforeEach[{}] ===", hash);
        Thread.sleep(5);
    }

    @Override
    public void beforeTestExecution(TestExtensionContext context) throws Exception {
        log.info("=== beforeTestExecution[{}] ===", getTestInstanceHash(context));
        Thread.sleep(5);
    }

    @Override
    public void afterTestExecution(TestExtensionContext context) throws Exception {
        log.info("=== afterTestExecution[{}] ===", getTestInstanceHash(context));
        Thread.sleep(5);
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        log.info("=== afterEach[{}] ===", getTestInstanceHash(context));
        Thread.sleep(5);
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        log.info("=== afterAll ===");
        Thread.sleep(5);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        log.info("=== postProcessTestInstance[{}] ===", getHash(testInstance));
        Thread.sleep(5);
    }
}
