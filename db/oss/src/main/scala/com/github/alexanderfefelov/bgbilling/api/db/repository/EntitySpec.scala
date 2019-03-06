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
    sql"""select ${es.result.*} from ${EntitySpec as es} where ${es.id} = ${id}"""
      .map(EntitySpec(es.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpec] = {
    sql"""select ${es.result.*} from ${EntitySpec as es}""".map(EntitySpec(es.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntitySpec.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpec] = {
    sql"""select ${es.result.*} from ${EntitySpec as es} where ${where}"""
      .map(EntitySpec(es.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpec] = {
    sql"""select ${es.result.*} from ${EntitySpec as es} where ${where}"""
      .map(EntitySpec(es.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntitySpec as es} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    entityspectypeid: Int,
    comment: String,
    hidden: Int,
    entitytitlemacros: String)(implicit session: DBSession = autoSession): EntitySpec = {
    val generatedKey = sql"""
      insert into ${EntitySpec.table} (
        ${column.title},
        ${column.entityspectypeid},
        ${column.comment},
        ${column.hidden},
        ${column.entitytitlemacros}
      ) values (
        ${title},
        ${entityspectypeid},
        ${comment},
        ${hidden},
        ${entitytitlemacros}
      )
      """.updateAndReturnGeneratedKey.apply()

    EntitySpec(
      id = generatedKey.toInt,
      title = title,
      entityspectypeid = entityspectypeid,
      comment = comment,
      hidden = hidden,
      entitytitlemacros = entitytitlemacros)
  }

  def batchInsert(entities: collection.Seq[EntitySpec])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
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
    sql"""
      update
        ${EntitySpec.table}
      set
        ${column.id} = ${entity.id},
        ${column.title} = ${entity.title},
        ${column.entityspectypeid} = ${entity.entityspectypeid},
        ${column.comment} = ${entity.comment},
        ${column.hidden} = ${entity.hidden},
        ${column.entitytitlemacros} = ${entity.entitytitlemacros}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: EntitySpec)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntitySpec.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
