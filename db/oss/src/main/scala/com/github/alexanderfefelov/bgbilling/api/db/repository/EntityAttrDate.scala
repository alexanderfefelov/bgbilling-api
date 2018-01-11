package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class EntityAttrDate(
  entityid: Int,
  entityspecattrid: Int,
  value: LocalDate) {

  def save()(implicit session: DBSession = EntityAttrDate.autoSession): EntityAttrDate = EntityAttrDate.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrDate.autoSession): Int = EntityAttrDate.destroy(this)(session)

}


object EntityAttrDate extends SQLSyntaxSupport[EntityAttrDate] {

  override val tableName = "entity_attr_date"

  override val columns = Seq("entityId", "entitySpecAttrId", "value")

  def apply(ead: SyntaxProvider[EntityAttrDate])(rs: WrappedResultSet): EntityAttrDate = autoConstruct(rs, ead)
  def apply(ead: ResultName[EntityAttrDate])(rs: WrappedResultSet): EntityAttrDate = autoConstruct(rs, ead)

  val ead = EntityAttrDate.syntax("ead")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrDate] = {
    withSQL {
      select.from(EntityAttrDate as ead).where.eq(ead.entityid, entityid).and.eq(ead.entityspecattrid, entityspecattrid)
    }.map(EntityAttrDate(ead.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrDate] = {
    withSQL(select.from(EntityAttrDate as ead)).map(EntityAttrDate(ead.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrDate as ead)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrDate] = {
    withSQL {
      select.from(EntityAttrDate as ead).where.append(where)
    }.map(EntityAttrDate(ead.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrDate] = {
    withSQL {
      select.from(EntityAttrDate as ead).where.append(where)
    }.map(EntityAttrDate(ead.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrDate as ead).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    value: LocalDate)(implicit session: DBSession = autoSession): EntityAttrDate = {
    withSQL {
      insert.into(EntityAttrDate).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.value -> value
      )
    }.update.apply()

    EntityAttrDate(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      value = value)
  }

  def batchInsert(entities: Seq[EntityAttrDate])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'value -> entity.value))
    SQL("""insert into entity_attr_date(
      entityId,
      entitySpecAttrId,
      value
    ) values (
      {entityid},
      {entityspecattrid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrDate)(implicit session: DBSession = autoSession): EntityAttrDate = {
    withSQL {
      update(EntityAttrDate).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.value -> entity.value
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrDate)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrDate).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
