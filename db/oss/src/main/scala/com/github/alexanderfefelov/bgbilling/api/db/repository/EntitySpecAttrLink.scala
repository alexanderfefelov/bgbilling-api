package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntitySpecAttrLink(
  entityspecid: Int,
  entityspecattrid: Int,
  pos: Int) {

  def save()(implicit session: DBSession = EntitySpecAttrLink.autoSession): EntitySpecAttrLink = EntitySpecAttrLink.save(this)(session)

  def destroy()(implicit session: DBSession = EntitySpecAttrLink.autoSession): Int = EntitySpecAttrLink.destroy(this)(session)

}


object EntitySpecAttrLink extends SQLSyntaxSupport[EntitySpecAttrLink] {

  override val tableName = "entity_spec_attr_link"

  override val columns = Seq("entitySpecId", "entitySpecAttrId", "pos")

  def apply(esal: SyntaxProvider[EntitySpecAttrLink])(rs: WrappedResultSet): EntitySpecAttrLink = autoConstruct(rs, esal)
  def apply(esal: ResultName[EntitySpecAttrLink])(rs: WrappedResultSet): EntitySpecAttrLink = autoConstruct(rs, esal)

  val esal = EntitySpecAttrLink.syntax("esal")

  override val autoSession = AutoSession

  def find(entityspecid: Int, entityspecattrid: Int, pos: Int)(implicit session: DBSession = autoSession): Option[EntitySpecAttrLink] = {
    withSQL {
      select.from(EntitySpecAttrLink as esal).where.eq(esal.entityspecid, entityspecid).and.eq(esal.entityspecattrid, entityspecattrid).and.eq(esal.pos, pos)
    }.map(EntitySpecAttrLink(esal.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpecAttrLink] = {
    withSQL(select.from(EntitySpecAttrLink as esal)).map(EntitySpecAttrLink(esal.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntitySpecAttrLink as esal)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpecAttrLink] = {
    withSQL {
      select.from(EntitySpecAttrLink as esal).where.append(where)
    }.map(EntitySpecAttrLink(esal.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpecAttrLink] = {
    withSQL {
      select.from(EntitySpecAttrLink as esal).where.append(where)
    }.map(EntitySpecAttrLink(esal.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntitySpecAttrLink as esal).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityspecid: Int,
    entityspecattrid: Int,
    pos: Int)(implicit session: DBSession = autoSession): EntitySpecAttrLink = {
    withSQL {
      insert.into(EntitySpecAttrLink).namedValues(
        column.entityspecid -> entityspecid,
        column.entityspecattrid -> entityspecattrid,
        column.pos -> pos
      )
    }.update.apply()

    EntitySpecAttrLink(
      entityspecid = entityspecid,
      entityspecattrid = entityspecattrid,
      pos = pos)
  }

  def batchInsert(entities: Seq[EntitySpecAttrLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityspecid -> entity.entityspecid,
        'entityspecattrid -> entity.entityspecattrid,
        'pos -> entity.pos))
    SQL("""insert into entity_spec_attr_link(
      entitySpecId,
      entitySpecAttrId,
      pos
    ) values (
      {entityspecid},
      {entityspecattrid},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntitySpecAttrLink)(implicit session: DBSession = autoSession): EntitySpecAttrLink = {
    withSQL {
      update(EntitySpecAttrLink).set(
        column.entityspecid -> entity.entityspecid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.pos -> entity.pos
      ).where.eq(column.entityspecid, entity.entityspecid).and.eq(column.entityspecattrid, entity.entityspecattrid).and.eq(column.pos, entity.pos)
    }.update.apply()
    entity
  }

  def destroy(entity: EntitySpecAttrLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntitySpecAttrLink).where.eq(column.entityspecid, entity.entityspecid).and.eq(column.entityspecattrid, entity.entityspecattrid).and.eq(column.pos, entity.pos) }.update.apply()
  }

}
