package controllers

import models._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

object Reports extends Controller with Secured{
  def thisWeek = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val reports = Report.findAll(user)
      Ok(Json.toJson(reports))
    }.getOrElse(Forbidden)
  }

  def list(from: String, to: String) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val reports = Report.getRange(user, from, to)
      Ok(Json.toJson(reports))
    }.getOrElse(Forbidden)
  }
}