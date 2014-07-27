package controllers

import java.text.DecimalFormat

import models.Report
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

object Reports extends Controller {
  val formatter = new DecimalFormat("#.#")

  def thisWeek = Action {
    val workouts = Report.findAll
    Ok(getSummary(workouts))
  }

  def list(from: String, to: String) = Action {
    val workouts = Report.getRange(from, to)
    Ok(getSummary(workouts))
  }

  def getSummary(w: List[Report]) = {
    Nil
  }
}