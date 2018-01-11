package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntitySpecAttrList(
  id: Int,
  entityspecattrid: Int,
  title: String) {

  def save()(implicit session: DBSession = EntitySpecAttrList.autoSession): EntitySpecAttrList = EntitySpecAttrList.save(this)(session)

  def destroy()(implicit session: DBSession = EntitySpecAttrList.autoSession): Int = EntitySpecAttrList.destroy(this)(session)

}


object EntitySpecAttrList extends SQLSyntaxSupport[EntitySpecAttrList] {

  override val tableName = "entity_spec_attr_list"

  override val columns = Seq("id", "entitySpecAttrId", "title")

  def apply(esal: SyntaxProvider[EntitySpecAttrList])(rs: WrappedResultSet): EntitySpecAttrList = autoConstruct(rs, esal)
  def apply(esal: ResultName[EntitySpecAttrList])(rs: WrappedResultSet): EntitySpecAttrList = autoConstruct(rs, esal)

  val esal = EntitySpecAttrList.syntax("esal")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[EntitySpecAttrList] = {
    withSQL {
      select.from(EntitySpecAttrList as esal).where.eq(esal.id, id)
    }.map(EntitySpecAttrList(esal.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntitySpecAttrList] = {
    withSQL(select.from(EntitySpecAttrList as esal)).map(EntitySpecAttrList(esal.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntitySpecAttrList as esal)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntitySpecAttrList] = {
    withSQL {
      select.from(EntitySpecAttrList as esal).where.append(where)
    }.map(EntitySpecAttrList(esal.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntitySpecAttrList] = {
    withSQL {
      select.from(EntitySpecAttrList as esal).where.append(where)
    }.map(EntitySpecAttrList(esal.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntitySpecAttrList as esal).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityspecattrid: Int,
    title: String)(implicit session: DBSession = autoSession): EntitySpecAttrList = {
    val generatedKey = withSQL {
      insert.into(EntitySpecAttrList).namedValues(
        column.entityspecattrid -> entityspecattrid,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    EntitySpecAttrList(
      id = generatedKey.toInt,
      entityspecattrid = entityspecattrid,
      title = title)
  }

  def batchInsert(entities: Seq[EntitySpecAttrList])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityspecattrid -> entity.entityspecattrid,
        'title -> entity.title))
    SQL("""insert into entity_spec_attr_list(
      entitySpecAttrId,
      title
    ) values (
      {entityspecattrid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntitySpecAttrList)(implicit session: DBSession = autoSession): EntitySpecAttrList = {
    withSQL {
      update(EntitySpecAttrList).set(
        column.id -> entity.id,
        column.entityspecattrid -> entity.entityspecattrid,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: EntitySpecAttrList)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntitySpecAttrList).where.eq(column.id, entity.id) }.update.apply()
  }

}
