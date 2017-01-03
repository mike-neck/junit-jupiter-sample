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
package com.example.guice;

import com.example.util.Pair;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public abstract class GuiceExtension implements
        ParameterResolver
        , BeforeAllCallback
        , AfterAllCallback
{
    private static final Namespace GUICE = Namespace.create(GuiceExtension.class);

    private static Store getStore(ExtensionContext context) {
        return context.getStore(GUICE);
    }

    abstract protected Module prepareModule(
            @NotNull Class<?> testClass
            , @NotNull Set<String> tags
            , @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @NotNull Optional<AnnotatedElement> annotations
    );

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        final Store store = getStore(context);
        final Optional<AnnotatedElement> annotations = context.getElement();
        context.getTestClass()
                .map(Pair.createPair(c -> prepareModule(c, context.getTags(), annotations)))
                .map(Pair.mapPair(Guice::createInjector))
                .ifPresent(p -> store.put(p.getLeft(), p.getRight()));
    }

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Parameter parameter = parameterContext.getParameter();
        final Class<?> paramType = parameter.getType();
        final Stream<? extends Key<?>> keyStream = Arrays.stream(parameter.getAnnotations())
                .map(a -> Key.get(paramType, a));
        return extensionContext.getTestClass()
                .map(c -> getStore(extensionContext).get(c, Injector.class))
                .map(Injector::getAllBindings)
                .map(Pair.createPair(Function.identity()))
                .filter(Pair.orFilterPair(
                        b -> b.containsKey(Key.get(paramType))
                        , b -> keyStream.anyMatch(b::containsKey)
                )).isPresent();
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Parameter parameter = parameterContext.getParameter();
        final Class<?> paramType = parameter.getType();
        final Annotation[] annotations = parameter.getAnnotations();
        return extensionContext.getTestClass()
                .map(c -> getStore(extensionContext).get(c, Injector.class))
                .map(Injector::getAllBindings)
                .map(Pair.createPair(b -> b.get(Key.get(paramType))))
                .map(Pair.mapPair(Optional::ofNullable))
                .map(Pair.mapPair(o -> o.map(Binding::getProvider)))
                .map(Pair.mapPair(o -> o.map(p -> (Object) p.get())))
                .map(Pair.mapPair(o -> o.map(Optional::of)))
                .flatMap(Pair.transformPair((m, o) -> o.orElseGet(findBindingByClassAndAnnotation(paramType, annotations, m))))
                .orElseThrow(() -> new ParameterResolutionException(
                        String.format("System cannot find binding for type: [%s] parameter index: %d"
                                , parameter.getType()
                                , parameterContext.getIndex())
                ));
    }

    @NotNull
    @Contract("null,_,_ -> fail; _,null,_ -> fail;_,_,null -> fail")
    private static Supplier<Optional<Object>> findBindingByClassAndAnnotation(
            @NotNull Class<?> paramType
            , @NotNull Annotation[] annotations
            , @NotNull Map<Key<?>, Binding<?>> bindings
    ) {
        Objects.requireNonNull(paramType);
        Objects.requireNonNull(annotations);
        Objects.requireNonNull(bindings);

        return () -> Arrays.stream(annotations)
                .map(a -> Key.get(paramType, a))
                .map(k -> Optional.ofNullable(bindings.get(k)))
                .filter(Optional::isPresent)
                .findFirst()
                .flatMap(Function.identity())
                .map(Binding::getProvider)
                .map(Provider::get);
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        getStore(context).remove(Injector.class, Injector.class);
    }
}
