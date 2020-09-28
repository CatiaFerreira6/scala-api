package com.quickstart

import akka.http.scaladsl.server.Directives.authenticateBasic
import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}

trait Authentication {

  def authenticate: AuthenticationDirective[String] = authenticateBasic(realm = "secure site", passwordAuthenticator)

  def passwordAuthenticator(credentials: Credentials): Option[String] =
    credentials match {
      case p @ Credentials.Provided(id) if p.verify("password") => Some(id)
      case _ => None
    }
}