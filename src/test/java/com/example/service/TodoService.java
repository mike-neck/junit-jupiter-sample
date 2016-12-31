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
package com.example.service;

import com.example.entity.Account;
import com.example.entity.Todo;
import com.example.util.Pair;
import com.google.inject.persist.Transactional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class TodoService {

    private final EntityManager em;

    @Inject
    public TodoService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Todo createNewIssue(
            @NotNull String title
            , @NotNull String description
            , @NotNull Account reportedBy
    ) {
        final Todo todo = new Todo(title, description, reportedBy);
        em.persist(todo);
        return todo;
    }

    @Transactional
    public Optional<Todo> findTodoById(Long id) {
        final Todo todo = em.find(Todo.class, id);
        return Optional.ofNullable(todo);
    }

    @Transactional
    public Todo changeTitle(
            @NotNull Todo todo
            , @NotNull String newTitle
            , @NotNull Account changedBy
    ) {
        Objects.requireNonNull(todo);
        Objects.requireNonNull(newTitle);
        Objects.requireNonNull(changedBy);

        final Optional<Todo> opt = findTodoById(todo.getId());
        return opt.map(Pair.createPair(Todo.changeTitle(newTitle, changedBy)))
                .map(Pair.consumePair(Todo::addHistory))
                .map(em::merge)
                .orElseThrow(entityNotFound(todo));
    }

    @NotNull
    @Contract("null,_,_ -> fail;_,null,_ -> fail;_,_,null -> fail")
    @Transactional
    public Todo descriptionChange(
            @NotNull Todo todo
            , @NotNull String newDescription
            , @NotNull Account changedBy
    ) {
        Objects.requireNonNull(todo);
        Objects.requireNonNull(newDescription);
        Objects.requireNonNull(changedBy);

        return findTodoById(todo.getId())
                .map(Pair.createPair(Todo.changeDescription(newDescription, changedBy)))
                .map(Pair.consumePair(Todo::addHistory))
                .map(em::merge)
                .orElseThrow(entityNotFound(todo));
    }

    @NotNull
    @Contract("null -> fail")
    private static Supplier<EntityNotFoundException> entityNotFound(
            @NotNull Todo todo
    ) {
        Objects.requireNonNull(todo);

        return () -> new EntityNotFoundException(
                String.format("Todo cannot be found in the system. [id : %d]", todo.getId())
        );
    }
}
