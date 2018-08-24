package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions

object TariffActions extends BaseActions {

  override def module = "tariff"

  /**
    * Создаёт тариф.
    *
    * @param used тариф используется? 0 - нет, 1 - да
    * @return (идентификатор тарифа, идентификатор тарифного дерева)
    */
  def addTariffPlan(used: Int): (Int, Int) = {
    val responseXml = executeHttpPostRequest("action" -> "AddTariffPlan",
      "used" -> used.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <tariffPlan face="0" id="4" mask="" title="New tariff plan" titleWeb="New tariff plan" tree_id="4" useTitleInWeb="1" used="0">
    //        <config/>
    //    </tariffPlan>
    //</data>
    val tariffPlan = responseXml \\ "tariffPlan"
    val id = (tariffPlan \ "@id").text.toInt
    val tree_id = (tariffPlan \ "@tree_id").text.toInt
    (id, tree_id)
  }

  /**
    * Изменяет тариф.
    *
    * @param tpid идентификатор тарифа
    * @param face 0 - физ. лицо, 1 - юр. лицо
    * @param title основное название тарифа
    * @param title_web название тарифа в личном кабинете
    * @param use_title_in_web использовать основное название тарифа в личном кабинете? 0 - нет, 1 - да
    * @param values ???
    * @param tpused тариф используется? 0 - нет, 1 - да
    * @param config конфигурация тарифа
    * @param mask ???
    * @return
    */
  def updateTariffPlan(tpid: Int, face: Int, title: String, title_web: String, use_title_in_web: Int, values: String, tpused: Int, config: String, mask: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateTariffPlan",
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
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  /**
    * Клонирует тариф.
    *
    * @param tpid идентификатор тарифа-источника
    * @return (идентификатор тарифа, идентификатор тарифного дерева)
    */
  def copyTariffPlan(tpid: Int): (Int, Int) = {
    val responseXml = executeHttpPostRequest("action" -> "CopyTariffPlan",
      "tpid" -> tpid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <tariffPlan face="0" id="5" mask="" title="Internet-1 copy" titleWeb="Internet-1 copy" tree_id="5" useTitleInWeb="1" used="1">
    //        <config/>
    //    </tariffPlan>
    //</data>
    val tariffPlan = responseXml \\ "tariffPlan"
    val id = (tariffPlan \ "@id").text.toInt
    val tree_id = (tariffPlan \ "@tree_id").text.toInt
    (id, tree_id)
  }

  /**
    * ???
    *
    * @param mid идентификатор модуля
    * @param parent_tree ???
    * @param tree ???
    * @return
    */
  def createMtree(mid: Int, parent_tree: Int, tree: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "CreateMtree",
      "mid" -> mid.toString,
      "parent_tree" -> parent_tree.toString,
      "tree" -> tree.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  /**
    * Создаёт узел тарифного дерева.
    *
    * @param parent ???
    * @param mtree_id ???
    * @param typ тип узла тарифного дерева
    * @return идентификатор узла тарифного дерева
    */
  def modifTariffNode_create(parent: Int, mtree_id: Int, typ: String): Int = {
    val responseXml = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "create",
      "parent" -> parent.toString,
      "mtree_id" -> mtree_id.toString,
      "type" -> typ
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data id="31" status="ok"/>
    (responseXml \\ "data" \ "@id").text.toInt
  }

  /**
    * Изменяет узел тарифного дерева.
    *
    * @param id идентификатор узла тарифного дерева
    * @param data данные узла тарифного дерева
    * @return
    */
  def modifTariffNode_update(id: Int, data: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "update",
      "id" -> id.toString,
      "data" -> data
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  /**
    * Удаляет узел тарифного дерева.
    *
    * @param id идентификатор узла тарифного дерева
    * @return
    */
  def modifTariffNode_delete(id: Int): Boolean = { // Удаляет ветку, начинающуюся с id
    val responseXml = executeHttpPostRequest("action" -> "ModifTariffNode",
      "command" -> "delete",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class GetMtreeRecord(id: Int, parent: Int, typ: String, data: String, deep: Int, editable: Boolean)

  /**
    * ???
    *
    * @param tree_id ???
    * @param mid ???
    * @return ???
    */
  def getMtree(tree_id: Int, mid: Int): List[GetMtreeRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetMTree",
      "tree_id" -> tree_id.toString,
      "mid" -> mid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data id="1" status="ok">
    //    <item data="" deep="0" editable="true" id="1" parent="0" type="root"/>
    //    <item data="trafficTypeId&amp;0,1,2" deep="0" editable="true" id="2" parent="1" type="trafficType"/>
    //    <item data="serviceId&amp;1" deep="0" editable="true" id="3" parent="2" type="serviceSet"/>
    //    <item data="type&amp;2%col&amp;1%cost&amp;0.0" deep="0" editable="true" id="4" parent="2" type="cost"/>
    //    <item data="type&amp;5%col&amp;1%cost&amp;0.0" deep="0" editable="true" id="5" parent="2" type="cost"/>
    //    <item data="inetOptionId&amp;2" deep="0" editable="true" id="6" parent="2" type="optionAdd"/>
    //</data>
    (responseXml \\ "item").map(x =>
      GetMtreeRecord(
        (x \ "@id").text.toInt,
        (x \ "@parent").text.toInt,
        (x \ "@type").text,
        (x \ "@data").text,
        (x \ "@deep").text.toInt,
        (x \ "@editable").text.toBoolean
      )
    ).toList
  }

}
