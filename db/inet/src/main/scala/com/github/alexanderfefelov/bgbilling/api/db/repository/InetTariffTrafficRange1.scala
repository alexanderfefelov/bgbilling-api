package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetTariffTrafficRange1(
  contractid: Int,
  treenodeid: Long,
  rangekey: Long,
  amount: Long,
  counter: Int,
  maxamount: Long,
  yy: Int,
  mm: Int) {

  def save()(implicit session: DBSession = InetTariffTrafficRange1.autoSession): InetTariffTrafficRange1 = InetTariffTrafficRange1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTariffTrafficRange1.autoSession): Int = InetTariffTrafficRange1.destroy(this)(session)

}


object InetTariffTrafficRange1 extends SQLSyntaxSupport[InetTariffTrafficRange1] {

  override val tableName = "inet_tariff_traffic_range_1"

  override val columns = Seq("contractId", "treeNodeId", "rangeKey", "amount", "counter", "maxAmount", "yy", "mm")

  def apply(ittr: SyntaxProvider[InetTariffTrafficRange1])(rs: WrappedResultSet): InetTariffTrafficRange1 = autoConstruct(rs, ittr)
  def apply(ittr: ResultName[InetTariffTrafficRange1])(rs: WrappedResultSet): InetTariffTrafficRange1 = autoConstruct(rs, ittr)

  val ittr = InetTariffTrafficRange1.syntax("ittr")

  override val autoSession = AutoSession

  def find(contractid: Int, mm: Int, rangekey: Long, treenodeid: Long, yy: Int)(implicit session: DBSession = autoSession): Option[InetTariffTrafficRange1] = {
    withSQL {
      select.from(InetTariffTrafficRange1 as ittr).where.eq(ittr.contractid, contractid).and.eq(ittr.mm, mm).and.eq(ittr.rangekey, rangekey).and.eq(ittr.treenodeid, treenodeid).and.eq(ittr.yy, yy)
    }.map(InetTariffTrafficRange1(ittr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTariffTrafficRange1] = {
    withSQL(select.from(InetTariffTrafficRange1 as ittr)).map(InetTariffTrafficRange1(ittr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTariffTrafficRange1 as ittr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTariffTrafficRange1] = {
    withSQL {
      select.from(InetTariffTrafficRange1 as ittr).where.append(where)
    }.map(InetTariffTrafficRange1(ittr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTariffTrafficRange1] = {
    withSQL {
      select.from(InetTariffTrafficRange1 as ittr).where.append(where)
    }.map(InetTariffTrafficRange1(ittr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTariffTrafficRange1 as ittr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    treenodeid: Long,
    rangekey: Long,
    amount: Long,
    counter: Int,
    maxamount: Long,
    yy: Int,
    mm: Int)(implicit session: DBSession = autoSession): InetTariffTrafficRange1 = {
    withSQL {
      insert.into(InetTariffTrafficRange1).namedValues(
        column.contractid -> contractid,
        column.treenodeid -> treenodeid,
        column.rangekey -> rangekey,
        column.amount -> amount,
        column.counter -> counter,
        column.maxamount -> maxamount,
        column.yy -> yy,
        column.mm -> mm
      )
    }.update.apply()

    InetTariffTrafficRange1(
      contractid = contractid,
      treenodeid = treenodeid,
      rangekey = rangekey,
      amount = amount,
      counter = counter,
      maxamount = maxamount,
      yy = yy,
      mm = mm)
  }

  def batchInsert(entities: Seq[InetTariffTrafficRange1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'treenodeid -> entity.treenodeid,
        'rangekey -> entity.rangekey,
        'amount -> entity.amount,
        'counter -> entity.counter,
        'maxamount -> entity.maxamount,
        'yy -> entity.yy,
        'mm -> entity.mm))
    SQL("""insert into inet_tariff_traffic_range_1(
      contractId,
      treeNodeId,
      rangeKey,
      amount,
      counter,
      maxAmount,
      yy,
      mm
    ) values (
      {contractid},
      {treenodeid},
      {rangekey},
      {amount},
      {counter},
      {maxamount},
      {yy},
      {mm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTariffTrafficRange1)(implicit session: DBSession = autoSession): InetTariffTrafficRange1 = {
    withSQL {
      update(InetTariffTrafficRange1).set(
        column.contractid -> entity.contractid,
        column.treenodeid -> entity.treenodeid,
        column.rangekey -> entity.rangekey,
        column.amount -> entity.amount,
        column.counter -> entity.counter,
        column.maxamount -> entity.maxamount,
        column.yy -> entity.yy,
        column.mm -> entity.mm
      ).where.eq(column.contractid, entity.contractid).and.eq(column.mm, entity.mm).and.eq(column.rangekey, entity.rangekey).and.eq(column.treenodeid, entity.treenodeid).and.eq(column.yy, entity.yy)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTariffTrafficRange1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTariffTrafficRange1).where.eq(column.contractid, entity.contractid).and.eq(column.mm, entity.mm).and.eq(column.rangekey, entity.rangekey).and.eq(column.treenodeid, entity.treenodeid).and.eq(column.yy, entity.yy) }.update.apply()
  }

}
