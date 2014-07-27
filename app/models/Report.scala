package models


case class Report(userId: Int, weekOfYear: Int, distanceMeters: Int, durationSeconds: Int)

object Report {
  def getRange(startDate: String, endDate: String) = {Nil}

  def findAll = {Nil}

}