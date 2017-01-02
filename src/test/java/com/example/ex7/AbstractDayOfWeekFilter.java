/*
 * Copyright 2017 Shinya Mochida
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

import com.example.ex7.filter.RunOn;
import com.example.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public abstract class AbstractDayOfWeekFilter {

    private static final Function<DayOfWeek, String> displayDayOfWeek = d -> d.getDisplayName(TextStyle.SHORT, Locale.getDefault());

    @NotNull
    @Contract("null -> fail")
    protected final ConditionEvaluationResult evalDayOfWeek(
            @NotNull ExtensionContext context
    ) {
        Objects.requireNonNull(context);

        final Pair<List<DayOfWeek>, DayOfWeek> pair = context.getElement()
                .map(a -> a.getAnnotation(RunOn.class))
                .map(r -> new Pair<>(Arrays.asList(r.dayOfWeek()), ZoneId.of(r.zoneId())))
                .orElse(new Pair<>(Arrays.asList(DayOfWeek.values()), ZoneId.of("UTC")))
                .map(LocalDate::now)
                .map(LocalDate::getDayOfWeek);

        if (pair.getLeft().contains(pair.getRight())) {
            return ConditionEvaluationResult.enabled(reason(context, pair));
        } else {
            return ConditionEvaluationResult.disabled(reason(context, pair));
        }
    }

    @NotNull
    @Contract("null,_->fail;_,null->fail")
    private static String reason(
            @NotNull ExtensionContext context
            , @NotNull Pair<List<DayOfWeek>, DayOfWeek> pair
    ) {
        return String.format(
                "The test %s can run on [%s](today %s)."
                , context.getDisplayName()
                , pair.getLeft().stream()
                        .map(displayDayOfWeek)
                        .collect(joining(", "))
                , displayDayOfWeek.apply(pair.getRight())
        );
    }
}
