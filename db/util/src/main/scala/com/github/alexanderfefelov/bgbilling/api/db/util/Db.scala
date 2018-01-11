package com.github.alexanderfefelov.bgbilling.api.db.util

import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}

object Db extends ApiDbConfig {

  def init(): Unit = {
    Class.forName(dbDriver)

    val connectionPoolSettings = ConnectionPoolSettings(
      initialSize = 5,
      maxSize = 20,
      connectionTimeoutMillis = 3000L,
      validationQuery = "select 1 from user"
    )

    ConnectionPool.add('default, dbUrl, dbUsername, dbPassword, connectionPoolSettings)
  }

}
