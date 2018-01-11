package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserGroupAccess(
  grCode: Short,
  maId: Short) {

  def save()(implicit session: DBSession = UserGroupAccess.autoSession): UserGroupAccess = UserGroupAccess.save(this)(session)

  def destroy()(implicit session: DBSession = UserGroupAccess.autoSession): Int = UserGroupAccess.destroy(this)(session)

}


object UserGroupAccess extends SQLSyntaxSupport[UserGroupAccess] {

  override val tableName = "user_group_access"

  override val columns = Seq("gr_code", "ma_id")

  def apply(uga: SyntaxProvider[UserGroupAccess])(rs: WrappedResultSet): UserGroupAccess = autoConstruct(rs, uga)
  def apply(uga: ResultName[UserGroupAccess])(rs: WrappedResultSet): UserGroupAccess = autoConstruct(rs, uga)

  val uga = UserGroupAccess.syntax("uga")

  override val autoSession = AutoSession

  def find(grCode: Short, maId: Short)(implicit session: DBSession = autoSession): Option[UserGroupAccess] = {
    withSQL {
      select.from(UserGroupAccess as uga).where.eq(uga.grCode, grCode).and.eq(uga.maId, maId)
    }.map(UserGroupAccess(uga.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserGroupAccess] = {
    withSQL(select.from(UserGroupAccess as uga)).map(UserGroupAccess(uga.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserGroupAccess as uga)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserGroupAccess] = {
    withSQL {
      select.from(UserGroupAccess as uga).where.append(where)
    }.map(UserGroupAccess(uga.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserGroupAccess] = {
    withSQL {
      select.from(UserGroupAccess as uga).where.append(where)
    }.map(UserGroupAccess(uga.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserGroupAccess as uga).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    grCode: Short,
    maId: Short)(implicit session: DBSession = autoSession): UserGroupAccess = {
    withSQL {
      insert.into(UserGroupAccess).namedValues(
        column.grCode -> grCode,
        column.maId -> maId
      )
    }.update.apply()

    UserGroupAccess(
      grCode = grCode,
      maId = maId)
  }

  def batchInsert(entities: Seq[UserGroupAccess])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'grCode -> entity.grCode,
        'maId -> entity.maId))
    SQL("""insert into user_group_access(
      gr_code,
      ma_id
    ) values (
      {grCode},
      {maId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserGroupAccess)(implicit session: DBSession = autoSession): UserGroupAccess = {
    withSQL {
      update(UserGroupAccess).set(
        column.grCode -> entity.grCode,
        column.maId -> entity.maId
      ).where.eq(column.grCode, entity.grCode).and.eq(column.maId, entity.maId)
    }.update.apply()
    entity
  }

  def destroy(entity: UserGroupAccess)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserGroupAccess).where.eq(column.grCode, entity.grCode).and.eq(column.maId, entity.maId) }.update.apply()
  }

}
