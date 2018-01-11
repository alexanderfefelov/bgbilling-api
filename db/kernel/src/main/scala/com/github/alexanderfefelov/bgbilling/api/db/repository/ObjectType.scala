package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectType(
  id: Int,
  title: String,
  nameMacros: String,
  comment: String,
  ishidden: Option[Boolean] = None) {

  def save()(implicit session: DBSession = ObjectType.autoSession): ObjectType = ObjectType.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectType.autoSession): Int = ObjectType.destroy(this)(session)

}


object ObjectType extends SQLSyntaxSupport[ObjectType] {

  override val tableName = "object_type"

  override val columns = Seq("id", "title", "name_macros", "comment", "ishidden")

  def apply(ot: SyntaxProvider[ObjectType])(rs: WrappedResultSet): ObjectType = autoConstruct(rs, ot)
  def apply(ot: ResultName[ObjectType])(rs: WrappedResultSet): ObjectType = autoConstruct(rs, ot)

  val ot = ObjectType.syntax("ot")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ObjectType] = {
    withSQL {
      select.from(ObjectType as ot).where.eq(ot.id, id)
    }.map(ObjectType(ot.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectType] = {
    withSQL(select.from(ObjectType as ot)).map(ObjectType(ot.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectType as ot)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectType] = {
    withSQL {
      select.from(ObjectType as ot).where.append(where)
    }.map(ObjectType(ot.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectType] = {
    withSQL {
      select.from(ObjectType as ot).where.append(where)
    }.map(ObjectType(ot.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectType as ot).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    nameMacros: String,
    comment: String,
    ishidden: Option[Boolean] = None)(implicit session: DBSession = autoSession): ObjectType = {
    val generatedKey = withSQL {
      insert.into(ObjectType).namedValues(
        column.title -> title,
        column.nameMacros -> nameMacros,
        column.comment -> comment,
        column.ishidden -> ishidden
      )
    }.updateAndReturnGeneratedKey.apply()

    ObjectType(
      id = generatedKey.toInt,
      title = title,
      nameMacros = nameMacros,
      comment = comment,
      ishidden = ishidden)
  }

  def batchInsert(entities: Seq[ObjectType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'nameMacros -> entity.nameMacros,
        'comment -> entity.comment,
        'ishidden -> entity.ishidden))
    SQL("""insert into object_type(
      title,
      name_macros,
      comment,
      ishidden
    ) values (
      {title},
      {nameMacros},
      {comment},
      {ishidden}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectType)(implicit session: DBSession = autoSession): ObjectType = {
    withSQL {
      update(ObjectType).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.nameMacros -> entity.nameMacros,
        column.comment -> entity.comment,
        column.ishidden -> entity.ishidden
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectType).where.eq(column.id, entity.id) }.update.apply()
  }

}
