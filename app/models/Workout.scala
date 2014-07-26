package models

import java.text.SimpleDateFormat

import com.github.nscala_time.time.Imports._
import play.api.libs.json.Json

case class Workout(id: Long, userId: Int, name: String, date: String, distanceMeters: Int, durationSeconds: Int)

object Workout {

  var workouts = Set(
    Workout(1L, 1, "morning run", DateTime.now.hour(6).toString("yyyy-MM-dd"), 100, 9),
    Workout(2L, 1, "evening run", DateTime.now.hour(18).toString("yyyy-MM-dd"), 2332, 322),
    Workout(3L, 1, "evening run last week", DateTime.now.day(18).toString("yyyy-MM-dd"), 100, 9),
    Workout(4L, 2, "run for different user", DateTime.now.toString("yyyy-MM-dd"), 100, 9)
  )

  def findAll = this.workouts.toList.sortBy(_.id)

  def findById(id: Long) = this.workouts.find(_.id == id)

  def add(workout: Workout) = {
    this.workouts = this.workouts + workout
  }

  def edit(workout: Workout){
    findById(workout.id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout + workout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }

  def delete(workout: Workout){
    findById(workout.id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }

  def delete(id: Int){
    findById(id).map( oldWorkout =>
      this.workouts = this.workouts - oldWorkout
    ).getOrElse(
        throw new IllegalArgumentException("Workout not found")
      )
  }


}
