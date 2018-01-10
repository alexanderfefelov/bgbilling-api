package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractParameterTypePhoneLog(
  pid: Int,
  cid: Int,
  `val`: Option[String] = None,
  data: Option[String] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterTypePhoneLog.autoSession): ContractParameterTypePhoneLog = ContractParameterTypePhoneLog.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypePhoneLog.autoSession): Int = ContractParameterTypePhoneLog.destroy(this)(session)

}


object ContractParameterTypePhoneLog extends SQLSyntaxSupport[ContractParameterTypePhoneLog] {

  override val tableName = "contract_parameter_type_phone_log"

  override val columns = Seq("pid", "cid", "val", "data", "dt_change", "user_id")

  def apply(cptpl: SyntaxProvider[ContractParameterTypePhoneLog])(rs: WrappedResultSet): ContractParameterTypePhoneLog = autoConstruct(rs, cptpl)
  def apply(cptpl: ResultName[ContractParameterTypePhoneLog])(rs: WrappedResultSet): ContractParameterTypePhoneLog = autoConstruct(rs, cptpl)

  val cptpl = ContractParameterTypePhoneLog.syntax("cptpl")

  override val autoSession = AutoSession

  def find(pid: Int, cid: Int, `val`: Option[String], data: Option[String], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhoneLog] = {
    withSQL {
      select.from(ContractParameterTypePhoneLog as cptpl).where.eq(cptpl.pid, pid).and.eq(cptpl.cid, cid).and.eq(cptpl.`val`, `val`).and.eq(cptpl.data, data).and.eq(cptpl.dtChange, dtChange).and.eq(cptpl.userId, userId)
    }.map(ContractParameterTypePhoneLog(cptpl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypePhoneLog] = {
    withSQL(select.from(ContractParameterTypePhoneLog as cptpl)).map(ContractParameterTypePhoneLog(cptpl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypePhoneLog as cptpl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhoneLog] = {
    withSQL {
      select.from(ContractParameterTypePhoneLog as cptpl).where.append(where)
    }.map(ContractParameterTypePhoneLog(cptpl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypePhoneLog] = {
    withSQL {
      select.from(ContractParameterTypePhoneLog as cptpl).where.append(where)
    }.map(ContractParameterTypePhoneLog(cptpl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypePhoneLog as cptpl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    cid: Int,
    `val`: Option[String] = None,
    data: Option[String] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterTypePhoneLog = {
    withSQL {
      insert.into(ContractParameterTypePhoneLog).namedValues(
        column.pid -> pid,
        column.cid -> cid,
        column.`val` -> `val`,
        column.data -> data,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterTypePhoneLog(
      pid = pid,
      cid = cid,
      `val` = `val`,
      data = data,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ContractParameterTypePhoneLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'cid -> entity.cid,
        'val -> entity.`val`,
        'data -> entity.data,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_phone_log(
      pid,
      cid,
      val,
      data,
      dt_change,
      user_id
    ) values (
      {pid},
      {cid},
      {val},
      {data},
      {dtChange},
      {userId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypePhoneLog)(implicit session: DBSession = autoSession): ContractParameterTypePhoneLog = {
    withSQL {
      update(ContractParameterTypePhoneLog).set(
        column.pid -> entity.pid,
        column.cid -> entity.cid,
        column.`val` -> entity.`val`,
        column.data -> entity.data,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.pid, entity.pid).and.eq(column.cid, entity.cid).and.eq(column.`val`, entity.`val`).and.eq(column.data, entity.data).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypePhoneLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypePhoneLog).where.eq(column.pid, entity.pid).and.eq(column.cid, entity.cid).and.eq(column.`val`, entity.`val`).and.eq(column.data, entity.data).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
