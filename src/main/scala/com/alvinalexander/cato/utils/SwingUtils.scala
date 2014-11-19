package com.alvinalexander.cato.utils

import javax.swing.SwingUtilities
import javax.swing.JFrame
import java.awt.FileDialog

object SwingUtils {

    def invokeLater(callback: => Unit) {
        SwingUtilities.invokeLater(new Runnable {
            def run {
                callback
            }
        });
    }

    /**
     * @param jFrame
     * @param currentDirectory
     * @return You can get the filename with fileDialog.getFile(),
     * and you can get the directory with fileDialog.getDirectory().
     * String filename = fileDialog.getDirectory() + System.getProperty("file.separator") + fileDialog.getFile();
     * 
     */
    def letUserChooseFile(jFrame: JFrame, defaultDirectory: String): FileDialog = {
        System.setProperty("apple.awt.fileDialogForDirectories", "true")  //choose a dir
        val fileDialog = new FileDialog(jFrame)
        fileDialog.setModal(true)
        fileDialog.setMode(FileDialog.LOAD)
        fileDialog.setTitle("Select Your Templates Directory")
        if (defaultDirectory!=null && !defaultDirectory.trim.equals("")) {
            fileDialog.setDirectory(defaultDirectory)
        }
        fileDialog.setVisible(true)
        return fileDialog
    }

    /**
     * Returns the full path to the file, if a file was selected.
     * Otherwise it returns null.
     */
    def getCanonicalFilenameFromFileDialog(fileDialog: FileDialog): String = {
        if (fileDialog.getDirectory() == null) return null
        if (fileDialog.getFile() == null) return null
        // this line not needed on mac os x
        //return fileDialog.getDirectory() + System.getProperty("file.separator") + fileDialog.getFile();
        return fileDialog.getDirectory + fileDialog.getFile
    }
  
    def getFilenameFromFileDialog(fileDialog: FileDialog): String = fileDialog.getFile  
    def getDirectoryFromFileDialog(fileDialog: FileDialog): String = fileDialog.getDirectory

}