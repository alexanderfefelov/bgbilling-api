package com.github.alexanderfefelov.bgbilling.api.action.kernel

import com.github.alexanderfefelov.bgbilling.api.action.util.BaseModule

object TariffModule extends BaseModule {

  override def module = "tariff"

  def addTariffPlan(used: Int) = {
    val (responseStatusCode, responseText, headers) = executeHttpPostRequest(
      "action" -> "AddTariffPlan",
      "used" -> used.toString
    )
  }

}
