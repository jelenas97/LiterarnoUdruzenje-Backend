package com.literarnoudruzenje.exceptions;

public class FormFieldInputException extends RuntimeException {
    private String message;

    public FormFieldInputException(String message) {
        super(message);
    }
}
