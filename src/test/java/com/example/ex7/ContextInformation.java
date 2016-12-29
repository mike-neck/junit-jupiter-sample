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
package com.example.ex7;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExtensionContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collector;

import static java.util.stream.Collectors.joining;

@Slf4j
public class ContextInformation implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        log.info("before all execution\n{}", showContext(context));
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        log.info("before each execution\n{}", showContext(context));
    }

    @Override
    public void beforeTestExecution(TestExtensionContext context) throws Exception {
        log.info("before test execution\n{}", showContext(context));
    }

    @Override
    public void afterTestExecution(TestExtensionContext context) throws Exception {
        log.info("after test execution\n{}", showContext(context));
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        log.info("after each execution\n{}", showContext(context));
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        log.info("after all execution\n{}", showContext(context));
    }

    private static final Collector<CharSequence, ?, String> joinByComma = joining(", ");

    private static String showContext(ContainerExtensionContext ctx) {
        StringBuilder sb = new StringBuilder();
        String nl = System.lineSeparator();
        String empty = "<empty>";
        return sb.append("=== id [").append(ctx.getUniqueId()).append("] ===").append(nl)
                .append("  display name [").append(ctx.getDisplayName()).append("]").append(nl)
                .append("  parent       [").append(ctx.getParent().map(ExtensionContext::getDisplayName).orElse(empty)).append("]").append(nl)
                .append("  test class   [").append(ctx.getTestClass().map(Class::getSimpleName).orElse(empty)).append("]").append(nl)
                .append("  test method  [").append(ctx.getTestMethod().map(Method::getName).orElse(empty)).append("]").append(nl)
                .append("  tags         [").append(ctx.getTags().stream().collect(joinByComma)).append("]").append(nl)
                .append("  element      [").append(ctx.getElement().map(AnnotatedElement::getAnnotations).map(Arrays::stream).map(s -> s.map(Annotation::annotationType).map(Class::getSimpleName).collect(joinByComma)).orElse(empty)).append("]").append(nl)
                .toString();
    }

    private static String showContext(TestExtensionContext ctx) {
        StringBuilder sb = new StringBuilder();
        String nl = System.lineSeparator();
        String empty = "<empty>";
        return sb.append("=== id [").append(ctx.getUniqueId()).append("] ===").append(nl)
                .append("  display name [").append(ctx.getDisplayName()).append("]").append(nl)
                .append("  parent       [").append(ctx.getParent().map(ExtensionContext::getDisplayName).orElse(empty)).append("]").append(nl)
                .append("  instance     [").append(ctx.getTestInstance()).append("]").append(nl)
                .append("  test class   [").append(ctx.getTestClass().map(Class::getSimpleName).orElse(empty)).append("]").append(nl)
                .append("  test method  [").append(ctx.getTestMethod().map(Method::getName).orElse(empty)).append("]").append(nl)
                .append("  tags         [").append(ctx.getTags().stream().collect(joinByComma)).append("]").append(nl)
                .append("  element      [").append(ctx.getElement().map(AnnotatedElement::getAnnotations).map(Arrays::stream).map(s -> s.map(Annotation::annotationType).map(Class::getSimpleName).collect(joinByComma)).orElse(empty)).append("]").append(nl)
                .append("  exception    [").append(ctx.getTestException().map(Object::getClass).map(Class::getSimpleName).orElse(empty)).append("]").append(nl)
                .toString();
    }
}
