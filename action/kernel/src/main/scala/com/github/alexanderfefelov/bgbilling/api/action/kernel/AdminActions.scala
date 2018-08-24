package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions
import org.joda.time.DateTime

object AdminActions extends BaseActions {

  override def module = "admin"

  /**
    * Ставит задание в очередь на выполнение.
    *
    * @param id идентификатор задания
    * @return текстовое описание результата
    */
  def runScheduledTask(id: Int): String = {
    val responseXml = executeHttpPostRequest("action" -> "RunScheduledTask",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="message">Задание "Валидатор" поставлено на выполнение</data>
    (responseXml \\ "data").text
  }

  case class ShowCurrentTasksQueueRecord(id: Int, description: String, status: String, startTime: Option[DateTime])
  case class ShowCurrentTasksPeriodicRecord(id: Int, description: String, amount: Int)

  /**
    * Получает информацию о состоянии планировщика.
    *
    * @return ???
    */
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
        (x \ "@id").text.toInt,
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
        (x \ "@id").text.toInt,
        (x \ "@description").text,
        (x \ "@amount").text.toInt
      )
    ).toList
    (queueTable, periodicTable)
  }

  /**
    * Получает дату окончания закрытого периода.
    *
    * @return дата окончания закрытого периода
    */
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

  /**
    * Устанавливает дату окончания закрытого периода.
    *
    * @param date дата окончания закрытого периода
    * @param typeId тип зарытого периода
    * @return
    */
  def setClosedDate(date: DateTime, typeId: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "SetClosedDate",
      "date" -> date.toString(DATE_FORMAT),
      "typeId" -> typeId.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class ClosedDateTypeListRecord(id: Int, title: String)

  /**
    * Получает список типов закрытых периодов.
    *
    * @return список типов закрытых периодов
    */
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

  case class UserListRecord(id: Int, login: String, status: Int, name: String, title: String, descr: String)

  /**
    * Получает список учётных записей пользователей.
    *
    * @return список учётных записей
    */
  def userList: List[UserListRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "UserList")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <users>
    //        <user descr="" id="1" login="admin" name="admin" status="0" title="admin"/>
    //    </users>
    //</data>
    (responseXml \\ "user").map(x =>
      UserListRecord(
        (x \ "@id").text.toInt,
        (x \ "@login").text,
        (x \ "@status").text.toInt,
        (x \ "@name").text,
        (x \ "@title").text,
        (x \ "@descr").text
      )
    ).toList
  }

  /**
    * Создаёт или обновляет учётную запись пользователей.
    *
    * @param id идентификатор учётной записи
    * @param login логин
    * @param name имя пользователя
    * @param descr описание учётной записи
    * @param user_pswd пароль (plain text)
    * @return
    */
  def userUpdate(id: Int, login: String, name: String, descr: String, user_pswd: String): Boolean = {
    def md5Hash(text: String) : String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8")).map(0xff & _).map { "%02x".format(_) }.foldLeft(""){_ + _}
    val responseXml = executeHttpPostRequest("action" -> "UserUpdate",
      "id" -> (if (id == 0) "new" else id.toString),
      "login" -> login,
      "name" -> name,
      "descr" -> descr,
      "user_pswd" -> md5Hash(user_pswd).toUpperCase
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  /**
    * Удаляет значение параметра типа "Адрес".
    *
    * @param cid идентификатор договора
    * @param pid идентификатор значения параметра
    * @return
    */
  def deleteAddressParameter(cid: Int, pid: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "DeleteAddressParameter",
      "cid" -> cid.toString,
      "pid" -> pid.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  /**
    * Удаляет дом из справочника домов.
    *
    * @param id идентификатор дома
    * @return
    */
  def deleteAddressHouse(id: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "DeleteAddressHouse",
      "id" -> id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }

  case class GetContractGroupListRecord(id: Int, enabled: Boolean, title: String, editable: Boolean, comment: String, contractCount: Int)

  /**
    * Получает список групп договоров.
    *
    * @param contracts считать договора, входящие в группы?
    * @return список групп договоров
    */
  def getContractGroupList(contracts: Boolean): List[GetContractGroupListRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetContractGroupList",
      "contracts" -> contracts.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <data>
    //            <row f0="1" f1="false" f2="Группа 01" f3="false" f4="" f5="0"/>
    // ...
    //            <row f0="49" f1="false" f2="Группа 49" f3="false" f4="" f5="0"/>
    //            <row f0="50" f1="true" f2="Группа 50" f3="true" f4="" f5="1"/>
    //            <row f0="51" f1="false" f2="Группа 51" f3="false" f4="" f5="0"/>
    // ...
    //            <row f0="62" f1="false" f2="Группа 62" f3="false" f4="" f5="0"/>
    //            <row f0="0" f1="true" f2="Служебный" f3="true" f4="" f5="1"/>
    //        </data>
    //    </table>
    //</data>
    (responseXml \\ "row").map(x =>
      GetContractGroupListRecord(
        (x \ "@f0").text.toInt,
        (x \ "@f1").text.toBoolean,
        (x \ "@f2").text,
        (x \ "@f3").text.toBoolean,
        (x \ "@f4").text,
        (x \ "@f5").text.toInt
      )
    ).toList
  }

}
