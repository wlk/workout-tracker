package controllers

import play.api.mvc._

trait Secured {
  def username(request: RequestHeader) = request.session.get("email")

  //redirect to login page if user is not authorized
  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

  //controller action when user is authenticated correctly
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }
}