package com.quickstart

import spray.json.DefaultJsonProtocol
import com.quickstart.core.users.User

/** This is where we declare the JSON marshall/unmarshall of our entities
* There are libraries that allow us to skip this manual step and generate generic marshall/unmarshall functions
* for each of our entities, beware that it may come at a performance cost since some of them do it at runtime
*/
trait JsonFormats extends DefaultJsonProtocol {

    implicit val userJsonFormat = jsonFormat3(User)
}