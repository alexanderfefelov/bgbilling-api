package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class LogContractLimitManageMode(
  dt: DateTime,
  uid: Int,
  mode: Int,
  cid: Int) {

  def save()(implicit session: DBSession = LogContractLimitManageMode.autoSession): LogContractLimitManageMode = LogContractLimitManageMode.save(this)(session)

  def destroy()(implicit session: DBSession = LogContractLimitManageMode.autoSession): Int = LogContractLimitManageMode.destroy(this)(session)

}


object LogContractLimitManageMode extends SQLSyntaxSupport[LogContractLimitManageMode] {

  override val tableName = "log_contract_limit_manage_mode"

  override val columns = Seq("dt", "uid", "mode", "cid")

  def apply(lclmm: SyntaxProvider[LogContractLimitManageMode])(rs: WrappedResultSet): LogContractLimitManageMode = autoConstruct(rs, lclmm)
  def apply(lclmm: ResultName[LogContractLimitManageMode])(rs: WrappedResultSet): LogContractLimitManageMode = autoConstruct(rs, lclmm)

  val lclmm = LogContractLimitManageMode.syntax("lclmm")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, mode: Int, cid: Int)(implicit session: DBSession = autoSession): Option[LogContractLimitManageMode] = {
    withSQL {
      select.from(LogContractLimitManageMode as lclmm).where.eq(lclmm.dt, dt).and.eq(lclmm.uid, uid).and.eq(lclmm.mode, mode).and.eq(lclmm.cid, cid)
    }.map(LogContractLimitManageMode(lclmm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogContractLimitManageMode] = {
    withSQL(select.from(LogContractLimitManageMode as lclmm)).map(LogContractLimitManageMode(lclmm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogContractLimitManageMode as lclmm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogContractLimitManageMode] = {
    withSQL {
      select.from(LogContractLimitManageMode as lclmm).where.append(where)
    }.map(LogContractLimitManageMode(lclmm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogContractLimitManageMode] = {
    withSQL {
      select.from(LogContractLimitManageMode as lclmm).where.append(where)
    }.map(LogContractLimitManageMode(lclmm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogContractLimitManageMode as lclmm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    mode: Int,
    cid: Int)(implicit session: DBSession = autoSession): LogContractLimitManageMode = {
    withSQL {
      insert.into(LogContractLimitManageMode).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.mode -> mode,
        column.cid -> cid
      )
    }.update.apply()

    LogContractLimitManageMode(
      dt = dt,
      uid = uid,
      mode = mode,
      cid = cid)
  }

  def batchInsert(entities: collection.Seq[LogContractLimitManageMode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'mode -> entity.mode,
        'cid -> entity.cid))
    SQL("""insert into log_contract_limit_manage_mode(
      dt,
      uid,
      mode,
      cid
    ) values (
      {dt},
      {uid},
      {mode},
      {cid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: LogContractLimitManageMode)(implicit session: DBSession = autoSession): LogContractLimitManageMode = {
    withSQL {
      update(LogContractLimitManageMode).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.mode -> entity.mode,
        column.cid -> entity.cid
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.mode, entity.mode).and.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: LogContractLimitManageMode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogContractLimitManageMode).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.mode, entity.mode).and.eq(column.cid, entity.cid) }.update.apply()
  }

}
