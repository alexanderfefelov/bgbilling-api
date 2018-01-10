package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractParameterTypeMultilistLog(
  cid: Int,
  pid: Int,
  `val`: Option[String] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterTypeMultilistLog.autoSession): ContractParameterTypeMultilistLog = ContractParameterTypeMultilistLog.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypeMultilistLog.autoSession): Int = ContractParameterTypeMultilistLog.destroy(this)(session)

}


object ContractParameterTypeMultilistLog extends SQLSyntaxSupport[ContractParameterTypeMultilistLog] {

  override val tableName = "contract_parameter_type_multilist_log"

  override val columns = Seq("cid", "pid", "val", "dt_change", "user_id")

  def apply(cptml: SyntaxProvider[ContractParameterTypeMultilistLog])(rs: WrappedResultSet): ContractParameterTypeMultilistLog = autoConstruct(rs, cptml)
  def apply(cptml: ResultName[ContractParameterTypeMultilistLog])(rs: WrappedResultSet): ContractParameterTypeMultilistLog = autoConstruct(rs, cptml)

  val cptml = ContractParameterTypeMultilistLog.syntax("cptml")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Option[String], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistLog] = {
    withSQL {
      select.from(ContractParameterTypeMultilistLog as cptml).where.eq(cptml.cid, cid).and.eq(cptml.pid, pid).and.eq(cptml.`val`, `val`).and.eq(cptml.dtChange, dtChange).and.eq(cptml.userId, userId)
    }.map(ContractParameterTypeMultilistLog(cptml.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistLog] = {
    withSQL(select.from(ContractParameterTypeMultilistLog as cptml)).map(ContractParameterTypeMultilistLog(cptml.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypeMultilistLog as cptml)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistLog] = {
    withSQL {
      select.from(ContractParameterTypeMultilistLog as cptml).where.append(where)
    }.map(ContractParameterTypeMultilistLog(cptml.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistLog] = {
    withSQL {
      select.from(ContractParameterTypeMultilistLog as cptml).where.append(where)
    }.map(ContractParameterTypeMultilistLog(cptml.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypeMultilistLog as cptml).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Option[String] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistLog = {
    withSQL {
      insert.into(ContractParameterTypeMultilistLog).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterTypeMultilistLog(
      cid = cid,
      pid = pid,
      `val` = `val`,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ContractParameterTypeMultilistLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_multilist_log(
      cid,
      pid,
      val,
      dt_change,
      user_id
    ) values (
      {cid},
      {pid},
      {val},
      {dtChange},
      {userId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypeMultilistLog)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistLog = {
    withSQL {
      update(ContractParameterTypeMultilistLog).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypeMultilistLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypeMultilistLog).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
