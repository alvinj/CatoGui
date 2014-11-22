Cato (GUI)
==========

Cato is a simple, template-based, database-driven, GUI/web app that lets you generate
source code for any programming language or tool based on the tables in your database.

This version of Cato runs as a Java Swing application. There is another version
of Cato which works as a web application.


MySQL JDBC Driver
-----------------

When this project was created, the MySQL JDBC driver was included in the _lib_
folder of this project.

If that driver doesn't work for your version of MySQL, just follow these steps:

* remove that driver from the _lib_ folder
* copy your new driver into the _lib_ folder
* re-build the application


Postgres JDBC Driver
--------------------

Get the correct Posgtres driver from http://jdbc.postgresql.org/download.html

(They haven't different drivers for Java 6, 7, and 8.)


