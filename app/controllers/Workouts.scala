package controllers

import java.text.DecimalFormat
import play.api.mvc._
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models._
import play.api.Logger

object Workouts extends Controller {

  val dateFormat = org.joda.time.format.ISODateTimeFormat.dateTime()

  def all() = Action {
    val workouts = Workout.findAll
    Ok(Json.toJson(workouts))
  }

  def list(from: String, to: String) = Action {
    val workouts = Workout.getRange(from, to)
    Ok(Json.toJson(workouts))
  }

  def show(id: Int) = Action {
    val workout = Workout.findById(id)
    Ok(Json.toJson(workout))
  }

  def add() = Action(parse.json) {
    request =>
      try {
        val workoutJson = request.body
        val workout = workoutJson.as[Workout]

        if(Workout.exists(workout.id)){
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


  def edit(id: Int) = Action(parse.json){
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

  def delete(id: Int) = Action {
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