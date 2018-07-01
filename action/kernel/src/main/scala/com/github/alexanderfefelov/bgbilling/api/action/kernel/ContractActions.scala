package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions
import org.joda.time.DateTime

object ContractActions extends BaseActions {

  override def module = "contract"

  def newContract(date: DateTime, pattern_id: Long, super_id: Long, sub_mode: Int, params: String, title: Option[String], custom_title: Option[String]): Long = {
    val responseXml = executeHttpPostRequest("action" -> "NewContract",
      "date" -> date.formatted(DATE_FORMAT),
      "pattern_id" -> pattern_id.toString,
      "super_id" -> super_id.toString,
      "sub_mode" -> sub_mode.toString,
      "params" -> params,
      optionalArg("title", title),
      optionalArg("custom_title", custom_title)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <contract id="4" title="20160315-001"/>
    //</data>
    (responseXml \\ "contract" \ "@id").text.toLong
  }

  def updateContractMode(cid: Long, value: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractMode",
      "cid" -> cid.toString,
      "value" -> value
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  def updateParameterType1(cid: Long, pid: Long, value: String): Boolean = { // Текстовое поле
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType1",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  def updateParameterType5(cid: Long, pid: Long, value: Int): Boolean = { // Флаг
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType5",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  def updateParameterType6(cid: Long, pid: Long, value: DateTime): Boolean = { // Дата
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType6",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.formatted(DATE_FORMAT)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  def updateListParameter(cid: Long, pid: Long, value: Long, custom_value: Option[String]): Boolean = { // Значение из списка
    val responseXml = executeHttpPostRequest("action" -> "UpdateListParam",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.toString,
      optionalArg("custom_value", custom_value)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  case class ContractParametersRecord(pid: Long, pt: Int, title: String, value: String)

  def contractParameters(cid: Long): List[ContractParametersRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "ContractParameters",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <parameters>
    //        <parameter alwaysVisible="false" history="loopa" pid="1" pt="1" title="Логин" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="2" pt="1" title="Лицевой счёт" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="3" pt="2" title="Адрес подключения" value=", г. Звенигород, ул. Мира, д. 1Б"/>
    //        <parameter alwaysVisible="false" history="loopa" pid="4" pt="9" title="Телефон" value="12345678980[zzzzzzzzzzzzzzz]"/>
    //        <parameter alwaysVisible="false" history="loopa" pid="5" pt="3" title="Email" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="6" pt="1" title="Фамилия" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="7" pt="1" title="Имя" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="8" pt="1" title="Отчество" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="9" pt="7" title="Тип удостоверения личности" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="10" pt="1" title="Данные удостоверения личности" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="11" pt="1" title="Адрес регистрации" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="12" pt="6" title="Дата рождения" value="01.01.1999"/>
    //        <parameter alwaysVisible="false" history="loopa" pid="13" pt="1" title="Место рождения" value=""/>
    //        <parameter alwaysVisible="false" history="loopa" pid="14" pt="1" title="Название" value=""/>
    //    </parameters>
    //    <condel pgid="1"/>
    //</data>
    (responseXml \\ "item").map(x =>
      ContractParametersRecord(
        (x \ "@pid").text.toLong,
        (x \ "@pt").text.toInt,
        (x \ "@title").text,
        (x \ "@value").text
      )
    ).toList
  }

  def contractModuleAdd(cid: Long, module_ids: List[Long]): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "ContractModuleAdd",
      "cid" -> cid.toString,
      "module_ids" -> module_ids.mkString(",")
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  case class GetPatternListRecord(id: Long, title: String)

  def getPatternList: List[GetPatternListRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetPatternList")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <patterns>
    //        <item id="0" title="По умолчанию"/>
    //        <item id="1" title="Ф/Л, дебет"/>
    //        <item id="2" title="Ю/Л, кредит"/>
    //    </patterns>
    //</data>
    (responseXml \\ "item").map(x =>
      GetPatternListRecord(
        (x \ "@id").text.toLong,
        (x \ "@title").text
      )
    ).toList
  }

}
