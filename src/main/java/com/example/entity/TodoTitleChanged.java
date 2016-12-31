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
package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class TodoTitleChanged extends TodoChange {

    @Column(nullable = false, updatable = false)
    private String oldTitle;

    @Column(nullable = false, updatable = false)
    private String newTitle;

    TodoTitleChanged(
            @NotNull String oldTitle
            , @NotNull String newTitle
            , @NotNull TodoChangeType type) {
        Objects.requireNonNull(oldTitle);
        Objects.requireNonNull(newTitle);

        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
        this.type = type;
    }

    @NotNull
    public static TodoTitleChanged newInstance(@NotNull String oldTitle, @NotNull String newTitle) {
        Objects.requireNonNull(oldTitle);
        Objects.requireNonNull(newTitle);
        return new TodoTitleChanged(oldTitle, newTitle, TodoChangeType.TITLE_CHANGED);
    }
}
