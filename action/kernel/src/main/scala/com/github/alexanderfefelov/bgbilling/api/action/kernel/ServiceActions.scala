package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions

object ServiceActions extends BaseActions {

  override def module = "service"

  /**
    * Устанавливает активную конфигурацию модуля.
    *
    * @param mid идентификатор модуля
    * @param config_id идентификатор конфигурации
    * @return конфигурация модуля
    */
  /* В 7.1_1073 этот функционал больше не доступен.
  def setModuleConfig(mid: Int, config_id: Int): Boolean = {
    val responseXml = executeHttpPostRequest("action" -> "SetModuleConfig",
      "mid" -> mid.toString,
      "config_id" -> config_id.toString
    )
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok"/>
    (responseXml \ "@status").text == "ok"
  }
  */

  case class AboutServer(
    version: String, build_number: String, build_time: String, versionstring: String,
    serverfull: String, memory: String, serverlocale: String, servertime: String, uptimetatus: String,
    serverdbcharset: String, serverdbtime: String
  )
  case class AboutModuleRecord(name: String, version: String, build_number: String, build_time: String, versionstring: String)

  /**
    * Получает информацию о сервере.
    *
    * @return информация о сервере
    */
  def about: (AboutServer, Seq[AboutModuleRecord]) = {
    val responseXml = executeHttpPostRequest("action" -> "About")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <modules>
    //        <module build.number="257" build.time="06.04.2018 16:09:36" name="bill" version="7.1" versionstring="7.1.257 / 06.04.2018 16:09:36"/>
    //        <module build.number="189" build.time="08.06.2018 11:12:58" name="card" version="7.1" versionstring="7.1.189 / 08.06.2018 11:12:58"/>
    //        <module build.number="601" build.time="20.06.2018 21:07:47" name="inet" version="7.1" versionstring="7.1.601 / 20.06.2018 21:07:47"/>
    //        <module build.number="8" build.time="21.03.2018 14:09:59" name="moneta" version="7.1" versionstring="7.1.8 / 21.03.2018 14:09:59"/>
    //        <module build.number="175" build.time="08.06.2018 11:13:02" name="mps" version="7.1" versionstring="7.1.175 / 08.06.2018 11:13:02"/>
    //        <module build.number="169" build.time="06.06.2018 16:25:24" name="npay" version="7.1" versionstring="7.1.169 / 06.06.2018 16:25:24"/>
    //        <module build.number="15" build.time="21.03.2018 14:10:42" name="qiwi" version="7.1" versionstring="7.1.15 / 21.03.2018 14:10:42"/>
    //        <module build.number="158" build.time="31.05.2018 19:59:28" name="rscm" version="7.1" versionstring="7.1.158 / 31.05.2018 19:59:28"/>
    //        <module build.number="96" build.time="21.03.2018 14:08:49" name="ru.bitel.bgbilling.plugins.bonus" version="7.1" versionstring="7.1.96 / 21.03.2018 14:08:49"/>
    //        <module build.number="31" build.time="14.06.2018 04:21:35" name="ru.bitel.bgbilling.plugins.dispatch" version="7.1" versionstring="7.1.31 / 14.06.2018 04:21:35"/>
    //        <module build.number="14" build.time="21.03.2018 14:11:13" name="subscription" version="7.1" versionstring="7.1.14 / 21.03.2018 14:11:13"/>
    //    </modules>
    //    <server build.number="986" build.time="20.06.2018 19:21:09" memory="89M/214M/455M"
    //            server="os: Linux; java: GraalVM 1.0.0-rc2, v.1.8.0_171&#10;    ВНИМАНИЕ: Виртуальная машина GraalVM 1.0.0-rc2 не рекомендуется"
    //            serverdbcharset="connection: utf8(utf8_unicode_ci), database: utf8(utf8_unicode_ci)"
    //            serverdbtime="master: 27.06.2018 08:50 MSK, slave@3: 27.06.2018 08:50 MSK"
    //            serverfull="os: Linux amd64, 4.4.0-116-generic&#10;java: GraalVM 1.0.0-rc2, v.1.8.0_171&#10;jre home: /graalvm-ce-1.0.0-rc2/jre"
    //            serverlocale="en_US" servertime="27.06.2018 08:50 MSK +0300 (Europe/Moscow)"
    //            uptimestatus="Started: 26.06.2018 03:27:38 Uptime: 1 d 05:22:30" version="7.1"
    //            versionstring="7.1.986 / 20.06.2018 19:21:09"/>
    //</data>
    val server = (responseXml \\ "server").map(x =>
      AboutServer(
        (x \ "@version").text,
        (x \ "@build.number").text,
        (x \ "@build.time").text,
        (x \ "@versionstring").text,
        (x \ "@serverfull").text,
        (x \ "@memory").text,
        (x \ "@serverlocale").text,
        (x \ "@servertime").text,
        (x \ "@uptimestatus").text,
        (x \ "@serverdbcharset").text,
        (x \ "@serverdbtime").text
      )
    ).toList.head
    val modules = (responseXml \\ "module").map(x =>
      AboutModuleRecord(
        (x \ "@name").text,
        (x \ "@version").text,
        (x \ "@build.number").text,
        (x \ "@build.time").text,
        (x \ "@versionstring").text
      )
    ).toList
    (server, modules)
  }

}
