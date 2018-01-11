package com.github.alexanderfefelov.bgbilling.api.db.util

import com.github.alexanderfefelov.bgbilling.api.util._

trait ApiDbConfig extends ApiConfig {

  val CONFIG_PREFIX_API_DB: String = "bgbilling-api-db"

  val dbDriver: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.dbDriver")
  val dbUrl: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.dbUrl")
  val dbUsername: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.dbUsername")
  val dbPassword: String = config.getString(s"$CONFIG_PREFIX_API_DB.db.dbPassword")

}
