package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType1(
  cid: Int,
  pid: Int,
  `val`: String) {

  def save()(implicit session: DBSession = ContractParameterType1.autoSession): ContractParameterType1 = ContractParameterType1.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType1.autoSession): Int = ContractParameterType1.destroy(this)(session)

}


object ContractParameterType1 extends SQLSyntaxSupport[ContractParameterType1] {

  override val tableName = "contract_parameter_type_1"

  override val columns = Seq("cid", "pid", "val")

  def apply(cpt: SyntaxProvider[ContractParameterType1])(rs: WrappedResultSet): ContractParameterType1 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType1])(rs: WrappedResultSet): ContractParameterType1 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType1.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType1] = {
    withSQL {
      select.from(ContractParameterType1 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid)
    }.map(ContractParameterType1(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType1] = {
    withSQL(select.from(ContractParameterType1 as cpt)).map(ContractParameterType1(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType1 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType1] = {
    withSQL {
      select.from(ContractParameterType1 as cpt).where.append(where)
    }.map(ContractParameterType1(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType1] = {
    withSQL {
      select.from(ContractParameterType1 as cpt).where.append(where)
    }.map(ContractParameterType1(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType1 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: String)(implicit session: DBSession = autoSession): ContractParameterType1 = {
    withSQL {
      insert.into(ContractParameterType1).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`
      )
    }.update.apply()

    ContractParameterType1(
      cid = cid,
      pid = pid,
      `val` = `val`)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`))
    SQL("""insert into contract_parameter_type_1(
      cid,
      pid,
      val
    ) values (
      {cid},
      {pid},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType1)(implicit session: DBSession = autoSession): ContractParameterType1 = {
    withSQL {
      update(ContractParameterType1).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType1).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
