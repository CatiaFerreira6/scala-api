package com.quickstart.core.users

import scala.collection.mutable.ListBuffer

class UserStorage {

  private var users: ListBuffer[User] = ListBuffer()

  def addUser(user: User): ListBuffer[User] = users.addOne(user)

  def getUsers = users.toList

  def updateUser(user: User): ListBuffer[User] = {
    users.map(usr => if(usr.name == user.name) {user} else usr)
  }
}