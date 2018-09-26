package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractLimitManageMode(
  cid: Int,
  mode: Int,
  cnt: Int) {

  def save()(implicit session: DBSession = ContractLimitManageMode.autoSession): ContractLimitManageMode = ContractLimitManageMode.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLimitManageMode.autoSession): Int = ContractLimitManageMode.destroy(this)(session)

}


object ContractLimitManageMode extends SQLSyntaxSupport[ContractLimitManageMode] {

  override val tableName = "contract_limit_manage_mode"

  override val columns = Seq("cid", "mode", "cnt")

  def apply(clmm: SyntaxProvider[ContractLimitManageMode])(rs: WrappedResultSet): ContractLimitManageMode = autoConstruct(rs, clmm)
  def apply(clmm: ResultName[ContractLimitManageMode])(rs: WrappedResultSet): ContractLimitManageMode = autoConstruct(rs, clmm)

  val clmm = ContractLimitManageMode.syntax("clmm")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[ContractLimitManageMode] = {
    withSQL {
      select.from(ContractLimitManageMode as clmm).where.eq(clmm.cid, cid)
    }.map(ContractLimitManageMode(clmm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLimitManageMode] = {
    withSQL(select.from(ContractLimitManageMode as clmm)).map(ContractLimitManageMode(clmm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLimitManageMode as clmm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLimitManageMode] = {
    withSQL {
      select.from(ContractLimitManageMode as clmm).where.append(where)
    }.map(ContractLimitManageMode(clmm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLimitManageMode] = {
    withSQL {
      select.from(ContractLimitManageMode as clmm).where.append(where)
    }.map(ContractLimitManageMode(clmm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLimitManageMode as clmm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    mode: Int,
    cnt: Int)(implicit session: DBSession = autoSession): ContractLimitManageMode = {
    withSQL {
      insert.into(ContractLimitManageMode).namedValues(
        column.cid -> cid,
        column.mode -> mode,
        column.cnt -> cnt
      )
    }.update.apply()

    ContractLimitManageMode(
      cid = cid,
      mode = mode,
      cnt = cnt)
  }

  def batchInsert(entities: collection.Seq[ContractLimitManageMode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'mode -> entity.mode,
        'cnt -> entity.cnt))
    SQL("""insert into contract_limit_manage_mode(
      cid,
      mode,
      cnt
    ) values (
      {cid},
      {mode},
      {cnt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLimitManageMode)(implicit session: DBSession = autoSession): ContractLimitManageMode = {
    withSQL {
      update(ContractLimitManageMode).set(
        column.cid -> entity.cid,
        column.mode -> entity.mode,
        column.cnt -> entity.cnt
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLimitManageMode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLimitManageMode).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
