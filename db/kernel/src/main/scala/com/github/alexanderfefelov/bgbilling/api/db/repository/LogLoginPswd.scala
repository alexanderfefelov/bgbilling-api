package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class LogLoginPswd(
  dt: DateTime,
  uid: Int,
  mid: Int,
  lid: Int) {

  def save()(implicit session: DBSession = LogLoginPswd.autoSession): LogLoginPswd = LogLoginPswd.save(this)(session)

  def destroy()(implicit session: DBSession = LogLoginPswd.autoSession): Int = LogLoginPswd.destroy(this)(session)

}


object LogLoginPswd extends SQLSyntaxSupport[LogLoginPswd] {

  override val tableName = "log_login_pswd"

  override val columns = Seq("dt", "uid", "mid", "lid")

  def apply(llp: SyntaxProvider[LogLoginPswd])(rs: WrappedResultSet): LogLoginPswd = autoConstruct(rs, llp)
  def apply(llp: ResultName[LogLoginPswd])(rs: WrappedResultSet): LogLoginPswd = autoConstruct(rs, llp)

  val llp = LogLoginPswd.syntax("llp")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, mid: Int, lid: Int)(implicit session: DBSession = autoSession): Option[LogLoginPswd] = {
    withSQL {
      select.from(LogLoginPswd as llp).where.eq(llp.dt, dt).and.eq(llp.uid, uid).and.eq(llp.mid, mid).and.eq(llp.lid, lid)
    }.map(LogLoginPswd(llp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogLoginPswd] = {
    withSQL(select.from(LogLoginPswd as llp)).map(LogLoginPswd(llp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogLoginPswd as llp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogLoginPswd] = {
    withSQL {
      select.from(LogLoginPswd as llp).where.append(where)
    }.map(LogLoginPswd(llp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogLoginPswd] = {
    withSQL {
      select.from(LogLoginPswd as llp).where.append(where)
    }.map(LogLoginPswd(llp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogLoginPswd as llp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    mid: Int,
    lid: Int)(implicit session: DBSession = autoSession): LogLoginPswd = {
    withSQL {
      insert.into(LogLoginPswd).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.mid -> mid,
        column.lid -> lid
      )
    }.update.apply()

    LogLoginPswd(
      dt = dt,
      uid = uid,
      mid = mid,
      lid = lid)
  }

  def batchInsert(entities: Seq[LogLoginPswd])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'mid -> entity.mid,
        'lid -> entity.lid))
    SQL("""insert into log_login_pswd(
      dt,
      uid,
      mid,
      lid
    ) values (
      {dt},
      {uid},
      {mid},
      {lid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: LogLoginPswd)(implicit session: DBSession = autoSession): LogLoginPswd = {
    withSQL {
      update(LogLoginPswd).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.mid -> entity.mid,
        column.lid -> entity.lid
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.mid, entity.mid).and.eq(column.lid, entity.lid)
    }.update.apply()
    entity
  }

  def destroy(entity: LogLoginPswd)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogLoginPswd).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.mid, entity.mid).and.eq(column.lid, entity.lid) }.update.apply()
  }

}
