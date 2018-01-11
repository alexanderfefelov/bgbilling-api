package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntitySpecAttr(
  id: Int,
  title: String,
  `type`: Int,
  comment: String) {

  def save()(implicit session: DBSession = EntitySpecAttr.autoSession): EntitySpecAttr = EntitySpecAttr.save(this)(session)

  def destroy()(implicit session: DBSession = EntitySpecAttr.autoSession): Int = EntitySpecAttr.destroy(this)(session)

}


object EntitySpecAttr extends SQLSyntaxSupport[EntitySpecAttr] {

  override val tableName = "entity_spec_attr"

  override val columns = Seq("id", "title", "type", "comment")

  def apply(esa: SyntaxProvider[EntitySpecAttr])(rs: WrappedResultSet): EntitySpecAttr = autoConstruct(rs, esa)
  def apply(esa: ResultName[EntitySpecAttr])(rs: WrappedResultSet): EntitySpecAttr = autoConstruct(rs, esa)

  val esa = EntitySpecAttr.syntax("esa")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[EntitySpecAttr] = {
    withSQL {
      select.from(EntitySpecAttr as esa).where.eq(esa.id, id)
    }.map(EntitySpecAttr(esa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpecAttr] = {
    withSQL(select.from(EntitySpecAttr as esa)).map(EntitySpecAttr(esa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntitySpecAttr as esa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpecAttr] = {
    withSQL {
      select.from(EntitySpecAttr as esa).where.append(where)
    }.map(EntitySpecAttr(esa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpecAttr] = {
    withSQL {
      select.from(EntitySpecAttr as esa).where.append(where)
    }.map(EntitySpecAttr(esa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntitySpecAttr as esa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    `type`: Int,
    comment: String)(implicit session: DBSession = autoSession): EntitySpecAttr = {
    val generatedKey = withSQL {
      insert.into(EntitySpecAttr).namedValues(
        column.title -> title,
        column.`type` -> `type`,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    EntitySpecAttr(
      id = generatedKey.toInt,
      title = title,
      `type` = `type`,
      comment = comment)
  }

  def batchInsert(entities: Seq[EntitySpecAttr])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'type -> entity.`type`,
        'comment -> entity.comment))
    SQL("""insert into entity_spec_attr(
      title,
      type,
      comment
    ) values (
      {title},
      {type},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntitySpecAttr)(implicit session: DBSession = autoSession): EntitySpecAttr = {
    withSQL {
      update(EntitySpecAttr).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.`type` -> entity.`type`,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: EntitySpecAttr)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntitySpecAttr).where.eq(column.id, entity.id) }.update.apply()
  }

}
