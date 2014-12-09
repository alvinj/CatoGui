
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

// ===================================================================
// copy this file to your Play 'app/models/${classname}.scala' file.
// ===================================================================

package models

import play.api.db._
import play.api.Play.current
import anorm.SQL
import anorm.SqlQuery

/**
 * TODO if you have a Date field, and it's not shown to the user (dateCreated, dateUpdated), make it optional here.
 */
case class ${classname} (
<#list fields as field>
<#if field.isRequired() >
    var ${field.camelCaseFieldName}: ${field.scalaFieldType}<#if field_has_next>,</#if>
<#else>
    var ${field.camelCaseFieldName}: Option[${field.scalaFieldType}]<#if field_has_next>,</#if>
</#if>
</#list>
)

object ${classname} {

    import play.api.Play.current 
    import play.api.db.DB

    val sqlQuery = SQL("SELECT * FROM ${tablename} ORDER BY id ASC")

    def selectAll(): List[${classname}] = DB.withConnection { implicit connection => 
        sqlQuery().map ( row =>
            ${classname}(
<#list fields as field>
<#if field.isRequired() >
                row[${field.scalaFieldType}]("${field.fieldName}")<#if field_has_next>,</#if>
<#else>
                row[Option[${field.scalaFieldType}]]("${field.fieldName}")<#if field_has_next>,</#if>
</#if>
</#list>
            )
        ).toList
    }

    // INSERT
    def insert(${objectname}: ${classname}): Option[Long] = {
        val id: Option[Long] = DB.withConnection { implicit c =>
            // NOTE: intentionally skipping `id` field in insert "columns" list
            SQL("""
                insert into ${tablename} (${fieldsAsInsertCsvStringWoIdField})
                values (
                <#list fields as field>
                <#if field.camelCaseFieldName != "id" >
                    {${field.camelCaseFieldName}}<#if field_has_next>,</#if>
                </#if>
                </#list>
                )
                """
            )
            .on(
            <#list fields as field>
            <#if field.camelCaseFieldName != "id" >
                  '${field.camelCaseFieldName} -> ${objectname}.${field.camelCaseFieldName}<#if field_has_next>,</#if>
            </#if>
            </#list>
            ).executeInsert()
        }
        id
    }
    
    // DELETE
    def delete(id: Long): Int = {
        DB.withConnection { implicit c =>
            val nRowsDeleted = SQL("DELETE FROM ${tablename} WHERE id = {id}")
                .on('id -> id)
                .executeUpdate()
            nRowsDeleted
        }
    }

    // FIND BY ID
    def findById(id: Long): ${classname} = {
        DB.withConnection { implicit c =>
            val row = SQL("SELECT * FROM ${tablename} WHERE id = {id}")
                         .on('id -> id)
                         .apply
                         .head
            val ${objectname} = ${classname}(
            <#list fields as field>
            <#if field.isRequired() >
                row[${field.scalaFieldType}]("${field.fieldName}")<#if field_has_next>,</#if>
            <#else>
                row[Option[${field.scalaFieldType}]]("${field.fieldName}")<#if field_has_next>,</#if>
            </#if>
            </#list>
            )
            ${objectname}
        }
    }

    // UPDATE
    def update(${objectname}: ${classname}) {
        DB.withConnection { implicit c =>
            SQL("""
                update ${tablename} set
                <#list fields as field>
                <#if field.camelCaseFieldName != "id" >
                    ${field.fieldName} = {${field.camelCaseFieldName}}<#if field_has_next>,</#if>
                </#if>
                </#list>
                where id={id}"""
            ).on(
            <#list fields as field>
            <#if field.camelCaseFieldName != "id" >
                '${field.camelCaseFieldName} -> ${objectname}.${field.camelCaseFieldName},
            </#if>
            </#list>
                'id -> ${objectname}.id
            ).executeUpdate()
        }
    }

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


} // ${classname} object




