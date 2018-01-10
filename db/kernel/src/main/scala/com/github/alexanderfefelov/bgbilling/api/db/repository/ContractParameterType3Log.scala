package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractParameterType3Log(
  cid: Int,
  pid: Int,
  `val`: Option[String] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterType3Log.autoSession): ContractParameterType3Log = ContractParameterType3Log.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType3Log.autoSession): Int = ContractParameterType3Log.destroy(this)(session)

}


object ContractParameterType3Log extends SQLSyntaxSupport[ContractParameterType3Log] {

  override val tableName = "contract_parameter_type_3_log"

  override val columns = Seq("cid", "pid", "val", "dt_change", "user_id")

  def apply(cptl: SyntaxProvider[ContractParameterType3Log])(rs: WrappedResultSet): ContractParameterType3Log = autoConstruct(rs, cptl)
  def apply(cptl: ResultName[ContractParameterType3Log])(rs: WrappedResultSet): ContractParameterType3Log = autoConstruct(rs, cptl)

  val cptl = ContractParameterType3Log.syntax("cptl")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Option[String], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType3Log] = {
    withSQL {
      select.from(ContractParameterType3Log as cptl).where.eq(cptl.cid, cid).and.eq(cptl.pid, pid).and.eq(cptl.`val`, `val`).and.eq(cptl.dtChange, dtChange).and.eq(cptl.userId, userId)
    }.map(ContractParameterType3Log(cptl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType3Log] = {
    withSQL(select.from(ContractParameterType3Log as cptl)).map(ContractParameterType3Log(cptl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType3Log as cptl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType3Log] = {
    withSQL {
      select.from(ContractParameterType3Log as cptl).where.append(where)
    }.map(ContractParameterType3Log(cptl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType3Log] = {
    withSQL {
      select.from(ContractParameterType3Log as cptl).where.append(where)
    }.map(ContractParameterType3Log(cptl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType3Log as cptl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Option[String] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterType3Log = {
    withSQL {
      insert.into(ContractParameterType3Log).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterType3Log(
      cid = cid,
      pid = pid,
      `val` = `val`,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ContractParameterType3Log])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_3_log(
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

  def save(entity: ContractParameterType3Log)(implicit session: DBSession = autoSession): ContractParameterType3Log = {
    withSQL {
      update(ContractParameterType3Log).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType3Log)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType3Log).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
