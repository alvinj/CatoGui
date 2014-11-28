package com.alvinalexander.cato.controllers

import com.alvinalexander.cato.CatoGui
import com.alvinalexander.cato.view.MainFrame
import javax.swing.JTabbedPane
import com.alvinalexander.cato.utils.SwingUtils
import java.awt.event.WindowEvent
import java.awt.event.WindowAdapter
import javax.swing.event.ChangeListener
import javax.swing.event.ChangeEvent
import java.awt.Color
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.BorderLayout
import com.alvinalexander.cato.utils.FileUtils
import com.alvinalexander.annotations.impure

class MainFrameController (mainController: CatoGui,
                           propertiesController: PropertiesController, 
                           tablesFieldsTemplatesController: TablesFieldsTemplatesController) {

    val mainFrame = new MainFrame
    
    val PROPERTIES_PANEL_NUM    = 0
    val GENERATE_CODE_PANEL_NUM = 1
    
    // get the panels
    val propertiesPanel = propertiesController.propertiesPanel
    val tablesFieldsTemplatesPanel = tablesFieldsTemplatesController.tablesFieldsTemplatesPanel
    
    // TODO there's probably a better way to do this (give the tabbedpane some breathing room on the top)
    val fillerPanel = new JPanel
    fillerPanel.setPreferredSize(new Dimension(16, 16))
    mainFrame.getContentPane.add(fillerPanel, BorderLayout.NORTH)

    // set up the tabs
    val tabbedPane = new JTabbedPane
    tabbedPane.addTab("Properties", propertiesPanel)
    tabbedPane.addTab("Generate Code", tablesFieldsTemplatesPanel)
    mainFrame.getContentPane.add(tabbedPane)

    mainFrame.addWindowListener(new MainFrameListener(this))
    
    tabbedPane.addChangeListener(new ChangeListener {
        @impure def stateChanged(e: ChangeEvent) {
            // if the "generate code" tab is selected, update the list of template files
            if (tabbedPane.getSelectedIndex == GENERATE_CODE_PANEL_NUM) {
                val templatesDir = propertiesController.getTemplatesDir
                val listOfTemplateFiles = FileUtils.getListOfFilesInDirectoryAsOption(templatesDir)
                listOfTemplateFiles match {
                    case Some(files) => tablesFieldsTemplatesController.setListOfTemplateFiles(files)
                    case None => tablesFieldsTemplatesController.clearListOfTemplateFiles
                }
            }
        }
    });
    
    @impure def displayTheGui {
        SwingUtils.invokeLater({
            mainFrame.setTitle("Cato")
            mainFrame.pack
            mainFrame.setLocationRelativeTo(null)  //center
            mainFrame.setVisible(true)
        })
    }
    
    @impure def handleWindowClosingEvent {
        mainController.handleWindowClosingEvent
    }

}

class MainFrameListener(controller: MainFrameController) extends WindowAdapter {
    override def windowClosing(e: WindowEvent) {
        controller.handleWindowClosingEvent
    }
}













