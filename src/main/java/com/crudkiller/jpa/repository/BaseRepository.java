package com.crudkiller.jpa.repository;

import com.crudkiller.jpa.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity<Id>, Id>
        extends JpaRepository<Entity, Id>, JpaSpecificationExecutor<Entity> {
}