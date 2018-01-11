package com.github.alexanderfefelov.bgbilling.api.util

import com.typesafe.config.{Config, ConfigFactory}

trait ApiConfig {

  val CONFIG_PREFIX_API: String = "bgbilling-api"

  val config: Config = ConfigFactory.load()

  def bgBillingModuleId(moduleName: String): Int = config.getInt(s"$CONFIG_PREFIX_API.bgbilling.module-id.$moduleName")

}
