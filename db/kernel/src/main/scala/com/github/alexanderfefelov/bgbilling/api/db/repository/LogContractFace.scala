package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class LogContractFace(
  dt: DateTime,
  uid: Int,
  value: Int,
  cid: Int) {

  def save()(implicit session: DBSession = LogContractFace.autoSession): LogContractFace = LogContractFace.save(this)(session)

  def destroy()(implicit session: DBSession = LogContractFace.autoSession): Int = LogContractFace.destroy(this)(session)

}


object LogContractFace extends SQLSyntaxSupport[LogContractFace] {

  override val tableName = "log_contract_face"

  override val columns = Seq("dt", "uid", "value", "cid")

  def apply(lcf: SyntaxProvider[LogContractFace])(rs: WrappedResultSet): LogContractFace = autoConstruct(rs, lcf)
  def apply(lcf: ResultName[LogContractFace])(rs: WrappedResultSet): LogContractFace = autoConstruct(rs, lcf)

  val lcf = LogContractFace.syntax("lcf")

  override val autoSession = AutoSession

  def find(dt: DateTime, uid: Int, value: Int, cid: Int)(implicit session: DBSession = autoSession): Option[LogContractFace] = {
    withSQL {
      select.from(LogContractFace as lcf).where.eq(lcf.dt, dt).and.eq(lcf.uid, uid).and.eq(lcf.value, value).and.eq(lcf.cid, cid)
    }.map(LogContractFace(lcf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[LogContractFace] = {
    withSQL(select.from(LogContractFace as lcf)).map(LogContractFace(lcf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(LogContractFace as lcf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[LogContractFace] = {
    withSQL {
      select.from(LogContractFace as lcf).where.append(where)
    }.map(LogContractFace(lcf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[LogContractFace] = {
    withSQL {
      select.from(LogContractFace as lcf).where.append(where)
    }.map(LogContractFace(lcf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(LogContractFace as lcf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: DateTime,
    uid: Int,
    value: Int,
    cid: Int)(implicit session: DBSession = autoSession): LogContractFace = {
    withSQL {
      insert.into(LogContractFace).namedValues(
        column.dt -> dt,
        column.uid -> uid,
        column.value -> value,
        column.cid -> cid
      )
    }.update.apply()

    LogContractFace(
      dt = dt,
      uid = uid,
      value = value,
      cid = cid)
  }

  def batchInsert(entities: Seq[LogContractFace])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'uid -> entity.uid,
        'value -> entity.value,
        'cid -> entity.cid))
    SQL("""insert into log_contract_face(
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

  def save(entity: LogContractFace)(implicit session: DBSession = autoSession): LogContractFace = {
    withSQL {
      update(LogContractFace).set(
        column.dt -> entity.dt,
        column.uid -> entity.uid,
        column.value -> entity.value,
        column.cid -> entity.cid
      ).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.value, entity.value).and.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: LogContractFace)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(LogContractFace).where.eq(column.dt, entity.dt).and.eq(column.uid, entity.uid).and.eq(column.value, entity.value).and.eq(column.cid, entity.cid) }.update.apply()
  }

}
