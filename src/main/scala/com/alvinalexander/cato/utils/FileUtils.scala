package com.alvinalexander.cato.utils

import java.io.File

object FileUtils {

    val SLASH = System.getProperty("file.separator")
    
    /**
     * Get a recursive listing of all files underneath the given directory.
     * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
     */
    def getRecursiveListOfFiles(dir: File): Seq[File] = {
        val fileArray = dir.listFiles
        fileArray ++ fileArray.filter(_.isDirectory).flatMap(getRecursiveListOfFiles)
    }
    
    def getListOfFilesInDirectory(directoryName: String): Seq[String] = {
        val dir = new File(directoryName)
        dir.list
    }

}