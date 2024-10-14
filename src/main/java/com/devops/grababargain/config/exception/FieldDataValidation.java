package com.devops.grababargain.config.exception;

import org.springframework.validation.FieldError;

public record FieldDataValidation(String field, String message) {
    public FieldDataValidation(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
