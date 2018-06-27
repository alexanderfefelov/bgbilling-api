package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule
import org.joda.time.DateTime

import scala.xml.XML

object ContractModule extends BaseModule {

  override def module = "contract"

  def newContract(date: DateTime, pattern_id: Long, super_id: Long, sub_mode: Int, params: String, title: String, custom_title: String): Long = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "NewContract",
      "date" -> date.formatted("dd.MM.yyyy"),
      "pattern_id" -> pattern_id.toString,
      "super_id" -> super_id.toString,
      "sub_mode" -> sub_mode.toString,
      "params" -> params,
      "title" -> title,
      "custom_title" -> custom_title
    )
    (XML.loadString(responseText) \ "data" \ "contract" \ "@id").text.toLong
  }

  def updateContractMode(cid: Long, value: String): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "UpdateContractMode",
      "cid" -> cid.toString,
      "value" -> value
    )
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def contractModuleAdd(cid: Long, module_ids: List[Long]): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "ContractModuleAdd",
      "cid" -> cid.toString,
      "module_ids" -> module_ids.mkString(",")
    )
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

}
