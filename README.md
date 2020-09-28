# Here's a simple guide on how to get an Akka Http API up and running in 5 minutes

# Step 0

## Setup environment

- Go to https://github.com/CatiaFerreira6/scala-api.git and install sbt
  - If on Mac `brew install sbt` should do

# Step 1

## Setup project

- In a folder of your choosing create a build.sbt file

In this file we're going to declare the project and all needed libraries

- Then create a project folder with a build.properties and plugins.sbt files

In build.properties we're going to specify the version of sbt we want to use in this project

In plugins.sbt we're going to specify the plugins we want to have in sbt for this project

## Project structure

- A regular scala project will have a `src` folder and inside it you'll usually find a `main` and `test` directories

Both of these folders will then have `resources` and `scala` directories within them

The `resources` folder is the home for the configuration files and some times for the migration files

The `scala` folder is the home for the Scala code you're going to be building

The same applies for the folders within the `test` directory, but in this case we are dealing with test configurations and test Scala code.

- Within the `main/scala` you should have directories representing the package name you want, in this case we have `com/quickstart`

- Then we move to the project actual folder and file structure `core` and `routes` folders

On the base folder you will find the starting point of the application, usually called `{something}App`

Within the `core` folder you will find all the code that is related to the application logic

Within the `routes` folder you will find all the code that is related to the interface with the outside world

# Step 2

## Create endpoints and logic for CRUD operations

### Create User

In order to create a user we need to do several things, starting with the interface layer.

- We need to open a route for creating a User
- We need to link that route with the service for creating a user
- We need the service to call the storage layer for persisting the new user

### Get Users

- We need to open a route for getting the users
- We need to link that route with the service for getting the users
- We need the service to call the storage layer to get the users

### Update user

- We need to open a route for updating a User
- We need to link that route with the service for updating a user
- We need the service to call the storage layer for persisting the new user

### Delete user

- We need to open a route for deleting a User
- We need to link that route with the service for deleting a user
- We need the service to call the storage layer for persisting the new user

# Step 3

## Authentication

The easiest way to use authentication is by using the directives provided by Akka Http.

Let's create an `Authentication.scala` file in the quickstart folder.

In this file we shall create an Authentication trait that will be used by every route aggregator we'll have.

This trait shall have an `authenticate` directive that will make use of the `authenticateBasic` directive of Akka Http

`authenticate` will call on another function of our creation, aptly named `passwordAuthenticator`.

This function will return the username of the authenticated user if the Basic Authentication provided has a password of value `password`


## Run project

`sbt run`

If you want to keep it listening for changes you can use `~reStart` inside the `sbt` shell