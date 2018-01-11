package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetTariffTrafficMax1(
  contractid: Int,
  treenodeid: Long,
  maxkey: Long,
  counter: Int,
  yy: Int,
  mm: Int,
  amountmax: Long,
  amount1: Long,
  amount2: Long) {

  def save()(implicit session: DBSession = InetTariffTrafficMax1.autoSession): InetTariffTrafficMax1 = InetTariffTrafficMax1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTariffTrafficMax1.autoSession): Int = InetTariffTrafficMax1.destroy(this)(session)

}


object InetTariffTrafficMax1 extends SQLSyntaxSupport[InetTariffTrafficMax1] with ApiDbConfig {

  override val tableName = s"inet_tariff_traffic_max_${bgBillingModuleId("inet")}"

  override val columns = Seq("contractId", "treeNodeId", "maxKey", "counter", "yy", "mm", "amountMax", "amount1", "amount2")

  def apply(ittm: SyntaxProvider[InetTariffTrafficMax1])(rs: WrappedResultSet): InetTariffTrafficMax1 = autoConstruct(rs, ittm)
  def apply(ittm: ResultName[InetTariffTrafficMax1])(rs: WrappedResultSet): InetTariffTrafficMax1 = autoConstruct(rs, ittm)

  val ittm = InetTariffTrafficMax1.syntax("ittm")

  override val autoSession = AutoSession

  def find(contractid: Int, maxkey: Long, mm: Int, treenodeid: Long, yy: Int)(implicit session: DBSession = autoSession): Option[InetTariffTrafficMax1] = {
    withSQL {
      select.from(InetTariffTrafficMax1 as ittm).where.eq(ittm.contractid, contractid).and.eq(ittm.maxkey, maxkey).and.eq(ittm.mm, mm).and.eq(ittm.treenodeid, treenodeid).and.eq(ittm.yy, yy)
    }.map(InetTariffTrafficMax1(ittm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTariffTrafficMax1] = {
    withSQL(select.from(InetTariffTrafficMax1 as ittm)).map(InetTariffTrafficMax1(ittm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTariffTrafficMax1 as ittm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTariffTrafficMax1] = {
    withSQL {
      select.from(InetTariffTrafficMax1 as ittm).where.append(where)
    }.map(InetTariffTrafficMax1(ittm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTariffTrafficMax1] = {
    withSQL {
      select.from(InetTariffTrafficMax1 as ittm).where.append(where)
    }.map(InetTariffTrafficMax1(ittm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTariffTrafficMax1 as ittm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    treenodeid: Long,
    maxkey: Long,
    counter: Int,
    yy: Int,
    mm: Int,
    amountmax: Long,
    amount1: Long,
    amount2: Long)(implicit session: DBSession = autoSession): InetTariffTrafficMax1 = {
    withSQL {
      insert.into(InetTariffTrafficMax1).namedValues(
        column.contractid -> contractid,
        column.treenodeid -> treenodeid,
        column.maxkey -> maxkey,
        column.counter -> counter,
        column.yy -> yy,
        column.mm -> mm,
        column.amountmax -> amountmax,
        column.amount1 -> amount1,
        column.amount2 -> amount2
      )
    }.update.apply()

    InetTariffTrafficMax1(
      contractid = contractid,
      treenodeid = treenodeid,
      maxkey = maxkey,
      counter = counter,
      yy = yy,
      mm = mm,
      amountmax = amountmax,
      amount1 = amount1,
      amount2 = amount2)
  }

  def batchInsert(entities: Seq[InetTariffTrafficMax1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'treenodeid -> entity.treenodeid,
        'maxkey -> entity.maxkey,
        'counter -> entity.counter,
        'yy -> entity.yy,
        'mm -> entity.mm,
        'amountmax -> entity.amountmax,
        'amount1 -> entity.amount1,
        'amount2 -> entity.amount2))
    SQL("""insert into inet_tariff_traffic_max_1(
      contractId,
      treeNodeId,
      maxKey,
      counter,
      yy,
      mm,
      amountMax,
      amount1,
      amount2
    ) values (
      {contractid},
      {treenodeid},
      {maxkey},
      {counter},
      {yy},
      {mm},
      {amountmax},
      {amount1},
      {amount2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTariffTrafficMax1)(implicit session: DBSession = autoSession): InetTariffTrafficMax1 = {
    withSQL {
      update(InetTariffTrafficMax1).set(
        column.contractid -> entity.contractid,
        column.treenodeid -> entity.treenodeid,
        column.maxkey -> entity.maxkey,
        column.counter -> entity.counter,
        column.yy -> entity.yy,
        column.mm -> entity.mm,
        column.amountmax -> entity.amountmax,
        column.amount1 -> entity.amount1,
        column.amount2 -> entity.amount2
      ).where.eq(column.contractid, entity.contractid).and.eq(column.maxkey, entity.maxkey).and.eq(column.mm, entity.mm).and.eq(column.treenodeid, entity.treenodeid).and.eq(column.yy, entity.yy)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTariffTrafficMax1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTariffTrafficMax1).where.eq(column.contractid, entity.contractid).and.eq(column.maxkey, entity.maxkey).and.eq(column.mm, entity.mm).and.eq(column.treenodeid, entity.treenodeid).and.eq(column.yy, entity.yy) }.update.apply()
  }

}
