# put this code in your module's "conf/routes" file

GET     /${tablename}                 controllers.${classnamePlural}.list
GET     /${tablename}/add             controllers.${classnamePlural}.add
POST    /${tablename}/add             controllers.${classnamePlural}.submit
GET     /${tablename}/:id/edit        controllers.${classnamePlural}.edit(id: Long)
GET     /${tablename}/:id/delete      controllers.${classnamePlural}.delete(id: Long)

