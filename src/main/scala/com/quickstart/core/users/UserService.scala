package com.quickstart.core.users

import akka.actor.typed.{ ActorRef, Behavior }
import akka.actor.typed.scaladsl.Behaviors

/** This is the declaration of what the UserRegistry can do. (For Java types is similar to an Interface)
 * Right now it can't do anything and only holds a trait Command
 * Every action UserRegistry will be capable of doing will extend the Command trait
 * This gives us type safety in the extent that we can be sure that we're not going to call a Registry
 * with another Registry's commands
 */
object UserRegistry {

  sealed trait Command

}

/** This is where the magic happens, cof cof, business logic happens */
class UserRegistry(userStorage: UserStorage){
  import UserRegistry._

  def register(): Behavior[Command] = Behaviors.empty
}
