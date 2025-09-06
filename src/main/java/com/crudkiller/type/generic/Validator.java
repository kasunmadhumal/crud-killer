package com.crudkiller.type.generic;

import com.crudkiller.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Validator {
    private final List<Supplier<String>> validationErrorSuppliers = new ArrayList<>();

    public static Validator of() {
        return new Validator();
    }

    public Validator addValidationError(Supplier<String> validationErrorSupplier) {
        if (validationErrorSupplier != null) {
            validationErrorSuppliers.add(validationErrorSupplier);
        }
        return this;
    }

    public Validator addValidationError(Supplier<Boolean> validation, Supplier<String> errorSupplier) {
        validationErrorSuppliers.add(() -> validation.get() ? errorSupplier.get() : "");
        return this;
    }

    public Validator validateNotNull(Object object, String fieldName) {
        if (CommonUtil.isEmpty(object)) {
            validationErrorSuppliers.add(() -> fieldName + " required");
        }
        return this;
    }

    public Validator validateStringField(String field, String fieldName) {
        if (CommonUtil.isTrimmedEmpty(field)) {
            validationErrorSuppliers.add(() -> fieldName + " required");
        }
        return this;
    }

    public Validator validatePositive(Number value, String fieldName) {
        if (value == null || value.doubleValue() < 0) {
            validationErrorSuppliers.add(() -> fieldName + " should be positive");
        }
        return this;
    }

    public void validate() {
        String errorMessage = validationErrorSuppliers.stream()
                .map(Supplier::get)
                .filter(CommonUtil::isNotEmpty)
                .collect(Collectors.joining(", "));
        if (CommonUtil.isNotEmpty(errorMessage)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}