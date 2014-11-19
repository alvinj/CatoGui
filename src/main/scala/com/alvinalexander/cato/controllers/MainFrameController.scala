package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.MainGuiController
import com.alvinalexander.cato.view.MainFrame
import javax.swing.JTabbedPane
import com.alvinalexander.cato.utils.SwingUtils
import java.awt.event.WindowEvent
import java.awt.event.WindowAdapter
import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent

class MainFrameController (mainController: MainGuiController,
                           propertiesController: PropertiesController, 
                           tablesFieldsTemplatesController: TablesFieldsTemplatesController) {

    val mainFrame = new MainFrame
    
    val PROPERTIES_PANEL_NUM    = 0
    val GENERATE_CODE_PANEL_NUM = 1
    
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
    
    tabbedPane.addChangeListener(new ChangeListener {
        def stateChanged(e: ChangeEvent) {
            // if the "generate code" tab is selected, update the list of template files
            if (tabbedPane.getSelectedIndex == GENERATE_CODE_PANEL_NUM) {
                val listOfTemplateFiles = mainController.getListOfTemplateFiles
                listOfTemplateFiles match {
                    case Some(files) => tablesFieldsTemplatesController.setListOfTemplateFiles(files)
                    case None => tablesFieldsTemplatesController.clearListOfTemplateFiles
                }
            }
        }
    });
    
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
