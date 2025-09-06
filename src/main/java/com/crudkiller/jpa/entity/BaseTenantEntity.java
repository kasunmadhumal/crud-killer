package com.crudkiller.jpa.entity;

public interface BaseTenantEntity<Entity, TenantId> extends BaseEntity<Entity>, TenantIdProvider<TenantId> {
}