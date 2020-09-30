package com.quickstart.core.users

/** This is where we would hold all the entities regarding the Users aggregate */
case class User(
    name: String,
    age: Int,
    countryOfResidence: String
)