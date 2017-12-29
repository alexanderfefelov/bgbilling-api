package com.github.alexanderfefelov.bgbilling.api.soap.util

import com.typesafe.config.ConfigFactory

trait BgBillingApiSoapConfig {

  val CONFIG_PREFIX_API_SOAP: String = "bgbilling-api-soap"
  val CONFIG_PREFIX_BGBILLING: String = s"$CONFIG_PREFIX_API_SOAP.bgbilling"

  private val config = ConfigFactory.load()

  val url: String = config.getString(s"$CONFIG_PREFIX_BGBILLING.url")
  val username: String = config.getString(s"$CONFIG_PREFIX_BGBILLING.username")
  val password: String = config.getString(s"$CONFIG_PREFIX_BGBILLING.password")

  def serviceBaseAddress(serviceName: String) = config.getString(s"$CONFIG_PREFIX_BGBILLING.service-base-addresses.$serviceName")
  def serviceBaseAddress(serviceName: String, moduleId: Int) = config.getString(s"$CONFIG_PREFIX_BGBILLING.service-base-addresses.$serviceName")

}
