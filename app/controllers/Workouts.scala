package controllers

import java.text.DecimalFormat

import org.joda.time.DateTime
import play.api.mvc._
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models._
import play.api.Logger

object Workouts extends Controller {
  val dateFormat = org.joda.time.format.ISODateTimeFormat.dateTime()
  val formatter = new DecimalFormat("#.#")

  //Product to Json
  implicit object workoutWrites extends Writes[Workout] {
    def writes(w: Workout) = Json.obj(
      "id" -> Json.toJson(w.id),
      "name" -> Json.toJson(w.name),
      "userId" -> Json.toJson(w.userId),
      "distanceMeters" -> Json.toJson(w.distanceMeters),
      "date" -> Json.toJson(w.date.toString()),
      "durationSeconds" -> Json.toJson(w.durationSeconds),
      "speed" -> Json.toJson( formatter.format(1.0 * w.distanceMeters / w.durationSeconds) + " m/s" )
    )
  }

  //Json to Product
  implicit val workoutReads: Reads[Workout] = (
    (JsPath \ "id").read[Long] and
      (JsPath \ "userId").read[Int] and
      (JsPath \ "name").read[String] and
      (JsPath \ "date").read[String] and
      (JsPath \ "distanceMeters").read[Int] and
      (JsPath \ "durationSeconds").read[Int]
    )(Workout.apply _)


  def thisWeek() = Action {
    val workouts = Workout.findAll.map { workouts => workouts }
    Ok(Json.toJson(workouts))
  }


  def list(from: String, to: String) = thisWeek()


  def show(id: Int) = Action {
    val workout = Workout.findById(id)
    Ok(Json.toJson(workout))
  }


  def add() = Action(parse.json) {
    request =>
      try {
        val workoutJson = request.body
        val workout = workoutJson.as[Workout]
        Workout.add(workout)
        Ok("saved")
      }
      catch {
        case e: IllegalArgumentException => BadRequest("Workout not found")
        case e: Exception => {
          Logger.info("exception = %s" format e)
          BadRequest("Invalid Request")
        }
      }
  }

  def edit(id: Int) = Action(parse.json){
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