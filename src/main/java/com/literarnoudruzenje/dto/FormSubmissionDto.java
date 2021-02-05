package com.literarnoudruzenje.dto;

import java.io.Serializable;
import java.util.List;

public class FormSubmissionDto implements Serializable {

    private String fieldId;
    private String fieldValue;
    private List<String> fieldValues;

    public FormSubmissionDto() {
        super();
    }

    public FormSubmissionDto(String fieldId, String fieldValue, List<String> fieldValues) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
        this.fieldValues = fieldValues;
    }

    public String getFieldId() { return fieldId; }

    public void setFieldId(String fieldId) { this.fieldId = fieldId; }

    public String getFieldValue() { return fieldValue; }

    public void setFieldValue(String fieldValue) { this.fieldValue = fieldValue; }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
