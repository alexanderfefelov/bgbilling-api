package com.github.alexanderfefelov.bgbilling.api.soap.scalaxb

import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import scalaxb.HttpClientsAsync

trait ConfigurableDispatchHttpClientsAsync extends HttpClientsAsync with ApiSoapConfig {

  lazy val httpClient = new ConfigurableDispatchHttpClient {}

  trait ConfigurableDispatchHttpClient extends HttpClient {
    import dispatch._, Defaults._

    // Keep it lazy. See https://github.com/eed3si9n/scalaxb/pull/279
    lazy val http = Http.configure(_.
      setConnectTimeout(soapHttpConnectionTimeout.toMillis.toInt).
      setReadTimeout(soapHttpReadTimeout.toMillis.toInt).
      setRequestTimeout(soapHttpRequestTimeout.toMillis.toInt)
    )

    def request(in: String, address: java.net.URI, headers: Map[String, String]): concurrent.Future[String] = {
      val req = url(address.toString).setBodyEncoding("UTF-8") <:< headers << in
      http(req > as.String)
    }
  }

}
