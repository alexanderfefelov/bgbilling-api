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
    withSQL {
      select.from(EntityAttrInt as eai).where.eq(eai.entityid, entityid).and.eq(eai.entityspecattrid, entityspecattrid)
    }.map(EntityAttrInt(eai.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrInt] = {
    withSQL(select.from(EntityAttrInt as eai)).map(EntityAttrInt(eai.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrInt as eai)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrInt] = {
    withSQL {
      select.from(EntityAttrInt as eai).where.append(where)
    }.map(EntityAttrInt(eai.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrInt] = {
    withSQL {
      select.from(EntityAttrInt as eai).where.append(where)
    }.map(EntityAttrInt(eai.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrInt as eai).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrInt = {
    withSQL {
      insert.into(EntityAttrInt).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.value -> value
      )
    }.update.apply()

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
    withSQL {
      update(EntityAttrInt).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.value -> entity.value
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrInt)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrInt).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
