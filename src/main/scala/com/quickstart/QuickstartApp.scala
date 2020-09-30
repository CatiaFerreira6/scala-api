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

    //this starts our http server and binds it to the 8080 port
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

    //this loads the configuration file located under main/resources and gets the config tree under quickstart
    val config = ConfigFactory.load().getConfig("quickstart")

    // If ask takes more time than this to complete the request is failed
    implicit val timeout: Timeout = Timeout.create(config.getDuration("routes.ask-timeout"))

    // This describes the behavior of our server. This is usually the place where you declare all the components
    // that are going to exist and interact within the server.
    // Here we say we have a userStorage, a UserRegistry and UserRoutes
    // We then pass the userRoutes as our server's HTTP available routes
    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val userStorage = new UserStorage()

      val userRegistryActor = context.spawn(new UserRegistry(userStorage).register(), "UserRegistryActor")
      context.watch(userRegistryActor)

      val routes = new UserRoutes(userRegistryActor)(context.system, timeout)

      startHttpServer(routes.userRoutes)(context.system)

      Behaviors.empty
    }

    ActorSystem[Nothing](rootBehavior, "QuickstartHttpServer")
  }
}
