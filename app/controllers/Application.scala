package controllers

import models.User
import play.api.mvc._

object Application extends Controller with Secured {

  //this will load application interface (the one written in js)
  def index = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      Ok(views.html.index())
    }.getOrElse(Redirect(routes.Auth.login()))
  }

}