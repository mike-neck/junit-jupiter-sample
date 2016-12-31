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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class TodoStatusChanged extends TodoChange {

    @ManyToOne(optional = false)
    @JoinColumn(name = "old_status", referencedColumnName = "id", nullable = false, updatable = false)
    private Status oldStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "new_status", referencedColumnName = "id", nullable = false, updatable = false)
    private Status newStatus;

    public TodoStatusChanged(
            @NotNull Status oldStatus
            , @NotNull Status newStatus
    ) {
        Objects.requireNonNull(oldStatus);
        Objects.requireNonNull(newStatus);

        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.type = TodoChangeType.STATUS_CHANGED;
    }
}
