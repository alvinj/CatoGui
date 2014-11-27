Cato Templates Cheat Sheet
==========================

Cato reads your database, then lets you select a database table,
and then one or more fields from your table. Cato then does some
work to make those fields available to you in a variety of formats.

These "formats" are actually variable names that you can use in your
templates. For instance, if you select a database table named `order_items`,
Cato will make these variables available inside your templates, and
the variables will have the values shown in the second column:

    ${classname}    OrderItem
    ${objectname}   orderItem
    ${tablename}    order_items


FreeMarker
----------

Cato uses the FreeMarker template engine. I think FreeMarker is the most
popular Java template engine around. If you know how to use FreeMarker,
you can use any legal FreeMarker expression in a Cato template.
    

Scalar Variables
----------------

Cato makes the following scalar variable variables available to you:

    ${classname}
    ${objectname}
    ${tablename}
    ${fieldsAsInsertCsvString}
    ${prepStmtAsInsertCsvString}
    ${prepStmtAsUpdateCsvString}


Array Variables
---------------

Cato currently makes on array variable available to you:

    ${fields}

This variable is a list of `Field` objects corresponding to the fields you
selected in the GUI. You can use the `fields` object in loops in
your templates. This is just one example of how you can use them: 

````
<#list fields as field>
<#if field.isRequired() >
    var ${field.fieldName}: ${field.fieldType},
<#else>
    var ${field.fieldName}: Option[${field.fieldType}],
</#if>
</#list>
````

As you can see, a `Field` instance has properties like `fieldName`,
`fieldType`, and so on. Here's the list of field properties you
can use in a `Field` loop in your templates:

````
fieldName              // String,   ex: first_name  (the field name in the db table)
camelCaseFieldName     // String,   ex: firstName
javaFieldType          // String,   ex: String
jsonFieldType          // String,   ex: JsString
phpFieldType           // String,   ex: String
playFieldType          // String,   ex: nonEmptyText
playOptionalFieldType  // String,   ex: optional(nonEmptyText)
scalaFieldType         // String,   ex: String
databaseFieldType      // String,   ex: varchar or text
isRequired             // Boolean,  ex: true
````

The proper way to access the `isRequired` field in a template was shown 
in the previous example, and is repeated here for your convenience:

````
<#if field.isRequired() >
````


Lists and Conditional Expressions
---------------------------------

The previous examples show how to loop over the list of fields, and also
show how to use conditional expressions (if/then statements). The example
is repeated here for your convenience:

````
<#list fields as field>
<#if field.isRequired() >
    var ${field.fieldName}: ${field.fieldType},
<#else>
    var ${field.fieldName}: Option[${field.fieldType}],
</#if>
</#list>
````


Template Comments
-----------------

Template comments begin with `<#--` and end with `-->`, as shown here: 

````
<#-- TODO get the "if/else" syntax right -->
````

(Cato uses the FreeMarker template engine, and that's how comments are
defined with that template engine.)










