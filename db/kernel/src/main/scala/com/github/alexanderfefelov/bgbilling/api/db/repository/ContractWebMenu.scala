package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractWebMenu(
  contractId: Int,
  webMenuId: Int) {

  def save()(implicit session: DBSession = ContractWebMenu.autoSession): ContractWebMenu = ContractWebMenu.save(this)(session)

  def destroy()(implicit session: DBSession = ContractWebMenu.autoSession): Int = ContractWebMenu.destroy(this)(session)

}


object ContractWebMenu extends SQLSyntaxSupport[ContractWebMenu] {

  override val tableName = "contract_web_menu"

  override val columns = Seq("contract_id", "web_menu_id")

  def apply(cwm: SyntaxProvider[ContractWebMenu])(rs: WrappedResultSet): ContractWebMenu = autoConstruct(rs, cwm)
  def apply(cwm: ResultName[ContractWebMenu])(rs: WrappedResultSet): ContractWebMenu = autoConstruct(rs, cwm)

  val cwm = ContractWebMenu.syntax("cwm")

  override val autoSession = AutoSession

  def find(contractId: Int, webMenuId: Int)(implicit session: DBSession = autoSession): Option[ContractWebMenu] = {
    withSQL {
      select.from(ContractWebMenu as cwm).where.eq(cwm.contractId, contractId).and.eq(cwm.webMenuId, webMenuId)
    }.map(ContractWebMenu(cwm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractWebMenu] = {
    withSQL(select.from(ContractWebMenu as cwm)).map(ContractWebMenu(cwm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractWebMenu as cwm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractWebMenu] = {
    withSQL {
      select.from(ContractWebMenu as cwm).where.append(where)
    }.map(ContractWebMenu(cwm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractWebMenu] = {
    withSQL {
      select.from(ContractWebMenu as cwm).where.append(where)
    }.map(ContractWebMenu(cwm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractWebMenu as cwm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    webMenuId: Int)(implicit session: DBSession = autoSession): ContractWebMenu = {
    withSQL {
      insert.into(ContractWebMenu).namedValues(
        column.contractId -> contractId,
        column.webMenuId -> webMenuId
      )
    }.update.apply()

    ContractWebMenu(
      contractId = contractId,
      webMenuId = webMenuId)
  }

  def batchInsert(entities: Seq[ContractWebMenu])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'webMenuId -> entity.webMenuId))
    SQL("""insert into contract_web_menu(
      contract_id,
      web_menu_id
    ) values (
      {contractId},
      {webMenuId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractWebMenu)(implicit session: DBSession = autoSession): ContractWebMenu = {
    withSQL {
      update(ContractWebMenu).set(
        column.contractId -> entity.contractId,
        column.webMenuId -> entity.webMenuId
      ).where.eq(column.contractId, entity.contractId).and.eq(column.webMenuId, entity.webMenuId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractWebMenu)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractWebMenu).where.eq(column.contractId, entity.contractId).and.eq(column.webMenuId, entity.webMenuId) }.update.apply()
  }

}
