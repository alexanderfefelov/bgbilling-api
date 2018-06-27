package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule

import scala.xml.XML

object TariffModule extends BaseModule {

  override def module = "tariff"

  def addTariffPlan(used: Int): (Long, Long) = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "AddTariffPlan",
      "used" -> used.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <tariffPlan face="0" id="4" mask="" title="New tariff plan" titleWeb="New tariff plan" tree_id="4" useTitleInWeb="1" used="0">
    //        <config/>
    //    </tariffPlan>
    //</data>
    val tariffPlan = XML.loadString(responseText) \\ "tariffPlan"
    val id = (tariffPlan \ "@id").text.toLong
    val tree_id = (tariffPlan \ "@tree_id").text.toLong
    (id, tree_id)
  }

  def updateTariffPlan(tpid: Long, face: Int, title: String, title_web: String, use_title_in_web: Int, values: String, tpused: Int, config: String, mask: String): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "UpdateTariffPlan",
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
    // <?xml version="1.0" encoding="UTF-8"?>
    // <data status="ok"/>
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def createMtree(mid: Long, parent_tree: Long, tree: Long): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "CreateMtree",
      "mid" -> mid.toString,
      "parent_tree" -> parent_tree.toString,
      "tree" -> tree.toString
    )
    // <?xml version="1.0" encoding="UTF-8"?>
    // <data status="ok"/>
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def modifTariffNode_create(parent: Long, mtree_id: Long, typ: String): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "create",
      "parent" -> parent.toString,
      "mtree_id" -> mtree_id.toString,
      "type" -> typ
    )
    // <?xml version="1.0" encoding="UTF-8"?>
    // <data status="ok"/>
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

  def modifTariffNode_update(id: Long, data: String): Boolean = {
    val (_, responseText, _) = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "update",
      "id" -> id.toString,
      "data" -> data
    )
    // <?xml version="1.0" encoding="UTF-8"?>
    // <data status="ok"/>
    (XML.loadString(responseText) \ "data" \ "@status").text == "ok"
  }

}
