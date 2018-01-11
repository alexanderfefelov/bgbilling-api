package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectParamValueText(
  objectId: Int,
  paramId: Int,
  value: String) {

  def save()(implicit session: DBSession = ObjectParamValueText.autoSession): ObjectParamValueText = ObjectParamValueText.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueText.autoSession): Int = ObjectParamValueText.destroy(this)(session)

}


object ObjectParamValueText extends SQLSyntaxSupport[ObjectParamValueText] {

  override val tableName = "object_param_value_text"

  override val columns = Seq("object_id", "param_id", "value")

  def apply(opvt: SyntaxProvider[ObjectParamValueText])(rs: WrappedResultSet): ObjectParamValueText = autoConstruct(rs, opvt)
  def apply(opvt: ResultName[ObjectParamValueText])(rs: WrappedResultSet): ObjectParamValueText = autoConstruct(rs, opvt)

  val opvt = ObjectParamValueText.syntax("opvt")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueText] = {
    withSQL {
      select.from(ObjectParamValueText as opvt).where.eq(opvt.objectId, objectId).and.eq(opvt.paramId, paramId)
    }.map(ObjectParamValueText(opvt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueText] = {
    withSQL(select.from(ObjectParamValueText as opvt)).map(ObjectParamValueText(opvt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueText as opvt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueText] = {
    withSQL {
      select.from(ObjectParamValueText as opvt).where.append(where)
    }.map(ObjectParamValueText(opvt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueText] = {
    withSQL {
      select.from(ObjectParamValueText as opvt).where.append(where)
    }.map(ObjectParamValueText(opvt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueText as opvt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: String)(implicit session: DBSession = autoSession): ObjectParamValueText = {
    withSQL {
      insert.into(ObjectParamValueText).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value
      )
    }.update.apply()

    ObjectParamValueText(
      objectId = objectId,
      paramId = paramId,
      value = value)
  }

  def batchInsert(entities: Seq[ObjectParamValueText])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value))
    SQL("""insert into object_param_value_text(
      object_id,
      param_id,
      value
    ) values (
      {objectId},
      {paramId},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueText)(implicit session: DBSession = autoSession): ObjectParamValueText = {
    withSQL {
      update(ObjectParamValueText).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueText)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueText).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId) }.update.apply()
  }

}
