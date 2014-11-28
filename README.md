Cato (GUI)
==========

Cato is a simple, template-based, database-driven, GUI/web app that lets you generate
source code for any programming language or tool based on the tables in your database.

This version of Cato runs as a Java Swing application. There is another version
of Cato which works as a web application.

I've tested this code with MySQL. I haven't tested it recently with Postgres, but
the old version of this project used to work well with Postgres, and I haven't 
changed those old functions (yet).


MySQL JDBC Driver
-----------------

You can get the latest MySQL JDBC driver from this URL:

* http://dev.mysql.com/downloads/connector/j/

I use MAMP on Mac OS X, and this is the MySQL information I use for testing:

    DRIVER:        com.mysql.jdbc.Driver
    URL:           jdbc:mysql://localhost:8889/finance
    USERNAME:      root
    PASSWORD:      root
    TEMPLATES_DIR: /Users/Al/Projects/Scala/CatoGui/resources/templates


Postgres JDBC Driver
--------------------

Get the correct Posgtres driver from http://jdbc.postgresql.org/download.html

(They haven't different drivers for Java 6, 7, and 8.)


Libraries
---------

Cato uses quite a few libraries, including:

* [inflector (to singularize plural database table names)](https://github.com/philliphaines/inflector)







