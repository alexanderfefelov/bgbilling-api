package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType7(
  cid: Int,
  pid: Int,
  `val`: Int,
  customValue: String) {

  def save()(implicit session: DBSession = ContractParameterType7.autoSession): ContractParameterType7 = ContractParameterType7.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType7.autoSession): Int = ContractParameterType7.destroy(this)(session)

}


object ContractParameterType7 extends SQLSyntaxSupport[ContractParameterType7] {

  override val tableName = "contract_parameter_type_7"

  override val columns = Seq("cid", "pid", "val", "custom_value")

  def apply(cpt: SyntaxProvider[ContractParameterType7])(rs: WrappedResultSet): ContractParameterType7 = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractParameterType7])(rs: WrappedResultSet): ContractParameterType7 = autoConstruct(rs, cpt)

  val cpt = ContractParameterType7.syntax("cpt")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int, `val`: Int, customValue: String)(implicit session: DBSession = autoSession): Option[ContractParameterType7] = {
    withSQL {
      select.from(ContractParameterType7 as cpt).where.eq(cpt.cid, cid).and.eq(cpt.pid, pid).and.eq(cpt.`val`, `val`).and.eq(cpt.customValue, customValue)
    }.map(ContractParameterType7(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType7] = {
    withSQL(select.from(ContractParameterType7 as cpt)).map(ContractParameterType7(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType7 as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType7] = {
    withSQL {
      select.from(ContractParameterType7 as cpt).where.append(where)
    }.map(ContractParameterType7(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType7] = {
    withSQL {
      select.from(ContractParameterType7 as cpt).where.append(where)
    }.map(ContractParameterType7(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType7 as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Int,
    customValue: String)(implicit session: DBSession = autoSession): ContractParameterType7 = {
    withSQL {
      insert.into(ContractParameterType7).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.customValue -> customValue
      )
    }.update.apply()

    ContractParameterType7(
      cid = cid,
      pid = pid,
      `val` = `val`,
      customValue = customValue)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType7])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'customValue -> entity.customValue))
    SQL("""insert into contract_parameter_type_7(
      cid,
      pid,
      val,
      custom_value
    ) values (
      {cid},
      {pid},
      {val},
      {customValue}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType7)(implicit session: DBSession = autoSession): ContractParameterType7 = {
    withSQL {
      update(ContractParameterType7).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.customValue -> entity.customValue
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.customValue, entity.customValue)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType7)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType7).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`).and.eq(column.customValue, entity.customValue) }.update.apply()
  }

}
