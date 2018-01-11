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
    withSQL {
      select.from(EntityAttrHouse as eah).where.eq(eah.entityid, entityid).and.eq(eah.entityspecattrid, entityspecattrid)
    }.map(EntityAttrHouse(eah.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrHouse] = {
    withSQL(select.from(EntityAttrHouse as eah)).map(EntityAttrHouse(eah.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrHouse as eah)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrHouse] = {
    withSQL {
      select.from(EntityAttrHouse as eah).where.append(where)
    }.map(EntityAttrHouse(eah.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrHouse] = {
    withSQL {
      select.from(EntityAttrHouse as eah).where.append(where)
    }.map(EntityAttrHouse(eah.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrHouse as eah).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrHouse = {
    withSQL {
      insert.into(EntityAttrHouse).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.value -> value
      )
    }.update.apply()

    EntityAttrHouse(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: Seq[EntityAttrHouse])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
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
    withSQL {
      update(EntityAttrHouse).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.value -> entity.value
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrHouse)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrHouse).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
