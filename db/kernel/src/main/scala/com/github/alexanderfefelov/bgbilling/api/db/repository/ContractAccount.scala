package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractAccount(
  yy: Short,
  mm: Byte,
  cid: Int,
  sid: Int,
  summa: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = ContractAccount.autoSession): ContractAccount = ContractAccount.save(this)(session)

  def destroy()(implicit session: DBSession = ContractAccount.autoSession): Int = ContractAccount.destroy(this)(session)

}


object ContractAccount extends SQLSyntaxSupport[ContractAccount] {

  override val tableName = "contract_account"

  override val columns = Seq("yy", "mm", "cid", "sid", "summa")

  def apply(ca: SyntaxProvider[ContractAccount])(rs: WrappedResultSet): ContractAccount = autoConstruct(rs, ca)
  def apply(ca: ResultName[ContractAccount])(rs: WrappedResultSet): ContractAccount = autoConstruct(rs, ca)

  val ca = ContractAccount.syntax("ca")

  override val autoSession = AutoSession

  def find(cid: Int, mm: Byte, sid: Int, yy: Short)(implicit session: DBSession = autoSession): Option[ContractAccount] = {
    withSQL {
      select.from(ContractAccount as ca).where.eq(ca.cid, cid).and.eq(ca.mm, mm).and.eq(ca.sid, sid).and.eq(ca.yy, yy)
    }.map(ContractAccount(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractAccount] = {
    withSQL(select.from(ContractAccount as ca)).map(ContractAccount(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractAccount as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractAccount] = {
    withSQL {
      select.from(ContractAccount as ca).where.append(where)
    }.map(ContractAccount(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractAccount] = {
    withSQL {
      select.from(ContractAccount as ca).where.append(where)
    }.map(ContractAccount(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractAccount as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    yy: Short,
    mm: Byte,
    cid: Int,
    sid: Int,
    summa: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): ContractAccount = {
    withSQL {
      insert.into(ContractAccount).namedValues(
        column.yy -> yy,
        column.mm -> mm,
        column.cid -> cid,
        column.sid -> sid,
        column.summa -> summa
      )
    }.update.apply()

    ContractAccount(
      yy = yy,
      mm = mm,
      cid = cid,
      sid = sid,
      summa = summa)
  }

  def batchInsert(entities: Seq[ContractAccount])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'yy -> entity.yy,
        'mm -> entity.mm,
        'cid -> entity.cid,
        'sid -> entity.sid,
        'summa -> entity.summa))
    SQL("""insert into contract_account(
      yy,
      mm,
      cid,
      sid,
      summa
    ) values (
      {yy},
      {mm},
      {cid},
      {sid},
      {summa}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractAccount)(implicit session: DBSession = autoSession): ContractAccount = {
    withSQL {
      update(ContractAccount).set(
        column.yy -> entity.yy,
        column.mm -> entity.mm,
        column.cid -> entity.cid,
        column.sid -> entity.sid,
        column.summa -> entity.summa
      ).where.eq(column.cid, entity.cid).and.eq(column.mm, entity.mm).and.eq(column.sid, entity.sid).and.eq(column.yy, entity.yy)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractAccount)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractAccount).where.eq(column.cid, entity.cid).and.eq(column.mm, entity.mm).and.eq(column.sid, entity.sid).and.eq(column.yy, entity.yy) }.update.apply()
  }

}
