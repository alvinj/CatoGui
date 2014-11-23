# put this code in your module's "conf/routes" file

GET     /${tablename}                 controllers.${classname}.list
GET     /${tablename}/add             controllers.${classname}.add
POST    /${tablename}/add             controllers.${classname}.submit
GET     /${tablename}/:id/edit        controllers.${classname}.edit(id: Long)
GET     /${tablename}/:id/delete      controllers.${classname}.delete(id: Long)

