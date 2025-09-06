package com.crudkiller.service;

public interface RequestValidator<Req> {
    void validate(Req request);
}