package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntitySpec(
  id: Int,
  title: String,
  entityspectypeid: Int,
  comment: String,
  hidden: Int,
  entitytitlemacros: String) {

  def save()(implicit session: DBSession = EntitySpec.autoSession): EntitySpec = EntitySpec.save(this)(session)

  def destroy()(implicit session: DBSession = EntitySpec.autoSession): Int = EntitySpec.destroy(this)(session)

}


object EntitySpec extends SQLSyntaxSupport[EntitySpec] {

  override val tableName = "entity_spec"

  override val columns = Seq("id", "title", "entitySpecTypeId", "comment", "hidden", "entityTitleMacros")

  def apply(es: SyntaxProvider[EntitySpec])(rs: WrappedResultSet): EntitySpec = autoConstruct(rs, es)
  def apply(es: ResultName[EntitySpec])(rs: WrappedResultSet): EntitySpec = autoConstruct(rs, es)

  val es = EntitySpec.syntax("es")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[EntitySpec] = {
    withSQL {
      select.from(EntitySpec as es).where.eq(es.id, id)
    }.map(EntitySpec(es.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpec] = {
    withSQL(select.from(EntitySpec as es)).map(EntitySpec(es.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntitySpec as es)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpec] = {
    withSQL {
      select.from(EntitySpec as es).where.append(where)
    }.map(EntitySpec(es.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpec] = {
    withSQL {
      select.from(EntitySpec as es).where.append(where)
    }.map(EntitySpec(es.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntitySpec as es).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    entityspectypeid: Int,
    comment: String,
    hidden: Int,
    entitytitlemacros: String)(implicit session: DBSession = autoSession): EntitySpec = {
    val generatedKey = withSQL {
      insert.into(EntitySpec).namedValues(
        column.title -> title,
        column.entityspectypeid -> entityspectypeid,
        column.comment -> comment,
        column.hidden -> hidden,
        column.entitytitlemacros -> entitytitlemacros
      )
    }.updateAndReturnGeneratedKey.apply()

    EntitySpec(
      id = generatedKey.toInt,
      title = title,
      entityspectypeid = entityspectypeid,
      comment = comment,
      hidden = hidden,
      entitytitlemacros = entitytitlemacros)
  }

  def batchInsert(entities: Seq[EntitySpec])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'entityspectypeid -> entity.entityspectypeid,
        'comment -> entity.comment,
        'hidden -> entity.hidden,
        'entitytitlemacros -> entity.entitytitlemacros))
    SQL("""insert into entity_spec(
      title,
      entitySpecTypeId,
      comment,
      hidden,
      entityTitleMacros
    ) values (
      {title},
      {entityspectypeid},
      {comment},
      {hidden},
      {entitytitlemacros}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntitySpec)(implicit session: DBSession = autoSession): EntitySpec = {
    withSQL {
      update(EntitySpec).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.entityspectypeid -> entity.entityspectypeid,
        column.comment -> entity.comment,
        column.hidden -> entity.hidden,
        column.entitytitlemacros -> entity.entitytitlemacros
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: EntitySpec)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntitySpec).where.eq(column.id, entity.id) }.update.apply()
  }

}
