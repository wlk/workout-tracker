package controllers

import models.User
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Auth extends Controller {

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => User.authenticate(email, password).isDefined
    })
  )

  val signupForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => {
        User.create(email, password)
        User.findByEmail(email).nonEmpty
      }
    })
  )

  //displays login form
  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  //performs logout action
  def logout = Action {
    Redirect(routes.Application.index).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

  //verifies login form, configures session if form is ok
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      user => Redirect(routes.Application.index).withSession("email" -> user._1)
    )
  }

  //verifies signup form, adds user when successful, then redirects to login page
  def create = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.signup(formWithErrors)),
      user => Redirect(routes.Application.index)
    )
  }
}