
class ${classname} (
    <#list fields as field>
    var ${field.camelCaseFieldName}: ${field.scalaFieldType}<#if field_has_next>,</#if>
    </#list>
)
