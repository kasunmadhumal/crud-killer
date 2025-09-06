package com.crudkiller.jpa.repository;

import com.crudkiller.jpa.entity.BaseTenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseTenantRepository<Entity extends BaseTenantEntity<Id, TenantId>, Id, TenantId>
        extends JpaRepository<Entity, Id>, JpaSpecificationExecutor<Entity> {

    default List<Entity> findAll() {
        throw new RuntimeException("Not implemented - use findAll with specification");
    }
}