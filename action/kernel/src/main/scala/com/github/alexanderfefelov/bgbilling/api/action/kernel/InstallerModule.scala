package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule

object InstallerModule extends BaseModule {

  override def module = "installer"

  case class GetInstalledModulesRecord(name: String, module_name: String, version: String, build: String)

  def getInstalledModules: List[GetInstalledModulesRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetInstalledModules")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <installed_modules serverversion="7.1">
    //        <item build="257" module_name="bill" name="bill" version="7.1"/>
    //        <item build="96" module_name="ru.bitel.bgbilling.plugins.bonus" name="ru.bitel.bgbilling.plugins.bonus" version="7.1"/>
    //        <item build="189" module_name="card" name="card" version="7.1"/>
    //        <item build="31" module_name="ru.bitel.bgbilling.plugins.dispatch" name="ru.bitel.bgbilling.plugins.dispatch" version="7.1"/>
    //        <item build="601" module_name="inet" name="inet" version="7.1"/>
    //        <item build="8" module_name="moneta" name="moneta" version="7.1"/>
    //        <item build="175" module_name="mps" name="mps" version="7.1"/>
    //        <item build="169" module_name="npay" name="npay" version="7.1"/>
    //        <item build="15" module_name="qiwi" name="qiwi" version="7.1"/>
    //        <item build="158" module_name="rscm" name="rscm" version="7.1"/>
    //        <item build="14" module_name="subscription" name="subscription" version="7.1"/>
    //    </installed_modules>
    //</data>
    (responseXml \\ "item").map(x =>
      GetInstalledModulesRecord(
        (x \ "@name").text,
        (x \ "@module_name").text,
        (x \ "@version").text,
        (x \ "@build").text
      )
    ).toList
  }

  case class GetInstalledPluginsRecord(id: String)

  def getInstalledPlugins: List[GetInstalledPluginsRecord] = {
    val responseXml = executeHttpPostRequest("action" -> "GetInstalledPlugins")
    //<?xml version="1.0" encoding="UTF-8"?>
    //<data status="ok">
    //    <plugin_list>
    //        <plugin id="ru.bitel.bgbilling.plugins.dispatch"/>
    //        <plugin id="ru.bitel.bgbilling.plugins.bonus"/>
    //    </plugin_list>
    //</data>
    (responseXml \\ "plugin").map(x =>
      GetInstalledPluginsRecord(
        (x \ "@id").text
      )
    ).toList
  }

}
