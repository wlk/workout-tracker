package controllers

import play.api.mvc._
import play.api.mvc.Controller
import models.Workout
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.Logger


object Workouts extends Controller {

  def index() = Action {
    val workoutIds = Workout.findAll.map(_.id)
    Ok(Json.toJson(workoutIds))
  }

  def list(from: String, to: String) = play.mvc.Results.TODO

  def show(id: Int) = play.mvc.Results.TODO

  def add() = play.mvc.Results.TODO
}