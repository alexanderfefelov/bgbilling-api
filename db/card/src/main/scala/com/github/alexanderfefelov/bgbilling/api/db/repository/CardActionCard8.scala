package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class CardActionCard8(
  actionId: Int,
  cardId: Int) {

  def save()(implicit session: DBSession = CardActionCard8.autoSession): CardActionCard8 = CardActionCard8.save(this)(session)

  def destroy()(implicit session: DBSession = CardActionCard8.autoSession): Int = CardActionCard8.destroy(this)(session)

}


object CardActionCard8 extends SQLSyntaxSupport[CardActionCard8] with ApiDbConfig {

  override val tableName = s"card_action_card_${bgBillingModuleId("card")}"

  override val columns = Seq("action_id", "card_id")

  def apply(cac: SyntaxProvider[CardActionCard8])(rs: WrappedResultSet): CardActionCard8 = autoConstruct(rs, cac)
  def apply(cac: ResultName[CardActionCard8])(rs: WrappedResultSet): CardActionCard8 = autoConstruct(rs, cac)

  val cac = CardActionCard8.syntax("cac")

  override val autoSession = AutoSession

  def find(actionId: Int, cardId: Int)(implicit session: DBSession = autoSession): Option[CardActionCard8] = {
    withSQL {
      select.from(CardActionCard8 as cac).where.eq(cac.actionId, actionId).and.eq(cac.cardId, cardId)
    }.map(CardActionCard8(cac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CardActionCard8] = {
    withSQL(select.from(CardActionCard8 as cac)).map(CardActionCard8(cac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CardActionCard8 as cac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CardActionCard8] = {
    withSQL {
      select.from(CardActionCard8 as cac).where.append(where)
    }.map(CardActionCard8(cac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CardActionCard8] = {
    withSQL {
      select.from(CardActionCard8 as cac).where.append(where)
    }.map(CardActionCard8(cac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CardActionCard8 as cac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    actionId: Int,
    cardId: Int)(implicit session: DBSession = autoSession): CardActionCard8 = {
    withSQL {
      insert.into(CardActionCard8).namedValues(
        column.actionId -> actionId,
        column.cardId -> cardId
      )
    }.update.apply()

    CardActionCard8(
      actionId = actionId,
      cardId = cardId)
  }

  def batchInsert(entities: Seq[CardActionCard8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'actionId -> entity.actionId,
        'cardId -> entity.cardId))
    SQL("""insert into card_action_card_8(
      action_id,
      card_id
    ) values (
      {actionId},
      {cardId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CardActionCard8)(implicit session: DBSession = autoSession): CardActionCard8 = {
    withSQL {
      update(CardActionCard8).set(
        column.actionId -> entity.actionId,
        column.cardId -> entity.cardId
      ).where.eq(column.actionId, entity.actionId).and.eq(column.cardId, entity.cardId)
    }.update.apply()
    entity
  }

  def destroy(entity: CardActionCard8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CardActionCard8).where.eq(column.actionId, entity.actionId).and.eq(column.cardId, entity.cardId) }.update.apply()
  }

}
