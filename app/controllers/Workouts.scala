package controllers

import play.api.mvc._
import play.api.mvc.Controller
import play.api.libs.json._
import models._
import play.api.Logger

object Workouts extends Controller with Secured {

  val dateFormat = org.joda.time.format.ISODateTimeFormat.dateTime()

  //action to display all workouts for authenticated user
  def all() = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.findAll(user)
      Ok(Json.toJson(workouts))
    }.getOrElse(Forbidden)
  }

  //action to display workouts limtied by from, to parameters for authenticated user
  def list(from: String, to: String) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.getRange(user, from, to)
      Ok(Json.toJson(workouts))
    }.getOrElse(Forbidden)
  }

  //action to show details of given workout
  def show(id: Int) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workout = Workout.findByIdForUser(user, id)
      Ok(Json.toJson(workout))
    }.getOrElse(Forbidden)
  }

  //add new workout from json
  def add() = Security.Authenticated(username, onUnauthorized) {
    email => Action(parse.json) {
      request =>
        try {
          val workoutJson = request.body
          val workout = workoutJson.as[IncomingWorkout]

          Workout.add(email, workout)
          Ok("added")
        }
        catch {
          case e: IllegalArgumentException => BadRequest("Workout already exists")
          case e: Exception => {
            Logger.info("exception = %s" format e)
            BadRequest("Invalid Request")
          }
        }
    }
  }

  //edit workout that has id equal to passed argument, verifies if user is logged in and owns that workout
  def edit(id: Int) = Security.Authenticated(username, onUnauthorized) {
    email => Action(parse.json) {
      request =>
        try {
          val workoutJson = request.body
          val workout = workoutJson.as[IncomingWorkout]
          Workout.edit(email, workout, id)
          Ok("edited")
        }
        catch {
          case e: IllegalArgumentException => BadRequest("Workout not found")
          case e: Exception => {
            Logger.info("exception = %s" format e)
            BadRequest("Invalid Request")
          }
        }
    }
  }

  //delete workout that has id equal to one passed as parameter, verifies if user is logged in and owns that workout
  def delete(id: Int) = Security.Authenticated(username, onUnauthorized) {
    email => Action {
      request =>
        try {
          Workout.delete(email, id)
          Ok("deleted")
        }
        catch {
          case e: IllegalArgumentException => BadRequest("Workout not found")
          case e: Exception => {
            Logger.info("exception = %s" format e)
            BadRequest("Invalid Request")
          }
        }
    }
  }
}