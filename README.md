Cato (GUI)
==========

Cato is a simple, template-based, database-driven, GUI/web app that lets you generate
source code for any programming language or tool based on the tables in your database.

This version of Cato runs as a Java Swing application. There is another version
of Cato which works as a web application.

I've tested this code with MySQL. I haven't tested it recently with Postgres, but
the old version of this project used to work well with Postgres, and I haven't 
changed those old functions (yet).


Cato Demo
---------

I created a short demo video of how to use Cato with the Play Framework, and posted
it here:

* TBD

While that video shows how to use the Scala version of the Play Framework, you can
use Cato to generate JavaBean classes, XML configuration files, etc., basically 
any text file that can be generated from a database table definition.


How to Run Cato
---------------

After downloading this code from Github, you should be able to run Cato
with this script on Mac OS X and *nix systems:

    _runJar.sh

If you're using a current version of MySQL, that may be all you need to
start using Cato. If you're using an older version of MySQL or some other
database, you'll need to modify that script to include the JDBC driver
for your database.

You may also be able to run Cato on Windows systems using a similar script.
However, I don't have any Windows computers, so I haven't tried to provide
a batch file.


The "Data Types Mapping File"
-----------------------------

Cato uses something I call a "data types mapping file." There are several reasons for this
file:

* I don't want to hard-code the data types.
* Some templates will require the use of multiple data type mappings.
  The Play Framework, for instance, needs Scala and Play mappings, and may
  also be better if there are "Play Optional" types; so they need two and
  perhaps three types at one time.
* I may not get the data types right for languages I don't use often, like PHP.

So that's why you need to specify the data types mapping file in the shell
script. In the future I may have Cato automatically look for the mapping file
in certain directories, like _$HOME/Library/Application Support/..._ on Mac OS X,
but that's how it works today.

Also in the future, at some point I may revise the code to use reflection to more
easily pick up on data types that you define, but again, this is how it works
today.


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







