package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ObjectParamValueDate(
  objectId: Int,
  paramId: Int,
  value: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = ObjectParamValueDate.autoSession): ObjectParamValueDate = ObjectParamValueDate.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueDate.autoSession): Int = ObjectParamValueDate.destroy(this)(session)

}


object ObjectParamValueDate extends SQLSyntaxSupport[ObjectParamValueDate] {

  override val tableName = "object_param_value_date"

  override val columns = Seq("object_id", "param_id", "value")

  def apply(opvd: SyntaxProvider[ObjectParamValueDate])(rs: WrappedResultSet): ObjectParamValueDate = autoConstruct(rs, opvd)
  def apply(opvd: ResultName[ObjectParamValueDate])(rs: WrappedResultSet): ObjectParamValueDate = autoConstruct(rs, opvd)

  val opvd = ObjectParamValueDate.syntax("opvd")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueDate] = {
    withSQL {
      select.from(ObjectParamValueDate as opvd).where.eq(opvd.objectId, objectId).and.eq(opvd.paramId, paramId)
    }.map(ObjectParamValueDate(opvd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueDate] = {
    withSQL(select.from(ObjectParamValueDate as opvd)).map(ObjectParamValueDate(opvd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueDate as opvd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueDate] = {
    withSQL {
      select.from(ObjectParamValueDate as opvd).where.append(where)
    }.map(ObjectParamValueDate(opvd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueDate] = {
    withSQL {
      select.from(ObjectParamValueDate as opvd).where.append(where)
    }.map(ObjectParamValueDate(opvd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueDate as opvd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Option[LocalDate] = None)(implicit session: DBSession = autoSession): ObjectParamValueDate = {
    withSQL {
      insert.into(ObjectParamValueDate).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value
      )
    }.update.apply()

    ObjectParamValueDate(
      objectId = objectId,
      paramId = paramId,
      value = value)
  }

  def batchInsert(entities: Seq[ObjectParamValueDate])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value))
    SQL("""insert into object_param_value_date(
      object_id,
      param_id,
      value
    ) values (
      {objectId},
      {paramId},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueDate)(implicit session: DBSession = autoSession): ObjectParamValueDate = {
    withSQL {
      update(ObjectParamValueDate).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueDate)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueDate).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId) }.update.apply()
  }

}
