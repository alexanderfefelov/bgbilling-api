package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectListValue(
  id: Int,
  paramId: Int,
  title: String) {

  def save()(implicit session: DBSession = ObjectListValue.autoSession): ObjectListValue = ObjectListValue.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectListValue.autoSession): Int = ObjectListValue.destroy(this)(session)

}


object ObjectListValue extends SQLSyntaxSupport[ObjectListValue] {

  override val tableName = "object_list_value"

  override val columns = Seq("id", "param_id", "title")

  def apply(olv: SyntaxProvider[ObjectListValue])(rs: WrappedResultSet): ObjectListValue = autoConstruct(rs, olv)
  def apply(olv: ResultName[ObjectListValue])(rs: WrappedResultSet): ObjectListValue = autoConstruct(rs, olv)

  val olv = ObjectListValue.syntax("olv")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ObjectListValue] = {
    withSQL {
      select.from(ObjectListValue as olv).where.eq(olv.id, id)
    }.map(ObjectListValue(olv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectListValue] = {
    withSQL(select.from(ObjectListValue as olv)).map(ObjectListValue(olv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectListValue as olv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectListValue] = {
    withSQL {
      select.from(ObjectListValue as olv).where.append(where)
    }.map(ObjectListValue(olv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectListValue] = {
    withSQL {
      select.from(ObjectListValue as olv).where.append(where)
    }.map(ObjectListValue(olv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectListValue as olv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    paramId: Int,
    title: String)(implicit session: DBSession = autoSession): ObjectListValue = {
    val generatedKey = withSQL {
      insert.into(ObjectListValue).namedValues(
        column.paramId -> paramId,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ObjectListValue(
      id = generatedKey.toInt,
      paramId = paramId,
      title = title)
  }

  def batchInsert(entities: collection.Seq[ObjectListValue])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'paramId -> entity.paramId,
        'title -> entity.title))
    SQL("""insert into object_list_value(
      param_id,
      title
    ) values (
      {paramId},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectListValue)(implicit session: DBSession = autoSession): ObjectListValue = {
    withSQL {
      update(ObjectListValue).set(
        column.id -> entity.id,
        column.paramId -> entity.paramId,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectListValue)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectListValue).where.eq(column.id, entity.id) }.update.apply()
  }

}
