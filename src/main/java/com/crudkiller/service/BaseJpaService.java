package com.crudkiller.service;

import com.crudkiller.exception.NoDataFoundException;
import com.crudkiller.jpa.entity.BaseEntity;
import com.crudkiller.jpa.repository.BaseRepository;
import com.crudkiller.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class BaseJpaService<E extends BaseEntity<I>, I>
        implements BaseJpaServiceContract<E, I> {

    private final BaseRepository<E, I> repository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<E> findAll(Specification<E> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Optional<E> findOne(Specification<E> specification) {
        return repository.findOne(specification);
    }

    @Override
    public Optional<E> findById(I id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        entity.setId(null);
        preCreateProcess(entity);
        E savedEntity = repository.save(entity);
        postCreateProcess(entity, savedEntity);
        return savedEntity;
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entityList) {
        return repository.saveAll(entityList);
    }

    @Override
    @Transactional
    public E update(I id, E entity) {
        entity.setId(id);
        Optional<E> existedEntityOptional = repository.findById(id);
        preUpdateProcess(existedEntityOptional, entity);
        existedEntityOptional.ifPresent(this::detachEntity);
        E savedEntity = repository.save(entity);
        postUpdateProcess(existedEntityOptional, savedEntity);
        return savedEntity;
    }

    @Override
    @Transactional
    public void deleteById(I id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public E deleteOrThrow(I id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.deleteById(id);
                    return entity;
                })
                .orElseThrow(() -> new NoDataFoundException("No entity found with id: " + id));
    }

    protected void preCreateProcess(E requestEntity) {
        validate(requestEntity);
    }

    protected void postCreateProcess(E requestEntity, E savedEntity) {
        // Override in subclasses for custom logic
    }

    protected void preUpdateProcess(Optional<E> existingEntity, E requestEntity) {
        validate(requestEntity);
    }

    protected void postUpdateProcess(Optional<E> existedEntity, E savedEntity) {
        // Override in subclasses for custom logic
    }

    protected void validate(E entity) {
        ValidatorUtil.validate(entity);
    }

    public <T> void detachEntity(T entity) {
        if (entity != null) {
            entityManager.detach(entity);
        }
    }
}