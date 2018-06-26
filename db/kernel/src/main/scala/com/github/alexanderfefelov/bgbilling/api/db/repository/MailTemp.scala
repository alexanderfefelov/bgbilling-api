package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class MailTemp(
  id: Int,
  eid: Option[Int] = None,
  dt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = MailTemp.autoSession): MailTemp = MailTemp.save(this)(session)

  def destroy()(implicit session: DBSession = MailTemp.autoSession): Int = MailTemp.destroy(this)(session)

}


object MailTemp extends SQLSyntaxSupport[MailTemp] {

  override val tableName = "mail_temp"

  override val columns = Seq("id", "eid", "dt")

  def apply(mt: SyntaxProvider[MailTemp])(rs: WrappedResultSet): MailTemp = autoConstruct(rs, mt)
  def apply(mt: ResultName[MailTemp])(rs: WrappedResultSet): MailTemp = autoConstruct(rs, mt)

  val mt = MailTemp.syntax("mt")

  override val autoSession = AutoSession

  def find(id: Int, eid: Option[Int], dt: Option[DateTime])(implicit session: DBSession = autoSession): Option[MailTemp] = {
    withSQL {
      select.from(MailTemp as mt).where.eq(mt.id, id).and.eq(mt.eid, eid).and.eq(mt.dt, dt)
    }.map(MailTemp(mt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MailTemp] = {
    withSQL(select.from(MailTemp as mt)).map(MailTemp(mt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MailTemp as mt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MailTemp] = {
    withSQL {
      select.from(MailTemp as mt).where.append(where)
    }.map(MailTemp(mt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MailTemp] = {
    withSQL {
      select.from(MailTemp as mt).where.append(where)
    }.map(MailTemp(mt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MailTemp as mt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    eid: Option[Int] = None,
    dt: Option[DateTime] = None)(implicit session: DBSession = autoSession): MailTemp = {
    withSQL {
      insert.into(MailTemp).namedValues(
        column.id -> id,
        column.eid -> eid,
        column.dt -> dt
      )
    }.update.apply()

    MailTemp(
      id = id,
      eid = eid,
      dt = dt)
  }

  def batchInsert(entities: Seq[MailTemp])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'eid -> entity.eid,
        'dt -> entity.dt))
    SQL("""insert into mail_temp(
      id,
      eid,
      dt
    ) values (
      {id},
      {eid},
      {dt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MailTemp)(implicit session: DBSession = autoSession): MailTemp = {
    withSQL {
      update(MailTemp).set(
        column.id -> entity.id,
        column.eid -> entity.eid,
        column.dt -> entity.dt
      ).where.eq(column.id, entity.id).and.eq(column.eid, entity.eid).and.eq(column.dt, entity.dt)
    }.update.apply()
    entity
  }

  def destroy(entity: MailTemp)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MailTemp).where.eq(column.id, entity.id).and.eq(column.eid, entity.eid).and.eq(column.dt, entity.dt) }.update.apply()
  }

}
