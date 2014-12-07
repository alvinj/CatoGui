#!/bin/sh

# Use this script to run Cato by adding your JDBC driver (jar file) to the 
# classpath here.
#
# As an example of how to do this, I've included the Postgres JDBC driver in
# the classpath in this example:

java -classpath \
    "resources/mysql-connector-java-5.1.34-bin.jar:target/scala-2.10/CatoGui-assembly-1.0.jar" \
    com.alvinalexander.cato.CatoGui \
    -m resources/datatypemappings.json


