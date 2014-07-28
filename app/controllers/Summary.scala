package controllers

import java.text.DecimalFormat

import models.Workout
import play.api.mvc._
import play.api.libs.json._

object Summary extends Controller {
  val formatter = new DecimalFormat("#.#")

  def thisWeek = Action {
    val workouts = Workout.findAll
    Ok(toJson(workouts))
  }

  def list(from: String, to: String) = Action {
    val workouts = Workout.getRange(from, to)
    Ok(toJson(workouts))
  }

  def toJson(w: List[Workout]) = {
    val totalDistance = w.map( w => w.distanceMeters).sum
    val totalTime = w.map( w => w.durationSeconds).sum
    val avgSpeed = formatter.format(1.0 * totalDistance / totalTime) + " m/s"

    val jsonObject = Json.toJson(
      Map(
        "totalDistance" -> Json.toJson(totalDistance),
        "totalTime" -> Json.toJson(totalTime),
        "avgSpeed" -> Json.toJson(avgSpeed)
      )
    )
    jsonObject
  }
}