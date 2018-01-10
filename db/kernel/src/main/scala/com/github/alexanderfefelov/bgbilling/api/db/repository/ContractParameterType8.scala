package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType8(
  cid: Int,
  pid: Int,
  `val`: Int) {

  def save()(implicit session: DBSession = ContractParameterType8.autoSession): ContractParameterType8 = ContractParameterType8.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType8.autoSession): Int = ContractParameterType8.destroy(this)(session)

}


object ContractParameterType8 extends SQLSyntaxSupport[ContractParameterType8] {

  override val tableName = "contract_parameter_type_8"

  override val columns = Seq("cid", "pid", "val")

  def apply(cpt: SyntaxProvider[ContractParameterType8])(rs: WrappedResultSet): ContractParameterType8 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType8])(rs: WrappedResultSet): ContractParameterType8 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType8.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType8] = {
    withSQL {
      select.from(ContractParameterType8 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid).and.eq(cpt.`val`, `val`)
    }.map(ContractParameterType8(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType8] = {
    withSQL(select.from(ContractParameterType8 as cpt)).map(ContractParameterType8(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType8 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType8] = {
    withSQL {
      select.from(ContractParameterType8 as cpt).where.append(where)
    }.map(ContractParameterType8(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType8] = {
    withSQL {
      select.from(ContractParameterType8 as cpt).where.append(where)
    }.map(ContractParameterType8(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType8 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Int)(implicit session: DBSession = autoSession): ContractParameterType8 = {
    withSQL {
      insert.into(ContractParameterType8).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`
      )
    }.update.apply()

    ContractParameterType8(
      cid = cid,
      pid = pid,
      `val` = `val`)
  }

  def batchInsert(entities: Seq[ContractParameterType8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`))
    SQL("""insert into contract_parameter_type_8(
      cid,
      pid,
      val
    ) values (
      {cid},
      {pid},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType8)(implicit session: DBSession = autoSession): ContractParameterType8 = {
    withSQL {
      update(ContractParameterType8).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType8).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`) }.update.apply()
  }

}
