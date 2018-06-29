package com.github.alexanderfefelov.bgbilling.api.action.util

case class ApiActionException(message: String, exception: Throwable = null) extends RuntimeException(message, exception)
