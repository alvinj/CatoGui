
<#macro getFieldsForConstructor the_fields>
    <#list the_fields as f>
        <#if f.isRequired()>
            ${f.camelCaseFieldName}<#if f_has_next>,</#if>
        <#else>
            Some(${f.camelCaseFieldName})<#if f_has_next>,</#if>
        </#if>
    </#list>
</#macro>

    // TODO - may need to adjust these field types; see the documentation below.
    // TODO - i should be able to handle "optional" fields here now 
    
    val ${objectname}Form: Form[${classname}] = Form(
        mapping(
        <#list fields as field>
            "${field.camelCaseFieldName}" -> ${field.playFieldType}<#if field_has_next>,</#if>
        </#list>
        )
        // (1) ${objectname}Form -> ${classname}
        // TODO probably won't need all these fields, such as 'id'
        // DATE might want to use `Calendar.getInstance.getTime` to create a Date in your constructor
        // ID might want to use 0 for your `id` field in constructor
        ((${fieldsAsCamelCaseCsvString}) => ${classname}(<@compress single_line=true><@getFieldsForConstructor the_fields=fields/></@compress>))
        //
        // (2) ${classname} -> ${objectname}Form (form fields must match above)
        ((${objectname}: ${classname}) => Some((
        <#list fields as field>
            <#if field.isRequired()>
                ${objectname}.${field.camelCaseFieldName}<#if field_has_next>,</#if>
            <#else>
                ${objectname}.${field.camelCaseFieldName}.getOrElse("")<#if field_has_next>,</#if>
            </#if>
        </#list>
        )))
    )

//
// see this url for play framework form field mapping examples:
// http://alvinalexander.com/scala/sample-play-framework-controller-forms-fields-mappings
//

//--------------------------------------
// OTHER PLAY FRAMEWORK EXAMPLES & TYPES
//--------------------------------------
"date" -> optional(date("yyyy-MM-dd")),   // java.util.Date
"date" -> date("yyyy-MM-dd"),   // java.util.Date
"username" -> nonEmptyText,
"username" -> nonEmptyText(6),       // requires a minimum of six characters
"notes" -> text,
"password" -> text(minLength = 10),
"count" -> number,
"addToMailingList" -> boolean,
"email" -> email,
"company" -> optional(text),
"numberOfShares" -> optional(number),
"stocks" -> list(text),
"emailAddresses" -> list(email),
"id" -> ignored(1234),

boolean
checked
date
email
ignored
list
longNumber
nonEmptyText
number
optional
seq
single
sqlDate
text


