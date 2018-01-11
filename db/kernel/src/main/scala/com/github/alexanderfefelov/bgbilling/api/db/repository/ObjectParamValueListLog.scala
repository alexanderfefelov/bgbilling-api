package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ObjectParamValueListLog(
  objectId: Int,
  paramId: Int,
  value: Int,
  title: String,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ObjectParamValueListLog.autoSession): ObjectParamValueListLog = ObjectParamValueListLog.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueListLog.autoSession): Int = ObjectParamValueListLog.destroy(this)(session)

}


object ObjectParamValueListLog extends SQLSyntaxSupport[ObjectParamValueListLog] {

  override val tableName = "object_param_value_list_log"

  override val columns = Seq("object_id", "param_id", "value", "title", "dt_change", "user_id")

  def apply(opvll: SyntaxProvider[ObjectParamValueListLog])(rs: WrappedResultSet): ObjectParamValueListLog = autoConstruct(rs, opvll)
  def apply(opvll: ResultName[ObjectParamValueListLog])(rs: WrappedResultSet): ObjectParamValueListLog = autoConstruct(rs, opvll)

  val opvll = ObjectParamValueListLog.syntax("opvll")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int, value: Int, title: String, dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueListLog] = {
    withSQL {
      select.from(ObjectParamValueListLog as opvll).where.eq(opvll.objectId, objectId).and.eq(opvll.paramId, paramId).and.eq(opvll.value, value).and.eq(opvll.title, title).and.eq(opvll.dtChange, dtChange).and.eq(opvll.userId, userId)
    }.map(ObjectParamValueListLog(opvll.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueListLog] = {
    withSQL(select.from(ObjectParamValueListLog as opvll)).map(ObjectParamValueListLog(opvll.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueListLog as opvll)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueListLog] = {
    withSQL {
      select.from(ObjectParamValueListLog as opvll).where.append(where)
    }.map(ObjectParamValueListLog(opvll.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueListLog] = {
    withSQL {
      select.from(ObjectParamValueListLog as opvll).where.append(where)
    }.map(ObjectParamValueListLog(opvll.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueListLog as opvll).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Int,
    title: String,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ObjectParamValueListLog = {
    withSQL {
      insert.into(ObjectParamValueListLog).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value,
        column.title -> title,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ObjectParamValueListLog(
      objectId = objectId,
      paramId = paramId,
      value = value,
      title = title,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ObjectParamValueListLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value,
        'title -> entity.title,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into object_param_value_list_log(
      object_id,
      param_id,
      value,
      title,
      dt_change,
      user_id
    ) values (
      {objectId},
      {paramId},
      {value},
      {title},
      {dtChange},
      {userId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueListLog)(implicit session: DBSession = autoSession): ObjectParamValueListLog = {
    withSQL {
      update(ObjectParamValueListLog).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value,
        column.title -> entity.title,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.title, entity.title).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueListLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueListLog).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.title, entity.title).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
