//
// add this code to your model object
//

    def insert(${objectname}: ${classname}): Option[Long] = {
        val id: Option[Long] = DB.withConnection { implicit c =>
            // NOTE: intentionally skipping `id` field in insert "columns" list
            SQL("""
                insert into ${tablename} (${fieldsAsInsertCsvStringWoIdField})
                values (
                <#list fields as field>
                <#if field.camelCaseFieldName != "id" >
                    {${field.camelCaseFieldName}}<#if field_has_next>,</#if>
                </#if>
                </#list>
                )
                """
            )
            .on(
            <#list fields as field>
            <#if field.camelCaseFieldName != "id" >
                  '${field.camelCaseFieldName} -> ${objectname}.${field.camelCaseFieldName}<#if field_has_next>,</#if>
            </#if>
            </#list>
            ).executeInsert()
        }
        id
    }
  
    // THIS IS A PLACEHOLDER; NEEDED FOR CONTROLLER 'submit' METHOD
    def update(${objectname}: ${classname}): Boolean = true
  
