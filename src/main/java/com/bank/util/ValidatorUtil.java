package com.bank.util;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public final class ValidatorUtil {

    private ValidatorUtil() {
        // Empty
    }

    public static String createValidationMessage(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
