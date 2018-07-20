package com.github.alexanderfefelov.bgbilling.api.action.util

import java.util

import scala.xml.{Elem, XML}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpPost, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

trait BaseActions extends ApiActionConfig {

  def module: String

  def executeHttpGetRequest(args: (String, String)*): Elem = {
    val parameters = createHttpRequestParameters(args: _*)
    val httpClient = createHttpClient()
    val request = createUriGetRequest(parameters)

    val response = httpClient.execute(request)
    val responseStatusCode = response.getStatusLine.getStatusCode
    val responseText = EntityUtils.toString(response.getEntity)
    val headers = response.getAllHeaders

    response.close()
    httpClient.close()

    val responseXml = XML.loadString(responseText)
    if (!List("ok", "message").contains((responseXml \ "@status").text)) throw ApiActionException(responseText)

    responseXml
  }

  def executeHttpPostRequest(args: (String, String)*): Elem = {
    val parameters = createHttpRequestParameters(args: _*)
    val httpClient = createHttpClient()
    val request = createPostRequest(parameters)

    val response = httpClient.execute(request)
    val responseStatusCode = response.getStatusLine.getStatusCode
    val responseText = EntityUtils.toString(response.getEntity)
    val responseHeaders = response.getAllHeaders

    response.close()
    httpClient.close()

    val responseXml = XML.loadString(responseText)
    if (!List("ok", "message").contains((responseXml \ "@status").text)) throw ApiActionException(responseText)

    responseXml
  }

  private def createHttpRequestParameters(args: (String, String)*): util.ArrayList[NameValuePair] = {
    val nameValuePairs = new util.ArrayList[NameValuePair]()
    nameValuePairs.add(new BasicNameValuePair("user", actionUsername))
    nameValuePairs.add(new BasicNameValuePair("pswd", actionPassword))
    nameValuePairs.add(new BasicNameValuePair("authToSession", "0"))
    nameValuePairs.add(new BasicNameValuePair("module", module))
    args.foreach(a => nameValuePairs.add(new BasicNameValuePair(a._1, a._2)))
    nameValuePairs
  }

  private def createHttpClient(): CloseableHttpClient = {
    HttpClients.custom.build
  }

  private def createUriGetRequest(parameters: util.List[NameValuePair]): HttpUriRequest = {
    val requestBuilder = RequestBuilder
      .get
      .setUri(actionUrl)
      .addHeader("User-Agent", actionUserAgent)
    import scala.collection.JavaConversions._
    for (nvp <- parameters) {
      requestBuilder.addParameter(nvp)
    }
    requestBuilder.build
  }

  private def createPostRequest(parameters: util.List[NameValuePair]): HttpPost = {
    val request = new HttpPost(actionUrl)
    request.addHeader("Content-Type", "application/x-www-form-urlencoded")
    request.addHeader("User-Agent", actionUserAgent)
    request.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"))
    request
  }

}
