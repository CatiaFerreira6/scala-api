package com.quickstart.core.users

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

object UserRegistry {

  sealed trait Command

  final case class CreateUser(user: User, replyTo: ActorRef[User]) extends Command

  final case class GetUsers(replyTo: ActorRef[List[User]]) extends Command

  final case class UpdateUser(user: User, replyTo: ActorRef[User]) extends Command

  final case class DeleteUser(userName: String, replyTo: ActorRef[Unit]) extends Command

}

class UserRegistry(userStorage: UserStorage){
  import UserRegistry._

  def register(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateUser(newUser, replyTo) =>
        userStorage.addUser(newUser)
        replyTo ! newUser
        Behaviors.same

      case GetUsers(replyTo) =>
        replyTo ! userStorage.getUsers
        Behaviors.same

      case UpdateUser(user, replyTo) =>
        userStorage.updateUser(user)
        replyTo ! user
        Behaviors.same

      case DeleteUser(name, replyTo) =>
        userStorage.deleteUser(name)
        replyTo ! ()
        Behaviors.same
    }
}
