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
import org.junit.jupiter.api.extension.TestExtensionContext;

@Slf4j
public class TestLifecycleCallbacks implements
        BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback,
        AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        log.info("=== beforeAll ===");
        Thread.sleep(5);
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        log.info("=== beforeEach ===");
        Thread.sleep(5);
    }

    @Override
    public void beforeTestExecution(TestExtensionContext context) throws Exception {
        log.info("=== beforeTestExecution ===");
        Thread.sleep(5);
    }

    @Override
    public void afterTestExecution(TestExtensionContext context) throws Exception {
        log.info("=== afterTestExecution ===");
        Thread.sleep(5);
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        log.info("=== afterEach ===");
        Thread.sleep(5);
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        log.info("=== afterAll ===");
        Thread.sleep(5);
    }
}
