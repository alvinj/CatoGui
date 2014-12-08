@*
 * --------------------------------------------------
 * NAME THIS FILE views/${objectname}/list.scala.html
 * -----------------------------------------------------
 *
 *@

@(${objectname}s: List[${classname}], ${objectname}Form: Form[${classname}])

@import helper._

@* helper for table headers *@
@header(orderBy: Int, title: String) = {
  <th class="col@orderBy header">
    <a href="#">@title</a>
  </th>
}

@main("${classnamePlural}") {
  
  <h1>There are @${objectname}s.size ${classname}(s)</h1>
  
  @* emit the <table> *@
  @Option(${objectname}s).filterNot(_.isEmpty).map { ${objectname} =>
    
    <table class="computers zebra-striped" cellpadding="2">

      <thead>
        <tr>
<#list fields as field>
          @header(${field_index + 1}, "${field.fieldNameAsLabel}")
</#list>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody>

        @${objectname}s.map { ${objectname} =>
           <tr>
             <td><a href="@routes.${classnamePlural}.edit(${objectname}.id)">@${objectname}.id</a></td>
<#list fields as field>
<#if field.isRequired() >
    <#if field.camelCaseFieldName != "id" >
             <td>@${objectname}.${field.camelCaseFieldName}</td>
    </#if>
<#else>
    <#if field.camelCaseFieldName != "id" >
             <td>@${objectname}.${field.camelCaseFieldName}.getOrElse { <em>-</em> }</td>
    </#if>
</#if>
</#list>
             <td>
               <a href="@routes.${classnamePlural}.delete(${objectname}.id)" onclick="return confirm('Really delete?');">Delete</a>
             </td>
           </tr>
        }

      </tbody>
    </table>

  }.getOrElse {
    
    <div class="well">
      <em>Nothing to display</em>
    </div>
    
  }

  <p>
  <a href="@routes.${classnamePlural}.add">add a new ${classname}</a>
  </p>
  
  
}

