package com.crudkiller.service;

import com.crudkiller.exception.NoDataFoundException;
import com.crudkiller.jpa.entity.BaseEntity;
import com.crudkiller.jpa.type.PredicationResolver;
import com.crudkiller.type.api.Response;
import com.crudkiller.type.api.SearchFilter;
import com.crudkiller.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public abstract class BaseService<Req, Res, E extends BaseEntity<I>, I>
        implements BaseServiceContract<Req, Res, E, I> {

    private final BaseJpaServiceContract<E, I> jpaService;
    private final BaseMapper<Req, Res, E, I> mapper;
    private final PredicationResolver<Req, E> predicationResolver;
    private final RequestValidator<Req> requestValidator;

    protected BaseService(BaseJpaServiceContract<E, I> jpaService,
                          BaseMapper<Req, Res, E, I> mapper,
                          PredicationResolver<Req, E> predicationResolver) {
        this.jpaService = jpaService;
        this.mapper = mapper;
        this.predicationResolver = predicationResolver;
        this.requestValidator = new DefaultRequestValidator<>();
    }

    @Override
    public Response<Res> findAll(Req req, SearchFilter searchFilter) {
        Specification<E> specification = predicationResolver.specification(req, searchFilter);
        return findByCustomQuery(specification, mapper::byEntity);
    }

    @Override
    public <CustomRes> Response<CustomRes> findByCustomQuery(
            Specification<E> specification,
            MapperContract<CustomRes, E> mapperContract) {
        List<E> resultEntities = jpaService.findAll(specification);
        List<CustomRes> responseList = mapperContract.convert(resultEntities);
        return new Response<>(responseList);
    }

    @Override
    public Response<Res> findOne(Req req, SearchFilter searchFilter) {
        Specification<E> specification = predicationResolver.specification(req, searchFilter);
        return findOne(specification, mapper::byEntity);
    }

    @Override
    public <CustomRes> Response<CustomRes> findOne(Specification<E> specification,
                                                   MapperContract<CustomRes, E> mapperContract) {
        E resultEntity = jpaService.findOne(specification).orElseThrow(() -> NoDataFoundException.by("No data found"));
        CustomRes response = mapperContract.convert(resultEntity);
        return new Response<>(response);
    }

    @Override
    public Response<Res> findById(I id) {
        E entityById = jpaService.findById(id)
                .orElseThrow(() -> NoDataFoundException.by("No data found for id: " + id));
        Res response = mapper.byEntity(entityById);
        return new Response<>(response);
    }

    @Override
    public Response<Res> create(Req request) {
        validateRequest(request);
        preCreateProcess(request);
        E entity = mapper.byRequest(request);
        E savedEntity = jpaService.save(entity);
        Res resultResponse = mapper.byEntity(savedEntity);
        postCreateProcess(request, savedEntity, resultResponse);
        return new Response<>(resultResponse);
    }

    @Override
    public Response<Res> create(Collection<Req> requests) {
        preCreateProcess(requests);
        List<E> entityList = mapper.byRequests(requests);
        List<E> savedEntityList = jpaService.saveAll(entityList);
        postCreateProcess(requests, savedEntityList);
        List<Res> responseList = mapper.byEntities(savedEntityList);
        return new Response<>(responseList);
    }

    @Override
    public Response<Res> update(I id, Req request) {
        validateRequest(request);
        preUpdateProcess(id, request);
        E entity = mapper.byRequest(request);
        E savedEntity = jpaService.update(id, entity);
        Res response = mapper.byEntity(savedEntity);
        postUpdateProcess(id, request, response);
        return new Response<>(response);
    }

    @Override
    public void deleteById(I id) {
        jpaService.deleteById(id);
    }

    @Override
    public E deleteOrThrowById(I id) {
        return jpaService.deleteOrThrow(id);
    }

    @Override
    public Response<Res> partialUpdate(I id, Req request, Set<String> fieldsToUpdate) {
        validateRequest(request);
        E existingEntity = jpaService.findById(id)
                .orElseThrow(() -> NoDataFoundException.by("No data found for id: " + id));
        E requestEntity = mapper.byRequest(request);
        CommonUtil.copyNonNullProperties(requestEntity, existingEntity, fieldsToUpdate);
        E updatedEntity = jpaService.update(id, existingEntity);
        Res response = mapper.byEntity(updatedEntity);
        return new Response<>(response);
    }

    protected void preCreateProcess(Req request) {}
    protected void postCreateProcess(Req request, E entity, Res resultResponse) {}
    protected void preCreateProcess(Collection<Req> requests) {}
    protected void postCreateProcess(Collection<Req> requests, List<E> entities) {}
    protected void preUpdateProcess(I id, Req request) {}
    protected void postUpdateProcess(I id, Req request, Res resultResponse) {}

    protected void validateRequest(Req request) {
        requestValidator.validate(request);
    }
}