package com.quickstart

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import com.quickstart.core.users.User

trait JsonFormats extends DefaultJsonProtocol with SprayJsonSupport {

    implicit val userJsonFormat = jsonFormat3(User)

}