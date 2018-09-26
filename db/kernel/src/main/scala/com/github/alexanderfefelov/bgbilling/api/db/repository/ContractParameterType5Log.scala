package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractParameterType5Log(
  cid: Int,
  pid: Int,
  `val`: Option[Int] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterType5Log.autoSession): ContractParameterType5Log = ContractParameterType5Log.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType5Log.autoSession): Int = ContractParameterType5Log.destroy(this)(session)

}


object ContractParameterType5Log extends SQLSyntaxSupport[ContractParameterType5Log] {

  override val tableName = "contract_parameter_type_5_log"

  override val columns = Seq("cid", "pid", "val", "dt_change", "user_id")

  def apply(cptl: SyntaxProvider[ContractParameterType5Log])(rs: WrappedResultSet): ContractParameterType5Log = autoConstruct(rs, cptl)
  def apply(cptl: ResultName[ContractParameterType5Log])(rs: WrappedResultSet): ContractParameterType5Log = autoConstruct(rs, cptl)

  val cptl = ContractParameterType5Log.syntax("cptl")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Option[Int], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType5Log] = {
    withSQL {
      select.from(ContractParameterType5Log as cptl).where.eq(cptl.cid, cid).and.eq(cptl.pid, pid).and.eq(cptl.`val`, `val`).and.eq(cptl.dtChange, dtChange).and.eq(cptl.userId, userId)
    }.map(ContractParameterType5Log(cptl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType5Log] = {
    withSQL(select.from(ContractParameterType5Log as cptl)).map(ContractParameterType5Log(cptl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType5Log as cptl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType5Log] = {
    withSQL {
      select.from(ContractParameterType5Log as cptl).where.append(where)
    }.map(ContractParameterType5Log(cptl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType5Log] = {
    withSQL {
      select.from(ContractParameterType5Log as cptl).where.append(where)
    }.map(ContractParameterType5Log(cptl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType5Log as cptl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Option[Int] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterType5Log = {
    withSQL {
      insert.into(ContractParameterType5Log).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterType5Log(
      cid = cid,
      pid = pid,
      `val` = `val`,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType5Log])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_5_log(
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

  def save(entity: ContractParameterType5Log)(implicit session: DBSession = autoSession): ContractParameterType5Log = {
    withSQL {
      update(ContractParameterType5Log).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType5Log)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType5Log).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
