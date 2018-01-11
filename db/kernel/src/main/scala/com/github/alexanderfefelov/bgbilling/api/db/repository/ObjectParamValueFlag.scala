package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectParamValueFlag(
  objectId: Int,
  paramId: Int,
  value: Int) {

  def save()(implicit session: DBSession = ObjectParamValueFlag.autoSession): ObjectParamValueFlag = ObjectParamValueFlag.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectParamValueFlag.autoSession): Int = ObjectParamValueFlag.destroy(this)(session)

}


object ObjectParamValueFlag extends SQLSyntaxSupport[ObjectParamValueFlag] {

  override val tableName = "object_param_value_flag"

  override val columns = Seq("object_id", "param_id", "value")

  def apply(opvf: SyntaxProvider[ObjectParamValueFlag])(rs: WrappedResultSet): ObjectParamValueFlag = autoConstruct(rs, opvf)
  def apply(opvf: ResultName[ObjectParamValueFlag])(rs: WrappedResultSet): ObjectParamValueFlag = autoConstruct(rs, opvf)

  val opvf = ObjectParamValueFlag.syntax("opvf")

  override val autoSession = AutoSession

  def find(objectId: Int, paramId: Int)(implicit session: DBSession = autoSession): Option[ObjectParamValueFlag] = {
    withSQL {
      select.from(ObjectParamValueFlag as opvf).where.eq(opvf.objectId, objectId).and.eq(opvf.paramId, paramId)
    }.map(ObjectParamValueFlag(opvf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectParamValueFlag] = {
    withSQL(select.from(ObjectParamValueFlag as opvf)).map(ObjectParamValueFlag(opvf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectParamValueFlag as opvf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectParamValueFlag] = {
    withSQL {
      select.from(ObjectParamValueFlag as opvf).where.append(where)
    }.map(ObjectParamValueFlag(opvf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectParamValueFlag] = {
    withSQL {
      select.from(ObjectParamValueFlag as opvf).where.append(where)
    }.map(ObjectParamValueFlag(opvf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectParamValueFlag as opvf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    objectId: Int,
    paramId: Int,
    value: Int)(implicit session: DBSession = autoSession): ObjectParamValueFlag = {
    withSQL {
      insert.into(ObjectParamValueFlag).namedValues(
        column.objectId -> objectId,
        column.paramId -> paramId,
        column.value -> value
      )
    }.update.apply()

    ObjectParamValueFlag(
      objectId = objectId,
      paramId = paramId,
      value = value)
  }

  def batchInsert(entities: Seq[ObjectParamValueFlag])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'objectId -> entity.objectId,
        'paramId -> entity.paramId,
        'value -> entity.value))
    SQL("""insert into object_param_value_flag(
      object_id,
      param_id,
      value
    ) values (
      {objectId},
      {paramId},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectParamValueFlag)(implicit session: DBSession = autoSession): ObjectParamValueFlag = {
    withSQL {
      update(ObjectParamValueFlag).set(
        column.objectId -> entity.objectId,
        column.paramId -> entity.paramId,
        column.value -> entity.value
      ).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectParamValueFlag)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectParamValueFlag).where.eq(column.objectId, entity.objectId).and.eq(column.paramId, entity.paramId) }.update.apply()
  }

}
