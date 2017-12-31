package com.github.alexanderfefelov.bgbilling.api.soap.scalaxb

import com.github.alexanderfefelov.bgbilling.api.soap.util.BgBillingApiSoapConfig
import scalaxb._
import soapenvelope11._

import scala.concurrent.Future

trait Soap11ClientsWithAuthHeaderAsync extends Soap11ClientsAsync with BgBillingApiSoapConfig { this: HttpClientsAsync =>

  override lazy val soapClient: Soap11ClientAsync = new Soap11ClientWithAuthHeaderAsync {}

  trait Soap11ClientWithAuthHeaderAsync extends Soap11ClientAsync {

    override def requestResponse(body: scala.xml.NodeSeq, headers: scala.xml.NodeSeq, scope: scala.xml.NamespaceBinding, address: java.net.URI, webMethod: String, action: Option[java.net.URI]): Future[(scala.xml.NodeSeq, scala.xml.NodeSeq)] = {
      val bodyRecords = body map { DataRecord(None, None, _) }
      val newHeaders = Seq(<auth xmlns="http://ws.base.kernel.bgbilling.bitel.ru/" pswd={password} user={username}></auth>)
      val headerOption = newHeaders.headOption map { _ =>
        Header(newHeaders map {DataRecord(None, None, _)}, Map())
      }
      val envelope = Envelope(headerOption, Body(bodyRecords, Map()), Nil, Map())
      buildResponse(soapRequest(Some(envelope), scope, address, webMethod, action))
    }

  }

}
