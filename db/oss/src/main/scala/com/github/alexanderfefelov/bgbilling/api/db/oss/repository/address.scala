package com.github.alexanderfefelov.bgbilling.api.db.oss.repository

import com.github.alexanderfefelov.bgbilling.api.db.oss.model._
import scalikejdbc._

object AddressAreaRepository {

  def create(obj: AddressArea): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_area(id, title, cityid)
        values(${obj.id}, ${obj.title}, ${obj.cityId})
      """.update.apply()
    }
  }

  def findAll: List[AddressArea] = {
    DB readOnly { implicit session =>
      sql"select * from address_area".map(AddressArea.*).list().apply()
    }
  }

}

object AddressCityRepository {

  def create(obj: AddressCity): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_city(id, country_id, title, pos)
        values(${obj.id}, ${obj.countryId}, ${obj.title}, ${obj.posOption})
      """.update.apply()
    }
  }

  def findAll: List[AddressCity] = {
    DB readOnly { implicit session =>
      sql"select * from address_city".map(AddressCity.*).list().apply()
    }
  }

}

object AddressCountryRepository {

  def create(obj: AddressCountry): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_country(id, title)
        values(${obj.id}, ${obj.title})
      """.update.apply()
    }
  }

  def findAll: List[AddressCountry] = {
    DB readOnly { implicit session =>
      sql"select * from address_country".map(AddressCountry.*).list().apply()
    }
  }

}

object AddressHouseRepository {

  def create(obj: AddressHouse): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_house(id, streetid, house, frac, amount, comment, areaid, quarterid, box_index, dt, pod_diapazon, pod)
        values(${obj.id}, ${obj.streetId}, ${obj.house}, ${obj.fracOption}, ${obj.amount}, ${obj.commentOption}, ${obj.areaId}, ${obj.quarterId}, ${obj.boxIndex}, ${obj.dtOption}, ${obj.podDiapazon}, ${obj.pod})
      """.update.apply()
    }
  }

  def findAll: List[AddressHouse] = {
    DB readOnly { implicit session =>
      sql"select * from address_house".map(AddressHouse.*).list().apply()
    }
  }

}

object AddressQuarterRepository {

  def create(obj: AddressQuarter): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_quarter(id, title, gid, cityid)
        values(${obj.id}, ${obj.title}, ${obj.gidOption}, ${obj.cityId})
      """.update.apply()
    }
  }

  def findAll: List[AddressQuarter] = {
    DB readOnly { implicit session =>
      sql"select * from address_quarter".map(AddressQuarter.*).list().apply()
    }
  }

}

object AddressStreetRepository {

  def create(obj: AddressStreet): Int = {
    DB localTx { implicit session =>
      sql"""
        insert into address_street(id, title, p_index, cityid)
        values(${obj.id}, ${obj.title}, ${obj.pIndex}, ${obj.cityId})
      """.update.apply()
    }
  }

  def findAll: List[AddressStreet] = {
    DB readOnly { implicit session =>
      sql"select * from address_street".map(AddressStreet.*).list().apply()
    }
  }

}
