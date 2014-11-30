
I'm trying to make the "types" better (more accurate).
here are some notes from current work

* if i can do anything, it will be in TableUtils::getColumnData

Here's a new MySQL table I'm using to test the code:

````
--
-- for whole numbers, mysql 5.x has int (alias for integer),
-- bit, smallint
-- see http://dev.mysql.com/doc/refman/5.0/en/numeric-types.html
--
drop table if exists whole_number_types;
create table whole_number_types (
    id int unsigned auto_increment not null,
    unsigned_int_type int unsigned,
    int_type int,
    bit_type bit,
    tinyint_type tinyint,
    smallint_type smallint,
    smallint_uns_type smallint unsigned,
    mediumint_type mediumint,
    mediumint_uns_type mediumint unsigned,
    bigint_type bigint,
    bigint_uns_type bigint unsigned,
    primary key (id)
) ENGINE = InnoDB;
````

TableUtils::getColumnData currently prints this output for that table:

````
id
~~~~~~~~~~~~~~~
id, colStringType = 4
typeName             = INT UNSIGNED
columnSize           = 10
sqlDataType          = 0
isAutoIncrementField = YES

unsigned_int_type
~~~~~~~~~~~~~~~
unsigned_int_type, colStringType = 4
typeName             = INT UNSIGNED
columnSize           = 10
sqlDataType          = 0
isAutoIncrementField = NO

int_type
~~~~~~~~~~~~~~~
int_type, colStringType = 4
typeName             = INT
columnSize           = 10
sqlDataType          = 0
isAutoIncrementField = NO

bit_type
~~~~~~~~~~~~~~~
bit_type, colStringType = -7
typeName             = BIT
columnSize           = 1
sqlDataType          = 0
isAutoIncrementField = NO

tinyint_type
~~~~~~~~~~~~~~~
tinyint_type, colStringType = -6
typeName             = TINYINT
columnSize           = 3
sqlDataType          = 0
isAutoIncrementField = NO

smallint_type
~~~~~~~~~~~~~~~
smallint_type, colStringType = 5
typeName             = SMALLINT
columnSize           = 5
sqlDataType          = 0
isAutoIncrementField = NO

smallint_uns_type
~~~~~~~~~~~~~~~
smallint_uns_type, colStringType = 5
typeName             = SMALLINT UNSIGNED
columnSize           = 5
sqlDataType          = 0
isAutoIncrementField = NO

mediumint_type
~~~~~~~~~~~~~~~
mediumint_type, colStringType = 4
typeName             = MEDIUMINT
columnSize           = 7
sqlDataType          = 0
isAutoIncrementField = NO

mediumint_uns_type
~~~~~~~~~~~~~~~
mediumint_uns_type, colStringType = 4
typeName             = MEDIUMINT UNSIGNED
columnSize           = 8
sqlDataType          = 0
isAutoIncrementField = NO

bigint_type
~~~~~~~~~~~~~~~
bigint_type, colStringType = -5
typeName             = BIGINT
columnSize           = 19
sqlDataType          = 0
isAutoIncrementField = NO

bigint_uns_type
~~~~~~~~~~~~~~~
bigint_uns_type, colStringType = -5
typeName             = BIGINT UNSIGNED
columnSize           = 20
sqlDataType          = 0
isAutoIncrementField = NO
````

Notes on MySQL data types:

````
MySQL Data Type Sizes
---------------------

Type	Storage	Minimum Value	Maximum Value
 	(Bytes)	(Signed/Unsigned)	(Signed/Unsigned)
TINYINT	1	-128	127
 	 	0	255
SMALLINT	2	-32768	32767
 	 	0	65535
MEDIUMINT	3	-8388608	8388607
 	 	0	16777215
INT	4	-2147483648	2147483647
 	 	0	4294967295
BIGINT	8	-9223372036854775808	9223372036854775807
 	 	0	18446744073709551615
````

that came from here:
http://dev.mysql.com/doc/refman/5.0/en/integer-types.html

Notes on mapping MySQL types to Java:

````
MySQL Type      Java Type
----------      ---------
CHAR            String
VARCHAR         String
LONGVARCHAR     String
NUMERIC         java.math.BigDecimal
DECIMAL         java.math.BigDecimal
BIT             boolean
TINYINT         byte
SMALLINT        short
INTEGER         int
BIGINT          long
REAL            float
FLOAT           double
DOUBLE          double
BINARY          byte[]
VARBINARY       byte[]
LONGVARBINARY   byte[]
DATE            java.sql.Date
TIME            java.sql.Time
TIMESTAMP       java.sql.Tiimestamp
````

those are from me:
http://alvinalexander.com/java/mysql-to-java-field-mappings-decimal-bigdecimal-scala



