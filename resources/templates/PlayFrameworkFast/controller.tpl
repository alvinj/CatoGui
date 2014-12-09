<#-- you don't need to account for optional fields here; see the resulting, generated code -->
<#macro getFieldsForConstructor the_fields>
    <#list the_fields as f>
        ${f.camelCaseFieldName}<#if f_has_next>,</#if>
    </#list>
</#macro>

<#macro accountForDateField f>
    <#if f.databaseFieldType == "timestamp">
        ${f.camelCaseFieldName}.asInstanceOf[java.util.Date]
    <#else>
        ${f.camelCaseFieldName}
    </#if>
</#macro>

package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import views._
import models._
import java.util.Calendar

object ${classnamePlural} extends Controller {

    /**
     * -----------------
     *    CODE STARTS
     * -----------------
     * Date Handling:
     * -----------------
     * how to handle `dateCreated` or `dateUpdated`:
     *     make it "dateCreated" -> optional(date)" in the mapping
     *     make it "Calendar.getInstance.getTime" in the ${objectname}Form -> ${classname} mapping
     *     make it "Some(user.dateCreated.asInstanceOf[java.util.Date])" in the ${classname} -> ${objectname}Form
     */
    // TODO you may need to adjust these field types; see the documentation below.
    val ${objectname}Form: Form[${classname}] = Form(
        mapping(
        <#list fields as field>
          <#if field.isRequired() && field.camelCaseFieldName != "dateCreated">
            "${field.camelCaseFieldName}" -> ${field.playFieldType}<#if field_has_next>,</#if>
          <#else>
            "${field.camelCaseFieldName}" -> ${field.playOptionalFieldType}<#if field_has_next>,</#if>
          </#if>
        </#list>
        )
        // --------------------------------------------------------
        // (1) convert ${objectname}Form -> ${classname}
        // --------------------------------------------------------
        // NOTE: probably won't need all these fields, such as 'id'
        // NOTE: might want to use `Calendar.getInstance.getTime` to create a Date in your constructor
        // NOTE: might want to use 0 for your `id` field in constructor
        ((${fieldsAsCamelCaseCsvString}) => ${classname}(<@compress single_line=true><@getFieldsForConstructor the_fields=fields/></@compress>))
        // -----------------------------------------------------------
        // (2) convert ${classname} -> ${objectname}Form (form fields must match above)
        // -----------------------------------------------------------
        ((${objectname}: ${classname}) => Some((
            <#list fields as field>
            <#-- don't need 'getOrElse("")' here, as i initially thought -->
                ${objectname}.<@compress single_line=true><@accountForDateField f=field/></@compress><#if field_has_next>,</#if>
            </#list>
        )))
    )

    def list = Action {
        Ok(html.${objectname}.list(${classname}.selectAll, ${objectname}Form))
    }
    
    // (1A) non-async controller method with a Play view
    def add = Action {
        Ok(html.${objectname}.form(${objectname}Form))
    }
  
    // (1B) - handle the submit process of a Play view
    // TODO update won't be implemented yet
    def submit = Action { implicit request =>
        ${objectname}Form.bindFromRequest.fold(
            errors => BadRequest(html.${objectname}.form(errors)),
            ${objectname} => {
                if (${objectname}.id == 0) {
                    val res = ${classname}.insert(${objectname})
                } else {
                    val res = ${classname}.update(${objectname})
                }
                Redirect(routes.${classnamePlural}.list)
          }
        )
    }
    
    def delete(id: Long) = Action {
        ${classname}.delete(id)
        Redirect(routes.${classnamePlural}.list)
    }  
  
    def edit(id: Long) = Action {
        val ${objectname} = ${classname}.findById(id)
        Ok(html.${objectname}.form(${objectname}Form.fill(${objectname})))
    }
  
}





