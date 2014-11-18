package com.alvinalexander.cato.view

import javax.swing.JFrame
import java.awt.Toolkit
import java.awt.Dimension
import javax.swing.JTabbedPane

class MainFrame extends JFrame {
  
    initializePreferredScreenSize
    pack

    private def initializePreferredScreenSize {
        val screenSize = Toolkit.getDefaultToolkit.getScreenSize
        val desiredHeight = screenSize.height * 2/3
        val desiredWidth = (desiredHeight * 1.5).toInt
        setPreferredSize(new Dimension(desiredWidth, desiredHeight))
    }

}