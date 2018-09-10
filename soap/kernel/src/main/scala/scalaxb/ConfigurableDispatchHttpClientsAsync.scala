package scalaxb

import java.net._
import java.nio.charset.Charset

import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig

import scala.concurrent.ExecutionContext

trait ConfigurableDispatchHttpClientsAsync extends HttpClientsAsync with ApiSoapConfig {

  lazy val httpClient = new ConfigurableDispatchHttpClient {}

  trait ConfigurableDispatchHttpClient extends HttpClient {
    import dispatch._

    // Keep it lazy. See https://github.com/eed3si9n/scalaxb/pull/279
    lazy val http = Http.withConfiguration(_.
      setConnectTimeout(soapHttpConnectionTimeout.toMillis.toInt).
      setReadTimeout(soapHttpReadTimeout.toMillis.toInt).
      setRequestTimeout(soapHttpRequestTimeout.toMillis.toInt)
    )

    def request(in: String, address: URI, headers: Map[String, String])(implicit executionContext: ExecutionContext): Future[String] = {
      val req = url(address.toString).setBodyEncoding(Charset.forName("UTF-8")) <:< headers << in
      http(req > as.String)
    }
  }

}
