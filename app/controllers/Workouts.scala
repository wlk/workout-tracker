package controllers

import models.Workout._
import org.joda.time.DateTime
import play.api.mvc._
import play.api.mvc.Controller
import play.api.libs.json._
import play.api.libs.functional.syntax._
import models._
import play.api.Logger

object Workouts extends Controller {

  //Product to Json
  implicit object workoutWrites extends Writes[Workout] {
    def writes(w: Workout) = Json.obj(
      "name" -> Json.toJson(w.name),
      "distanceMeters" -> Json.toJson(w.distanceMeters)
    )
  }

  //Json to Product
  implicit val workoutReads: Reads[Workout] = (
    (JsPath \ "id").read[Long] and
    (JsPath \ "userId").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "date").read[DateTime] and
    (JsPath \ "distanceMeters").read[Int] and
    (JsPath \ "durationSeconds").read[Int]
    )(Workout.apply _)

  def index() = Action {
    val workoutIds = Workout.findAll.map(_.id)
    Ok(Json.toJson(workoutIds))
  }

  def thisWeek() = Action {
    val workoutIds = Workout.findAll.map(_.id)
    Ok(Json.toJson(workoutIds))
  }


  def list(from: String, to: String) = play.mvc.Results.TODO

  def show(id: Int) = play.mvc.Results.TODO


  def add() = Action(parse.json) {
    request =>
      try {
        val workoutJson = request.body
        val workout = workoutJson.as[Workout]
        Workout.add(workout)
        Ok("saved")
      }
      catch {
        case e: IllegalArgumentException => BadRequest("Product not found")
        case e: Exception => {
          Logger.info("exception = %s" format e)
          BadRequest("Invalid EAN")
        }
      }
  }

}