package com.crudkiller.jpa.entity;

public interface BaseEntity<I> extends IdProvider<I> {
    void setId(I id);
}