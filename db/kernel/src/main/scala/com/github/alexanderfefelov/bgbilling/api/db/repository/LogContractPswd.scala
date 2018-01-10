package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class LogContractPswd(
  dt: DateTime,
  uid: Int,
  cid: Int) {

  def save()(implicit session: DBSession = LogContractPswd.autoSession): LogContractPswd = LogContractPswd.save(this)(session)

  def destroy()(implicit session: DBSession = LogContractPswd.autoSession): Int = LogContractPswd.destroy(this)(session)

}


object LogContractPswd extends SQLSyntaxSupport[LogContractPswd] {

  override val tableName = "log_contract_pswd"

  override val columns = Seq("dt", "uid", "cid")

  def apply(lcp: SyntaxProvider[LogContractPswd])(rs: WrappedResultSet): LogContractPswd = autoConstruct(rs, lcp)
  def apply(lcp: ResultName[LogContractPswd])(rs: WrappedResultSet): LogContractPswd = autoConstruct(rs, lcp)

  val lcp = LogContractPswd.syntax("lcp")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, cid: Int)(implicit session: DBSession = autoSession): Option[LogContractPswd] = {
    withSQL {
      select.from(LogContractPswd as lcp).where.eq(lcp.dt, dt).and.eq(lcp.uid, uid).and.eq(lcp.cid, cid)
    }.map(LogContractPswd(lcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogContractPswd] = {
    withSQL(select.from(LogContractPswd as lcp)).map(LogContractPswd(lcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogContractPswd as lcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogContractPswd] = {
    withSQL {
      select.from(LogContractPswd as lcp).where.append(where)
    }.map(LogContractPswd(lcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogContractPswd] = {
    withSQL {
      select.from(LogContractPswd as lcp).where.append(where)
    }.map(LogContractPswd(lcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogContractPswd as lcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    cid: Int)(implicit session: DBSession = autoSession): LogContractPswd = {
    withSQL {
      insert.into(LogContractPswd).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.cid -> cid
      )
    }.update.apply()

    LogContractPswd(
      dt = dt,
      uid = uid,
      cid = cid)
  }

  def batchInsert(entities: Seq[LogContractPswd])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'cid -> entity.cid))
    SQL("""insert into log_contract_pswd(
      dt,
      uid,
      cid
    ) values (
      {dt},
      {uid},
      {cid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: LogContractPswd)(implicit session: DBSession = autoSession): LogContractPswd = {
    withSQL {
      update(LogContractPswd).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.cid -> entity.cid
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: LogContractPswd)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogContractPswd).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.cid, entity.cid) }.update.apply()
  }

}
