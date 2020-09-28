package com.quickstart.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.util.Timeout

import com.quickstart.core.users.UserRegistry
import com.quickstart.JsonFormats


class UserRoutes(
    userRegistry: ActorRef[UserRegistry.Command]
  )(
    implicit val system: ActorSystem[_],
    timeout: Timeout
  )
  extends JsonFormats {

  val userRoutes: Route = pathPrefix("users") {
    complete(StatusCodes.NotFound)
  }
}
