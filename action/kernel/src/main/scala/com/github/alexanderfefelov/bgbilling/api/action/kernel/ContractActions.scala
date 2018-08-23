package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions
import org.joda.time.DateTime

object ContractActions extends BaseActions {

  override def module = "contract"

  def newContract(date: DateTime, pattern_id: Int, super_id: Int = 0, sub_mode: Int = 0, params: String = "", title: Option[String] = None, custom_title: Option[String] = None): Int = {
    val responseXml = executeHttpPostRequest("action" -> "NewContract",
      "date" -> date.toString(DATE_FORMAT),
      "pattern_id" -> pattern_id.toString,
      "super_id" -> super_id.toString,
      "sub_mode" -> sub_mode.toString,
      "params" -> params,
      optionalStringArg("title", title),
      optionalStringArg("custom_title", custom_title)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <contract id="4" title="20160315-001"/>
    //</data>
    (responseXml \\ "contract" \ "@id").text.toInt
  }

  def deleteContract(cid: Int, save: Int, folder: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "DeleteContract",
      "cid" -> cid.toString,
      "save" -> save.toString,
      "folder" -> folder
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def setDelContract(cid: Int, del: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "SetDelContract",
      "cid" -> cid.toString,
      "del" -> del.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class ContractBalance(summa1: Float, summa2: Float, summa3: Float, summa4: Float, summa5: Float)

  def contractBalance(cid: Int): ContractBalance = {
    val responseXml = executeHttpPostRequest("action" -> "ContractBalance",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table summa1="0.00" summa2="0.00" summa3="0.00" summa4="90.00" summa5="90.00">
    //        <data>
    //            <row f0="июль 2018" f1="Входящий остаток на начало месяца" f2="0.00"/>
    //            <row f0="июль 2018" f1="Приход за месяц" f2="90.00"/>
    //            <row f0="июль 2018" f1="Наработка за месяц" f2="0.00"/>
    //            <row f0="июль 2018" f1="Расход за месяц" f2="0.00"/>
    //            <row f0="июль 2018" f1="Исходящий остаток на конец месяца" f2="90.00"/>
    //        </data>
    //    </table>
    //</data>
    ContractBalance(
      (responseXml \\ "table" \ "@summa1").text.toFloat,
      (responseXml \\ "table" \ "@summa2").text.toFloat,
      (responseXml \\ "table" \ "@summa3").text.toFloat,
      (responseXml \\ "table" \ "@summa4").text.toFloat,
      (responseXml \\ "table" \ "@summa5").text.toFloat
    )
  }

  case class GetContractMemo(subject: String, visibled: Boolean, row: String)

  def getContractMemo(id: Int): GetContractMemo = {
    val responseXml = executeHttpPostRequest("action" -> "GetContractMemo",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <comment subject="foo" visibled="false">
    //        <row>bar</row>
    //    </comment>
    //</data>
    GetContractMemo(
      (responseXml \ "data" \ "comment" \ "@subject").text,
      (responseXml \ "data" \ "comment" \ "@visibled").text.toBoolean,
      (responseXml \ "data" \ "comment" \ "row").text,
    )
  }

  def updateContractMemo(id: Int, cid: Int, subject: String, comment: String, visibled: Boolean): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractMemo",
      "id" -> id.toString,
      "cid" -> cid.toString,
      "subject" -> subject,
      "comment" -> comment,
      "visibled" -> visibled.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def deleteContractMemo(id: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "DeleteContractMemo",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class GetParameterHistoryRecord(when: DateTime, who: String, value: String)

  def getParameterHistory(cid: Int, pid: Int): List[GetParameterHistoryRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetParameterHistory",
      "cid" -> cid.toString,
      "pid" -> pid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <data>
    //            <row value="Швейк" when="01.07.2018 10:38:56" who="admin"/>
    //            <row value="Тихий" when="01.07.2018 13:26:50" who="admin"/>
    //        </data>
    //    </table>
    //</data>
    (responseXml \\ "row").map(x =>
      GetParameterHistoryRecord(
        DateTime.parse((x \ "@when").text, dateTimeFormatter),
        (x \ "@who").text,
        (x \ "@value").text
      )
    ).toList
  }

  def updateContractPassword(cid: Int, value: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractPassword",
      "cid" -> cid.toString,
      "value" -> value
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateContractLimit(cid: Int, value: Double, comment: Option[String] = None): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractLimit",
      "cid" -> cid.toString,
      "value" -> value.toString,
      optionalStringArg("comment", comment)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateContractMode(cid: Int, value: String): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractMode",
      "cid" -> cid.toString,
      "value" -> value
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateContractTitleAndComment(cid: Int, title: String, comment: Option[String] = None, patid: Option[Int] = None): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractTitleAndComment",
      "cid" -> cid.toString,
      "title" -> title,
      optionalStringArg("comment", comment),
      optionalIntArg("patid", patid)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateContractDate1(cid: Int, date: DateTime): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractDate1",
      "id" -> cid.toString,
      "value" -> date.toString(DATE_FORMAT)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateContractDate2(cid: Int, date: DateTime): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractDate2",
      "id" -> cid.toString,
      "value" -> date.toString(DATE_FORMAT)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateParameterType1(cid: Int, pid: Int, value: String): Boolean = { // Текстовое поле
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType1",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateParameterType5(cid: Int, pid: Int, value: Int): Boolean = { // Флаг
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType5",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateParameterType6(cid: Int, pid: Int, value: DateTime): Boolean = { // Дата
    val responseXml = executeHttpPostRequest("action" -> "UpdateParameterType6",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.toString(DATE_FORMAT)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateEmailInfo(cid: Int, pid: Int, email: String, comment: Option[String] = None): Boolean = {
    val data = s"${comment.getOrElse("").trim} <${email.trim}>".trim
    val responseXml = executeHttpPostRequest("action" -> "UpdateEmailInfo",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "e-mail" -> data, // ОПИСАНИЕ1 <email1@acme.com>\nОПИСАНИЕ2 <email2@acme.com>\n...ОПИСАНИЕN <emailN@acme.com>
                        // Любое описание может быть пустым
      "buf" -> ""
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updatePhoneInfo(cid: Int, pid: Int, phone: String, comment: Option[String] = None): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdatePhoneInfo",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "count" -> "1",
      "phone1" -> phone,
      "comment1" -> comment.getOrElse("")
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateListParameter(cid: Int, pid: Int, value: Int, custom_value: Option[String] = None): Boolean = { // Значение из списка
    val responseXml = executeHttpPostRequest("action" -> "UpdateListParam",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "value" -> value.toString,
      optionalStringArg("custom_value", custom_value)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def updateAddressInfo(cid: Int, pid: Int, hid: Int, pod: Int, floor: Int, flat: String, room: String = "", comment: String = "", formatKey: Int = 0): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateAddressInfo",
      "cid" -> cid.toString,
      "pid" -> pid.toString,
      "hid" -> hid.toString,
      "pod" -> pod.toString,
      "floor" -> floor.toString,
      "flat" -> flat,
      "room" -> room,
      "comment" -> comment,
      "formatKey" -> formatKey.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class ContractParametersRecord(pid: Int, pt: Int, title: String, value: String)

  def contractParameters(cid: Int): List[ContractParametersRecord] = {
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
    //        <parameter alwaysVisible="false" history="loopa" pid="5" pt="3" title="Email" value="Бухгалтерия &lt;account@acme.com&gt;; Сисадмин &lt;admin@acme.com&gt;; "/>
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
    (responseXml \\ "parameter").map(x =>
      ContractParametersRecord(
        (x \ "@pid").text.toInt,
        (x \ "@pt").text.toInt,
        (x \ "@title").text,
        (x \ "@value").text
      )
    ).toList
  }

  case class ContractModuleListRecord(id: Int, title: String)

  def contractModuleList(cid: Int): (List[ContractModuleListRecord], List[ContractModuleListRecord]) = {
    val responseXml = executeHttpPostRequest("action" -> "ContractModuleList",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <list_select>
    //        <item id="1" title="Интернет"/>
    //        <item id="2" title="Периодические услуги"/>
    //        <item id="3" title="Товары и разовые услуги"/>
    //    </list_select>
    //    <list_avaliable>
    //        <item id="6" title="Moneta"/>
    //        <item id="9" title="MPS"/>
    //        <item id="7" title="Qiwi"/>
    //        <item id="8" title="Карты"/>
    //        <item id="4" title="Подписки"/>
    //        <item id="5" title="Счета"/>
    //    </list_avaliable>
    //</data>
    val listSelect = (responseXml \\ "data" \ "list_select" \ "item").map(x =>
      ContractModuleListRecord(
        (x \ "@id").text.toInt,
        (x \ "@title").text
      )
    ).toList
    val listAvailable = (responseXml \\ "data" \ "list_available" \ "item").map(x =>
      ContractModuleListRecord(
        (x \ "@id").text.toInt,
        (x \ "@title").text
      )
    ).toList
    (listSelect, listAvailable)
  }

  def contractModuleAdd(cid: Int, module_ids: List[Int]): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "ContractModuleAdd",
      "cid" -> cid.toString,
      "module_ids" -> module_ids.mkString(",")
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def contractModuleDelete(cid: Int, module_id: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "ContractModuleDelete",
      "cid" -> cid.toString,
      "module_id" -> module_id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class GetPatternListRecord(id: Int, title: String)

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
        (x \ "@id").text.toInt,
        (x \ "@title").text
      )
    ).toList
  }

  case class ContractGroupRecord(id: Int, title: String, editable: Boolean)

  def contractGroup(cid: Int): List[ContractGroupRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "ContractGroup",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <groups selected="1125899906842625">
    //        <group editable="1" id="50" title="Группа 50"/>
    //        <group editable="1" id="0" title="Служебный"/>
    //    </groups>
    //</data>
    (responseXml \\ "group").map(x =>
      ContractGroupRecord(
        (x \ "@id").text.toInt,
        (x \ "@title").text,
        (x \ "@editable").text.toInt == 1
      )
    ).toList
  }

  case class ContractTariffPlansRecord(id: Int, tpid: Int, title: String, date1: DateTime, date2: Option[DateTime], period: String, comment: String, pos: Int)

  def contractTariffPlans(cid: Int): List[ContractTariffPlansRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "ContractTariffPlans",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <data>
    //            <row comment="" date1="01.02.1835" date2="" f0="15" f1="4" f2="Разовые услуги" f3="01.02.1835-…" f4="" f5="0" id="15" period="01.02.1835-…" pos="0" title="Разовые услуги" tpid="4"/>
    //            <row comment="" date1="01.02.1835" date2="" f0="16" f1="5" f2="Товары" f3="01.02.1835-…" f4="" f5="0" id="16" period="01.02.1835-…" pos="0" title="Товары" tpid="5"/>
    //        </data>
    //    </table>
    //</data>
    (responseXml \\ "row").map(x =>
      ContractTariffPlansRecord(
        (x \ "@id").text.toInt,
        (x \ "@tpid").text.toInt,
        (x \ "@title").text,
        DateTime.parse((x \ "@date1").text, dateFormatter),
        (x \ "@date2").text match {
          case s if s != "" => Some(DateTime.parse(s, dateFormatter))
          case _ => None
        },
        (x \ "@period").text,
        (x \ "@comment").text,
        (x \ "@pos").text.toInt
      )
    ).toList
  }

  def updateContractTariffPlan(id: Int, cid: Int, tpid: Int, comment: String = "", date1: DateTime, date2: Option[DateTime] = None): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "UpdateContractTariffPlan",
      "id" -> id.toString,
      "cid" -> cid.toString,
      "tpid" -> tpid.toString,
      "date1" -> date1.toString(DATE_FORMAT),
      optionalDateArg("date2", date2),
      "comment" -> comment
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def deleteContractTariffPlan(id: Int, cid: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "DeleteContractTariffPlan",
      "id" -> id.toString,
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  def changeStatus(cids: List[Int], status: Int, date1: DateTime, date2: Option[DateTime] = None, comment: Option[String] = None): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "ContractGroupOperation",
      "type" -> "changeStatus",
      "cids" -> cids.mkString(","),
      "status" -> status.toString,
      "date1" -> date1.toString(DATE_FORMAT),
      optionalDateArg("date2", date2),
      optionalStringArg("comment", comment)
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

}
