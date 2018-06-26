package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractParameterType6(
  cid: Int,
  pid: Int,
  `val`: LocalDate) {

  def save()(implicit session: DBSession = ContractParameterType6.autoSession): ContractParameterType6 = ContractParameterType6.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType6.autoSession): Int = ContractParameterType6.destroy(this)(session)

}


object ContractParameterType6 extends SQLSyntaxSupport[ContractParameterType6] {

  override val tableName = "contract_parameter_type_6"

  override val columns = Seq("cid", "pid", "val")

  def apply(cpt: SyntaxProvider[ContractParameterType6])(rs: WrappedResultSet): ContractParameterType6 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType6])(rs: WrappedResultSet): ContractParameterType6 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType6.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType6] = {
    withSQL {
      select.from(ContractParameterType6 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid)
    }.map(ContractParameterType6(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType6] = {
    withSQL(select.from(ContractParameterType6 as cpt)).map(ContractParameterType6(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType6 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType6] = {
    withSQL {
      select.from(ContractParameterType6 as cpt).where.append(where)
    }.map(ContractParameterType6(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType6] = {
    withSQL {
      select.from(ContractParameterType6 as cpt).where.append(where)
    }.map(ContractParameterType6(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType6 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: LocalDate)(implicit session: DBSession = autoSession): ContractParameterType6 = {
    withSQL {
      insert.into(ContractParameterType6).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`
      )
    }.update.apply()

    ContractParameterType6(
      cid = cid,
      pid = pid,
      `val` = `val`)
  }

  def batchInsert(entities: Seq[ContractParameterType6])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`))
    SQL("""insert into contract_parameter_type_6(
      cid,
      pid,
      val
    ) values (
      {cid},
      {pid},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType6)(implicit session: DBSession = autoSession): ContractParameterType6 = {
    withSQL {
      update(ContractParameterType6).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType6)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType6).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
