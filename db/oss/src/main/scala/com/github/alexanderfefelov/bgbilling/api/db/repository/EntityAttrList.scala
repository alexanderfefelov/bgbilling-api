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
    withSQL {
      select.from(EntityAttrList as eal).where.eq(eal.entityid, entityid).and.eq(eal.entityspecattrid, entityspecattrid)
    }.map(EntityAttrList(eal.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrList] = {
    withSQL(select.from(EntityAttrList as eal)).map(EntityAttrList(eal.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrList as eal)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrList] = {
    withSQL {
      select.from(EntityAttrList as eal).where.append(where)
    }.map(EntityAttrList(eal.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrList] = {
    withSQL {
      select.from(EntityAttrList as eal).where.append(where)
    }.map(EntityAttrList(eal.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrList as eal).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: Int)(implicit session: DBSession = autoSession): EntityAttrList = {
    withSQL {
      insert.into(EntityAttrList).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.value -> value
      )
    }.update.apply()

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
    withSQL {
      update(EntityAttrList).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.value -> entity.value
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrList)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrList).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
