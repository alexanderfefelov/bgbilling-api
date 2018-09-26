package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TariffGroup(
  id: Int,
  title: String,
  tm: Byte,
  df: Int,
  beh: Int,
  pos: Int) {

  def save()(implicit session: DBSession = TariffGroup.autoSession): TariffGroup = TariffGroup.save(this)(session)

  def destroy()(implicit session: DBSession = TariffGroup.autoSession): Int = TariffGroup.destroy(this)(session)

}


object TariffGroup extends SQLSyntaxSupport[TariffGroup] {

  override val tableName = "tariff_group"

  override val columns = Seq("id", "title", "tm", "df", "beh", "pos")

  def apply(tg: SyntaxProvider[TariffGroup])(rs: WrappedResultSet): TariffGroup = autoConstruct(rs, tg)
  def apply(tg: ResultName[TariffGroup])(rs: WrappedResultSet): TariffGroup = autoConstruct(rs, tg)

  val tg = TariffGroup.syntax("tg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffGroup] = {
    withSQL {
      select.from(TariffGroup as tg).where.eq(tg.id, id)
    }.map(TariffGroup(tg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffGroup] = {
    withSQL(select.from(TariffGroup as tg)).map(TariffGroup(tg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffGroup as tg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffGroup] = {
    withSQL {
      select.from(TariffGroup as tg).where.append(where)
    }.map(TariffGroup(tg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffGroup] = {
    withSQL {
      select.from(TariffGroup as tg).where.append(where)
    }.map(TariffGroup(tg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffGroup as tg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    tm: Byte,
    df: Int,
    beh: Int,
    pos: Int)(implicit session: DBSession = autoSession): TariffGroup = {
    val generatedKey = withSQL {
      insert.into(TariffGroup).namedValues(
        column.title -> title,
        column.tm -> tm,
        column.df -> df,
        column.beh -> beh,
        column.pos -> pos
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffGroup(
      id = generatedKey.toInt,
      title = title,
      tm = tm,
      df = df,
      beh = beh,
      pos = pos)
  }

  def batchInsert(entities: collection.Seq[TariffGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'tm -> entity.tm,
        'df -> entity.df,
        'beh -> entity.beh,
        'pos -> entity.pos))
    SQL("""insert into tariff_group(
      title,
      tm,
      df,
      beh,
      pos
    ) values (
      {title},
      {tm},
      {df},
      {beh},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffGroup)(implicit session: DBSession = autoSession): TariffGroup = {
    withSQL {
      update(TariffGroup).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.tm -> entity.tm,
        column.df -> entity.df,
        column.beh -> entity.beh,
        column.pos -> entity.pos
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
