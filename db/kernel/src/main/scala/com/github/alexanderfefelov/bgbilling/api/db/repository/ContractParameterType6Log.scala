package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}

case class ContractParameterType6Log(
  cid: Int,
  pid: Int,
  `val`: Option[LocalDate] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterType6Log.autoSession): ContractParameterType6Log = ContractParameterType6Log.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType6Log.autoSession): Int = ContractParameterType6Log.destroy(this)(session)

}


object ContractParameterType6Log extends SQLSyntaxSupport[ContractParameterType6Log] {

  override val tableName = "contract_parameter_type_6_log"

  override val columns = Seq("cid", "pid", "val", "dt_change", "user_id")

  def apply(cptl: SyntaxProvider[ContractParameterType6Log])(rs: WrappedResultSet): ContractParameterType6Log = autoConstruct(rs, cptl)
  def apply(cptl: ResultName[ContractParameterType6Log])(rs: WrappedResultSet): ContractParameterType6Log = autoConstruct(rs, cptl)

  val cptl = ContractParameterType6Log.syntax("cptl")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Option[LocalDate], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType6Log] = {
    withSQL {
      select.from(ContractParameterType6Log as cptl).where.eq(cptl.cid, cid).and.eq(cptl.pid, pid).and.eq(cptl.`val`, `val`).and.eq(cptl.dtChange, dtChange).and.eq(cptl.userId, userId)
    }.map(ContractParameterType6Log(cptl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType6Log] = {
    withSQL(select.from(ContractParameterType6Log as cptl)).map(ContractParameterType6Log(cptl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType6Log as cptl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType6Log] = {
    withSQL {
      select.from(ContractParameterType6Log as cptl).where.append(where)
    }.map(ContractParameterType6Log(cptl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType6Log] = {
    withSQL {
      select.from(ContractParameterType6Log as cptl).where.append(where)
    }.map(ContractParameterType6Log(cptl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType6Log as cptl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Option[LocalDate] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterType6Log = {
    withSQL {
      insert.into(ContractParameterType6Log).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterType6Log(
      cid = cid,
      pid = pid,
      `val` = `val`,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ContractParameterType6Log])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_6_log(
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

  def save(entity: ContractParameterType6Log)(implicit session: DBSession = autoSession): ContractParameterType6Log = {
    withSQL {
      update(ContractParameterType6Log).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType6Log)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType6Log).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
