package controllers

import models.User
import play.api.mvc.{Action, Controller}

object Users extends Controller {

  def create() = Action (parse.json) { request =>
    val password = request.body.\("password").toString().replace("\"", "")
    val email = request.body.\("email").toString().replace("\"", "")

    User.create(email, password)

    Ok("created")
  }
}