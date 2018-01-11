package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BonusDetailsCharge(
  id: Int,
  chargeid: Int,
  paymentid: Int,
  sum: BigDecimal) {

  def save()(implicit session: DBSession = BonusDetailsCharge.autoSession): BonusDetailsCharge = BonusDetailsCharge.save(this)(session)

  def destroy()(implicit session: DBSession = BonusDetailsCharge.autoSession): Int = BonusDetailsCharge.destroy(this)(session)

}


object BonusDetailsCharge extends SQLSyntaxSupport[BonusDetailsCharge] {

  override val tableName = "bonus_details_charge"

  override val columns = Seq("id", "chargeId", "paymentId", "sum")

  def apply(bdc: SyntaxProvider[BonusDetailsCharge])(rs: WrappedResultSet): BonusDetailsCharge = autoConstruct(rs, bdc)
  def apply(bdc: ResultName[BonusDetailsCharge])(rs: WrappedResultSet): BonusDetailsCharge = autoConstruct(rs, bdc)

  val bdc = BonusDetailsCharge.syntax("bdc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BonusDetailsCharge] = {
    withSQL {
      select.from(BonusDetailsCharge as bdc).where.eq(bdc.id, id)
    }.map(BonusDetailsCharge(bdc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusDetailsCharge] = {
    withSQL(select.from(BonusDetailsCharge as bdc)).map(BonusDetailsCharge(bdc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusDetailsCharge as bdc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusDetailsCharge] = {
    withSQL {
      select.from(BonusDetailsCharge as bdc).where.append(where)
    }.map(BonusDetailsCharge(bdc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusDetailsCharge] = {
    withSQL {
      select.from(BonusDetailsCharge as bdc).where.append(where)
    }.map(BonusDetailsCharge(bdc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusDetailsCharge as bdc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    chargeid: Int,
    paymentid: Int,
    sum: BigDecimal)(implicit session: DBSession = autoSession): BonusDetailsCharge = {
    val generatedKey = withSQL {
      insert.into(BonusDetailsCharge).namedValues(
        column.chargeid -> chargeid,
        column.paymentid -> paymentid,
        column.sum -> sum
      )
    }.updateAndReturnGeneratedKey.apply()

    BonusDetailsCharge(
      id = generatedKey.toInt,
      chargeid = chargeid,
      paymentid = paymentid,
      sum = sum)
  }

  def batchInsert(entities: Seq[BonusDetailsCharge])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'chargeid -> entity.chargeid,
        'paymentid -> entity.paymentid,
        'sum -> entity.sum))
    SQL("""insert into bonus_details_charge(
      chargeId,
      paymentId,
      sum
    ) values (
      {chargeid},
      {paymentid},
      {sum}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusDetailsCharge)(implicit session: DBSession = autoSession): BonusDetailsCharge = {
    withSQL {
      update(BonusDetailsCharge).set(
        column.id -> entity.id,
        column.chargeid -> entity.chargeid,
        column.paymentid -> entity.paymentid,
        column.sum -> entity.sum
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusDetailsCharge)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusDetailsCharge).where.eq(column.id, entity.id) }.update.apply()
  }

}
