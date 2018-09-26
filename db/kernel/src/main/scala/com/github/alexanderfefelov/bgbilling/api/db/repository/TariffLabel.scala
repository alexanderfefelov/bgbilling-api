package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TariffLabel(
  id: Int,
  parentId: Int,
  title: String) {

  def save()(implicit session: DBSession = TariffLabel.autoSession): TariffLabel = TariffLabel.save(this)(session)

  def destroy()(implicit session: DBSession = TariffLabel.autoSession): Int = TariffLabel.destroy(this)(session)

}


object TariffLabel extends SQLSyntaxSupport[TariffLabel] {

  override val tableName = "tariff_label"

  override val columns = Seq("id", "parent_id", "title")

  def apply(tl: SyntaxProvider[TariffLabel])(rs: WrappedResultSet): TariffLabel = autoConstruct(rs, tl)
  def apply(tl: ResultName[TariffLabel])(rs: WrappedResultSet): TariffLabel = autoConstruct(rs, tl)

  val tl = TariffLabel.syntax("tl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffLabel] = {
    withSQL {
      select.from(TariffLabel as tl).where.eq(tl.id, id)
    }.map(TariffLabel(tl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffLabel] = {
    withSQL(select.from(TariffLabel as tl)).map(TariffLabel(tl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffLabel as tl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffLabel] = {
    withSQL {
      select.from(TariffLabel as tl).where.append(where)
    }.map(TariffLabel(tl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffLabel] = {
    withSQL {
      select.from(TariffLabel as tl).where.append(where)
    }.map(TariffLabel(tl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffLabel as tl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentId: Int,
    title: String)(implicit session: DBSession = autoSession): TariffLabel = {
    val generatedKey = withSQL {
      insert.into(TariffLabel).namedValues(
        column.parentId -> parentId,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffLabel(
      id = generatedKey.toInt,
      parentId = parentId,
      title = title)
  }

  def batchInsert(entities: collection.Seq[TariffLabel])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentId -> entity.parentId,
        'title -> entity.title))
    SQL("""insert into tariff_label(
      parent_id,
      title
    ) values (
      {parentId},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffLabel)(implicit session: DBSession = autoSession): TariffLabel = {
    withSQL {
      update(TariffLabel).set(
        column.id -> entity.id,
        column.parentId -> entity.parentId,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffLabel)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffLabel).where.eq(column.id, entity.id) }.update.apply()
  }

}
