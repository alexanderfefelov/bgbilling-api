package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class CardSeries8(
  id: Int,
  title: Option[String] = None,
  free: Int) {

  def save()(implicit session: DBSession = CardSeries8.autoSession): CardSeries8 = CardSeries8.save(this)(session)

  def destroy()(implicit session: DBSession = CardSeries8.autoSession): Int = CardSeries8.destroy(this)(session)

}


object CardSeries8 extends SQLSyntaxSupport[CardSeries8] with ApiDbConfig {

  override val tableName = s"card_series_${bgBillingModuleId("card")}"

  override val columns = Seq("id", "title", "free")

  def apply(cs: SyntaxProvider[CardSeries8])(rs: WrappedResultSet): CardSeries8 = autoConstruct(rs, cs)
  def apply(cs: ResultName[CardSeries8])(rs: WrappedResultSet): CardSeries8 = autoConstruct(rs, cs)

  val cs = CardSeries8.syntax("cs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CardSeries8] = {
    withSQL {
      select.from(CardSeries8 as cs).where.eq(cs.id, id)
    }.map(CardSeries8(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CardSeries8] = {
    withSQL(select.from(CardSeries8 as cs)).map(CardSeries8(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CardSeries8 as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CardSeries8] = {
    withSQL {
      select.from(CardSeries8 as cs).where.append(where)
    }.map(CardSeries8(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CardSeries8] = {
    withSQL {
      select.from(CardSeries8 as cs).where.append(where)
    }.map(CardSeries8(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CardSeries8 as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    free: Int)(implicit session: DBSession = autoSession): CardSeries8 = {
    val generatedKey = withSQL {
      insert.into(CardSeries8).namedValues(
        column.title -> title,
        column.free -> free
      )
    }.updateAndReturnGeneratedKey.apply()

    CardSeries8(
      id = generatedKey.toInt,
      title = title,
      free = free)
  }

  def batchInsert(entities: Seq[CardSeries8])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'free -> entity.free))
    SQL("""insert into card_series_8(
      title,
      free
    ) values (
      {title},
      {free}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CardSeries8)(implicit session: DBSession = autoSession): CardSeries8 = {
    withSQL {
      update(CardSeries8).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.free -> entity.free
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CardSeries8)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CardSeries8).where.eq(column.id, entity.id) }.update.apply()
  }

}
