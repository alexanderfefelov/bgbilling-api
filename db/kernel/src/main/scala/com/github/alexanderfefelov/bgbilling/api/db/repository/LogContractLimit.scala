package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class LogContractLimit(
  dt: DateTime,
  uid: Int,
  nvalue: BigDecimal,
  cid: Int,
  comment: String,
  days: Option[String] = None) {

  def save()(implicit session: DBSession = LogContractLimit.autoSession): LogContractLimit = LogContractLimit.save(this)(session)

  def destroy()(implicit session: DBSession = LogContractLimit.autoSession): Int = LogContractLimit.destroy(this)(session)

}


object LogContractLimit extends SQLSyntaxSupport[LogContractLimit] {

  override val tableName = "log_contract_limit"

  override val columns = Seq("dt", "uid", "nvalue", "cid", "comment", "days")

  def apply(lcl: SyntaxProvider[LogContractLimit])(rs: WrappedResultSet): LogContractLimit = autoConstruct(rs, lcl)
  def apply(lcl: ResultName[LogContractLimit])(rs: WrappedResultSet): LogContractLimit = autoConstruct(rs, lcl)

  val lcl = LogContractLimit.syntax("lcl")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, nvalue: BigDecimal, cid: Int, comment: String, days: Option[String])(implicit session: DBSession = autoSession): Option[LogContractLimit] = {
    withSQL {
      select.from(LogContractLimit as lcl).where.eq(lcl.dt, dt).and.eq(lcl.uid, uid).and.eq(lcl.nvalue, nvalue).and.eq(lcl.cid, cid).and.eq(lcl.comment, comment).and.eq(lcl.days, days)
    }.map(LogContractLimit(lcl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogContractLimit] = {
    withSQL(select.from(LogContractLimit as lcl)).map(LogContractLimit(lcl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogContractLimit as lcl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogContractLimit] = {
    withSQL {
      select.from(LogContractLimit as lcl).where.append(where)
    }.map(LogContractLimit(lcl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogContractLimit] = {
    withSQL {
      select.from(LogContractLimit as lcl).where.append(where)
    }.map(LogContractLimit(lcl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogContractLimit as lcl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    nvalue: BigDecimal,
    cid: Int,
    comment: String,
    days: Option[String] = None)(implicit session: DBSession = autoSession): LogContractLimit = {
    withSQL {
      insert.into(LogContractLimit).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.nvalue -> nvalue,
        column.cid -> cid,
        column.comment -> comment,
        column.days -> days
      )
    }.update.apply()

    LogContractLimit(
      dt = dt,
      uid = uid,
      nvalue = nvalue,
      cid = cid,
      comment = comment,
      days = days)
  }

  def batchInsert(entities: Seq[LogContractLimit])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'nvalue -> entity.nvalue,
        'cid -> entity.cid,
        'comment -> entity.comment,
        'days -> entity.days))
    SQL("""insert into log_contract_limit(
      dt,
      uid,
      nvalue,
      cid,
      comment,
      days
    ) values (
      {dt},
      {uid},
      {nvalue},
      {cid},
      {comment},
      {days}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: LogContractLimit)(implicit session: DBSession = autoSession): LogContractLimit = {
    withSQL {
      update(LogContractLimit).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.nvalue -> entity.nvalue,
        column.cid -> entity.cid,
        column.comment -> entity.comment,
        column.days -> entity.days
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.nvalue, entity.nvalue).and.eq(column.cid, entity.cid).and.eq(column.comment, entity.comment).and.eq(column.days, entity.days)
    }.update.apply()
    entity
  }

  def destroy(entity: LogContractLimit)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogContractLimit).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.nvalue, entity.nvalue).and.eq(column.cid, entity.cid).and.eq(column.comment, entity.comment).and.eq(column.days, entity.days) }.update.apply()
  }

}
