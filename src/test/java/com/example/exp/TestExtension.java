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
package com.example.exp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class TestExtension implements 
        BeforeAllCallback
        , AfterAllCallback
{
    private static final Namespace namespace = Namespace.create(TestExtension.class);

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        final Store store = context.getStore(namespace);
        final String time = Optional.ofNullable(store.get(TestExtension.class, LocalDateTime.class))
                .map(format::format)
                .orElse("<empty>");
        final String name = context.getTestClass()
                .map(Class::getSimpleName)
                .orElse("<empty>");
        log.info("{} : beforeAll[{}] {}", Integer.toHexString(hashCode()), name, time);
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        final Store store = context.getStore(namespace);
        store.put(TestExtension.class, LocalDateTime.now());
    }
}
