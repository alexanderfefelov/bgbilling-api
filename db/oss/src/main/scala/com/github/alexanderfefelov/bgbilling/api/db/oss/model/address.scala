package com.github.alexanderfefelov.bgbilling.api.db.oss.model

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

case class AddressArea (

  id: Long,
  title: String,
  cityId: Long

)

object AddressArea {

  val * = (rs: WrappedResultSet) =>
    AddressArea(
      rs.long("id"),
      rs.string("title"),
      rs.long("cityid")
    )

}

case class AddressCity (

  id: Long,
  countryId: Long,
  title: String,
  posOption: Option[Long]

)

object AddressCity {

  val * = (rs: WrappedResultSet) =>
    AddressCity(
      rs.long("id"),
      rs.long("country_id"),
      rs.string("title"),
      rs.longOpt("pos")
    )

}

case class AddressCountry (

  id: Long,
  title: String

)

object AddressCountry {

  val * = (rs: WrappedResultSet) =>
    AddressCountry(
      rs.long("id"),
      rs.string("title")
    )

}

case class AddressHouse (

  id: Long,
  streetId: Long,
  house: Long,
  fracOption: Option[String],
  amount: Int,
  commentOption: Option[String],
  areaId: Long,
  quarterId: Long,
  boxIndex: Option[String],
  dtOption: Option[DateTime],
  podDiapazon:String,
  pod: String

)

object AddressHouse {

  val * = (rs: WrappedResultSet) =>
    AddressHouse(
      rs.long("id"),
      rs.long("streetid"),
      rs.long("house"),
      rs.stringOpt("frac"),
      rs.int("amount"),
      rs.stringOpt("comment"),
      rs.long("areaid"),
      rs.long("quarterid"),
      rs.stringOpt("box_index"),
      rs.jodaDateTimeOpt("dt"),
      rs.string("pod_diapazon"),
      rs.string("pod")
    )

}

case class AddressQuarter (

  id: Long,
  title: String,
  gidOption: Option[Long],
  cityId: Long

)

object AddressQuarter {

  val * = (rs: WrappedResultSet) =>
    AddressQuarter(
      rs.long("id"),
      rs.string("title"),
      rs.longOpt("gid"),
      rs.long("cityId")
    )

}
