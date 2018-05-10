package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule

import scala.xml.XML

object TariffModule extends BaseModule {

  override def module = "tariff"

  def addTariffPlan(used: Int): (Long, Long) = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest("action" -> "AddTariffPlan",
      "used" -> used.toString
    )
    val id = (XML.loadString(responseText) \ "data" \ "tariffPlan" \ "@id").text.toLong
    val tree_id = (XML.loadString(responseText) \ "data" \ "tariffPlan" \ "@tree_id").text.toLong
    (id, tree_id)
  }

  def updateTariffPlan(tpid: Long, face: Int, title: String, title_web: String, use_title_in_web: Int, values: String, tpused: Int, config: String, mask: String): Boolean = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest("action" -> "UpdateTariffPlan",
      "tpid" -> tpid.toString,
      "face" -> face.toString,
      "title" -> title,
      "title_web" -> title_web,
      "use_title_in_web" -> use_title_in_web.toString,
      "values" -> values,
      "tpused" -> tpused.toString,
      "config" -> config,
      "mask" -> mask
    )
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def createMtree(mid: Long, parent_tree: Long, tree: Long): Boolean = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest("action" -> "CreateMtree",
      "mid" -> mid.toString,
      "parent_tree" -> parent_tree.toString,
      "tree" -> tree.toString
    )
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def modifTariffNode_create(parent: Long, mtree_id: Long, typ: String): Unit = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "create",
      "parent" -> parent.toString,
      "mtree_id" -> mtree_id.toString,
      "type" -> typ
    )
    (XML.loadString(responseText) \ "data" \ "@id").text.toLong
  }

  def modifTariffNode_update(id: Long, data: String): Unit = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "update",
      "id" -> id.toString,
      "data" -> data
    )
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

}
