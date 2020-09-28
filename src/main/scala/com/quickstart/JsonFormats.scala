package com.quickstart

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import com.quickstart.core.users.{ User, PatchUser }

trait JsonFormats extends DefaultJsonProtocol with SprayJsonSupport {

    implicit val userJsonFormat = jsonFormat3(User)

    implicit val updateUserJsonFormat = jsonFormat2(PatchUser)

}