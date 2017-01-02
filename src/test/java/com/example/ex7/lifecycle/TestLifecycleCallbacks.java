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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ContainerExecutionCondition;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestExecutionCondition;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.util.Objects;

@Slf4j
public class TestLifecycleCallbacks implements
        BeforeAllCallback
        , BeforeEachCallback
        , BeforeTestExecutionCallback
        , AfterTestExecutionCallback
        , AfterEachCallback
        , AfterAllCallback
        , TestInstancePostProcessor
        , ContainerExecutionCondition
        , TestExecutionCondition
        , ParameterResolver
        , TestExecutionExceptionHandler
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

    @Override
    public ConditionEvaluationResult evaluate(ContainerExtensionContext context) {
        log.info("=== evaluate container condition ===");
        return ConditionEvaluationResult.enabled("No problem.");
    }

    @Override
    public ConditionEvaluationResult evaluate(TestExtensionContext context) {
        log.info("=== evaluate test condition [{}] ===", getHash(context.getTestInstance()));
        return ConditionEvaluationResult.enabled("No problem");
    }

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String hash = getHashOfInstanceFromParameterContext(parameterContext);
        log.info("=== supports[{}] ===", hash);
        final Class<?> type = parameterContext.getParameter().getType();
        return int.class.equals(type) | Integer.class.equals(type) | Integer.TYPE.equals(type);
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String hash = getHashOfInstanceFromParameterContext(parameterContext);
        log.info("=== resolve[{}] ===", hash);
        return 0;
    }

    @NotNull
    @Contract("null->fail")
    private static String getHashOfInstanceFromParameterContext(
            @NotNull ParameterContext parameterContext
    ) {
        final ParameterContext ctx = Objects.requireNonNull(parameterContext);
        return ctx.getTarget()
                .map(TestLifecycleCallbacks::getHash)
                .orElse("<empty>");
    }

    @Override
    public void handleTestExecutionException(TestExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable instanceof IgnorableException) {
            log.info("=== handleTestExecutionException[{}] ===", getHash(context.getTestInstance()));
        } else {
            throw Objects.requireNonNull(throwable);
        }
    }
}
