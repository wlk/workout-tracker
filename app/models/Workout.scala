package models

import java.text.SimpleDateFormat

import com.github.nscala_time.time.Imports._
import play.api.libs.json.Json

case class Workout(id: Long, userId: Int, name: String, date: String, distanceMeters: Int, durationSeconds: Int)

object Workout {

  def add(workout: Workout) = addOrEdit(workout)

  def edit(workout: Workout) = addOrEdit(workout)

  def exists(id: Long) = this.workouts.exists(_.id == id)

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
    Workout(1L, 1, "morning run", DateTime.now.hour(6).toString("yyyy-MM-dd"), 100, 9),
    Workout(2L, 1, "evening run", DateTime.now.hour(18).toString("yyyy-MM-dd"), 2332, 322),
    Workout(3L, 1, "evening run last week", DateTime.now.day(18).toString("yyyy-MM-dd"), 100, 9),
    Workout(4L, 2, "run for different user", DateTime.now.toString("yyyy-MM-dd"), 100, 9)
  )

  def findAll = this.workouts.toList.sortBy(_.id)

  def findById(id: Long) = this.workouts.find(_.id == id)

  def delete(id: Int){
    findById(id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }


}
