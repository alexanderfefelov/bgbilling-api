package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class AddressHouse(
  id: Int,
  streetid: Int,
  house: Int,
  frac: Option[String] = None,
  amount: Short,
  comment: Option[String] = None,
  areaid: Int,
  quarterid: Int,
  boxIndex: Option[String] = None,
  dt: Option[LocalDate] = None,
  podDiapazon: String,
  pod: String) {

  def save()(implicit session: DBSession = AddressHouse.autoSession): AddressHouse = AddressHouse.save(this)(session)

  def destroy()(implicit session: DBSession = AddressHouse.autoSession): Int = AddressHouse.destroy(this)(session)

}


object AddressHouse extends SQLSyntaxSupport[AddressHouse] {

  override val tableName = "address_house"

  override val columns = Seq("id", "streetid", "house", "frac", "amount", "comment", "areaid", "quarterid", "box_index", "dt", "pod_diapazon", "pod")

  def apply(ah: SyntaxProvider[AddressHouse])(rs: WrappedResultSet): AddressHouse = autoConstruct(rs, ah)
  def apply(ah: ResultName[AddressHouse])(rs: WrappedResultSet): AddressHouse = autoConstruct(rs, ah)

  val ah = AddressHouse.syntax("ah")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressHouse] = {
    sql"""select ${ah.result.*} from ${AddressHouse as ah} where ${ah.id} = ${id}"""
      .map(AddressHouse(ah.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressHouse] = {
    sql"""select ${ah.result.*} from ${AddressHouse as ah}""".map(AddressHouse(ah.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressHouse.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressHouse] = {
    sql"""select ${ah.result.*} from ${AddressHouse as ah} where ${where}"""
      .map(AddressHouse(ah.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressHouse] = {
    sql"""select ${ah.result.*} from ${AddressHouse as ah} where ${where}"""
      .map(AddressHouse(ah.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressHouse as ah} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    streetid: Int,
    house: Int,
    frac: Option[String] = None,
    amount: Short,
    comment: Option[String] = None,
    areaid: Int,
    quarterid: Int,
    boxIndex: Option[String] = None,
    dt: Option[LocalDate] = None,
    podDiapazon: String,
    pod: String)(implicit session: DBSession = autoSession): AddressHouse = {
    val generatedKey = sql"""
      insert into ${AddressHouse.table} (
        ${column.streetid},
        ${column.house},
        ${column.frac},
        ${column.amount},
        ${column.comment},
        ${column.areaid},
        ${column.quarterid},
        ${column.boxIndex},
        ${column.dt},
        ${column.podDiapazon},
        ${column.pod}
      ) values (
        ${streetid},
        ${house},
        ${frac},
        ${amount},
        ${comment},
        ${areaid},
        ${quarterid},
        ${boxIndex},
        ${dt},
        ${podDiapazon},
        ${pod}
      )
      """.updateAndReturnGeneratedKey.apply()

    AddressHouse(
      id = generatedKey.toInt,
      streetid = streetid,
      house = house,
      frac = frac,
      amount = amount,
      comment = comment,
      areaid = areaid,
      quarterid = quarterid,
      boxIndex = boxIndex,
      dt = dt,
      podDiapazon = podDiapazon,
      pod = pod)
  }

  def batchInsert(entities: collection.Seq[AddressHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'streetid -> entity.streetid,
        'house -> entity.house,
        'frac -> entity.frac,
        'amount -> entity.amount,
        'comment -> entity.comment,
        'areaid -> entity.areaid,
        'quarterid -> entity.quarterid,
        'boxIndex -> entity.boxIndex,
        'dt -> entity.dt,
        'podDiapazon -> entity.podDiapazon,
        'pod -> entity.pod))
    SQL("""insert into address_house(
      streetid,
      house,
      frac,
      amount,
      comment,
      areaid,
      quarterid,
      box_index,
      dt,
      pod_diapazon,
      pod
    ) values (
      {streetid},
      {house},
      {frac},
      {amount},
      {comment},
      {areaid},
      {quarterid},
      {boxIndex},
      {dt},
      {podDiapazon},
      {pod}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressHouse)(implicit session: DBSession = autoSession): AddressHouse = {
    sql"""
      update
        ${AddressHouse.table}
      set
        ${column.id} = ${entity.id},
        ${column.streetid} = ${entity.streetid},
        ${column.house} = ${entity.house},
        ${column.frac} = ${entity.frac},
        ${column.amount} = ${entity.amount},
        ${column.comment} = ${entity.comment},
        ${column.areaid} = ${entity.areaid},
        ${column.quarterid} = ${entity.quarterid},
        ${column.boxIndex} = ${entity.boxIndex},
        ${column.dt} = ${entity.dt},
        ${column.podDiapazon} = ${entity.podDiapazon},
        ${column.pod} = ${entity.pod}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: AddressHouse)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${AddressHouse.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
