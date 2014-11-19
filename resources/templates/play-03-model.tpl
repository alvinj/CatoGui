// copy this file to your Play 'app/models/<<$classname>>.scala' file.
// ===================================================================

package models

import play.api.db._
import play.api.Play.current
import anorm.SQL
import anorm.SqlQuery

//-----------------
// 1 THE CASE CLASS
//-----------------

// TODO delete the trailing comma
<#-- TODO get the "if/else" syntax right -->
case class ${classname} (
<#list fields as field>
<#if field.isRequired() >
    var ${field.fieldName}: ${field.fieldType},
<#else>
    var ${field.fieldName}: Option[${field.fieldType}],
</#if>
</#list>

)


object ${classname} {

    val sqlQuery = SQL("SELECT * FROM ${tablename} ORDER BY id ASC")

    //-----------------------------------
    // 2 THE DATABASE 'SELECT ALL' METHOD
    //-----------------------------------
    // TODO - delete trailing comma
    import play.api.Play.current 
    import play.api.db.DB
    def selectAll(): List[${classname}] = DB.withConnection { implicit connection => 
        sqlQuery().map ( row =>
            ${classname}(
<#list fields as field>
<#if field.isRequired() >
                row[${field.fieldType}]("${field.fieldName}"),
<#else>
                row[Option[${field.fieldType}]]("${field.fieldName}"),
</#if>
</#list>
            )
        ).toList
    }

}









