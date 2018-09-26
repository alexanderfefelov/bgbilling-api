package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntityAttrText(
  entityid: Int,
  entityspecattrid: Int,
  value: String) {

  def save()(implicit session: DBSession = EntityAttrText.autoSession): EntityAttrText = EntityAttrText.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrText.autoSession): Int = EntityAttrText.destroy(this)(session)

}


object EntityAttrText extends SQLSyntaxSupport[EntityAttrText] {

  override val tableName = "entity_attr_text"

  override val columns = Seq("entityId", "entitySpecAttrId", "value")

  def apply(eat: SyntaxProvider[EntityAttrText])(rs: WrappedResultSet): EntityAttrText = autoConstruct(rs, eat)
  def apply(eat: ResultName[EntityAttrText])(rs: WrappedResultSet): EntityAttrText = autoConstruct(rs, eat)

  val eat = EntityAttrText.syntax("eat")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrText] = {
    withSQL {
      select.from(EntityAttrText as eat).where.eq(eat.entityid, entityid).and.eq(eat.entityspecattrid, entityspecattrid)
    }.map(EntityAttrText(eat.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrText] = {
    withSQL(select.from(EntityAttrText as eat)).map(EntityAttrText(eat.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrText as eat)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrText] = {
    withSQL {
      select.from(EntityAttrText as eat).where.append(where)
    }.map(EntityAttrText(eat.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrText] = {
    withSQL {
      select.from(EntityAttrText as eat).where.append(where)
    }.map(EntityAttrText(eat.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrText as eat).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: String)(implicit session: DBSession = autoSession): EntityAttrText = {
    withSQL {
      insert.into(EntityAttrText).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.value -> value
      )
    }.update.apply()

    EntityAttrText(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[EntityAttrText])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'value -> entity.value))
    SQL("""insert into entity_attr_text(
      entityId,
      entitySpecAttrId,
      value
    ) values (
      {entityid},
      {entityspecattrid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrText)(implicit session: DBSession = autoSession): EntityAttrText = {
    withSQL {
      update(EntityAttrText).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.value -> entity.value
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrText)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrText).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
