
#THE_CLASSPATH="DbGrinder.jar:jbcl.jar:jdbc7.1-1.2.jar:postgresql-8.0-311.jdbc2.jar"
THE_CLASSPATH="DbGrinder.jar:jbcl.jar:jdbc7.1-1.2.jar:postgresql-8.4-701.jdbc3.jar"

java -classpath ".:$THE_CLASSPATH" com.devdaily.dbgrinder.Main org.postgresql.Driver \
   jdbc:postgresql://192.168.1.101/ddblog ddblog ddblog


#THE_CLASSPATH="DbGrinder.jar:jbcl.jar:jdbc7.1-1.2.jar:mysql-connector-java-3.1.12-bin.jar"

#java -classpath ".:$THE_CLASSPATH" com.devdaily.dbgrinder.Main \
#  org.gjt.mm.mysql.Driver jdbc:mysql://localhost/EmployeeEvents root ""

