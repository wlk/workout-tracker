import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class WorkoutsSpec extends Specification {

  "Workouts API" should {
    "redirect on unauthorized all workouts" in new WithApplication{
      val home = route(FakeRequest(GET, controllers.routes.Workouts.all.url )).get
      status(home) must equalTo(303)
    }

    "redirect on unauthorized workouts in range" in new WithApplication{
      val home = route(FakeRequest(GET, controllers.routes.Workouts.all.url + "/2014-01-01/2015-01-01" )).get
      status(home) must equalTo(303)
    }

    "redirect on unauthorized one workout" in new WithApplication{
      val home = route(FakeRequest(GET, controllers.routes.Workouts.show(0).url )).get
      status(home) must equalTo(303)
    }
  }
}
