package com.crudkiller.service;

import com.crudkiller.jpa.entity.BaseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BaseMapper<Req, Res, E extends BaseEntity<I>, I> {
    E byRequest(Req req);
    Res byEntity(E entity);

    default List<E> byRequests(Collection<Req> requests) {
        return Optional.ofNullable(requests).orElse(Collections.emptyList()).stream()
                .map(this::byRequest)
                .collect(Collectors.toList());
    }

    default List<Res> byEntities(Collection<E> entities) {
        return Optional.ofNullable(entities).orElse(Collections.emptyList()).stream()
                .map(this::byEntity)
                .collect(Collectors.toList());
    }
}