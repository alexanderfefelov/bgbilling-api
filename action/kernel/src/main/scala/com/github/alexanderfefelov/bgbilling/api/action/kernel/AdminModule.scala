package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule
import org.joda.time.DateTime

object AdminModule extends BaseModule {

  override def module = "admin"

  def runScheduledTask(id: Long): String = {
    val responseXml = executeHttpPostRequest("action" -> "RunScheduledTask",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="message">Задание "Валидатор" поставлено на выполнение</data>
    (responseXml \\ "data").text
  }

  case class ShowCurrentTasksQueueRecord(id: Long, description: String, status: String, startTime: Option[DateTime])
  case class ShowCurrentTasksPeriodicRecord(id: Long, description: String, amount: Int)

  def ShowCurrentTasks: (List[ShowCurrentTasksQueueRecord], List[ShowCurrentTasksPeriodicRecord]) = {
    val responseXml = executeHttpPostRequest("action" -> "ShowCurrentTasks")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <queue_table>
    //        <data>
    //            <row description="Задача валидации (проверки) балансов." id="4" startTime="-" status="В очереди"/>
    //        </data>
    //    </queue_table>
    //    <periodic_table>
    //        <data>
    //            <row amount="1" description="Модуль npay. Задача обсчета абонплат. Код модуля: 2. Код группы договоров: 0" id="2"/>
    //            <row amount="1" description="Модуль RSCM. Задача начислении наработки. Код модуля: 3. Коды договоров: null. За месяц: 2018.06" id="3"/>
    //            <row amount="1" description="Плагин Bonus. Задача, начисление бонусов за приходы." id="4"/>
    //            <row amount="1" description="Плагин Рассылки: Задача отправки сообщений рассылок." id="5"/>
    //        </data>
    //    </periodic_table>
    //</data>
    val queueTable = (responseXml \\ "data" \ "queue_table" \ "data" \ "row").map(x =>
      ShowCurrentTasksQueueRecord(
        (x \ "@id").text.toLong,
        (x \ "@description").text,
        (x \ "@status").text,
        (x \ "@startTime").text match {
          case s if s != "-" => Some(DateTime.parse(s, dateTimeFormatter))
          case _ => None
        }
      )
    ).toList
    val periodicTable = (responseXml \\ "data" \ "periodic_table" \ "data" \ "row").map(x =>
      ShowCurrentTasksPeriodicRecord(
        (x \ "@id").text.toLong,
        (x \ "@description").text,
        (x \ "@amount").text.toInt
      )
    ).toList
    (queueTable, periodicTable)
  }
def getClosedDate: Option[DateTime] = {
    val responseXml = executeHttpPostRequest("action" -> "GetClosedDate")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <closed date="01.01.2018"/>
    //</data>
    (responseXml \\ "closed" \ "@date").text.trim match {
      case s if s.length > 0 => Some(DateTime.parse(s, dateFormatter))
      case _ => None
    }
  }

  def setClosedDate(date: DateTime, typeId: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "SetClosedDate",
      "date" -> date.formatted(DATE_FORMAT),
      "typeId" -> typeId.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \\ "data" \ "@status").text == "ok"
  }

  case class ClosedDateTypeListRecord(id: Int, title: String)

  def closedDateTypeList: List[ClosedDateTypeListRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "ClosedDateTypeList")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <list>
    //        <item id="1" title="Основной"/>
    //    </list>
    //</data>
    (responseXml \\ "item").map(x =>
      ClosedDateTypeListRecord(
        (x \ "@id").text.toInt,
        (x \ "@title").text
      )
    ).toList
  }

}
