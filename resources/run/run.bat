
SET PATH=F:/Apps/JBuilder4/jdk1.3/bin;%PATH%

REM java -cp .;mysql_uncomp.jar;DbGrinder.jar -jar DbGrinder.jar org.gjt.mm.mysql.Driver jdbc:mysql://localhost/devdaily


REM java -classpath ".;mssqlserver-all.jar;DbGrinder.jar" -jar DbGrinder.jar com.microsoft.jdbc.sqlserver.SQLServerDriver jdbc:microsoft:sqlserver://hji-tap;SelectMethod=cursor/toolTAP tapUser t@p
REM java -classpath ".;mssqlserver-all.jar;DbGrinder.jar" com.devdaily.dbgrinder.Main com.microsoft.jdbc.sqlserver.SQLServerDriver jdbc:microsoft:sqlserver://hji-tap;SelectMethod=cursor/toolTAP tapUser t@p


REM
REM To run from the command line
REM 
REM java -classpath ".;mssqlserver-all.jar;jbcl.jar" com.devdaily.dbgrinder.Main com.microsoft.jdbc.sqlserver.SQLServerDriver jdbc:microsoft:sqlserver://hji-tap;SelectMethod=cursor/toolTAP tapUser t@p

REM java -classpath ".;jdbc7.1-1.2.jar;jbcl.jar" com.devdaily.dbgrinder.Main org.postgresql.Driver jdbc:postgresql://tool/blog blog blog

REM java -classpath ".;pg73jdbc3.jar;jbcl.jar;DbGrinder.jar" com.devdaily.dbgrinder.Main org.postgresql.Driver jdbc:postgresql://localhost/fptracker fpt fpt

java -classpath ".;hsqldb.jar;jbcl.jar;DbGrinder.jar" com.devdaily.dbgrinder.Main org.hsqldb.jdbcDriver jdbc:hsqldb:hsql://localhost sa ""
