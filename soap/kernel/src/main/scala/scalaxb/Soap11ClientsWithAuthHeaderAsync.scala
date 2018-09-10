package scalaxb

import java.net._

import com.github.alexanderfefelov.bgbilling.api.soap.util.ApiSoapConfig
import soapenvelope11._

import scala.concurrent.{ExecutionContext, Future}
import scala.xml._

trait Soap11ClientsWithAuthHeaderAsync extends Soap11ClientsAsync with ApiSoapConfig { this: HttpClientsAsync =>

  override lazy val soapClient: Soap11ClientAsync = new Soap11ClientWithAuthHeaderAsync {}

  trait Soap11ClientWithAuthHeaderAsync extends Soap11ClientAsync {

    override def requestResponse(body: NodeSeq, headers: NodeSeq, scope: NamespaceBinding, address: URI, webMethod: String, action: Option[URI])(implicit executionContext: ExecutionContext): Future[(NodeSeq, NodeSeq)] = {
      val bodyRecords = body map { DataRecord(None, None, _) }
      val newHeaders = Seq(<auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd={soapPassword} user={soapUsername}></auth>)
      val headerOption = newHeaders.headOption map { _ =>
        Header(newHeaders map {DataRecord(None, None, _)}, Map())
      }
      val envelope = Envelope(headerOption, Body(bodyRecords, Map()), Nil, Map())
      buildResponse(soapRequest(Some(envelope), scope, address, webMethod, action))
    }

  }

}
