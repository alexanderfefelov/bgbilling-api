package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}

case class CardAction8(
  id: Int,
  `type`: Byte,
  dtime: Option[DateTime] = None,
  dealerId: Int,
  count: Int,
  summ: Float,
  cost: Float) {

  def save()(implicit session: DBSession = CardAction8.autoSession): CardAction8 = CardAction8.save(this)(session)

  def destroy()(implicit session: DBSession = CardAction8.autoSession): Int = CardAction8.destroy(this)(session)

}


object CardAction8 extends SQLSyntaxSupport[CardAction8] with ApiDbConfig {

  override val tableName = s"card_action_${bgBillingModuleId("card")}"

  override val columns = Seq("id", "type", "dtime", "dealer_id", "count", "summ", "cost")

  def apply(ca: SyntaxProvider[CardAction8])(rs: WrappedResultSet): CardAction8 = autoConstruct(rs, ca)
  def apply(ca: ResultName[CardAction8])(rs: WrappedResultSet): CardAction8 = autoConstruct(rs, ca)

  val ca = CardAction8.syntax("ca")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CardAction8] = {
    withSQL {
      select.from(CardAction8 as ca).where.eq(ca.id, id)
    }.map(CardAction8(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CardAction8] = {
    withSQL(select.from(CardAction8 as ca)).map(CardAction8(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CardAction8 as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CardAction8] = {
    withSQL {
      select.from(CardAction8 as ca).where.append(where)
    }.map(CardAction8(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CardAction8] = {
    withSQL {
      select.from(CardAction8 as ca).where.append(where)
    }.map(CardAction8(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CardAction8 as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    `type`: Byte,
    dtime: Option[DateTime] = None,
    dealerId: Int,
    count: Int,
    summ: Float,
    cost: Float)(implicit session: DBSession = autoSession): CardAction8 = {
    val generatedKey = withSQL {
      insert.into(CardAction8).namedValues(
        column.`type` -> `type`,
        column.dtime -> dtime,
        column.dealerId -> dealerId,
        column.count -> count,
        column.summ -> summ,
        column.cost -> cost
      )
    }.updateAndReturnGeneratedKey.apply()

    CardAction8(
      id = generatedKey.toInt,
      `type` = `type`,
      dtime = dtime,
      dealerId = dealerId,
      count = count,
      summ = summ,
      cost = cost)
  }

  def batchInsert(entities: Seq[CardAction8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'type -> entity.`type`,
        'dtime -> entity.dtime,
        'dealerId -> entity.dealerId,
        'count -> entity.count,
        'summ -> entity.summ,
        'cost -> entity.cost))
    SQL("""insert into card_action_8(
      type,
      dtime,
      dealer_id,
      count,
      summ,
      cost
    ) values (
      {type},
      {dtime},
      {dealerId},
      {count},
      {summ},
      {cost}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CardAction8)(implicit session: DBSession = autoSession): CardAction8 = {
    withSQL {
      update(CardAction8).set(
        column.id -> entity.id,
        column.`type` -> entity.`type`,
        column.dtime -> entity.dtime,
        column.dealerId -> entity.dealerId,
        column.count -> entity.count,
        column.summ -> entity.summ,
        column.cost -> entity.cost
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CardAction8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CardAction8).where.eq(column.id, entity.id) }.update.apply()
  }

}
