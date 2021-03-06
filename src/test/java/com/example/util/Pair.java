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
package com.example.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public final class Pair<L, R> {

    private final L left;
    private final R right;

    @NotNull
    public Pair<R, L> reverse() {
        return new Pair<R, L>(right, left);
    }

    @NotNull
    @Contract("null -> fail")
    public <S> Pair<L, S> map(
            @NotNull Function<? super R, ? extends S> f
    ) {
        Objects.requireNonNull(f);
        return new Pair<>(left, f.apply(right));
    }

    @NotNull
    @Contract("null -> fail")
    public <F> F transform(
            @NotNull BiFunction<? super L, ? super R, ? extends F> trans
    ) {
        Objects.requireNonNull(trans);
        return trans.apply(left, right);
    }

    @NotNull
    @Contract("null -> fail")
    public <S> Pair<L, S> bimap(
            @NotNull BiFunction<? super L, ? super R, ? extends S> mapper
    ) {
        Objects.requireNonNull(mapper);
        return new Pair<>(left, mapper.apply(left, right));
    }

    @NotNull
    @Contract("null -> fail")
    public <S> Pair<Pair<L, R>, S> contextBimap(
            @NotNull BiFunction<? super L, ? super R, ? extends S> mapper
    ) {
        Objects.requireNonNull(mapper);

        return new Pair<>(this, mapper.apply(left, right));
    }

    @NotNull
    @Contract("null->fail")
    public L accept(
            @NotNull BiConsumer<? super L, ? super R> action
    ) {
        Objects.requireNonNull(action);
        action.accept(left, right);
        return left;
    }

    @Contract("null->fail")
    public void consume(
            @NotNull BiConsumer<? super L, ? super R> action
    ) {
        Objects.requireNonNull(action);
        action.accept(left, right);
    }

    @NotNull
    @Contract("null->fail")
    public static <T> Predicate<Pair<T, T>> bothFilterPair(
            @NotNull Predicate<? super T> predicate
    ) {
        Objects.requireNonNull(predicate);
        return p -> predicate.test(p.left) && predicate.test(p.right);
    }

    @NotNull
    @Contract(pure = true)
    public static Predicate<Pair<Boolean, Boolean>> bothFilterPair() {
        return p -> p.left && p.right;
    }

    @NotNull
    @Contract("null,_->fail;_,null->fail")
    public static <L, R> Predicate<Pair<L, R>> bothFilterPair(
            @NotNull Predicate<L> forLeft
            , @NotNull Predicate<R> forRight
    ) {
        Objects.requireNonNull(forLeft);
        Objects.requireNonNull(forRight);

        return p -> forLeft.test(p.left) && forRight.test(p.right);
    }

    @NotNull
    @Contract("null->fail")
    public static <T> Predicate<Pair<T, T>> orFilterPair(
            @NotNull Predicate<? super T> predicate
    ) {
        Objects.requireNonNull(predicate);
        return p -> predicate.test(p.left) || predicate.test(p.right);
    }

    @NotNull
    @Contract(pure = true)
    public static Predicate<Pair<Boolean, Boolean>> orFilterPair() {
        return p -> p.left || p.right;
    }

    @NotNull
    @Contract("null,_->fail;_,null->fail")
    public static <L, R> Predicate<Pair<L, R>> orFilterPair(
            @NotNull Predicate<L> forLeft
            , @NotNull Predicate<R> forRight
    ) {
        Objects.requireNonNull(forLeft);
        Objects.requireNonNull(forRight);

        return p -> forLeft.test(p.left) || forRight.test(p.right);
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R> Function<L, Pair<L, R>> createPair(
            @NotNull Function<? super L, ? extends R> mapper
    ) {
        Objects.requireNonNull(mapper);
        return l -> new Pair<L, R>(l, mapper.apply(l));
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R, S> Function<Pair<L, R>, Pair<L, S>> mapPair(
            @NotNull Function<? super R, ? extends S> mapper
    ) {
        Objects.requireNonNull(mapper);
        return p -> p.map(mapper);
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R, F> Function<Pair<L, R>, F> transformPair(
            @NotNull BiFunction<? super L, ? super R, ? extends F> trans
    ) {
        Objects.requireNonNull(trans);
        return p -> p.transform(trans);
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R, S> Function<Pair<L, R>, Pair<L, S>> bimapPair(
            @ NotNull BiFunction<? super L, ? super R, ? extends S> mapepr
    ) {
        Objects.requireNonNull(mapepr);
        return p -> p.bimap(mapepr);
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R, S> Function<Pair<L, R>, Pair<Pair<L, R>, S>> contextBimapPair(
            @NotNull BiFunction<? super L, ? super R, ? extends S> mapper
    ) {
        Objects.requireNonNull(mapper);

        return p -> p.contextBimap(mapper);
    }

    @NotNull
    @Contract("null -> fail")
    public static <L, R> Function<Pair<L, R>, L> acceptPair(
            @NotNull BiConsumer<L, R> action
    ) {
        Objects.requireNonNull(action);
        return p -> p.accept(action);
    }

    @NotNull
    @Contract("null->fail")
    public static <L, R> Consumer<Pair<L, R>> consumePair(
            @NotNull BiConsumer<? super L, ? super R> action
    ) {
        Objects.requireNonNull(action);
        return p -> p.consume(action);
    }
}
