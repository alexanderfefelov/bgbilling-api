package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class LogContractMode(
  dt: DateTime,
  uid: Int,
  value: Int,
  cid: Int) {

  def save()(implicit session: DBSession = LogContractMode.autoSession): LogContractMode = LogContractMode.save(this)(session)

  def destroy()(implicit session: DBSession = LogContractMode.autoSession): Int = LogContractMode.destroy(this)(session)

}


object LogContractMode extends SQLSyntaxSupport[LogContractMode] {

  override val tableName = "log_contract_mode"

  override val columns = Seq("dt", "uid", "value", "cid")

  def apply(lcm: SyntaxProvider[LogContractMode])(rs: WrappedResultSet): LogContractMode = autoConstruct(rs, lcm)
  def apply(lcm: ResultName[LogContractMode])(rs: WrappedResultSet): LogContractMode = autoConstruct(rs, lcm)

  val lcm = LogContractMode.syntax("lcm")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, value: Int, cid: Int)(implicit session: DBSession = autoSession): Option[LogContractMode] = {
    withSQL {
      select.from(LogContractMode as lcm).where.eq(lcm.dt, dt).and.eq(lcm.uid, uid).and.eq(lcm.value, value).and.eq(lcm.cid, cid)
    }.map(LogContractMode(lcm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogContractMode] = {
    withSQL(select.from(LogContractMode as lcm)).map(LogContractMode(lcm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogContractMode as lcm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogContractMode] = {
    withSQL {
      select.from(LogContractMode as lcm).where.append(where)
    }.map(LogContractMode(lcm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogContractMode] = {
    withSQL {
      select.from(LogContractMode as lcm).where.append(where)
    }.map(LogContractMode(lcm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogContractMode as lcm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    value: Int,
    cid: Int)(implicit session: DBSession = autoSession): LogContractMode = {
    withSQL {
      insert.into(LogContractMode).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.value -> value,
        column.cid -> cid
      )
    }.update.apply()

    LogContractMode(
      dt = dt,
      uid = uid,
      value = value,
      cid = cid)
  }

  def batchInsert(entities: Seq[LogContractMode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'value -> entity.value,
        'cid -> entity.cid))
    SQL("""insert into log_contract_mode(
      dt,
      uid,
      value,
      cid
    ) values (
      {dt},
      {uid},
      {value},
      {cid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: LogContractMode)(implicit session: DBSession = autoSession): LogContractMode = {
    withSQL {
      update(LogContractMode).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.value -> entity.value,
        column.cid -> entity.cid
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.value, entity.value).and.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: LogContractMode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogContractMode).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.value, entity.value).and.eq(column.cid, entity.cid) }.update.apply()
  }

}
