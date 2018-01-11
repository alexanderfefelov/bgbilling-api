package com.github.alexanderfefelov.bgbilling.api.soap.util

import com.github.alexanderfefelov.bgbilling.api.util._

trait ApiSoapConfig extends ApiConfig {

  val CONFIG_PREFIX_API_SOAP: String = "bgbilling-api-soap"

  val soapUrl: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.url")
  val soapUsername: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.username")
  val soapPassword: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.password")

  def soapServiceBaseAddress(serviceName: String): String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.service-base-address.$serviceName")

}
