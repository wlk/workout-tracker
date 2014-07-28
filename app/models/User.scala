package models


case class User(userId: Int, email: String, password: String)

object User {
  var users = Set (
    User(1, "admin@example.com", "pass"),
    User(2, "secondUser", "pass")
  )

  //performs a check to find if email and password match
  def authenticate(email: String, password: String): Option[User] = {
    this.users.find(u => u.email == email && u.password == password)
  }

  def findByEmail(email: String): Option[User] = {
    this.users.find(u => u.email == email)
  }

  def exists(email: String) = {
    this.users.exists(_.email == email)
  }

  //creates new user, makes sure that emails are unique
  def create(email: String, password: String){
    val newId = users.map(u => u.userId).max + 1
    val newUser = User(newId, email, password)

    if(!exists(email)){
      this.users = this.users + newUser
    }
  }
}