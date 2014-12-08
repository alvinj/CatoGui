README for Play Framework Template Users
========================================

The biggest thing I've found when using the Play Framework is that you need
to have a strategy about how you're going to deal with certain things,
including:

* database table `id` fields
* date fields, such as `date_created` and `date_updated` fields found in some tables

These issues have nothing to do with Cato, but they do have to do with your
Play Framework templates.

As an example, for a database table named `users`, the strategies you choose will 
affect these files:

* model/User.scala
* controllers/Users.scala
* views/user/form.scala.html


`id` Field Strategy
-------------------

The place where you need to have a strategy regarding `id` fields is in
(a) add/edit form (form.scala.html) and in (b) your mapping code. To have 
just one add/edit form for your object, I think you need to do what I'm doing
currently:

* in the template file (form.scala.html), set the `id` to 0 during the "add" process
* in the template file, set the `id` from the object during the "edit" process
* handle the form mapping in your controller like this: "id" -> longNumber


Date Strategy
-------------

There are different types of dates, and you'll want to handle those differently.
For instance, when prompting the user for a date such as their birthdate, you'll
want to show a date widget. After that it will either be required or optional.

The dates I'm thinking of right now are dates the user doesn't need to see.
These will be named things like `date_created` or `date_updated` in your database
tables.

My _current_ strategy regarding fields like `dateCreated` and `dateUpdated` is to 
generate the fields in all code, and then let the user make changes to the code
manually. I can fix all the current "problems" regarding these fields, but I
need a little more free time to do that.

This is how I will "fix" them when I have the time:

* if i see "dateCreated" or "dateUpdated" in any of the Play templates:
    * don't show the field in the add/edit form (form.scala.html) (DONE)
    * make it "dateCreated" -> optional(date)" in the mapping (DONE)
    * make it "Calendar.getInstance.getTime" in Part1 of the Ctrl Form
    * make it "Some(user.dateCreated.asInstanceOf[java.util.Date])" in Part2 of the Ctrl Form
    * import "java.util.Calendar" in the controller

A few notes around Java dates:

* play/anorm does not have an implicit conversion for Timestamp
* java.sql.timestamp is a subclass of java.util.Date
* java.sql.Date is also a subclass of java.util.Date
* solution: controller mapping should be "dateCreated" -> optional(date)"
  (and not "optional(sqlDate)")









