package com.alvinalexander.cato;

public class Field {

    String fieldName;
    String camelCaseFieldName;
    String fieldType;
    String databaseFieldType;
    boolean isRequired;

    public Field (
        String fieldName, 
        String camelCaseFieldName, 
        String fieldType, 
        String databaseFieldType, 
        boolean isRequired
    ) {
        this.fieldName = fieldName;
        this.camelCaseFieldName = camelCaseFieldName;
        this.fieldType = fieldType;
        this.databaseFieldType = databaseFieldType;
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
    
    public String getCamelCaseFieldName() {
        return camelCaseFieldName;
    }

    public void setCamelCaseFieldName(String camelCaseFieldName) {
        this.camelCaseFieldName = camelCaseFieldName;
    }

    public String getDatabaseFieldType() {
        return databaseFieldType;
    }

    public void setDatabaseFieldType(String databaseFieldType) {
        this.databaseFieldType = databaseFieldType;
    }

}
