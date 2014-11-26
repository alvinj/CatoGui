  // TODO delete trailing commas
  // TODO delete the 'id' field
  def insert(${objectname}: ${classname}): Option[Long] = {
    val id: Option[Long] = DB.withConnection { implicit c =>
      SQL("""
          insert into ${tablename} (${fieldsAsInsertCsvString})
          values (
<#list fields as field>
<#if field.camelCaseFieldName != "id" >
            {${field.camelCaseFieldName}},
</#if>
</#list>
          )
          """
      )
      .on(
<#list fields as field>
<#if field.camelCaseFieldName != "id" >
            {${field.camelCaseFieldName}},
        '${field.camelCaseFieldName} -> ${objectname}.${field.camelCaseFieldName},
</#if>
</#list>
      ).executeInsert()
    }
    id
  }

  // THIS IS A PLACEHOLDER; NEEDED FOR CONTROLLER 'submit' METHOD
  def update(${objectname}: ${classname}): Boolean = true


