package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterTypeMultilistItem(
  cid: Int,
  pid: Int,
  `val`: Int,
  customValue: String) {

  def save()(implicit session: DBSession = ContractParameterTypeMultilistItem.autoSession): ContractParameterTypeMultilistItem = ContractParameterTypeMultilistItem.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypeMultilistItem.autoSession): Int = ContractParameterTypeMultilistItem.destroy(this)(session)

}


object ContractParameterTypeMultilistItem extends SQLSyntaxSupport[ContractParameterTypeMultilistItem] {

  override val tableName = "contract_parameter_type_multilist_item"

  override val columns = Seq("cid", "pid", "val", "custom_value")

  def apply(cptmi: SyntaxProvider[ContractParameterTypeMultilistItem])(rs: WrappedResultSet): ContractParameterTypeMultilistItem = autoConstruct(rs, cptmi)
  def apply(cptmi: ResultName[ContractParameterTypeMultilistItem])(rs: WrappedResultSet): ContractParameterTypeMultilistItem = autoConstruct(rs, cptmi)

  val cptmi = ContractParameterTypeMultilistItem.syntax("cptmi")

  override val autoSession = AutoSession

  def find(cid: Int, customValue: String, pid: Int, `val`: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistItem] = {
    withSQL {
      select.from(ContractParameterTypeMultilistItem as cptmi).where.eq(cptmi.cid, cid).and.eq(cptmi.customValue, customValue).and.eq(cptmi.pid, pid).and.eq(cptmi.`val`, `val`)
    }.map(ContractParameterTypeMultilistItem(cptmi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistItem] = {
    withSQL(select.from(ContractParameterTypeMultilistItem as cptmi)).map(ContractParameterTypeMultilistItem(cptmi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypeMultilistItem as cptmi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistItem] = {
    withSQL {
      select.from(ContractParameterTypeMultilistItem as cptmi).where.append(where)
    }.map(ContractParameterTypeMultilistItem(cptmi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistItem] = {
    withSQL {
      select.from(ContractParameterTypeMultilistItem as cptmi).where.append(where)
    }.map(ContractParameterTypeMultilistItem(cptmi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypeMultilistItem as cptmi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: Int,
    customValue: String)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistItem = {
    withSQL {
      insert.into(ContractParameterTypeMultilistItem).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`,
        column.customValue -> customValue
      )
    }.update.apply()

    ContractParameterTypeMultilistItem(
      cid = cid,
      pid = pid,
      `val` = `val`,
      customValue = customValue)
  }

  def batchInsert(entities: collection.Seq[ContractParameterTypeMultilistItem])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`,
        'customValue -> entity.customValue))
    SQL("""insert into contract_parameter_type_multilist_item(
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

  def save(entity: ContractParameterTypeMultilistItem)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistItem = {
    withSQL {
      update(ContractParameterTypeMultilistItem).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`,
        column.customValue -> entity.customValue
      ).where.eq(column.cid, entity.cid).and.eq(column.customValue, entity.customValue).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypeMultilistItem)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypeMultilistItem).where.eq(column.cid, entity.cid).and.eq(column.customValue, entity.customValue).and.eq(column.pid, entity.pid).and.eq(column.`val`, entity.`val`) }.update.apply()
  }

}
