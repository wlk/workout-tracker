package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

//simple class that is used to parse incoming json objects to workouts, but without few fields - possibly better approach is to have few fields marked as optional in Workout class
case class IncomingWorkout(name: String, date: String, distanceMeters: Int, durationSeconds: Int)

object IncomingWorkout{
  //Json to IncomingWorkout
  implicit val workoutReads: Reads[IncomingWorkout] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "date").read[String] and
      (JsPath \ "distanceMeters").read[Int] and
      (JsPath \ "durationSeconds").read[Int]
    )(IncomingWorkout.apply _)
}