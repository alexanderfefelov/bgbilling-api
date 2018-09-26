package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType5(
  cid: Int,
  pid: Int,
  `val`: Int) {

  def save()(implicit session: DBSession = ContractParameterType5.autoSession): ContractParameterType5 = ContractParameterType5.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType5.autoSession): Int = ContractParameterType5.destroy(this)(session)

}


object ContractParameterType5 extends SQLSyntaxSupport[ContractParameterType5] {

  override val tableName = "contract_parameter_type_5"

  override val columns = Seq("cid", "pid", "val")

  def apply(cpt: SyntaxProvider[ContractParameterType5])(rs: WrappedResultSet): ContractParameterType5 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType5])(rs: WrappedResultSet): ContractParameterType5 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType5.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType5] = {
    withSQL {
      select.from(ContractParameterType5 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid)
    }.map(ContractParameterType5(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType5] = {
    withSQL(select.from(ContractParameterType5 as cpt)).map(ContractParameterType5(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType5 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType5] = {
    withSQL {
      select.from(ContractParameterType5 as cpt).where.append(where)
    }.map(ContractParameterType5(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType5] = {
    withSQL {
      select.from(ContractParameterType5 as cpt).where.append(where)
    }.map(ContractParameterType5(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType5 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Int)(implicit session: DBSession = autoSession): ContractParameterType5 = {
    withSQL {
      insert.into(ContractParameterType5).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`
      )
    }.update.apply()

    ContractParameterType5(
      cid = cid,
      pid = pid,
      `val` = `val`)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`))
    SQL("""insert into contract_parameter_type_5(
      cid,
      pid,
      val
    ) values (
      {cid},
      {pid},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType5)(implicit session: DBSession = autoSession): ContractParameterType5 = {
    withSQL {
      update(ContractParameterType5).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType5).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
