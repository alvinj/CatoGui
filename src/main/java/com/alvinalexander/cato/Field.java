package com.alvinalexander.cato;

public class Field {

    String fieldName;
    String fieldType;
    boolean isRequired;

    public Field (String fieldName, String fieldType, boolean isRequired) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isRequired = isRequired;
    }
    
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }
    
}
