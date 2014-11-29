
// ===================================================================
// copy this file to your Play 'app/models/${classname}.scala' file.
// ===================================================================

package models

import play.api.db._
import play.api.Play.current
import anorm.SQL
import anorm.SqlQuery

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

} //object

