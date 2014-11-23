  // TODO delete trailing commas
  // TODO delete the 'id' field
  def insert(${objectname}: ${classname}): Option[Long] = {
    val id: Option[Long] = DB.withConnection { implicit c =>
      SQL("""
          insert into <<$tablename>> (<<$fields_as_insert_csv_string>>)
          values (
<<section name=id loop=$camelcase_fields>>
<<if ($camelcase_fields[id] != 'id') >>
            {<<$camelcase_fields[id]>>},
<</if>>
<</section>>
          )
          """
      )
      .on(
<<section name=id loop=$camelcase_fields>>
<<if ($camelcase_fields[id] != 'id') >>
        '<<$camelcase_fields[id]>> -> ${objectname}.<<$camelcase_fields[id]>>,
<</if>>
<</section>>
      ).executeInsert()
    }
    id
  }


  // THIS IS A PLACEHOLDER; NEEDED FOR CONTROLLER 'submit' METHOD
  def update(${objectname}: ${classname}): Boolean = true


