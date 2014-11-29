
    // =========================================================
    // Use one of the two options below in your controller file,
    // app/controllers/${classnamePlural}.scala
    // =========================================================
  
  
    // -------------------------------------------------------
    // (A) controller 'delete' action (works with a Play view)
    // -------------------------------------------------------
  
    /*
     * The controller 'delete' action.
     */
    def delete(id: Long) = Action {
        ${classname}.delete(id)
        Redirect(routes.${classnamePlural}.list)
    }  
  
  
    // -------------------------
    // (B) async 'delete' action
    // -------------------------
  
    /**
     * Delete a ${classname}, asynchronously.
     * TODO This code is written to work with a Sencha ExtJS or Touch client; change as needed.
     */
    def delete(id: Long) = Action.async {
        // TODO handle the case where 'count < 1' properly 
        val futureNumRowsDeleted = scala.concurrent.Future{ ${classname}.delete(id) }
        futureNumRowsDeleted.map{ count =>
            val result = Map(
                "success" -> toJson(true), 
                "msg" -> toJson("${classname} was deleted"), 
                "id" -> toJson(count)
            )
            Ok(Json.toJson(result))
        }
    }










