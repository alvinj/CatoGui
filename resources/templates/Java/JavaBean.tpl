
public class ${classname} {

<#list fields as field>
  ${field.javaFieldType} ${field.camelCaseFieldName};
</#list>

  public ${classname} (
  <#list fields as field>
    ${field.javaFieldType} ${field.camelCaseFieldName}<#if field_has_next>,</#if>
  </#list>
  ) {
  <#list fields as field>
      this.${field.camelCaseFieldName} = ${field.camelCaseFieldName};
  </#list>
  }

<#list fields as field>
  public void set${field.camelCaseFieldName?capitalize}(${field.javaFieldType} ${field.camelCaseFieldName}) {
    this.${field.camelCaseFieldName} = ${field.camelCaseFieldName};
  }

  public ${field.javaFieldType} get${field.camelCaseFieldName?capitalize}() {
    return ${field.camelCaseFieldName};
  }

</#list>

}


