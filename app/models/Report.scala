package models

import java.text.DecimalFormat

import com.github.nscala_time.time.Imports._
import play.api.libs.json.{Json, Writes}

case class Report(userId: Int, weekOfYear: Int, distanceMeters: Int, durationSeconds: Int)

object Report {
  val formatter = new DecimalFormat("#.#")

  def getRange(startDate: String, endDate: String) = {
    workoutsToReport(Workout.getRange(startDate, endDate))
  }

  def workoutsToReport(workouts: List[Workout]) = {
    workouts.groupBy(w =>  new DateTime(w.date).getWeekOfWeekyear ).map(
      kv =>
        new Report(
          1, //TODO place correct userId here
          kv._1,
          kv._2.map(_.distanceMeters).sum,
          kv._2.map(_.durationSeconds).sum
        )
    ).toList.sortBy(_.weekOfYear)
  }

  def findAll = {
    workoutsToReport(Workout.findAll)
  }


  implicit object reportWrites extends Writes[Report] {
    def writes(r: Report) = Json.obj(
      "userId" -> Json.toJson(r.userId),
      "distanceMeters" -> Json.toJson(r.distanceMeters),
      "weekOfYear" -> Json.toJson(r.weekOfYear),
      "durationSeconds" -> Json.toJson(r.durationSeconds),
      "speed" -> Json.toJson( formatter.format(1.0 * r.distanceMeters / r.durationSeconds) + " m/s" )
    )
  }
}