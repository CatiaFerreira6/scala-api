package com.quickstart.core.users

import scala.collection.mutable.ListBuffer

class UserStorage {

    private var users: ListBuffer[User] = ListBuffer()

    def addUser(user: User): ListBuffer[User] = users.addOne(user)

    def getUsers = users.toList
}