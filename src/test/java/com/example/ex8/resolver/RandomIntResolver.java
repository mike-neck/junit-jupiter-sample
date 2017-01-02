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

import com.example.ex8.annotation.IntValue;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class RandomIntResolver implements ParameterResolver {

    private static final Random random = new Random(System.currentTimeMillis());

    @Override
    public boolean supports(ParameterContext pcx, ExtensionContext ext) throws ParameterResolutionException {
        final Class<?> type = pcx.getParameter().getType();
        return type.equals(int.class) || type.equals(Integer.class) || type.equals(Integer.TYPE);
    }

    @Override
    public Object resolve(ParameterContext pcx, ExtensionContext ext) throws ParameterResolutionException {
        final Optional<IntValue> opv = Optional.ofNullable(pcx.getParameter().getAnnotation(IntValue.class));
        return build(opv, random::nextInt)
                .select(iv -> iv.select().length > 0).map(iv -> iv.select()[random.nextInt(iv.select().length)])
                .orElse(iv -> iv.min() + generateInt(iv.max(), iv.min()));
    }

    private static int generateInt(int max, int min) {
        final BigInteger x = BigInteger.valueOf((long)max);
        final BigInteger n = BigInteger.valueOf((long) min);
        final int b = x.min(n).add(BigInteger.ONE).bitLength();
        return new BigInteger(b, random).intValue();
    }

    static IntBuilder build(
            @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<IntValue> opv
            , IntSupplier sup) {
        return opv.<IntBuilder>map(Opt::new)
                .orElse(new Int(sup.getAsInt()));
    }

    interface IntBuilder {
        int orElse(ToIntFunction<IntValue> fun);

        IntTransform select(Predicate<IntValue> pred);
    }

    interface IntTransform {
        IntBuilder map(ToIntFunction<IntValue> fun);
    }

    static class Int implements IntBuilder {
        final int value;

        Int(int value) {
            this.value = value;
        }

        @Override
        public int orElse(ToIntFunction<IntValue> fun) {
            return value;
        }

        @Override
        public IntTransform select(Predicate<IntValue> pred) {
            return f -> this;
        }
    }

    static class Opt implements IntBuilder {
        final IntValue iv;

        Opt(IntValue iv) {
            this.iv = iv;
        }

        @Override
        public int orElse(ToIntFunction<IntValue> fun) {
            return fun.applyAsInt(iv);
        }

        @Override
        public IntTransform select(Predicate<IntValue> pred) {
            if (pred.test(iv)) {
                return f -> new Int(f.applyAsInt(iv));
            } else {
                return f -> this;
            }
        }
    }
}
