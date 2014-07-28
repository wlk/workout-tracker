import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._


@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the login page" in new WithApplication{
      val home = route(FakeRequest(GET, "/login")).get
      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Sign in")
    }

    "show redirect for not logged in user" in new WithApplication{
      val main = route(FakeRequest(GET, "/")).get
      status(main) must equalTo(303)
      redirectLocation(main) must beSome.which(_ == "/login")
    }
  }
}
