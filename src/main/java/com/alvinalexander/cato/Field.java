package com.alvinalexander.cato;

// TODO delete this class
@Deprecated
public class Field {

    String fieldName;
    String camelCaseFieldName;
    @Deprecated String fieldType;
    String javaFieldType;
    String jsonFieldType;
    String phpFieldType;
    String playFieldType;
    String playOptionalFieldType;
    String scalaFieldType;
    String databaseFieldType;
    boolean isRequired;

    public Field (
        String fieldName, 
        String camelCaseFieldName, 
        String fieldType,
        String javaFieldType,
        String jsonFieldType,
        String phpFieldType,
        String playFieldType,
        String playOptionalFieldType,
        String scalaFieldType,
        String databaseFieldType, 
        boolean isRequired
    ) {
        this.fieldName = fieldName;
        this.camelCaseFieldName = camelCaseFieldName;
        this.fieldType = fieldType;
        this.javaFieldType = javaFieldType;
        this.jsonFieldType = jsonFieldType;
        this.phpFieldType = phpFieldType;
        this.playFieldType = playFieldType;
        this.playOptionalFieldType = playOptionalFieldType; 
        this.scalaFieldType = scalaFieldType;
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
