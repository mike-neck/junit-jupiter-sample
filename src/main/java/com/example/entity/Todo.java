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

import com.example.entity.listener.EventTable;
import com.example.entity.listener.EventTableListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
@EntityListeners({EventTableListener.class})
public class Todo implements Serializable
        , EventTable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    @Setter(onParam = @__({@NotNull}))
    private Date created;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "history")
    private List<TodoHistory> history;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reported_by", referencedColumnName = "id", nullable = false, updatable = false)
    private Account reportedBy;

    @ManyToOne
    @JoinColumn(name = "assign_to", referencedColumnName = "id")
    private Account assignTo;

    public Todo(
            @NotNull String title
            , @NotNull String description
            , @NotNull Account reportedBy
    ) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(reportedBy);

        this.title = title;
        this.description = description;
        final Todo self = this;
        this.history = new ArrayList<TodoHistory>() {{
            add(new TodoHistory(self, reportedBy));
        }};
        this.reportedBy = reportedBy;
    }
}
