package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class Entity(
  id: Int,
  entityspecid: Int,
  title: String) {

  def save()(implicit session: DBSession = Entity.autoSession): Entity = Entity.save(this)(session)

  def destroy()(implicit session: DBSession = Entity.autoSession): Int = Entity.destroy(this)(session)

}


object Entity extends SQLSyntaxSupport[Entity] {

  override val tableName = "entity"

  override val columns = Seq("id", "entitySpecId", "title")

  def apply(e: SyntaxProvider[Entity])(rs: WrappedResultSet): Entity = autoConstruct(rs, e)
  def apply(e: ResultName[Entity])(rs: WrappedResultSet): Entity = autoConstruct(rs, e)

  val e = Entity.syntax("e")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Entity] = {
    sql"""select ${e.result.*} from ${Entity as e} where ${e.id} = ${id}"""
      .map(Entity(e.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Entity] = {
    sql"""select ${e.result.*} from ${Entity as e}""".map(Entity(e.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${Entity.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Entity] = {
    sql"""select ${e.result.*} from ${Entity as e} where ${where}"""
      .map(Entity(e.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Entity] = {
    sql"""select ${e.result.*} from ${Entity as e} where ${where}"""
      .map(Entity(e.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${Entity as e} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityspecid: Int,
    title: String)(implicit session: DBSession = autoSession): Entity = {
    val generatedKey = sql"""
      insert into ${Entity.table} (
        ${column.entityspecid},
        ${column.title}
      ) values (
        ${entityspecid},
        ${title}
      )
      """.updateAndReturnGeneratedKey.apply()

    Entity(
      id = generatedKey.toInt,
      entityspecid = entityspecid,
      title = title)
  }

  def batchInsert(entities: collection.Seq[Entity])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityspecid -> entity.entityspecid,
        'title -> entity.title))
    SQL("""insert into entity(
      entitySpecId,
      title
    ) values (
      {entityspecid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Entity)(implicit session: DBSession = autoSession): Entity = {
    sql"""
      update
        ${Entity.table}
      set
        ${column.id} = ${entity.id},
        ${column.entityspecid} = ${entity.entityspecid},
        ${column.title} = ${entity.title}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: Entity)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${Entity.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
