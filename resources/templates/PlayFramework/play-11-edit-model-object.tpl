
    // (1) FIND
    def findById(id: Long): ${classname} = {
        DB.withConnection { implicit c =>
            val row = SQL("SELECT * FROM ${tablename} WHERE id = {id}")
                         .on('id -> id)
                         .apply
                         .head
            // TODO `fieldName` in these lines may need to be `camelCaseFieldName`
            val ${objectname} = ${classname}(
            <#list fields as field>
            <#if field.isRequired() >
                row[${field.scalaFieldType}]("${field.fieldName}")<#if field_has_next>,</#if>
            <#else>
                row[Option[${field.scalaFieldType}]]("${field.fieldName}")<#if field_has_next>,</#if>
            </#if>
            </#list>
            )
            ${objectname}
        }
    }

    // (2) UPDATE
    def update(${objectname}: ${classname}) {
        DB.withConnection { implicit c =>
            SQL("""
                update ${tablename} set
                <#list fields as field>
                <#if field.camelCaseFieldName != "id" >
                    ${field.fieldName} = {${field.camelCaseFieldName}}<#if field_has_next>,</#if>
                </#if>
                </#list>
                where id={id}"""
            ).on(
            <#list fields as field>
            <#if field.camelCaseFieldName != "id" >
                '${field.camelCaseFieldName} -> ${objectname}.${field.camelCaseFieldName}<#if field_has_next>,</#if>
            </#if>
            </#list>
                'id -> ${objectname}.id
            ).executeUpdate()
        }
    }


Requirements for Using This Template
------------------------------------

* must use the same form for 'add' and 'edit'  

Notes on the 'Edit' Form
------------------------

// the id is passed into the form, then there is this form statement
@form(routes.Application.update(id)) {

- the problem with this approach is that you need two '@form' statements, one
  for 'create' and another for 'edit'; this is okay if you can wrap it in an
  if/then statement
- @form(routes.Application.save()) {  // THE 'ADD' FORM STATEMENT

Martinez' Approach in His Form
------------------------------

a) "id" -> optional(of[Long]),
b) save method tests id to see if it should do an insert or update
c) sadly, he shows the id in the form
   i) this can be okay if i use id as a hidden field


FROM 'FORMS' EXAMPLE

Ok(html.contact.form(contactForm))                        // display empty form
Ok(html.contact.form(contactForm.fill(existingContact)))  // edit
errors => BadRequest(html.contact.form(errors)),          // validation errors

contact form:
@(contactForm: Form[Contact])











