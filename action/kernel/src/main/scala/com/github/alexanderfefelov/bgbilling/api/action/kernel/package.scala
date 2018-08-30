package com.github.alexanderfefelov.bgbilling.api.action

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

package object kernel {

  val DATE_FORMAT = "dd.MM.yyyy"
  val DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss"

  val dateFormatter: DateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT)
  val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT)

  def optionalArg(name: String, valueOption: Option[Any]): (String, String) = {
    valueOption match {
      case Some(value: DateTime) => name -> value.toString(DATE_FORMAT)
      case Some(value) => name -> value.toString
      case _ => "" -> ""
    }
  }

  def booleanToInt(boolean: Boolean): Int = if (boolean) 1 else 0

}
