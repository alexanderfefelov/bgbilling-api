package com.github.alexanderfefelov.bgbilling.api.soap.util

import java.time._

import com.github.alexanderfefelov.bgbilling.api.util._

trait ApiSoapConfig extends ApiConfig {

  val CONFIG_PREFIX_API_SOAP: String = "bgbilling-api-soap"

  val soapUrl: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.url")
  val soapUsername: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.username")
  val soapPassword: String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.password")
  val soapHttpConnectionTimeout: Duration = config.getDuration(s"$CONFIG_PREFIX_API_SOAP.soap.http.connection-timeout")
  val soapHttpReadTimeout: Duration = config.getDuration(s"$CONFIG_PREFIX_API_SOAP.soap.http.read-timeout")
  val soapHttpRequestTimeout: Duration = config.getDuration(s"$CONFIG_PREFIX_API_SOAP.soap.http.request-timeout")

  def soapServiceBaseAddress(serviceName: String): String = config.getString(s"$CONFIG_PREFIX_API_SOAP.soap.service-base-address.$serviceName")

}
