package com.github.alexanderfefelov.bgbilling.api.action.util

import com.github.alexanderfefelov.bgbilling.api.util._

trait ApiActionConfig extends ApiConfig {

  val CONFIG_PREFIX_API_ACTION: String = "bgbilling-api-action"

  val actionUrl: String = config.getString(s"$CONFIG_PREFIX_API_ACTION.action.url")
  val actionUsername: String = config.getString(s"$CONFIG_PREFIX_API_ACTION.action.username")
  val actionPassword: String = config.getString(s"$CONFIG_PREFIX_API_ACTION.action.password")
  val actionUserAgent: String = config.getString(s"$CONFIG_PREFIX_API_ACTION.action.userAgent")

}
