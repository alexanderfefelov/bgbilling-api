package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}

case class ObjectParamValueDateLog(
  objectId: Int,
  paramId: Int,
  value: Option[LocalDate] = None,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ObjectParamValueDateLog.autoSession): ObjectParamValueDateLog = ObjectParamValueDateLog.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueDateLog.autoSession): Int = ObjectParamValueDateLog.destroy(this)(session)

}


object ObjectParamValueDateLog extends SQLSyntaxSupport[ObjectParamValueDateLog] {

  override val tableName = "object_param_value_date_log"

  override val columns = Seq("object_id", "param_id", "value", "dt_change", "user_id")

  def apply(opvdl: SyntaxProvider[ObjectParamValueDateLog])(rs: WrappedResultSet): ObjectParamValueDateLog = autoConstruct(rs, opvdl)
  def apply(opvdl: ResultName[ObjectParamValueDateLog])(rs: WrappedResultSet): ObjectParamValueDateLog = autoConstruct(rs, opvdl)

  val opvdl = ObjectParamValueDateLog.syntax("opvdl")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int, value: Option[LocalDate], dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueDateLog] = {
    withSQL {
      select.from(ObjectParamValueDateLog as opvdl).where.eq(opvdl.objectId, objectId).and.eq(opvdl.paramId, paramId).and.eq(opvdl.value, value).and.eq(opvdl.dtChange, dtChange).and.eq(opvdl.userId, userId)
    }.map(ObjectParamValueDateLog(opvdl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueDateLog] = {
    withSQL(select.from(ObjectParamValueDateLog as opvdl)).map(ObjectParamValueDateLog(opvdl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueDateLog as opvdl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueDateLog] = {
    withSQL {
      select.from(ObjectParamValueDateLog as opvdl).where.append(where)
    }.map(ObjectParamValueDateLog(opvdl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueDateLog] = {
    withSQL {
      select.from(ObjectParamValueDateLog as opvdl).where.append(where)
    }.map(ObjectParamValueDateLog(opvdl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueDateLog as opvdl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Option[LocalDate] = None,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ObjectParamValueDateLog = {
    withSQL {
      insert.into(ObjectParamValueDateLog).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ObjectParamValueDateLog(
      objectId = objectId,
      paramId = paramId,
      value = value,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ObjectParamValueDateLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into object_param_value_date_log(
      object_id,
      param_id,
      value,
      dt_change,
      user_id
    ) values (
      {objectId},
      {paramId},
      {value},
      {dtChange},
      {userId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueDateLog)(implicit session: DBSession = autoSession): ObjectParamValueDateLog = {
    withSQL {
      update(ObjectParamValueDateLog).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueDateLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueDateLog).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
