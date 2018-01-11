package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ObjectTypeModule(
  typeId: Int,
  mid: Int) {

  def save()(implicit session: DBSession = ObjectTypeModule.autoSession): ObjectTypeModule = ObjectTypeModule.save(this)(session)

  def destroy()(implicit session: DBSession = ObjectTypeModule.autoSession): Int = ObjectTypeModule.destroy(this)(session)

}


object ObjectTypeModule extends SQLSyntaxSupport[ObjectTypeModule] {

  override val tableName = "object_type_module"

  override val columns = Seq("type_id", "mid")

  def apply(otm: SyntaxProvider[ObjectTypeModule])(rs: WrappedResultSet): ObjectTypeModule = autoConstruct(rs, otm)
  def apply(otm: ResultName[ObjectTypeModule])(rs: WrappedResultSet): ObjectTypeModule = autoConstruct(rs, otm)

  val otm = ObjectTypeModule.syntax("otm")

  override val autoSession = AutoSession

  def find(typeId: Int, mid: Int)(implicit session: DBSession = autoSession): Option[ObjectTypeModule] = {
    withSQL {
      select.from(ObjectTypeModule as otm).where.eq(otm.typeId, typeId).and.eq(otm.mid, mid)
    }.map(ObjectTypeModule(otm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ObjectTypeModule] = {
    withSQL(select.from(ObjectTypeModule as otm)).map(ObjectTypeModule(otm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ObjectTypeModule as otm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ObjectTypeModule] = {
    withSQL {
      select.from(ObjectTypeModule as otm).where.append(where)
    }.map(ObjectTypeModule(otm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ObjectTypeModule] = {
    withSQL {
      select.from(ObjectTypeModule as otm).where.append(where)
    }.map(ObjectTypeModule(otm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ObjectTypeModule as otm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    typeId: Int,
    mid: Int)(implicit session: DBSession = autoSession): ObjectTypeModule = {
    withSQL {
      insert.into(ObjectTypeModule).namedValues(
        column.typeId -> typeId,
        column.mid -> mid
      )
    }.update.apply()

    ObjectTypeModule(
      typeId = typeId,
      mid = mid)
  }

  def batchInsert(entities: Seq[ObjectTypeModule])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'typeId -> entity.typeId,
        'mid -> entity.mid))
    SQL("""insert into object_type_module(
      type_id,
      mid
    ) values (
      {typeId},
      {mid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ObjectTypeModule)(implicit session: DBSession = autoSession): ObjectTypeModule = {
    withSQL {
      update(ObjectTypeModule).set(
        column.typeId -> entity.typeId,
        column.mid -> entity.mid
      ).where.eq(column.typeId, entity.typeId).and.eq(column.mid, entity.mid)
    }.update.apply()
    entity
  }

  def destroy(entity: ObjectTypeModule)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ObjectTypeModule).where.eq(column.typeId, entity.typeId).and.eq(column.mid, entity.mid) }.update.apply()
  }

}
