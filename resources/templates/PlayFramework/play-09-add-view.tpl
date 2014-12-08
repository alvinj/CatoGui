@*
 * TODO: Name your file app/views/user/form.scala.html
 *@
@(${objectname}Form: Form[${classname}])

@import helper._
@import helper.twitterBootstrap._

@main("${classnamePlural}") {
  
  @if(${objectname}Form.hasErrors) {
    <div class="alert-message error">
      <p><strong>Bam!</strong> (There were errors)</p>
      <ul>
        @${objectname}Form.errors.map { error =>
          <li>@error</li>
        }
      </ul>
    </div>
  }

  @helper.form(routes.${classnamePlural}.submit) {
    
     <h1>${classname} information</h1>

     @* DATE NOTE: probably don't want date fields visible here, esp. "date created/updated" *@
     @* id = 0 for 'create', otherwise it has the real value *@
     @if(${objectname}Form.data.isEmpty) {
       <input type="hidden" id="id" name="id" value='0'>
     } else {
       <input type="hidden" id="id" name="id" value='@${objectname}Form.get.id'>
     }

     <#list fields as field>
     <#if field.camelCaseFieldName != "id" && field.camelCaseFieldName != "dateCreated" && field.camelCaseFieldName != "dateUpdated" >
     @inputText(
       ${objectname}Form("${field.camelCaseFieldName}"), 
       '_label -> "${field.fieldNameAsLabel}"
     )
     </#if>
     </#list>
     
    <div class="actions">
      <input type="submit" class="btn primary" value="Insert">
      <a href="@routes.${classnamePlural}.list" class="btn">Cancel</a>
    </div>
    
  }
  
}

@*
 * -------
 * HELPERS
 * -------
 *
 * DATE:
 *
 *    @inputDate(
 *      ${objectname}Form("date"), 
 *      '_label -> "Date"
 *    ) 
 *
 *@










