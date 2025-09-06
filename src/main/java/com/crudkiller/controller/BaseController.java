package com.crudkiller.controller;

import com.crudkiller.jpa.entity.BaseEntity;
import com.crudkiller.service.BaseServiceContract;
import com.crudkiller.type.api.Response;
import com.crudkiller.type.api.SearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public abstract class BaseController<Req, Res, E extends BaseEntity<I>, I> {

    private final BaseServiceContract<Req, Res, E, I> service;

    @GetMapping
    public ResponseEntity<Response<Res>> findAll(
            @ModelAttribute Req searchRequest,
            @ModelAttribute SearchFilter searchFilter) {
        Response<Res> response = service.findAll(searchRequest, searchFilter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Res>> findById(@PathVariable I id) {
        Response<Res> response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<Res>> create(@Valid @RequestBody Req request) {
        Response<Res> response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<Response<Res>> createBatch(@Valid @RequestBody Collection<Req> requests) {
        Response<Res> response = service.create(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Res>> update(@PathVariable I id, @Valid @RequestBody Req request) {
        Response<Res> response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<Res>> partialUpdate(
            @PathVariable I id,
            @Valid @RequestBody Req request,
            @RequestParam Set<String> fields) {
        Response<Res> response = service.partialUpdate(id, request, fields);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}