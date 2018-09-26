package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectParamValueList(
  objectId: Int,
  paramId: Int,
  value: Int) {

  def save()(implicit session: DBSession = ObjectParamValueList.autoSession): ObjectParamValueList = ObjectParamValueList.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueList.autoSession): Int = ObjectParamValueList.destroy(this)(session)

}


object ObjectParamValueList extends SQLSyntaxSupport[ObjectParamValueList] {

  override val tableName = "object_param_value_list"

  override val columns = Seq("object_id", "param_id", "value")

  def apply(opvl: SyntaxProvider[ObjectParamValueList])(rs: WrappedResultSet): ObjectParamValueList = autoConstruct(rs, opvl)
  def apply(opvl: ResultName[ObjectParamValueList])(rs: WrappedResultSet): ObjectParamValueList = autoConstruct(rs, opvl)

  val opvl = ObjectParamValueList.syntax("opvl")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueList] = {
    withSQL {
      select.from(ObjectParamValueList as opvl).where.eq(opvl.objectId, objectId).and.eq(opvl.paramId, paramId)
    }.map(ObjectParamValueList(opvl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueList] = {
    withSQL(select.from(ObjectParamValueList as opvl)).map(ObjectParamValueList(opvl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueList as opvl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueList] = {
    withSQL {
      select.from(ObjectParamValueList as opvl).where.append(where)
    }.map(ObjectParamValueList(opvl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueList] = {
    withSQL {
      select.from(ObjectParamValueList as opvl).where.append(where)
    }.map(ObjectParamValueList(opvl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueList as opvl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Int)(implicit session: DBSession = autoSession): ObjectParamValueList = {
    withSQL {
      insert.into(ObjectParamValueList).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value
      )
    }.update.apply()

    ObjectParamValueList(
      objectId = objectId,
      paramId = paramId,
      value = value)
  }

  def batchInsert(entities: collection.Seq[ObjectParamValueList])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value))
    SQL("""insert into object_param_value_list(
      object_id,
      param_id,
      value
    ) values (
      {objectId},
      {paramId},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueList)(implicit session: DBSession = autoSession): ObjectParamValueList = {
    withSQL {
      update(ObjectParamValueList).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueList)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueList).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId) }.update.apply()
  }

}
