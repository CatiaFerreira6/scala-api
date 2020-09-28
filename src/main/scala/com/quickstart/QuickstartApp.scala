package com.quickstart

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.util.Failure
import scala.util.Success

import com.quickstart.routes.UserRoutes
import com.quickstart.core.users.{ UserRegistry, UserStorage }

object QuickstartApp {

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {

    import system.executionContext

    val futureBinding = Http().newServerAt("localhost", 8080).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load().getConfig("quickstart")

    // If ask takes more time than this to complete the request is failed
    implicit val timeout: Timeout = Timeout.create(config.getDuration("routes.ask-timeout"))

    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val userStorage = new UserStorage()

      val userRegistryActor = context.spawn(new UserRegistry(userStorage).register(), "UserRegistryActor")
      context.watch(userRegistryActor)

      val routes = new UserRoutes(userRegistryActor)(context.system, timeout)

      startHttpServer(routes.userRoutes)(context.system)

      Behaviors.empty
    }

    val system = ActorSystem[Nothing](rootBehavior, "QuickstartHttpServer")
  }
}
