package controllers

import play.api.mvc.{Action, Controller}

object Report extends Controller {
  def thisWeek() = Action {
    Ok("")
  }

  def list(from: String, to: String) = Action {
    Ok("")
  }
}