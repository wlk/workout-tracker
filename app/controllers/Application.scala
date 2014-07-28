package controllers

import models.User
import play.api.mvc._

object Application extends Controller with Secured {

  def index = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      Ok(views.html.index())
    }.getOrElse(Redirect(routes.Auth.login()))
  }

}