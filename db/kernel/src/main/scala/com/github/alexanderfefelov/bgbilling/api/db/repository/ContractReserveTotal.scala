package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractReserveTotal(
  cid: Int,
  sum: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = ContractReserveTotal.autoSession): ContractReserveTotal = ContractReserveTotal.save(this)(session)

  def destroy()(implicit session: DBSession = ContractReserveTotal.autoSession): Int = ContractReserveTotal.destroy(this)(session)

}


object ContractReserveTotal extends SQLSyntaxSupport[ContractReserveTotal] {

  override val tableName = "contract_reserve_total"

  override val columns = Seq("cid", "sum")

  def apply(crt: SyntaxProvider[ContractReserveTotal])(rs: WrappedResultSet): ContractReserveTotal = autoConstruct(rs, crt)
  def apply(crt: ResultName[ContractReserveTotal])(rs: WrappedResultSet): ContractReserveTotal = autoConstruct(rs, crt)

  val crt = ContractReserveTotal.syntax("crt")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[ContractReserveTotal] = {
    withSQL {
      select.from(ContractReserveTotal as crt).where.eq(crt.cid, cid)
    }.map(ContractReserveTotal(crt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractReserveTotal] = {
    withSQL(select.from(ContractReserveTotal as crt)).map(ContractReserveTotal(crt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractReserveTotal as crt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractReserveTotal] = {
    withSQL {
      select.from(ContractReserveTotal as crt).where.append(where)
    }.map(ContractReserveTotal(crt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractReserveTotal] = {
    withSQL {
      select.from(ContractReserveTotal as crt).where.append(where)
    }.map(ContractReserveTotal(crt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractReserveTotal as crt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    sum: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): ContractReserveTotal = {
    withSQL {
      insert.into(ContractReserveTotal).namedValues(
        column.cid -> cid,
        column.sum -> sum
      )
    }.update.apply()

    ContractReserveTotal(
      cid = cid,
      sum = sum)
  }

  def batchInsert(entities: collection.Seq[ContractReserveTotal])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'sum -> entity.sum))
    SQL("""insert into contract_reserve_total(
      cid,
      sum
    ) values (
      {cid},
      {sum}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractReserveTotal)(implicit session: DBSession = autoSession): ContractReserveTotal = {
    withSQL {
      update(ContractReserveTotal).set(
        column.cid -> entity.cid,
        column.sum -> entity.sum
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractReserveTotal)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractReserveTotal).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
