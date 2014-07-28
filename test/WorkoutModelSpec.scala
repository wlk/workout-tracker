import models._
import com.github.nscala_time.time.Imports._
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._


import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class WorkoutModelSpec extends Specification {
  //be careful because those are Seqs not Sets
  var workouts = Seq (
    Workout(1, 1, "morning run", DateTime.now.hour(6).toString("yyyy-MM-dd"), 100, 9),
    Workout(2, 1, "evening run", DateTime.now.hour(18).toString("yyyy-MM-dd"), 2332, 322),
    Workout(3, 1, "evening run last week", DateTime.now.day(18).toString("yyyy-MM-dd"), 100, 9),
    Workout(3, 1, "evening run previous week", DateTime.now.day(7).toString("yyyy-MM-dd"), 100, 9),
    Workout(4, 2, "run for different user", DateTime.now.toString("yyyy-MM-dd"), 100, 9)
  )
  var users = Seq (
    User(1, "admin@example.com", "pass"),
    User(2, "secondUser", "pass")
  )

  "Workout Model" should {
    "find workouts" in {
      Workout.deleteAll()
      val incomingWorkout = IncomingWorkout(workouts(0).name, workouts(0).date, workouts(0).distanceMeters, workouts(0).durationSeconds)
      Workout.add(users(0).email, incomingWorkout)

      val workout = Workout.findByIdForUser(users(0), 0).get

      workout.id must equalTo(0)
    }

    "handle multiple workouts per user" in {
      Workout.deleteAll()
      val incomingWorkout1 = IncomingWorkout("test1", "2014-01-01", 100, 10)
      Workout.add(users(0).email, incomingWorkout1)

      val incomingWorkout2 = IncomingWorkout("test2", "2014-01-02", 100, 12)
      Workout.add(users(0).email, incomingWorkout2)

      val workouts = Workout.findAll(users(0))
      workouts.size must equalTo(2)
    }

  }
}
