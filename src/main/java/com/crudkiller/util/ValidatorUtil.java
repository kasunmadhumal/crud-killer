package com.crudkiller.util;

import com.crudkiller.exception.GeneralValidationException;
import org.springframework.util.CollectionUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public final class ValidatorUtil {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private ValidatorUtil() {
        // Utility class
    }

    public static <E> void validate(E object) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> validations = validator.validate(object);
        if (!CollectionUtils.isEmpty(validations)) {
            String errorMessage = validations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw GeneralValidationException.by(errorMessage);
        }
    }
}