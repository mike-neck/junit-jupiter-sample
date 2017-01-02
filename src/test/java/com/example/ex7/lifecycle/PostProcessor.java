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

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PostProcessor implements TestInstancePostProcessor {

    private static final Predicate<Method> annotatedWithPostConstruct = m -> m.isAnnotationPresent(PostConstruct.class);

    @NotNull
    private static Consumer<Method> invokePostConstruct(@NotNull Object o) {
        return m -> {
            try {
                m.setAccessible(true);
                m.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                final String name = m.getName();
                throw new IllegalStateException(name + " method cannot be invoked.", e);
            }
        };
    }

    @Override
    public void postProcessTestInstance(Object test, ExtensionContext context) throws Exception {
        Stream.of(test.getClass().getDeclaredMethods())
                .filter(annotatedWithPostConstruct)
                .findAny()
                .ifPresent(invokePostConstruct(test));
    }
}
