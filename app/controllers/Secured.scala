package controllers

import play.api.mvc._

import models._

trait Secured {
  private def username(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }

  /**
   * Check if the connected user is a member of this project.
   */
  def isOwnerOf(workoutId: Int)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    if(Workout.userCanAccess(workoutId, user)) {
      f(user)(request)
    } else {
      Results.Forbidden
    }
  }
}