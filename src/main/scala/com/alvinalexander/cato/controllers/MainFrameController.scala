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

class MainFrameController (mainController: CatoGui,
                           propertiesController: PropertiesController, 
                           tablesFieldsTemplatesController: TablesFieldsTemplatesController) {

    val mainFrame = new MainFrame
    
    val PROPERTIES_PANEL_NUM    = 0
    val GENERATE_CODE_PANEL_NUM = 1
    
    // get the panels
    val propertiesPanel = propertiesController.propertiesPanel
    val tablesFieldsTemplatesPanel = tablesFieldsTemplatesController.tablesFieldsTemplatesPanel

    // set up the tabs
    val tabbedPane = new CustomTabbedPane
    tabbedPane.addTab("Properties", propertiesPanel)
    tabbedPane.addTab("Generate Code", tablesFieldsTemplatesPanel)
    setTftTabEnabled(false)
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
    
    def setTftTabEnabled(b: Boolean) {
        tabbedPane.tftTabIsEnabled = b
        tabbedPane.setEnabledAt(1, b)
        tabbedPane.invalidate
        tabbedPane.updateUI
    }
    
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

/**
 * With Java 7 and OS X 10.9 (and maybe 10.10), there is no visual distinction between
 * a tab that's enabled and one that is disabled, so this is an effort to fix that.
 */
class CustomTabbedPane extends JTabbedPane {

    var tftTabIsEnabled = false

    override def getForegroundAt(index: Int): Color = {
        if (index == 1) {
            if (tftTabIsEnabled) {
                setToolTipTextAt(1, "")
                super.getForegroundAt(index)
            } else {
                setToolTipTextAt(1, "Tab is disabled until you connect to a database and select a template directory")
                Color.GRAY
            }
        } else {
            super.getForegroundAt(index)
        }
    }

}













