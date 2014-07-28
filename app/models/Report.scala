package models

import java.text.DecimalFormat

import com.github.nscala_time.time.Imports._
import play.api.libs.json.{Json, Writes}

case class Report(weekOfYear: Int, distanceMeters: Int, durationSeconds: Int)

object Report {
  val formatter = new DecimalFormat("#.#")

  def getRange(user: User, startDate: String, endDate: String) = {
    workoutsToReport(Workout.getRange(user, startDate, endDate))
  }

  def workoutsToReport(workouts: List[Workout]) = {
    workouts.groupBy(w =>  new DateTime(w.date).getWeekOfWeekyear ).map(
      kv =>
        new Report(
          kv._1,
          kv._2.map(_.distanceMeters).sum,
          kv._2.map(_.durationSeconds).sum
        )
    ).toList.sortBy(_.weekOfYear)
  }

  def findAll(user: User) = {
    workoutsToReport(Workout.findAll(user))
  }


  implicit object reportWrites extends Writes[Report] {
    def writes(r: Report) = Json.obj(
      "distanceMeters" -> Json.toJson(r.distanceMeters),
      "weekOfYear" -> Json.toJson(r.weekOfYear),
      "durationSeconds" -> Json.toJson(r.durationSeconds),
      "speed" -> Json.toJson( formatter.format(1.0 * r.distanceMeters / r.durationSeconds) + " m/s" )
    )
  }
}