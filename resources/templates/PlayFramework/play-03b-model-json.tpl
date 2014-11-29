
<#macro printJsonField the_field>
    <#if the_field.databaseFieldType == "timestamp">
               "${the_field.camelCaseFieldName}" ->  JsString(sdf.format((${objectname}.${the_field.camelCaseFieldName})))
    <#else>
               "${the_field.camelCaseFieldName}" ->   ${the_field.jsonFieldType}(${objectname}.${the_field.camelCaseFieldName})
    </#if>
</#macro>

<#macro printJsonFieldAsOption the_field>
    <#if the_field.databaseFieldType == "timestamp">
               "${the_field.camelCaseFieldName}" -> JsString(sdf.format(${the_field.jsonFieldType}(${objectname}.${the_field.camelCaseFieldName}.getOrElse("")))
    <#else>
               "${the_field.camelCaseFieldName}" ->  ${the_field.jsonFieldType}(${objectname}.${the_field.camelCaseFieldName}.getOrElse(""))
    </#if>
</#macro>  

    // copy this code to the `object` in your 
    // app/models/${classname}.scala file
    // ======================================

    /**
     * JSON Serializer Code
     * --------------------
     */
    import play.api.libs.json.Json
    import play.api.libs.json._
    import java.text.SimpleDateFormat
    
    implicit object ${classname}Format extends Format[${classname}] {
    
        <#-- 'compress' helps get rid of the newlines returned by the macros -->
        // convert from ${classname} object to JSON (serializing to JSON)
        def writes(${objectname}: ${classname}): JsValue = {
            val sdf = new SimpleDateFormat("yyyy-MM-dd")
            val ${objectname}Seq = Seq(
    <#list fields as field>
        <#if field.isRequired() >
               <#compress><@printJsonField the_field=field/></#compress><#if field_has_next>,</#if>
        <#else>
               <#compress><@printJsonFieldAsOption the_field=field/></#compress><#if field_has_next>,</#if>
        </#if>
    </#list>
            )
            JsObject(${objectname}Seq)
        }
    
        // convert from a JSON string to a ${classname} object (de-serializing from JSON)
        // @see http://www.playframework.com/documentation/2.2.x/ScalaJson regarding Option
        // DATE fields should be like: val datetime = (json \ "datetime").as[java.util.Date]
        def reads(json: JsValue): JsResult[${classname}] = {
    <#list fields as field>
    <#if field.isRequired() >
            val ${field.camelCaseFieldName} = (json \ "${field.camelCaseFieldName}").as[${field.scalaFieldType}] 
    <#else>
            val ${field.camelCaseFieldName} = (json \   "${field.camelCaseFieldName}").asOpt[${field.scalaFieldType}] 
    </#if>
    </#list>
            JsSuccess(${classname}(${fieldsAsCamelCaseCsvString}))
        }
    }

// NOTES
// valid types are JsString, JsNumber, JsBoolean, JsArray, JsNull, JsObject
// SEE http://www.playframework.com/documentation/2.2.x/ScalaJson



 
    
    