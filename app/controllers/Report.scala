package controllers

import java.text.DecimalFormat

import models.Workout
import play.api.mvc._
import play.api.libs.json._

object Report extends Controller {
  val formatter = new DecimalFormat("#.#")



  def thisWeek  = Action  {
    val workouts = Workout.findAll
    val totalDistance = workouts.map( w => w.distanceMeters).sum
    val totalTime = workouts.map( w => w.durationSeconds).sum
    val avgSpeed = formatter.format(1.0 * totalDistance / totalTime) + " m/s"


    val jsonObject = Json.toJson(
      Map(
        "totalDistance" -> Json.toJson(totalDistance),
        "totalTime" -> Json.toJson(totalTime),
        "avgSpeed" -> Json.toJson(avgSpeed)
      )
    )

    Ok(jsonObject) }

  def list(from: String, to: String) = play.mvc.Results.TODO
}