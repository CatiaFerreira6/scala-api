package com.quickstart.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.util.Timeout

import com.quickstart.core.users.User
import com.quickstart.core.users.UserRegistry
import com.quickstart.core.users.UserRegistry.CreateUser
import com.quickstart.core.users.UserRegistry.GetUsers
import com.quickstart.JsonFormats

import scala.concurrent.Future


class UserRoutes(
    userRegistry: ActorRef[UserRegistry.Command]
  )(
    implicit val system: ActorSystem[_],
    timeout: Timeout
  )
  extends JsonFormats {


  def createUser(user: User): Future[User] = userRegistry.ask(CreateUser(user, _))

  def getUsers: Future[List[User]] = userRegistry.ask(GetUsers)

  val userRoutes: Route = pathPrefix("users") {
    concat(
      post {
        entity(as[User]){ newUser =>
          onSuccess(createUser(newUser)) { user =>
            complete(StatusCodes.Created, user)
          }
        }
      },
      get {
        onSuccess(getUsers) { users =>
          complete(users)
        }
      }
    )
  }
}
