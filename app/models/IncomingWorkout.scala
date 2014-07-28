package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


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