package com.quickstart.core.users

/** This is the layer that interacts with the database, although in this sample project it
 * will hold a mutable list buffer instead of actually hitting a real database */
class UserStorage {

    private var users: List[User] = List()
}