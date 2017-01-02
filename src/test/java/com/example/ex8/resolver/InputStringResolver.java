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
package com.example.ex8.resolver;

import com.example.ex8.annotation.InputString;
import com.example.ex8.annotation.InputStringType;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Slf4j
public class InputStringResolver implements ParameterResolver {

    private final Random random = new Random(new Date().getTime());

    @Override
    public boolean supports(ParameterContext param, ExtensionContext ext) throws ParameterResolutionException {
        printInfo(param, ext);
        final Parameter p = param.getParameter();
        return p.getType().equals(String.class) && p.isAnnotationPresent(InputString.class);
    }

    @Override
    public Object resolve(ParameterContext param, ExtensionContext ext) throws ParameterResolutionException {
        final InputString is = param.getParameter().getAnnotation(InputString.class);
        final InputStringType type = is.value();
        if (type == InputStringType.STATIC) {
            return is.staticValue();
        } else if (type == InputStringType.RANDOM) {
            return randomString();
        }
        throw new ParameterResolutionException("Given InputStringType cannot resolve[" + type + "].");
    }

    private String randomString() {
        final int size = random.nextInt(20);
        return IntStream.generate(() -> random.nextInt(Character.MAX_VALUE))
                .limit(size)
                .mapToObj(i -> (char) i)
                .collect(StringBuilder::new, StringBuilder::append, (l, r) -> l.append(r.toString()))
                .toString();
    }

    private static void printInfo(ParameterContext param, ExtensionContext ext) {
        log.info("parameter for method: {} index: {}, target: {}, name: {}, type: {}",
                showOptional(Method::getName).apply(ext.getTestMethod())
                , param.getIndex()
                , showOptional(Object::toString).apply(param.getTarget())
                , param.getParameter().getName()
                , param.getParameter().getParameterizedType().getTypeName()
                , InputStringResolver.<Annotation>showArray(a -> a.annotationType().getSimpleName())
                        .apply(param.getParameter().getAnnotations())
        );
    }

    @NotNull
    private static <T> Function<T[], String> showArray(@NotNull Function<T, String> fun) {
        final Function<T, String> f = Objects.requireNonNull(fun);
        return a -> Stream.of(a).map(f).collect(joining(", "));
    }

    @NotNull
    private static <T> Function<Optional<T>, String> showOptional(@NotNull Function<T, String> fun) {
        final Function<T, String> f = Objects.requireNonNull(fun);
        return o -> o.map(f).orElse("<empty>");
    }
}
