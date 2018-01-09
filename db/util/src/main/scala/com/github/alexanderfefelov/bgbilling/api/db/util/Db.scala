package com.github.alexanderfefelov.bgbilling.api.db.util

import scalikejdbc.config.DBsWithEnv

object Db {

  def init(): Unit = {
    DBsWithEnv("bgbilling-api-db").setup('default)
  }

}
