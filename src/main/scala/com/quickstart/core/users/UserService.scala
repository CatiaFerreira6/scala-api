package com.quickstart.core.users

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

object UserRegistry {

  sealed trait Command

}

class UserRegistry(userStorage: UserStorage){
  import UserRegistry._

  def register(): Behavior[Command] = Behaviors.empty
}
