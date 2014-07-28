package controllers

import models.User
import play.api.mvc.{Action, Controller}

object Users extends Controller {

  //action to create new user, reads data from json parameters
  def create() = Action (parse.json) { request =>
    val password = request.body.\("password").toString().replace("\"", "")
    val email = request.body.\("email").toString().replace("\"", "")

    User.create(email, password)

    Ok("created")
  }
}