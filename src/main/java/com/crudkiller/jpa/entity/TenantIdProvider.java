package com.crudkiller.jpa.entity;

public interface TenantIdProvider<T> {
    T getTenantId();
    String getTenantAttribute();
}