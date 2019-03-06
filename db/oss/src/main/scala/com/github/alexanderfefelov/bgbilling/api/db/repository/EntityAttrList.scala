package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntityAttrList(
  entityid: Int,
  entityspecattrid: Int,
  value: Int) {

  def save()(implicit session: DBSession = EntityAttrList.autoSession): EntityAttrList = EntityAttrList.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrList.autoSession): Int = EntityAttrList.destroy(this)(session)

}


object EntityAttrList extends SQLSyntaxSupport[EntityAttrList] {

  override val tableName = "entity_attr_list"

  override val columns = Seq("entityId", "entitySpecAttrId", "value")

  def apply(eal: SyntaxProvider[EntityAttrList])(rs: WrappedResultSet): EntityAttrList = autoConstruct(rs, eal)
  def apply(eal: ResultName[EntityAttrList])(rs: WrappedResultSet): EntityAttrList = autoConstruct(rs, eal)

  val eal = EntityAttrList.syntax("eal")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrList] = {
    sql"""select ${eal.result.*} from ${EntityAttrList as eal} where ${eal.entityid} = ${entityid} and ${eal.entityspecattrid} = ${entityspecattrid}"""
      .map(EntityAttrList(eal.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrList] = {
    sql"""select ${eal.result.*} from ${EntityAttrList as eal}""".map(EntityAttrList(eal.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrList.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrList] = {
    sql"""select ${eal.result.*} from ${EntityAttrList as eal} where ${where}"""
      .map(EntityAttrList(eal.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrList] = {
    sql"""select ${eal.result.*} from ${EntityAttrList as eal} where ${where}"""
      .map(EntityAttrList(eal.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrList as eal} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrList = {
    sql"""
      insert into ${EntityAttrList.table} (
        ${column.entityid},
        ${column.entityspecattrid},
        ${column.value}
      ) values (
        ${entityid},
        ${entityspecattrid},
        ${value}
      )
      """.update.apply()

    EntityAttrList(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[EntityAttrList])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'value -> entity.value))
    SQL("""insert into entity_attr_list(
      entityId,
      entitySpecAttrId,
      value
    ) values (
      {entityid},
      {entityspecattrid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrList)(implicit session: DBSession = autoSession): EntityAttrList = {
    sql"""
      update
        ${EntityAttrList.table}
      set
        ${column.entityid} = ${entity.entityid},
        ${column.entityspecattrid} = ${entity.entityspecattrid},
        ${column.value} = ${entity.value}
      where
        ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}
      """.update.apply()
    entity
  }

  def destroy(entity: EntityAttrList)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntityAttrList.table} where ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}""".update.apply()
  }

}
