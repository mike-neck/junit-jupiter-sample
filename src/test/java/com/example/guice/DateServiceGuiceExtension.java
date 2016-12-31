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

import com.example.guice.annotation.Date;
import com.example.guice.annotation.Lang;
import com.example.guice.annotation.Time;
import com.example.guice.annotation.Zone;
import com.example.guice.api.DateService;
import com.example.guice.impl.DateServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class DateServiceGuiceExtension extends GuiceExtension {

    @NotNull
    @Contract("null -> fail")
    private static <T extends Annotation> Function<AnnotatedElement, T> annotation(
            @NotNull Class<T> type
    ) {
        Objects.requireNonNull(type);
        return ae -> ae.getAnnotation(type);
    }

    @NotNull
    @Contract("null -> fail")
    private static String getDefaultValue(
            @NotNull Class<? extends Annotation> klass
    ) {
        Objects.requireNonNull(klass);
        try {
            final Method value = klass.getMethod("value");
            return (String) value.getDefaultValue();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(
                    String.format("Cannot retrieve default value from annotation[%s].", klass.getCanonicalName()), e);
        }
    }

    @NotNull
    @Contract("null,_,_ -> fail;_,null,_ -> fail; _,_,null -> fail")
    private static <T extends Annotation> String retrieveConfig(
            @NotNull Class<T> klass
            , @NotNull Function<? super T, String> retrieve
            , @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @NotNull Optional<AnnotatedElement> annotations
    ) {
        Objects.requireNonNull(klass);
        Objects.requireNonNull(retrieve);
        Objects.requireNonNull(annotations);

        return annotations.map(annotation(klass))
                .map(retrieve)
                .orElse(getDefaultValue(klass));
    }

    @Override
    protected Module prepareModule(
            @NotNull Class<?> testClass
            , @NotNull Set<String> tags
            , @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @NotNull 
                    Optional<AnnotatedElement> annotations
    ) {
        Objects.requireNonNull(testClass);
        Objects.requireNonNull(tags);
        Objects.requireNonNull(annotations);

        return new DateServiceTestModule(
                retrieveConfig(Date.Format.class, Date.Format::value, annotations)
                , retrieveConfig(Time.Format.class, Time.Format::value, annotations)
                , retrieveConfig(Zone.class, Zone::value, annotations)
                , retrieveConfig(Lang.class, Lang::value, annotations)
        );
    }

    static class DateServiceTestModule extends AbstractModule {
        final String date;
        final String time;
        final String zone;
        final Locale lang;

        DateServiceTestModule(
                @NotNull String date
                , @NotNull String time
                , @NotNull String zone
                , @NotNull String lang
        ) {
            this.date = Objects.requireNonNull(date);
            this.time = Objects.requireNonNull(time);
            this.zone = Objects.requireNonNull(zone);
            this.lang = Optional.of(Objects.requireNonNull(lang))
                    .filter(notEmpty)
                    .map(Locale::new)
                    .orElseGet(Locale::getDefault);
        }

        static final Predicate<String> notEmpty = s -> !s.isEmpty();

        @Override
        protected void configure() {
            bind(DateTimeFormatter.class)
                    .annotatedWith(Date.class)
                    .toProvider(formatterProvider(date));
            bind(DateTimeFormatter.class)
                    .annotatedWith(Time.class)
                    .toProvider(formatterProvider(time));
            bind(ZoneId.class)
                    .toProvider(() -> ZoneId.of(zone));
            bind(Locale.class).toProvider(() -> lang);
            bind(DateService.class)
                    .to(DateServiceImpl.class);
        }
    }

    @NotNull
    @Contract("null -> fail")
    private static Provider<DateTimeFormatter> formatterProvider(@NotNull String format) {
        Objects.requireNonNull(format);
        return () -> DateTimeFormatter.ofPattern(format);
    }
}
