package com.alvinalexander.cato.utils

import javax.swing.SwingUtilities

object SwingUtils {

    def invokeLater(callback: => Unit) {
        SwingUtilities.invokeLater(new Runnable {
            def run {
                callback
            }
        });
    }

}