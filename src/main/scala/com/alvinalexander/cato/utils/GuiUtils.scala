package com.alvinalexander.cato.utils

import javax.swing.JTextArea
import javax.swing.JScrollPane
import javax.swing.JOptionPane

object GuiUtils {

    def createTextareaWidgetInsideScrollPane(text: String): JScrollPane = {
        val textArea = new JTextArea(20, 70)
        textArea.setText(text)
        textArea.setCaretPosition(0)
        textArea.setEditable(false)
        new JScrollPane(textArea)
    }
    
    def showErrorDialog(title: String, textToDisplay: String) {
        JOptionPane.showMessageDialog(
            null,
            textToDisplay,
            title,
            JOptionPane.ERROR_MESSAGE)
    }

    def showErrorDialogWithLongText(title: String, textToDisplay: String) {
        JOptionPane.showMessageDialog(
            null,
            createTextareaWidgetInsideScrollPane(textToDisplay),
            title,
            JOptionPane.ERROR_MESSAGE)
    }

}


