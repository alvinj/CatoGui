
case class ${classname} (
    <#list fields as field>
    ${field.camelCaseFieldName}: ${field.scalaFieldType}<#if field_has_next>,</#if>
    </#list>
)
