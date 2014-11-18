package com.alvinalexander.cato

import com.alvinalexander.cato.controllers._

trait MainGuiController

class CatoGui extends MainGuiController {
  
    val propertiesController = new PropertiesController(this)
    val tablesFieldsTemplatesController = new TablesFieldsTemplatesController(this)
    val mainFrameController = new MainFrameController(this, propertiesController, tablesFieldsTemplatesController)

    mainFrameController.displayTheGui
}

/**
 * Just start the app.
 */
object CatoGui extends App {
    new CatoGui
}

