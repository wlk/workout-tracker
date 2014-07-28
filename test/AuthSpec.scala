import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class AuthSpec extends Specification {

  "Auth" should {
    "show login form on login" in new WithApplication{
      val beforeLogin = route(FakeRequest(GET, controllers.routes.Auth.login().url)).get

      status(beforeLogin) must equalTo(OK)
      contentType(beforeLogin) must beSome.which(_ == "text/html")
      contentAsString(beforeLogin) must contain ("Sign in")
    }

    "show redirect on" in new WithApplication{
      val logout = route(FakeRequest(GET, controllers.routes.Auth.logout().url)).get

      status(logout) must equalTo(303)
      redirectLocation(logout) must beSome.which(_ == "/")
    }

    "show signup page and 400 when form is incorrect" in new WithApplication{
      val signup = route(FakeRequest(GET, controllers.routes.Auth.create().url)).get

      status(signup) must equalTo(400)
      contentType(signup) must beSome.which(_ == "text/html")
      contentAsString(signup) must contain ("Please create account")
    }

  }
}
