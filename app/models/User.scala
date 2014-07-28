package models

case class User(userId: Int, email: String, password: String)

object User {
  var users = Seq(
    User(1, "admin@example.com", "pass"),
    User(2, "secondUser", "pass")
  )

  def authenticate(email: String, password: String): Option[User] = {
    this.users.find(u => u.email == email && u.password == password)
  }

  def findByEmail(email: String): Option[User] = {
    this.users.find(u => u.email == email)
  }
}