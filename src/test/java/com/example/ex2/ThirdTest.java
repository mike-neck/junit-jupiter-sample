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
package com.example.ex2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@Slf4j
public class ThirdTest {

    @NotNull
    @Contract(" -> !null")
    private static int[] intArray() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    }

    @Data
    static class Identity {
        final int id;
    }

    @NotNull
    @Contract(value = " -> !null", pure = true)
    private static Identity[] identities() {
        int len = 3;
        Identity[] ids = new Identity[len];
        for (int i = 0; i < len; i++) {
            ids[i] = new Identity(i);
        }
        return ids;
    }

    private static List<Identity> identityList() {
        return IntStream.iterate(0, i -> i + 1).limit(4).
                mapToObj(Identity::new)
                .collect(toList());
    }

    @Test
    void assertionForCollections() {
        assertArrayEquals(new int[]{1,2,3,4,5,6,7,8}, intArray(), () -> "配列のテスト");
        assertArrayEquals(new Identity[] {
                new Identity(0)
                , new Identity(1)
                , new Identity(2)
        }, identities());
        assertIterableEquals(Arrays.asList(
                new Identity(0)
                , new Identity(1)
                , new Identity(2)
                , new Identity(3)
        ), identityList());
    }

    @Test
    void assertionForCollectionsThatFails() {
        assertArrayEquals(new int[]{1,2,4,4,8,8,8,8}, intArray(), () -> "絶対に落ちる");
    }

    
}
