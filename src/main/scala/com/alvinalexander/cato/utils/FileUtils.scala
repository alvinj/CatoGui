package com.alvinalexander.cato.utils

import java.io.File
import com.alvinalexander.annotations.impure

object FileUtils {

    val SLASH = System.getProperty("file.separator")
    
    /**
     * Get a recursive listing of all files underneath the given directory.
     * from stackoverflow.com/questions/2637643/how-do-i-list-all-files-in-a-subdirectory-in-scala
     */
    @impure def getRecursiveListOfFiles(dir: File): Seq[File] = {
        val fileArray = dir.listFiles
        fileArray ++ fileArray.filter(_.isDirectory).flatMap(getRecursiveListOfFiles)
    }
    
    @impure def getListOfFilesInDirectory(dirName: String): Seq[String] = {
        val dir = new File(dirName)
        dir.list
    }

    @impure def getListOfFilesInDirectoryAsOption (dirName: String): Option[Seq[String]] = {
        if (dirName==null || dirName.trim=="") {
            None
        } else {
            Some(getListOfFilesInDirectory(dirName))
        }
    }
    
}