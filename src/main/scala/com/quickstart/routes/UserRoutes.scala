package com.quickstart.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ ActorRef, ActorSystem }
import akka.util.Timeout

import com.quickstart.core.users.{ User, PatchUser }
import com.quickstart.core.users.UserRegistry
import com.quickstart.core.users.UserRegistry.CreateUser
import com.quickstart.core.users.UserRegistry.GetUsers
import com.quickstart.core.users.UserRegistry.UpdateUser
import com.quickstart.core.users.UserRegistry.DeleteUser
import com.quickstart.JsonFormats

import scala.concurrent.Future

class UserRoutes(userRegistry: ActorRef[UserRegistry.Command])(implicit val system: ActorSystem[_], timeout: Timeout)
  extends JsonFormats {

  def createUser(user: User): Future[User] = userRegistry.ask(CreateUser(user, _))

  def getUsers: Future[List[User]] = userRegistry.ask(GetUsers)

  def updateUser(user: User): Future[User] = userRegistry.ask(UpdateUser(user, _))

  def deleteUser(name: String): Future[Unit] = userRegistry.ask(DeleteUser(name, _))

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
      },
      path(Segment) { userName =>
        concat(
          put {
            entity(as[PatchUser]){ userToUpdate =>
              val user = User(userName, userToUpdate.age, userToUpdate.countryOfResidence)
              onSuccess(updateUser(user)) { patchedUser =>
                complete(patchedUser)
              }
            }
          },
          delete {
            onSuccess(deleteUser(userName)) {
              complete(StatusCodes.NoContent)
            }
          }
        )
      }
    )
  }
}
