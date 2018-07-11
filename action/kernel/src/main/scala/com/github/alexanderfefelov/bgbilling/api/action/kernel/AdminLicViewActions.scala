package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseActions
import org.joda.time.DateTime

object AdminLicViewActions extends BaseActions {

  override def module = "admin.licview"

  case class LicViewRecord(name: String, dateavail: DateTime, maxclient: Int, curclient: Int)

  def licView: List[LicViewRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "LicView")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <table>
    //        <module curclient="0" dateavail="17.07.2018" maxclient="0" name="alfabank"/>
    //        <module curclient="0" dateavail="17.07.2018" maxclient="0" name="assist"/>
    //        <module curclient="0" dateavail="17.07.2018" maxclient="20" name="bill"/>
    //...
    //        <module curclient="0" dateavail="17.07.2018" maxclient="0" name="wm"/>
    //        <module curclient="0" dateavail="17.07.2018" maxclient="0" name="yamoney"/>
    //    </table>
    //</data>
    (responseXml \\ "module").map(x =>
      LicViewRecord(
        (x \ "@name").text,
        DateTime.parse((x \ "@dateavail").text, dateFormatter),
        (x \ "@maxclient").text.toInt,
        (x \ "@curclient").text.toInt
      )
    ).toList

  }

}
