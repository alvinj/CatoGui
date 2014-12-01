
<#-- you don't need to account for optional fields here; see the resulting, generated code -->
<#macro getFieldsForConstructor the_fields>
    <#list the_fields as f>
        ${f.camelCaseFieldName}<#if f_has_next>,</#if>
    </#list>
</#macro>

<#macro accountForDateField f>
    <#if f.databaseFieldType == "timestamp">
        ${f.camelCaseFieldName}.asInstanceOf[java.sql.Date]
    <#else>
        ${f.camelCaseFieldName}
    </#if>
</#macro>

    /**
     * -----------------
     *    CODE STARTS
     * -----------------
     */
    // TODO you may need to adjust these field types; see the documentation below.
    val ${objectname}Form: Form[${classname}] = Form(
        mapping(
        <#list fields as field>
          <#if field.isRequired()>
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
    /**
     * ---------------
     *    CODE ENDS
     * ---------------
     */

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


