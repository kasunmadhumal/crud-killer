package com.crudkiller.service;

import com.crudkiller.jpa.entity.BaseEntity;
import com.crudkiller.type.api.Response;
import com.crudkiller.type.api.SearchFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Set;

public interface BaseServiceContract<Req, Res, E extends BaseEntity<I>, I> {
    Response<Res> findAll(Req req, SearchFilter searchFilter);
    <CustomRes> Response<CustomRes> findByCustomQuery(Specification<E> specification, MapperContract<CustomRes, E> mapperContract);
    Response<Res> findOne(Req req, SearchFilter searchFilter);
    <CustomRes> Response<CustomRes> findOne(Specification<E> specification, MapperContract<CustomRes, E> mapperContract);
    Response<Res> findById(I id);
    Response<Res> create(Req request);
    Response<Res> create(Collection<Req> request);
    Response<Res> update(I id, Req request);
    void deleteById(I id);
    E deleteOrThrowById(I id);
    Response<Res> partialUpdate(I id, Req request, Set<String> fieldsToUpdate);
}