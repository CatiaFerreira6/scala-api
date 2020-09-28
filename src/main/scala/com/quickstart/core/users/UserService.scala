package com.quickstart.core.users

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

object UserRegistry {

  sealed trait Command

  final case class CreateUser(user: User, replyTo: ActorRef[User]) extends Command

}

class UserRegistry(userStorage: UserStorage){
  import UserRegistry._

  def register(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateUser(newUser, replyTo) =>
        userStorage.addUser(newUser)
        replyTo ! newUser
        Behaviors.same
    }
}
