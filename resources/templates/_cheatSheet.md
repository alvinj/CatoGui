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

To help you take advantage of FreeMarker in your templates, here are some good
FreeMarker links:

* [Expressions](http://freemarker.org/docs/dgui_template_exp.html)
* [List](http://freemarker.org/docs/ref_directive_list.html)
* [List methods](http://freemarker.org/docs/ref_builtins_sequence.html)
* [FreeMarker string functions](http://freemarker.org/docs/ref_builtins_string.html)
* [Strings, numbers, dates, booleans ...](http://freemarker.org/docs/ref_builtins.html)
* [Macros](http://freemarker.org/docs/dgui_misc_userdefdir.html)
* [Define your own variables](http://freemarker.org/docs/dgui_misc_var.html)
    

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

Regarding lists, it can help to know that you can manage "commas" at the end
of expressions with this code:

    <#if field_has_next>,</#if>

For instance, when writing a constructor method for a JavaBean class, you can use that
if/else construct to control commas as shown at the end of the third line in this
code snippet:

      public ${classname} (
      <#list fields as field>
        ${field.javaFieldType} ${field.camelCaseFieldName}<#if field_has_next>,</#if>
      </#list>
      ) {
      <#list fields as field>
          this.${field.camelCaseFieldName} = ${field.camelCaseFieldName};
      </#list>
      }


Creating Your Own Template Variables
------------------------------------

You can create your own variables inside templates. See this link for the basics:

* [Define your own variables](http://freemarker.org/docs/dgui_misc_var.html)

More than that, you can create your own variables in one "include" file, and then
use those variables in many templates. This technique is shown in the Sencha 
templates. In each template you'll find this import statement:

````
<#import "/lib/includes.fm" as my>
````

and then later you'll see that I use this variable:

````
${my.applicationName}
````

That variable comes from the file named _includes.fm_, which is located in the
_lib_ folder under the main _Sencha_ folder. This is consistent with the 
FreeMarker instructions for imports, which are found at this URL:

* [FreeMarker template loading](http://freemarker.org/docs/pgui_config_templateloading.html)

The ability to define your own variables and use them across templates is very
powerful. You can use them for the following purposes:

* adding `package` names to Scala, Java, and other files
* adding `import` statements to Scala, Java, etc.
* defining an "Application Name", which I do in the Sencha templates

Of course you can use this technique for anything you want/need when it
comes to defining variable names that you can use in one or more 
template files. As shown on the FreeMarker page, you can also define and
use macros in the same way.


Template Comments
-----------------

Template comments begin with `<#--` and end with `-->`, as shown here: 

````
<#-- TODO get the "if/else" syntax right -->
````

(Cato uses the FreeMarker template engine, and that's how comments are
defined with that template engine.)










