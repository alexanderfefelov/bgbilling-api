package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ObjectParamValueFlagLog(
  objectId: Int,
  paramId: Int,
  value: Int,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ObjectParamValueFlagLog.autoSession): ObjectParamValueFlagLog = ObjectParamValueFlagLog.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueFlagLog.autoSession): Int = ObjectParamValueFlagLog.destroy(this)(session)

}


object ObjectParamValueFlagLog extends SQLSyntaxSupport[ObjectParamValueFlagLog] {

  override val tableName = "object_param_value_flag_log"

  override val columns = Seq("object_id", "param_id", "value", "dt_change", "user_id")

  def apply(opvfl: SyntaxProvider[ObjectParamValueFlagLog])(rs: WrappedResultSet): ObjectParamValueFlagLog = autoConstruct(rs, opvfl)
  def apply(opvfl: ResultName[ObjectParamValueFlagLog])(rs: WrappedResultSet): ObjectParamValueFlagLog = autoConstruct(rs, opvfl)

  val opvfl = ObjectParamValueFlagLog.syntax("opvfl")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int, value: Int, dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueFlagLog] = {
    withSQL {
      select.from(ObjectParamValueFlagLog as opvfl).where.eq(opvfl.objectId, objectId).and.eq(opvfl.paramId, paramId).and.eq(opvfl.value, value).and.eq(opvfl.dtChange, dtChange).and.eq(opvfl.userId, userId)
    }.map(ObjectParamValueFlagLog(opvfl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueFlagLog] = {
    withSQL(select.from(ObjectParamValueFlagLog as opvfl)).map(ObjectParamValueFlagLog(opvfl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueFlagLog as opvfl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueFlagLog] = {
    withSQL {
      select.from(ObjectParamValueFlagLog as opvfl).where.append(where)
    }.map(ObjectParamValueFlagLog(opvfl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueFlagLog] = {
    withSQL {
      select.from(ObjectParamValueFlagLog as opvfl).where.append(where)
    }.map(ObjectParamValueFlagLog(opvfl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueFlagLog as opvfl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Int,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ObjectParamValueFlagLog = {
    withSQL {
      insert.into(ObjectParamValueFlagLog).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ObjectParamValueFlagLog(
      objectId = objectId,
      paramId = paramId,
      value = value,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: collection.Seq[ObjectParamValueFlagLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into object_param_value_flag_log(
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

  def save(entity: ObjectParamValueFlagLog)(implicit session: DBSession = autoSession): ObjectParamValueFlagLog = {
    withSQL {
      update(ObjectParamValueFlagLog).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueFlagLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueFlagLog).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
