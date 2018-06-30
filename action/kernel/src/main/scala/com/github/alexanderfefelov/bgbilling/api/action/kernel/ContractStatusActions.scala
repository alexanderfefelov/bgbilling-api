package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object ContractStatusActions extends BaseModule {

  override def module = "contract.status"

  case class StatusListRecord(id: Long, title: String)

  def statusList(onlyManual: Int): List[StatusListRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "StatusList",
      "onlyManual" -> onlyManual.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <list>
    //        <item id="0" title="Активен"/>
    //        <item id="1" title="В отключении"/>
    //        <item id="2" title="Отключен"/>
    //        <item id="3" title="Закрыт"/>
    //        <item id="4" title="Приостановлен"/>
    //        <item id="5" title="В подключении"/>
    //    </list>
    //</data>
    (responseXml \\ "item").map(x =>
      StatusListRecord(
        (x \ "@id").text.toInt,
        (x \ "@title").text
      )
    ).toList
  }

  case class ContractStatusTableRecord(id: Long, status: String, period: String, comment: String)

  def contractStatusTable(cid: Long): List[ContractStatusTableRecord]= {
    val responseXml = executeHttpPostRequest("action" -> "ContractStatusTable",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <data>
    //            <row comment="Статус установлен сервером" id="1" period="26.06.2018-26.06.2018" status="Активен"/>
    //            <row comment="" id="4" period="27.06.2018-29.06.2018" status="Активен"/>
    //            <row comment="" id="6" period="30.06.2018-30.07.2018" status="Приостановлен"/>
    //            <row comment="" id="5" period="31.07.2018-…" status="Активен"/>
    //        </data>
    //    </table>
    //</data>
    (responseXml \\ "row").map(x =>
      ContractStatusTableRecord(
        (x \ "@id").text.toLong,
        (x \ "@status").text,
        (x \ "@period").text,
        (x \ "@comment").text
      )
    ).toList
  }

  case class ContractStatusLogRecord(date: DateTime, user: String, status: String, period: String, comment: String)

  def contractStatusLog(cid: Long): List[ContractStatusLogRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "ContractStatusLog",
      "cid" -> cid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <data>
    //            <row comment="Статус установлен сервером" date="26.06.2018 09:33:11" period="26.06.2018-…" status="Активен" user="Сервер"/>
    //            <row comment="" date="27.06.2018 03:44:25" period="27.06.2018-…" status="Активен" user="admin"/>
    //            <row comment="" date="27.06.2018 04:40:30" period="30.06.2018-30.07.2018" status="Приостановлен" user="admin"/>
    //        </data>
    //    </table>
    //</data>
    (responseXml \\ "row").map(x =>
      ContractStatusLogRecord(
        DateTime.parse((x \ "@date").text, DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss")),
        (x \ "@user").text,
        (x \ "@status").text,
        (x \ "@period").text,
        (x \ "@comment").text
      )
    ).toList
  }

}
