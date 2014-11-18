  // copy this code to the `object` in your 
  // app/models/<<$classname>>.scala file
  // ======================================

  /**
   * JSON Serializer Code
   * --------------------
   */
  import play.api.libs.json.Json
  import play.api.libs.json._
  import java.text.SimpleDateFormat

  implicit object <<$classname|capitalize>>Format extends Format[<<$classname|capitalize>>] {

      // convert from <<$classname|capitalize>> object to JSON (serializing to JSON)
      def writes(<<$objectname>>: <<$classname|capitalize>>): JsValue = {
          val sdf = new SimpleDateFormat("yyyy-MM-dd")
          val <<$objectname>>Seq = Seq(
          // VERIFY valid types are JsString, JsNumber, JsBoolean, JsArray, JsNull, JsObject
          // TODO delete trailing comma
          // DATE example: "datetime" -> JsString(sdf.format(researchLink.datetime)),
          // SEE http://www.playframework.com/documentation/2.2.x/ScalaJson
    <<section name=id loop=$camelcase_fields>>
    <<if ($field_is_reqd[id] == true) >>
    "<<$camelcase_fields[id]>>" -> <<$play_json_field_types[id]|capitalize>>(<<$objectname>>.<<$camelcase_fields[id]>>),
    <<else>>
    "<<$camelcase_fields[id]>>" -> <<$play_json_field_types[id]|capitalize>>(<<$objectname>>.<<$camelcase_fields[id]>>.getOrElse("")),
    <</if>>
    <</section>>
          )
          JsObject(<<$objectname>>Seq)
      }

      // convert from a JSON string to a <<$classname>> object (de-serializing from JSON)
      // TODO the CSV string inside the JsSuccess() is wrong; this is a known problem. copy/paste
      // the correct variable names inside the JsSuccess() method.
      // @see http://www.playframework.com/documentation/2.2.x/ScalaJson regarding Option
      // DATE fields should be like: val datetime = (json \ "datetime").as[java.util.Date]
      def reads(json: JsValue): JsResult[<<$classname|capitalize>>] = {
   <<section name=id loop=$camelcase_fields>>
   <<if ($field_is_reqd[id] == true) >>
   val <<$camelcase_fields[id]>> = (json \ "<<$camelcase_fields[id]>>").as[<<$scala_field_types[id]|capitalize>>]
   <<else>>
   val <<$camelcase_fields[id]>> = (json \ "<<$camelcase_fields[id]>>").asOpt[<<$scala_field_types[id]|capitalize>>]
   <</if>>
   <</section>>
   JsSuccess(<<$classname|capitalize>>(<<$fields_as_insert_csv_string>>))
      }
  }


