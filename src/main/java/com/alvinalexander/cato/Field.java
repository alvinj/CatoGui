package com.alvinalexander.cato;

/**
 * This class is deprecated because I'd like to move it to Scala, but I haven't been
 * able to get the Scala version to play well with FreeMarker (or vice-versa).
 */
@Deprecated
public class Field {

    String fieldName;
    String fieldNameAsLabel;
    String camelCaseFieldName;
    String javaFieldType;
    String jsonFieldType;
    String phpFieldType;
    String playFieldType;
    String playOptionalFieldType;
    String scalaFieldType;
    String senchaFieldType;
    String databaseFieldType;
    boolean isRequired;

    public Field (
        String fieldName, 
        String fieldNameAsLabel,
        String camelCaseFieldName, 
        String javaFieldType,
        String jsonFieldType,
        String phpFieldType,
        String playFieldType,
        String playOptionalFieldType,
        String scalaFieldType,
        String senchaFieldType,
        String databaseFieldType, 
        boolean isRequired
    ) {
        this.fieldName = fieldName;
        this.fieldNameAsLabel = fieldNameAsLabel;
        this.camelCaseFieldName = camelCaseFieldName;
        this.javaFieldType = javaFieldType;
        this.jsonFieldType = jsonFieldType;
        this.phpFieldType = phpFieldType;
        this.playFieldType = playFieldType;
        this.playOptionalFieldType = playOptionalFieldType; 
        this.scalaFieldType = scalaFieldType;
        this.senchaFieldType = senchaFieldType;
        this.databaseFieldType = databaseFieldType;
        this.isRequired = isRequired;
    }
    
    public String getFieldName() {
        return fieldName;
    }

    public String getFieldNameAsLabel() {
        return fieldNameAsLabel;
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

    public String getSenchaFieldType() {
      return senchaFieldType;
    }

}
