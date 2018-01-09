package com.github.alexanderfefelov.bgbilling.api.db.oss.repository

import com.github.alexanderfefelov.bgbilling.api.db.oss.model._
import scalikejdbc._

object AddressAreaRepository {

  def findAll: List[AddressArea] = {
    DB readOnly { implicit session =>
      sql"select * from address_area".map(AddressArea.*).list().apply()
    }
  }

}

object AddressCityRepository {

  def findAll: List[AddressCity] = {
    DB readOnly { implicit session =>
      sql"select * from address_city".map(AddressCity.*).list().apply()
    }
  }

}

object AddressCountryRepository {

  def findAll: List[AddressCountry] = {
    DB readOnly { implicit session =>
      sql"select * from address_country".map(AddressCountry.*).list().apply()
    }
  }

}

object AddressHouseRepository {

  def findAll: List[AddressHouse] = {
    DB readOnly { implicit session =>
      sql"select * from address_house".map(AddressHouse.*).list().apply()
    }
  }

}

object AddressQuarterRepository {

  def findAll: List[AddressQuarter] = {
    DB readOnly { implicit session =>
      sql"select * from address_quarter".map(AddressQuarter.*).list().apply()
    }
  }

}

object AddressStreetRepository {

  def findAll: List[AddressStreet] = {
    DB readOnly { implicit session =>
      sql"select * from address_street".map(AddressStreet.*).list().apply()
    }
  }

}
