package controllers

import java.text.DecimalFormat
import play.api.mvc._
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models._
import play.api.Logger

object Workouts extends Controller with Secured {

  val dateFormat = org.joda.time.format.ISODateTimeFormat.dateTime()

  def all() = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.findAll
      Ok(Json.toJson(workouts))
    }.getOrElse(Forbidden)
  }


  def list(from: String, to: String) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.getRange(from, to)
      Ok(Json.toJson(workouts))
    }.getOrElse(Forbidden)
  }

  def show(id: Int) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workout = Workout.findById(id)
      Ok(Json.toJson(workout))
    }.getOrElse(Forbidden)
  }

  def add() = Security.Authenticated(username, onUnauthorized) {
    user => Action(parse.json) {
      request =>
        try {
          val workoutJson = request.body
          val workout = workoutJson.as[Workout]

          if (Workout.exists(workout.id)) {
            throw new IllegalArgumentException("workout exists")
          }
          else {
            Workout.add(workout)
            Ok("added")
          }
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

  def edit(id: Int) = Security.Authenticated(username, onUnauthorized) { user => Action(parse.json) {
    //at the moment the id parameter is ignored, and api looks only at what was passed in json object
    request =>
      try {
        val workoutJson = request.body
        val workout = workoutJson.as[Workout]
        Workout.edit(workout)
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

  def delete(id: Int) = Security.Authenticated(username, onUnauthorized) {
    user => Action {
      request =>
        try {
          Workout.delete(id)
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