package controllers

import models._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}

object Reports extends Controller with Secured{

  //gets weekly report for all data
  def allReports = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val reports = Report.findAll(user)
      Ok(Json.toJson(reports))
    }.getOrElse(Forbidden)
  }

  //gets weekly report for data limited by from, to parameters
  def list(from: String, to: String) = IsAuthenticated { username => _ =>
    User.findByEmail(username).map { user =>
      val reports = Report.getRange(user, from, to)
      Ok(Json.toJson(reports))
    }.getOrElse(Forbidden)
  }
}