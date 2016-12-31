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

//import com.example.entity.TodoHistory.TodoHistoryKey;

import com.example.entity.key.TodoHistoryKey;
import com.example.entity.listener.EventTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo_history")
public class TodoHistory implements Serializable
        , EventTable {

    @EmbeddedId
    private TodoHistoryKey key;

    @ManyToOne(optional = false)
    @JoinColumn(name = "changed_by", referencedColumnName = "id")
    private Account changedBy;

    @Column(nullable = false, updatable = false)
    private Date changed;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private TodoChange detail;

    public TodoHistory(Todo todo, Account changedBy) {
        this.key = new TodoHistoryKey(todo, 1);
        this.changedBy = changedBy;
        this.detail = new TodoCreated();
    }

    public TodoHistory(Todo todo, int serial, Account changedBy, TodoChange detail) {
        this.key = new TodoHistoryKey(todo, serial);
        this.changedBy = changedBy;
        this.detail = detail;
    }

    @Override
    public void setCreated(Date created) {
        setChanged(created);
    }
}
