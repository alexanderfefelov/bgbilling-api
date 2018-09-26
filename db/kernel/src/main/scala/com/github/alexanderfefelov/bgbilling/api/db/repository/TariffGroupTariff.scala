package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class TariffGroupTariff(
  tgid: Int,
  tpid: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = TariffGroupTariff.autoSession): TariffGroupTariff = TariffGroupTariff.save(this)(session)

  def destroy()(implicit session: DBSession = TariffGroupTariff.autoSession): Int = TariffGroupTariff.destroy(this)(session)

}


object TariffGroupTariff extends SQLSyntaxSupport[TariffGroupTariff] {

  override val tableName = "tariff_group_tariff"

  override val columns = Seq("tgid", "tpid", "date1", "date2")

  def apply(tgt: SyntaxProvider[TariffGroupTariff])(rs: WrappedResultSet): TariffGroupTariff = autoConstruct(rs, tgt)
  def apply(tgt: ResultName[TariffGroupTariff])(rs: WrappedResultSet): TariffGroupTariff = autoConstruct(rs, tgt)

  val tgt = TariffGroupTariff.syntax("tgt")

  override val autoSession = AutoSession

  def find(tgid: Int, tpid: Int, date1: Option[LocalDate], date2: Option[LocalDate])(implicit session: DBSession = autoSession): Option[TariffGroupTariff] = {
    withSQL {
      select.from(TariffGroupTariff as tgt).where.eq(tgt.tgid, tgid).and.eq(tgt.tpid, tpid).and.eq(tgt.date1, date1).and.eq(tgt.date2, date2)
    }.map(TariffGroupTariff(tgt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffGroupTariff] = {
    withSQL(select.from(TariffGroupTariff as tgt)).map(TariffGroupTariff(tgt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffGroupTariff as tgt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffGroupTariff] = {
    withSQL {
      select.from(TariffGroupTariff as tgt).where.append(where)
    }.map(TariffGroupTariff(tgt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffGroupTariff] = {
    withSQL {
      select.from(TariffGroupTariff as tgt).where.append(where)
    }.map(TariffGroupTariff(tgt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffGroupTariff as tgt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tgid: Int,
    tpid: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None)(implicit session: DBSession = autoSession): TariffGroupTariff = {
    withSQL {
      insert.into(TariffGroupTariff).namedValues(
        column.tgid -> tgid,
        column.tpid -> tpid,
        column.date1 -> date1,
        column.date2 -> date2
      )
    }.update.apply()

    TariffGroupTariff(
      tgid = tgid,
      tpid = tpid,
      date1 = date1,
      date2 = date2)
  }

  def batchInsert(entities: collection.Seq[TariffGroupTariff])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tgid -> entity.tgid,
        'tpid -> entity.tpid,
        'date1 -> entity.date1,
        'date2 -> entity.date2))
    SQL("""insert into tariff_group_tariff(
      tgid,
      tpid,
      date1,
      date2
    ) values (
      {tgid},
      {tpid},
      {date1},
      {date2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffGroupTariff)(implicit session: DBSession = autoSession): TariffGroupTariff = {
    withSQL {
      update(TariffGroupTariff).set(
        column.tgid -> entity.tgid,
        column.tpid -> entity.tpid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2
      ).where.eq(column.tgid, entity.tgid).and.eq(column.tpid, entity.tpid).and.eq(column.date1, entity.date1).and.eq(column.date2, entity.date2)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffGroupTariff)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffGroupTariff).where.eq(column.tgid, entity.tgid).and.eq(column.tpid, entity.tpid).and.eq(column.date1, entity.date1).and.eq(column.date2, entity.date2) }.update.apply()
  }

}
