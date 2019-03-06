package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntityAttrInt(
  entityid: Int,
  entityspecattrid: Int,
  value: Int) {

  def save()(implicit session: DBSession = EntityAttrInt.autoSession): EntityAttrInt = EntityAttrInt.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrInt.autoSession): Int = EntityAttrInt.destroy(this)(session)

}


object EntityAttrInt extends SQLSyntaxSupport[EntityAttrInt] {

  override val tableName = "entity_attr_int"

  override val columns = Seq("entityId", "entitySpecAttrId", "value")

  def apply(eai: SyntaxProvider[EntityAttrInt])(rs: WrappedResultSet): EntityAttrInt = autoConstruct(rs, eai)
  def apply(eai: ResultName[EntityAttrInt])(rs: WrappedResultSet): EntityAttrInt = autoConstruct(rs, eai)

  val eai = EntityAttrInt.syntax("eai")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrInt] = {
    sql"""select ${eai.result.*} from ${EntityAttrInt as eai} where ${eai.entityid} = ${entityid} and ${eai.entityspecattrid} = ${entityspecattrid}"""
      .map(EntityAttrInt(eai.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrInt] = {
    sql"""select ${eai.result.*} from ${EntityAttrInt as eai}""".map(EntityAttrInt(eai.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrInt.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrInt] = {
    sql"""select ${eai.result.*} from ${EntityAttrInt as eai} where ${where}"""
      .map(EntityAttrInt(eai.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrInt] = {
    sql"""select ${eai.result.*} from ${EntityAttrInt as eai} where ${where}"""
      .map(EntityAttrInt(eai.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrInt as eai} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrInt = {
    sql"""
      insert into ${EntityAttrInt.table} (
        ${column.entityid},
        ${column.entityspecattrid},
        ${column.value}
      ) values (
        ${entityid},
        ${entityspecattrid},
        ${value}
      )
      """.update.apply()

    EntityAttrInt(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[EntityAttrInt])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'value -> entity.value))
    SQL("""insert into entity_attr_int(
      entityId,
      entitySpecAttrId,
      value
    ) values (
      {entityid},
      {entityspecattrid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrInt)(implicit session: DBSession = autoSession): EntityAttrInt = {
    sql"""
      update
        ${EntityAttrInt.table}
      set
        ${column.entityid} = ${entity.entityid},
        ${column.entityspecattrid} = ${entity.entityspecattrid},
        ${column.value} = ${entity.value}
      where
        ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}
      """.update.apply()
    entity
  }

  def destroy(entity: EntityAttrInt)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntityAttrInt.table} where ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}""".update.apply()
  }

}
