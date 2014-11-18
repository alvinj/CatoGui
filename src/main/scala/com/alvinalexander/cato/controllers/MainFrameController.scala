package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.MainFrame
import javax.swing.JTabbedPane
import com.alvinalexander.cato.utils.SwingUtils
import java.awt.event.WindowEvent
import java.awt.event.WindowAdapter

class MainFrameController (mainController: MainGuiController,
                           propertiesController: PropertiesController, 
                           tablesFieldsTemplatesController: TablesFieldsTemplatesController) {

    val mainFrame = new MainFrame
    
    // get the panels
    val propertiesPanel = propertiesController.propertiesPanel
    val tablesFieldsTemplatesPanel = tablesFieldsTemplatesController.tablesFieldsTemplatesPanel

    // set up the tabs
    val tabbedPane = new JTabbedPane
    tabbedPane.addTab("Properties", propertiesPanel)
    tabbedPane.addTab("Generate Code", tablesFieldsTemplatesPanel)
    //tabbedPane.setEnabledAt(1, false)
    mainFrame.getContentPane.add(tabbedPane)
    mainFrame.addWindowListener(new MainFrameListener(this))
    
    def displayTheGui {
        SwingUtils.invokeLater({
            mainFrame.setTitle("Cato")
            mainFrame.pack
            mainFrame.setLocationRelativeTo(null)  //center
            mainFrame.setVisible(true)
        })
    }
    
    def handleWindowClosingEvent {
        mainController.handleWindowClosingEvent
    }

}

class MainFrameListener(controller: MainFrameController) extends WindowAdapter {
    override def windowClosing(e: WindowEvent) {
        controller.handleWindowClosingEvent
    }
}
