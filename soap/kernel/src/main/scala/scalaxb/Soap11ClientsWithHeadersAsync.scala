package scalaxb

import scala.concurrent.Future

import soapenvelope11._

trait Soap11ClientsWithHeadersAsync extends Soap11ClientsAsync { this: HttpClientsAsync =>

  override lazy val soapClient: Soap11ClientAsync = new Soap11ClientWithHeadersAsync {}

  trait Soap11ClientWithHeadersAsync extends Soap11ClientAsync {

    override def requestResponse(body: scala.xml.NodeSeq, headers: scala.xml.NodeSeq, scope: scala.xml.NamespaceBinding, address: java.net.URI, webMethod: String, action: Option[java.net.URI]): Future[(scala.xml.NodeSeq, scala.xml.NodeSeq)] = {
      val bodyRecords = body.toSeq map { DataRecord(None, None, _) }
      val h = Seq(<auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd="admin" user="admin"></auth>)
      val headerOption = h.toSeq.headOption map { _ =>
        Header(h.toSeq map {DataRecord(None, None, _)}, Map())
      }
      val envelope = Envelope(headerOption, Body(bodyRecords, Map()), Nil, Map())
      buildResponse(soapRequest(Some(envelope), scope, address, webMethod, action))
    }

  }

}
