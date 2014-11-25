package com.alvinalexander.cato;

// TODO delete this class
@Deprecated
public class Field {

    String fieldName;
    String camelCaseFieldName;
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

    public boolean isRequired() {
        return isRequired;
    }

    public String getCamelCaseFieldName() {
        return camelCaseFieldName;
    }

    public String getDatabaseFieldType() {
        return databaseFieldType;
    }

    public String getJavaFieldType() {
        return javaFieldType;
    }

    public String getJsonFieldType() {
        return jsonFieldType;
    }

    public String getPhpFieldType() {
        return phpFieldType;
    }

    public String getPlayFieldType() {
        return playFieldType;
    }

    public String getPlayOptionalFieldType() {
        return playOptionalFieldType;
    }

    public String getScalaFieldType() {
        return scalaFieldType;
    }

}
