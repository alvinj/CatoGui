#!/bin/sh

# Note: I use this script to debug the application when it won't work
#       when I compile/assemble it as a Mac OS X application.

#scala -classpath "target/scala-2.10/Cato-assembly-1.0.jar" com.devdaily.dbgrinder.Main

scala -classpath "target/scala-2.10/CatoGui-assembly-1.0.jar" com.devdaily.kickstart.KickStartMain

