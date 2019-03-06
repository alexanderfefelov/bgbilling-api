package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntityAttrHouse(
  entityid: Int,
  entityspecattrid: Int,
  value: Int) {

  def save()(implicit session: DBSession = EntityAttrHouse.autoSession): EntityAttrHouse = EntityAttrHouse.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrHouse.autoSession): Int = EntityAttrHouse.destroy(this)(session)

}


object EntityAttrHouse extends SQLSyntaxSupport[EntityAttrHouse] {

  override val tableName = "entity_attr_house"

  override val columns = Seq("entityId", "entitySpecAttrId", "value")

  def apply(eah: SyntaxProvider[EntityAttrHouse])(rs: WrappedResultSet): EntityAttrHouse = autoConstruct(rs, eah)
  def apply(eah: ResultName[EntityAttrHouse])(rs: WrappedResultSet): EntityAttrHouse = autoConstruct(rs, eah)

  val eah = EntityAttrHouse.syntax("eah")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrHouse] = {
    sql"""select ${eah.result.*} from ${EntityAttrHouse as eah} where ${eah.entityid} = ${entityid} and ${eah.entityspecattrid} = ${entityspecattrid}"""
      .map(EntityAttrHouse(eah.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrHouse] = {
    sql"""select ${eah.result.*} from ${EntityAttrHouse as eah}""".map(EntityAttrHouse(eah.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrHouse.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrHouse] = {
    sql"""select ${eah.result.*} from ${EntityAttrHouse as eah} where ${where}"""
      .map(EntityAttrHouse(eah.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrHouse] = {
    sql"""select ${eah.result.*} from ${EntityAttrHouse as eah} where ${where}"""
      .map(EntityAttrHouse(eah.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrHouse as eah} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrHouse = {
    sql"""
      insert into ${EntityAttrHouse.table} (
        ${column.entityid},
        ${column.entityspecattrid},
        ${column.value}
      ) values (
        ${entityid},
        ${entityspecattrid},
        ${value}
      )
      """.update.apply()

    EntityAttrHouse(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[EntityAttrHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'value -> entity.value))
    SQL("""insert into entity_attr_house(
      entityId,
      entitySpecAttrId,
      value
    ) values (
      {entityid},
      {entityspecattrid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrHouse)(implicit session: DBSession = autoSession): EntityAttrHouse = {
    sql"""
      update
        ${EntityAttrHouse.table}
      set
        ${column.entityid} = ${entity.entityid},
        ${column.entityspecattrid} = ${entity.entityspecattrid},
        ${column.value} = ${entity.value}
      where
        ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}
      """.update.apply()
    entity
  }

  def destroy(entity: EntityAttrHouse)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntityAttrHouse.table} where ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}""".update.apply()
  }

}
