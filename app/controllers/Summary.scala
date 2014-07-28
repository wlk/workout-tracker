package controllers

import java.text.DecimalFormat

import models.{User, Workout}
import play.api.mvc._
import play.api.libs.json._

object Summary extends Controller with Secured{
  val formatter = new DecimalFormat("#.#")

  //displays summary for all workouts
  def allSummary = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.findAll(user)
      Ok(toJson(workouts))
    }.getOrElse(Forbidden)
  }

  //displays summary for workouts limited by from, to parameters
  def list(from: String, to: String) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val workouts = Workout.getRange(user, from, to)
      Ok(toJson(workouts))
    }.getOrElse(Forbidden)
  }

  //performs mapping from 3 values to JsonMap
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