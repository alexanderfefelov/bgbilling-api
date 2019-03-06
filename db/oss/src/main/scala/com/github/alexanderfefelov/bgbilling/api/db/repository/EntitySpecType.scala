package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntitySpecType(
  id: Int,
  title: String,
  `type`: String) {

  def save()(implicit session: DBSession = EntitySpecType.autoSession): EntitySpecType = EntitySpecType.save(this)(session)

  def destroy()(implicit session: DBSession = EntitySpecType.autoSession): Int = EntitySpecType.destroy(this)(session)

}


object EntitySpecType extends SQLSyntaxSupport[EntitySpecType] {

  override val tableName = "entity_spec_type"

  override val columns = Seq("id", "title", "type")

  def apply(est: SyntaxProvider[EntitySpecType])(rs: WrappedResultSet): EntitySpecType = autoConstruct(rs, est)
  def apply(est: ResultName[EntitySpecType])(rs: WrappedResultSet): EntitySpecType = autoConstruct(rs, est)

  val est = EntitySpecType.syntax("est")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[EntitySpecType] = {
    sql"""select ${est.result.*} from ${EntitySpecType as est} where ${est.id} = ${id}"""
      .map(EntitySpecType(est.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpecType] = {
    sql"""select ${est.result.*} from ${EntitySpecType as est}""".map(EntitySpecType(est.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntitySpecType.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpecType] = {
    sql"""select ${est.result.*} from ${EntitySpecType as est} where ${where}"""
      .map(EntitySpecType(est.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpecType] = {
    sql"""select ${est.result.*} from ${EntitySpecType as est} where ${where}"""
      .map(EntitySpecType(est.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntitySpecType as est} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    `type`: String)(implicit session: DBSession = autoSession): EntitySpecType = {
    val generatedKey = sql"""
      insert into ${EntitySpecType.table} (
        ${column.title},
        ${column.`type`}
      ) values (
        ${title},
        ${`type`}
      )
      """.updateAndReturnGeneratedKey.apply()

    EntitySpecType(
      id = generatedKey.toInt,
      title = title,
      `type` = `type`)
  }

  def batchInsert(entities: collection.Seq[EntitySpecType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'type -> entity.`type`))
    SQL("""insert into entity_spec_type(
      title,
      type
    ) values (
      {title},
      {type}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntitySpecType)(implicit session: DBSession = autoSession): EntitySpecType = {
    sql"""
      update
        ${EntitySpecType.table}
      set
        ${column.id} = ${entity.id},
        ${column.title} = ${entity.title},
        ${column.`type`} = ${entity.`type`}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: EntitySpecType)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntitySpecType.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
