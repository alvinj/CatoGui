#!/bin/sh

#---------------------------------------------------------------------------------
# Use this script to run the latest version of Cato with your database driver.
# (If you're not using MySQL, or you're using a different version of MySQL, you'll
# need to change the JDBC driver in the classpath below.)
#---------------------------------------------------------------------------------

java -classpath \
    "../resources/mysql-connector-java-5.1.34-bin.jar:CatoGui-assembly-1.0.jar" \
    com.alvinalexander.cato.CatoGui \
    -m ../resources/datatypemappings.json


