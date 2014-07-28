package controllers

import play.api.libs.json._
import play.api.mvc._

import models._

trait Secured {
  def username(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }

  def isOwnerOf(workoutId: Int)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    if(Workout.userCanAccess(workoutId, user)) {
      f(user)(request)
    } else {
      Results.Forbidden
    }
  }
}