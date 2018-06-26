package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractParameterType7Log(
  cid: Int,
  pid: Int,
  `val`: Option[Int] = None,
  title: Option[String] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ContractParameterType7Log.autoSession): ContractParameterType7Log = ContractParameterType7Log.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType7Log.autoSession): Int = ContractParameterType7Log.destroy(this)(session)

}


object ContractParameterType7Log extends SQLSyntaxSupport[ContractParameterType7Log] {

  override val tableName = "contract_parameter_type_7_log"

  override val columns = Seq("cid", "pid", "val", "title", "dt_change", "user_id")

  def apply(cptl: SyntaxProvider[ContractParameterType7Log])(rs: WrappedResultSet): ContractParameterType7Log = autoConstruct(rs, cptl)
  def apply(cptl: ResultName[ContractParameterType7Log])(rs: WrappedResultSet): ContractParameterType7Log = autoConstruct(rs, cptl)

  val cptl = ContractParameterType7Log.syntax("cptl")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Option[Int], title: Option[String], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType7Log] = {
    withSQL {
      select.from(ContractParameterType7Log as cptl).where.eq(cptl.cid, cid).and.eq(cptl.pid, pid).and.eq(cptl.`val`, `val`).and.eq(cptl.title, title).and.eq(cptl.dtChange, dtChange).and.eq(cptl.userId, userId)
    }.map(ContractParameterType7Log(cptl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType7Log] = {
    withSQL(select.from(ContractParameterType7Log as cptl)).map(ContractParameterType7Log(cptl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType7Log as cptl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType7Log] = {
    withSQL {
      select.from(ContractParameterType7Log as cptl).where.append(where)
    }.map(ContractParameterType7Log(cptl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType7Log] = {
    withSQL {
      select.from(ContractParameterType7Log as cptl).where.append(where)
    }.map(ContractParameterType7Log(cptl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType7Log as cptl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Option[Int] = None,
    title: Option[String] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ContractParameterType7Log = {
    withSQL {
      insert.into(ContractParameterType7Log).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.title -> title,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ContractParameterType7Log(
      cid = cid,
      pid = pid,
      `val` = `val`,
      title = title,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ContractParameterType7Log])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'title -> entity.title,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into contract_parameter_type_7_log(
      cid,
      pid,
      val,
      title,
      dt_change,
      user_id
    ) values (
      {cid},
      {pid},
      {val},
      {title},
      {dtChange},
      {userId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType7Log)(implicit session: DBSession = autoSession): ContractParameterType7Log = {
    withSQL {
      update(ContractParameterType7Log).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.title -> entity.title,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.title, entity.title).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType7Log)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType7Log).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.title, entity.title).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
