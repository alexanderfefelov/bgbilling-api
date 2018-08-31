package scalaxb

import java.nio.charset.Charset

import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig

trait ConfigurableDispatchHttpClientsAsync extends HttpClientsAsync with ApiSoapConfig { this: ExecutionContextProvider =>

  lazy val httpClient = new ConfigurableDispatchHttpClient {}

  trait ConfigurableDispatchHttpClient extends HttpClient {
    import dispatch._

    // Keep it lazy. See https://github.com/eed3si9n/scalaxb/pull/279
    lazy val http = Http.withConfiguration(_.
      setConnectTimeout(soapHttpConnectionTimeout.toMillis.toInt).
      setReadTimeout(soapHttpReadTimeout.toMillis.toInt).
      setRequestTimeout(soapHttpRequestTimeout.toMillis.toInt)
    )

    def request(in: String, address: java.net.URI, headers: Map[String, String]): concurrent.Future[String] = {
      val req = url(address.toString).setBodyEncoding(Charset.forName("UTF-8")) <:< headers << in
      http(req > as.String)
    }
  }

}
