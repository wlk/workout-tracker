import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.Json

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class UsersSpec extends Specification {

  "Users API" should {
    "allow to create new users" in new WithApplication{
      val body = "{\"email\": \"abc@abc.com\", \"password\": \"abc123\"}"
      val json = Json.parse(body)

      val created = route(FakeRequest(POST, controllers.routes.Users.create().url), json).get

      status(created) must equalTo(OK)
      contentType(created) must beSome.which(_ == "text/plain")
      contentAsString(created) must contain ("created")
    }
  }
}
