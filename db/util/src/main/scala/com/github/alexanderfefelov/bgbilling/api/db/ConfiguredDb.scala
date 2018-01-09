package com.github.alexanderfefelov.bgbilling.api.db

import scalikejdbc.config.DBsWithEnv

trait ConfiguredDb {

  DBsWithEnv("bgbilling-api-db").setup('default)

}
