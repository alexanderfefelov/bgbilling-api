package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class RegisterGroupTaskType(
  gid: Int,
  typeId: Int) {

  def save()(implicit session: DBSession = RegisterGroupTaskType.autoSession): RegisterGroupTaskType = RegisterGroupTaskType.save(this)(session)

  def destroy()(implicit session: DBSession = RegisterGroupTaskType.autoSession): Int = RegisterGroupTaskType.destroy(this)(session)

}


object RegisterGroupTaskType extends SQLSyntaxSupport[RegisterGroupTaskType] {

  override val tableName = "register_group_task_type"

  override val columns = Seq("gid", "type_id")

  def apply(rgtt: SyntaxProvider[RegisterGroupTaskType])(rs: WrappedResultSet): RegisterGroupTaskType = autoConstruct(rs, rgtt)
  def apply(rgtt: ResultName[RegisterGroupTaskType])(rs: WrappedResultSet): RegisterGroupTaskType = autoConstruct(rs, rgtt)

  val rgtt = RegisterGroupTaskType.syntax("rgtt")

  override val autoSession = AutoSession

  def find(gid: Int, typeId: Int)(implicit session: DBSession = autoSession): Option[RegisterGroupTaskType] = {
    withSQL {
      select.from(RegisterGroupTaskType as rgtt).where.eq(rgtt.gid, gid).and.eq(rgtt.typeId, typeId)
    }.map(RegisterGroupTaskType(rgtt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[RegisterGroupTaskType] = {
    withSQL(select.from(RegisterGroupTaskType as rgtt)).map(RegisterGroupTaskType(rgtt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(RegisterGroupTaskType as rgtt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[RegisterGroupTaskType] = {
    withSQL {
      select.from(RegisterGroupTaskType as rgtt).where.append(where)
    }.map(RegisterGroupTaskType(rgtt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[RegisterGroupTaskType] = {
    withSQL {
      select.from(RegisterGroupTaskType as rgtt).where.append(where)
    }.map(RegisterGroupTaskType(rgtt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(RegisterGroupTaskType as rgtt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gid: Int,
    typeId: Int)(implicit session: DBSession = autoSession): RegisterGroupTaskType = {
    withSQL {
      insert.into(RegisterGroupTaskType).namedValues(
        column.gid -> gid,
        column.typeId -> typeId
      )
    }.update.apply()

    RegisterGroupTaskType(
      gid = gid,
      typeId = typeId)
  }

  def batchInsert(entities: Seq[RegisterGroupTaskType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gid -> entity.gid,
        'typeId -> entity.typeId))
    SQL("""insert into register_group_task_type(
      gid,
      type_id
    ) values (
      {gid},
      {typeId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: RegisterGroupTaskType)(implicit session: DBSession = autoSession): RegisterGroupTaskType = {
    withSQL {
      update(RegisterGroupTaskType).set(
        column.gid -> entity.gid,
        column.typeId -> entity.typeId
      ).where.eq(column.gid, entity.gid).and.eq(column.typeId, entity.typeId)
    }.update.apply()
    entity
  }

  def destroy(entity: RegisterGroupTaskType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(RegisterGroupTaskType).where.eq(column.gid, entity.gid).and.eq(column.typeId, entity.typeId) }.update.apply()
  }

}
