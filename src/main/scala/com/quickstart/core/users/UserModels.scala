package com.quickstart.core.users

case class User(
  name: String,
  age: Int,
  countryOfResidence: String
)

case class PatchUser(
  age: Int,
  countryOfResidence: String
)