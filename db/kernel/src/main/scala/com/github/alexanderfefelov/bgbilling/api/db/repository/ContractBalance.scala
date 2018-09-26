package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractBalance(
  yy: Short,
  mm: Byte,
  cid: Int,
  summa1: BigDecimal,
  summa2: BigDecimal,
  summa3: BigDecimal,
  summa4: BigDecimal) {

  def save()(implicit session: DBSession = ContractBalance.autoSession): ContractBalance = ContractBalance.save(this)(session)

  def destroy()(implicit session: DBSession = ContractBalance.autoSession): Int = ContractBalance.destroy(this)(session)

}


object ContractBalance extends SQLSyntaxSupport[ContractBalance] {

  override val tableName = "contract_balance"

  override val columns = Seq("yy", "mm", "cid", "summa1", "summa2", "summa3", "summa4")

  def apply(cb: SyntaxProvider[ContractBalance])(rs: WrappedResultSet): ContractBalance = autoConstruct(rs, cb)
  def apply(cb: ResultName[ContractBalance])(rs: WrappedResultSet): ContractBalance = autoConstruct(rs, cb)

  val cb = ContractBalance.syntax("cb")

  override val autoSession = AutoSession

  def find(cid: Int, mm: Byte, yy: Short)(implicit session: DBSession = autoSession): Option[ContractBalance] = {
    withSQL {
      select.from(ContractBalance as cb).where.eq(cb.cid, cid).and.eq(cb.mm, mm).and.eq(cb.yy, yy)
    }.map(ContractBalance(cb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractBalance] = {
    withSQL(select.from(ContractBalance as cb)).map(ContractBalance(cb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractBalance as cb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractBalance] = {
    withSQL {
      select.from(ContractBalance as cb).where.append(where)
    }.map(ContractBalance(cb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractBalance] = {
    withSQL {
      select.from(ContractBalance as cb).where.append(where)
    }.map(ContractBalance(cb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractBalance as cb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    yy: Short,
    mm: Byte,
    cid: Int,
    summa1: BigDecimal,
    summa2: BigDecimal,
    summa3: BigDecimal,
    summa4: BigDecimal)(implicit session: DBSession = autoSession): ContractBalance = {
    withSQL {
      insert.into(ContractBalance).namedValues(
        column.yy -> yy,
        column.mm -> mm,
        column.cid -> cid,
        column.summa1 -> summa1,
        column.summa2 -> summa2,
        column.summa3 -> summa3,
        column.summa4 -> summa4
      )
    }.update.apply()

    ContractBalance(
      yy = yy,
      mm = mm,
      cid = cid,
      summa1 = summa1,
      summa2 = summa2,
      summa3 = summa3,
      summa4 = summa4)
  }

  def batchInsert(entities: collection.Seq[ContractBalance])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'yy -> entity.yy,
        'mm -> entity.mm,
        'cid -> entity.cid,
        'summa1 -> entity.summa1,
        'summa2 -> entity.summa2,
        'summa3 -> entity.summa3,
        'summa4 -> entity.summa4))
    SQL("""insert into contract_balance(
      yy,
      mm,
      cid,
      summa1,
      summa2,
      summa3,
      summa4
    ) values (
      {yy},
      {mm},
      {cid},
      {summa1},
      {summa2},
      {summa3},
      {summa4}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractBalance)(implicit session: DBSession = autoSession): ContractBalance = {
    withSQL {
      update(ContractBalance).set(
        column.yy -> entity.yy,
        column.mm -> entity.mm,
        column.cid -> entity.cid,
        column.summa1 -> entity.summa1,
        column.summa2 -> entity.summa2,
        column.summa3 -> entity.summa3,
        column.summa4 -> entity.summa4
      ).where.eq(column.cid, entity.cid).and.eq(column.mm, entity.mm).and.eq(column.yy, entity.yy)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractBalance)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractBalance).where.eq(column.cid, entity.cid).and.eq(column.mm, entity.mm).and.eq(column.yy, entity.yy) }.update.apply()
  }

}
