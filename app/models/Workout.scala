package models

import com.github.nscala_time.time.Imports._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.text.DecimalFormat

case class Workout(id: Int, userId: Int, name: String, date: String, distanceMeters: Int, durationSeconds: Int)

object Workout {
  def userCanAccess(id: Int, email: String): Boolean = {
    val workout = findById(id).get
    val user = User.findByEmail(email).get
    workout.userId == user.userId
  }

  val formatter = new DecimalFormat("#.#")


  def add(workout: Workout) = addOrEdit(workout)

  def edit(workout: Workout) = addOrEdit(workout)

  def exists(id: Int) = this.workouts.exists(_.id == id)

  def addOrEdit(workout: Workout) = {
    findById(workout.id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout + workout
    ).getOrElse(
        this.workouts = this.workouts + workout
      )
  }

  def getRange(from: String, to: String) = {
    this.workouts.filter( w => w.date >= from && w.date <= to).toList
  }

  var workouts = Set(
    Workout(1, 1, "morning run", DateTime.now.hour(6).toString("yyyy-MM-dd"), 100, 9),
    Workout(2, 1, "evening run", DateTime.now.hour(18).toString("yyyy-MM-dd"), 2332, 322),
    Workout(3, 1, "evening run last week", DateTime.now.day(18).toString("yyyy-MM-dd"), 100, 9),
    Workout(3, 1, "evening run previous week", DateTime.now.day(7).toString("yyyy-MM-dd"), 100, 9),
    Workout(4, 2, "run for different user", DateTime.now.toString("yyyy-MM-dd"), 100, 9)
  )

  def findAll = this.workouts.toList.sortBy(_.id)

  def findById(id: Int) = this.workouts.find(_.id == id)

  def delete(id: Int){
    findById(id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }


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
    (JsPath \ "id").read[Int] and
      (JsPath \ "userId").read[Int] and
      (JsPath \ "name").read[String] and
      (JsPath \ "date").read[String] and
      (JsPath \ "distanceMeters").read[Int] and
      (JsPath \ "durationSeconds").read[Int]
    )(Workout.apply _)

}
