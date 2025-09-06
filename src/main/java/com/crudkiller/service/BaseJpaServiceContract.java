package com.crudkiller.service;

import com.crudkiller.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BaseJpaServiceContract<E extends BaseEntity<I>, I> {
    List<E> findAll(Specification<E> specification);
    Optional<E> findOne(Specification<E> specification);
    Optional<E> findById(I id);
    E save(E entity);
    List<E> saveAll(List<E> entityList);
    E update(I id, E entity);
    void deleteById(I id);
    E deleteOrThrow(I id);
}