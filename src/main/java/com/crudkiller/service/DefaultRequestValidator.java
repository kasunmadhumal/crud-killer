package com.crudkiller.service;

import com.crudkiller.type.generic.Validator;

public class DefaultRequestValidator<Req> implements RequestValidator<Req> {
    @Override
    public void validate(Req request) {
        Validator.of().validateNotNull(request, "Request").validate();
    }
}