README for Play Framework Template Users
========================================

The biggest thing I've found when using the Play Framework is that you need
to have a strategy about how you're going to deal with certain things,
including:

* database table `id` fields
* date fields, such as `date_created` and `date_updated` fields found in some tables

These issues have nothing to do with Cato, but they do have to do with your
Play Framework templates.

I use one of many possible strategies to deal with these fields in my 
Play templates. If you look at the code in the following files that can be
generated from the sample `users` database table, you'll see my strategy:

* model/User.scala
* controllers/Users.scala
* views/user/form.scala.html



