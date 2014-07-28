package controllers

import java.text.DecimalFormat

import models._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}

object Reports extends Controller {
  def thisWeek = Action {
    val reports = Report.findAll
    Ok(Json.toJson(reports))
  }

  def list(from: String, to: String) = Action {
    val reports = Report.getRange(from, to)
    Ok(Json.toJson(reports))
  }
}