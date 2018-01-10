package com.github.alexanderfefelov.bgbilling.api.soap.util

import com.typesafe.config.ConfigFactory

trait BgBillingApiSoapConfig {

  val CONFIG_PREFIX_API: String = "bgbilling-api-soap"
  val CONFIG_PREFIX_SOAP: String = s"$CONFIG_PREFIX_API.soap"
  val CONFIG_PREFIX_BGBILLING: String = s"$CONFIG_PREFIX_API.bgbilling"

  private val config = ConfigFactory.load()

  val url: String = config.getString(s"$CONFIG_PREFIX_SOAP.url")
  val username: String = config.getString(s"$CONFIG_PREFIX_SOAP.username")
  val password: String = config.getString(s"$CONFIG_PREFIX_SOAP.password")

  def serviceBaseAddress(serviceName: String) = config.getString(s"$CONFIG_PREFIX_SOAP.service-base-addresses.$serviceName")

}
