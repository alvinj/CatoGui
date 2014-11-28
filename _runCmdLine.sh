#!/bin/sh
#
# Use this script to run Cato from the command line (rather than the GUI).
#
# Note: I don't anticipate using this tool very often; I mostly created it as a
#       way to force myself to improve my Cato source code, i.e., making sure my
#       behaviors were in the proper classes/objects to support two different
#       user interfaces.

java -classpath "resources/mysql-connector-java-5.1.34-bin.jar:target/scala-2.10/CatoGui-assembly-1.0.jar" \
     com.alvinalexander.cato.CatoCmdLine                     \
     --mappingfile  resources/datatypemappings.json          \
     --templatefile resources/templates/Scala/CaseClass.tpl  \
     --user         root                                     \
     --password     root                                     \
     --driver       com.mysql.jdbc.Driver                    \
     --url          jdbc:mysql://localhost:8889/finance      \
     --table        transactions


if [ $? != 0 ]
then
    echo ""
    echo "If you got this error message:"
    echo ""
    echo "    Error: Could not find or load main class com.alvinalexander.cato.CatoGui"
    echo ""
    echo "you need to run this command to build Cato's jar file:"
    echo ""
    echo "    sbt assembly"
    echo ""
    echo "Then run this script again."
    echo ""
    exit 1
fi

