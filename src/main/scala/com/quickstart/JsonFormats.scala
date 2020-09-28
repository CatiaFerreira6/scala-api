package com.quickstart

import spray.json.DefaultJsonProtocol
import com.quickstart.core.users.User

trait JsonFormats extends DefaultJsonProtocol {

    implicit val userJsonFormat = jsonFormat4(User)

}