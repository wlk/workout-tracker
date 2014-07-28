package models

import com.github.nscala_time.time.Imports._
import play.api.libs.json._
import java.text.DecimalFormat

case class Workout(id: Int, userId: Int, name: String, date: String, distanceMeters: Int, durationSeconds: Int)

object Workout {
  var workouts = Set(
    Workout(1, 1, "morning run", DateTime.now.hour(6).toString("yyyy-MM-dd"), 100, 9),
    Workout(2, 1, "evening run", DateTime.now.hour(18).toString("yyyy-MM-dd"), 2332, 322),
    Workout(3, 1, "evening run last week", DateTime.now.day(18).toString("yyyy-MM-dd"), 100, 9),
    Workout(3, 1, "evening run previous week", DateTime.now.day(7).toString("yyyy-MM-dd"), 100, 9),
    Workout(4, 2, "run for different user", DateTime.now.toString("yyyy-MM-dd"), 100, 9)
  )

  def userCanAccess(id: Int, email: String): Boolean = {
    val user = User.findByEmail(email).get
    val workout = findByIdForUser(user, id).get
    workout.userId == user.userId
  }

  val formatter = new DecimalFormat("#.#")

  def add(email: String, incomingWorkout: IncomingWorkout) = {
    val user = User.findByEmail(email).get

    var newId = 0 //ugly

    if(!workouts.isEmpty){
      newId = workouts.map(w => w.id).max + 1
    }

    val workout = Workout(newId, user.userId, incomingWorkout.name, incomingWorkout.date, incomingWorkout.distanceMeters, incomingWorkout.durationSeconds)

    if(!exists(workout.id)){
      this.workouts = this.workouts + workout
    }
  }

  def edit(email: String, incomingWorkout: IncomingWorkout, id: Int) = {
    val user = User.findByEmail(email).get

    if(exists(id)) {
      val oldWorkout = findByIdForUser(user, id).get
      val newWorkout = Workout(id, user.userId, incomingWorkout.name, incomingWorkout.date, incomingWorkout.distanceMeters, incomingWorkout.durationSeconds)
      this.workouts = this.workouts - oldWorkout + newWorkout
    }
  }

  def exists(id: Int) = this.workouts.exists(_.id == id)

  def getRange(user: User, from: String, to: String) = {
    this.workouts.filter( w => w.date >= from && w.date <= to && w.userId == user.userId).toList
  }

  def findAll(user: User) = Workout.workouts.filter(w => w.userId == user.userId).toList.sortBy(_.id)

  def findByIdForUser(user: User, id: Int) = {
    this.workouts.find( w => w.id == id && w.userId == user.userId )
  }

  def deleteAll(){
    workouts = Set()
  }

  def delete(email: String, id: Int){
    val user = User.findByEmail(email).get

    findByIdForUser(user, id).map( oldWorkout =>
        this.workouts = this.workouts - oldWorkout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }

  //Workout to Json
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



}
