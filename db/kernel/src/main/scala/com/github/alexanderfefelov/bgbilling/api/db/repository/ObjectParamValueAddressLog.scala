package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ObjectParamValueAddressLog(
  objectId: Int,
  paramId: Int,
  value: String,
  dtChange: DateTime,
  userId: Int) {

  def save()(implicit session: DBSession = ObjectParamValueAddressLog.autoSession): ObjectParamValueAddressLog = ObjectParamValueAddressLog.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueAddressLog.autoSession): Int = ObjectParamValueAddressLog.destroy(this)(session)

}


object ObjectParamValueAddressLog extends SQLSyntaxSupport[ObjectParamValueAddressLog] {

  override val tableName = "object_param_value_address_log"

  override val columns = Seq("object_id", "param_id", "value", "dt_change", "user_id")

  def apply(opval: SyntaxProvider[ObjectParamValueAddressLog])(rs: WrappedResultSet): ObjectParamValueAddressLog = autoConstruct(rs, opval)
  def apply(opval: ResultName[ObjectParamValueAddressLog])(rs: WrappedResultSet): ObjectParamValueAddressLog = autoConstruct(rs, opval)

  val opval = ObjectParamValueAddressLog.syntax("opval")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int, value: String, dtChange: DateTime, userId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueAddressLog] = {
    withSQL {
      select.from(ObjectParamValueAddressLog as opval).where.eq(opval.objectId, objectId).and.eq(opval.paramId, paramId).and.eq(opval.value, value).and.eq(opval.dtChange, dtChange).and.eq(opval.userId, userId)
    }.map(ObjectParamValueAddressLog(opval.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueAddressLog] = {
    withSQL(select.from(ObjectParamValueAddressLog as opval)).map(ObjectParamValueAddressLog(opval.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueAddressLog as opval)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueAddressLog] = {
    withSQL {
      select.from(ObjectParamValueAddressLog as opval).where.append(where)
    }.map(ObjectParamValueAddressLog(opval.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueAddressLog] = {
    withSQL {
      select.from(ObjectParamValueAddressLog as opval).where.append(where)
    }.map(ObjectParamValueAddressLog(opval.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueAddressLog as opval).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: String,
    dtChange: DateTime,
    userId: Int)(implicit session: DBSession = autoSession): ObjectParamValueAddressLog = {
    withSQL {
      insert.into(ObjectParamValueAddressLog).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value,
        column.dtChange -> dtChange,
        column.userId -> userId
      )
    }.update.apply()

    ObjectParamValueAddressLog(
      objectId = objectId,
      paramId = paramId,
      value = value,
      dtChange = dtChange,
      userId = userId)
  }

  def batchInsert(entities: collection.Seq[ObjectParamValueAddressLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value,
        'dtChange -> entity.dtChange,
        'userId -> entity.userId))
    SQL("""insert into object_param_value_address_log(
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

  def save(entity: ObjectParamValueAddressLog)(implicit session: DBSession = autoSession): ObjectParamValueAddressLog = {
    withSQL {
      update(ObjectParamValueAddressLog).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value,
        column.dtChange -> entity.dtChange,
        column.userId -> entity.userId
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueAddressLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueAddressLog).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId).and.eq(column.value, entity.value).and.eq(column.dtChange, entity.dtChange).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
