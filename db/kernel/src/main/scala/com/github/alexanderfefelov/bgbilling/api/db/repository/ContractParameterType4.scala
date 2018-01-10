package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType4(
  cid: Int,
  pid: Int,
  val1: Int,
  val2: Int) {

  def save()(implicit session: DBSession = ContractParameterType4.autoSession): ContractParameterType4 = ContractParameterType4.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType4.autoSession): Int = ContractParameterType4.destroy(this)(session)

}


object ContractParameterType4 extends SQLSyntaxSupport[ContractParameterType4] {

  override val tableName = "contract_parameter_type_4"

  override val columns = Seq("cid", "pid", "val1", "val2")

  def apply(cpt: SyntaxProvider[ContractParameterType4])(rs: WrappedResultSet): ContractParameterType4 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType4])(rs: WrappedResultSet): ContractParameterType4 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType4.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, val1: Int, val2: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType4] = {
    withSQL {
      select.from(ContractParameterType4 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid).and.eq(cpt.val1, val1).and.eq(cpt.val2, val2)
    }.map(ContractParameterType4(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType4] = {
    withSQL(select.from(ContractParameterType4 as cpt)).map(ContractParameterType4(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType4 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType4] = {
    withSQL {
      select.from(ContractParameterType4 as cpt).where.append(where)
    }.map(ContractParameterType4(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType4] = {
    withSQL {
      select.from(ContractParameterType4 as cpt).where.append(where)
    }.map(ContractParameterType4(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType4 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    val1: Int,
    val2: Int)(implicit session: DBSession = autoSession): ContractParameterType4 = {
    withSQL {
      insert.into(ContractParameterType4).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.val1 -> val1,
        column.val2 -> val2
      )
    }.update.apply()

    ContractParameterType4(
      cid = cid,
      pid = pid,
      val1 = val1,
      val2 = val2)
  }

  def batchInsert(entities: Seq[ContractParameterType4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val1 -> entity.val1,
        'val2 -> entity.val2))
    SQL("""insert into contract_parameter_type_4(
      cid,
      pid,
      val1,
      val2
    ) values (
      {cid},
      {pid},
      {val1},
      {val2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType4)(implicit session: DBSession = autoSession): ContractParameterType4 = {
    withSQL {
      update(ContractParameterType4).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.val1 -> entity.val1,
        column.val2 -> entity.val2
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.val1, entity.val1).and.eq(column.val2, entity.val2)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType4).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.val1, entity.val1).and.eq(column.val2, entity.val2) }.update.apply()
  }

}
