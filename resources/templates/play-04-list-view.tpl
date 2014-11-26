
//-----------------------------------------------------
// NAME THIS FILE views/${objectname}/list.scala.html
//-----------------------------------------------------

@(${objectname}s: List[${classname}], ${objectname}Form: Form[${classname}])

@import helper._

@* helper for table headers *@
@header(orderBy: Int, title: String) = {
  <th class="col@orderBy header">
    <a href="#">@title</a>
  </th>
}

@main("${classname}s") {
  
  <h1>There are @${objectname}s.size ${classname}(s)</h1>
  
  @* emit the <table> *@
  @Option(${objectname}s).filterNot(_.isEmpty).map { ${objectname} =>
    
    <table class="computers zebra-striped" cellpadding="2">

      <thead>
        <tr>
<#list fields as field>
          <!-- TODO this may not be right -->
          @header(${field_index + 1}, "${field.camelCaseFieldName}")
</#list>
        </tr>
      </thead>

      <tbody>

        @${objectname}s.map { ${objectname} =>
           <tr>
              @* TODO - DELETE EXTRA "ID" COLUMN *@
             <td><a href="@routes.${classname}s.edit(${objectname}.id)">@${objectname}.id</a></td>

<#list fields as field>
<#if field.isRequired() >
             <td>@${objectname}.${field.camelCaseFieldName}</td>
<#else>
             <td>@${objectname}.${field.camelCaseFieldName}.getOrElse { <em>-</em> }</td>
</#if>
</#list>
             <td>
               <a href="@routes.${classname}s.delete(${objectname}.id)" onclick="return confirm('Really delete?');">Delete</a>
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
  <a href="@routes.${classname}s.add">add a new ${classname}</a>
  </p>
  
  
}

