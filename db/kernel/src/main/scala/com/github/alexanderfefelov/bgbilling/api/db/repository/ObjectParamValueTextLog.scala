package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ObjectParamValueTextLog(
  objectId: Int,
  paramId: Int,
  value: String,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ObjectParamValueTextLog.autoSession): ObjectParamValueTextLog = ObjectParamValueTextLog.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueTextLog.autoSession): Int = ObjectParamValueTextLog.destroy(this)(session)

}


object ObjectParamValueTextLog extends SQLSyntaxSupport[ObjectParamValueTextLog] {

  override val tableName = "object_param_value_text_log"

  override val columns = Seq("object_id", "param_id", "value", "dt_change", "user_id")

  def apply(opvtl: SyntaxProvider[ObjectParamValueTextLog])(rs: WrappedResultSet): ObjectParamValueTextLog = autoConstruct(rs, opvtl)
  def apply(opvtl: ResultName[ObjectParamValueTextLog])(rs: WrappedResultSet): ObjectParamValueTextLog = autoConstruct(rs, opvtl)

  val opvtl = ObjectParamValueTextLog.syntax("opvtl")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int, value: String, dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueTextLog] = {
    withSQL {
      select.from(ObjectParamValueTextLog as opvtl).where.eq(opvtl.objectId, objectId).and.eq(opvtl.paramId, paramId).and.eq(opvtl.value, value).and.eq(opvtl.dtChange, dtChange).and.eq(opvtl.userId, userId)
    }.map(ObjectParamValueTextLog(opvtl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueTextLog] = {
    withSQL(select.from(ObjectParamValueTextLog as opvtl)).map(ObjectParamValueTextLog(opvtl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueTextLog as opvtl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueTextLog] = {
    withSQL {
      select.from(ObjectParamValueTextLog as opvtl).where.append(where)
    }.map(ObjectParamValueTextLog(opvtl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueTextLog] = {
    withSQL {
      select.from(ObjectParamValueTextLog as opvtl).where.append(where)
    }.map(ObjectParamValueTextLog(opvtl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueTextLog as opvtl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: String,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ObjectParamValueTextLog = {
    withSQL {
      insert.into(ObjectParamValueTextLog).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ObjectParamValueTextLog(
      objectId = objectId,
      paramId = paramId,
      value = value,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: Seq[ObjectParamValueTextLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into object_param_value_text_log(
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

  def save(entity: ObjectParamValueTextLog)(implicit session: DBSession = autoSession): ObjectParamValueTextLog = {
    withSQL {
      update(ObjectParamValueTextLog).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueTextLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueTextLog).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
