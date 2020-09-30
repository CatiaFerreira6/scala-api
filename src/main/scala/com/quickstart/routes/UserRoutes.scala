package com.quickstart.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.util.Timeout

import com.quickstart.core.users.UserRegistry
import com.quickstart.JsonFormats

/** This is where we are going to declare the Http routes available and what each route will call
 * Right now, any route under /users will just return a 404 HTTP Response with a default error message
 * saying: The requested resource could not be found but may be available again in the future.
 */
class UserRoutes(userRegistry: ActorRef[UserRegistry.Command])(implicit val system: ActorSystem[_], timeout: Timeout)
  extends JsonFormats {

  val userRoutes: Route = pathPrefix("users") {
    complete(StatusCodes.NotFound)
  }
}
