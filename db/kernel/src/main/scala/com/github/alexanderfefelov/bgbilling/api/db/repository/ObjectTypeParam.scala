package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectTypeParam(
  typeId: Int,
  paramId: Int,
  pos: Int) {

  def save()(implicit session: DBSession = ObjectTypeParam.autoSession): ObjectTypeParam = ObjectTypeParam.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectTypeParam.autoSession): Int = ObjectTypeParam.destroy(this)(session)

}


object ObjectTypeParam extends SQLSyntaxSupport[ObjectTypeParam] {

  override val tableName = "object_type_param"

  override val columns = Seq("type_id", "param_id", "pos")

  def apply(otp: SyntaxProvider[ObjectTypeParam])(rs: WrappedResultSet): ObjectTypeParam = autoConstruct(rs, otp)
  def apply(otp: ResultName[ObjectTypeParam])(rs: WrappedResultSet): ObjectTypeParam = autoConstruct(rs, otp)

  val otp = ObjectTypeParam.syntax("otp")

  override val autoSession = AutoSession

  def find(paramId: Int, typeId: Int)(implicit session: DBSession = autoSession): Option[ObjectTypeParam] = {
    withSQL {
      select.from(ObjectTypeParam as otp).where.eq(otp.paramId, paramId).and.eq(otp.typeId, typeId)
    }.map(ObjectTypeParam(otp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectTypeParam] = {
    withSQL(select.from(ObjectTypeParam as otp)).map(ObjectTypeParam(otp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectTypeParam as otp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectTypeParam] = {
    withSQL {
      select.from(ObjectTypeParam as otp).where.append(where)
    }.map(ObjectTypeParam(otp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectTypeParam] = {
    withSQL {
      select.from(ObjectTypeParam as otp).where.append(where)
    }.map(ObjectTypeParam(otp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectTypeParam as otp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typeId: Int,
    paramId: Int,
    pos: Int)(implicit session: DBSession = autoSession): ObjectTypeParam = {
    withSQL {
      insert.into(ObjectTypeParam).namedValues(
        column.typeId -> typeId,
        column.paramId -> paramId,
        column.pos -> pos
      )
    }.update.apply()

    ObjectTypeParam(
      typeId = typeId,
      paramId = paramId,
      pos = pos)
  }

  def batchInsert(entities: Seq[ObjectTypeParam])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typeId -> entity.typeId,
        'paramId -> entity.paramId,
        'pos -> entity.pos))
    SQL("""insert into object_type_param(
      type_id,
      param_id,
      pos
    ) values (
      {typeId},
      {paramId},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectTypeParam)(implicit session: DBSession = autoSession): ObjectTypeParam = {
    withSQL {
      update(ObjectTypeParam).set(
        column.typeId -> entity.typeId,
        column.paramId -> entity.paramId,
        column.pos -> entity.pos
      ).where.eq(column.paramId, entity.paramId).and.eq(column.typeId, entity.typeId)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectTypeParam)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectTypeParam).where.eq(column.paramId, entity.paramId).and.eq(column.typeId, entity.typeId) }.update.apply()
  }

}
