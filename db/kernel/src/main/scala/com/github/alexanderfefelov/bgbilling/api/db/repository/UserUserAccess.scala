package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserUserAccess(
  userId: Short,
  maId: Option[Short] = None) {

  def save()(implicit session: DBSession = UserUserAccess.autoSession): UserUserAccess = UserUserAccess.save(this)(session)

  def destroy()(implicit session: DBSession = UserUserAccess.autoSession): Int = UserUserAccess.destroy(this)(session)

}


object UserUserAccess extends SQLSyntaxSupport[UserUserAccess] {

  override val tableName = "user_user_access"

  override val columns = Seq("user_id", "ma_id")

  def apply(uua: SyntaxProvider[UserUserAccess])(rs: WrappedResultSet): UserUserAccess = autoConstruct(rs, uua)
  def apply(uua: ResultName[UserUserAccess])(rs: WrappedResultSet): UserUserAccess = autoConstruct(rs, uua)

  val uua = UserUserAccess.syntax("uua")

  override val autoSession = AutoSession

  def find(userId: Short, maId: Option[Short])(implicit session: DBSession = autoSession): Option[UserUserAccess] = {
    withSQL {
      select.from(UserUserAccess as uua).where.eq(uua.userId, userId).and.eq(uua.maId, maId)
    }.map(UserUserAccess(uua.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserUserAccess] = {
    withSQL(select.from(UserUserAccess as uua)).map(UserUserAccess(uua.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserUserAccess as uua)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserUserAccess] = {
    withSQL {
      select.from(UserUserAccess as uua).where.append(where)
    }.map(UserUserAccess(uua.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserUserAccess] = {
    withSQL {
      select.from(UserUserAccess as uua).where.append(where)
    }.map(UserUserAccess(uua.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserUserAccess as uua).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Short,
    maId: Option[Short] = None)(implicit session: DBSession = autoSession): UserUserAccess = {
    withSQL {
      insert.into(UserUserAccess).namedValues(
        column.userId -> userId,
        column.maId -> maId
      )
    }.update.apply()

    UserUserAccess(
      userId = userId,
      maId = maId)
  }

  def batchInsert(entities: collection.Seq[UserUserAccess])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'maId -> entity.maId))
    SQL("""insert into user_user_access(
      user_id,
      ma_id
    ) values (
      {userId},
      {maId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserUserAccess)(implicit session: DBSession = autoSession): UserUserAccess = {
    withSQL {
      update(UserUserAccess).set(
        column.userId -> entity.userId,
        column.maId -> entity.maId
      ).where.eq(column.userId, entity.userId).and.eq(column.maId, entity.maId)
    }.update.apply()
    entity
  }

  def destroy(entity: UserUserAccess)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserUserAccess).where.eq(column.userId, entity.userId).and.eq(column.maId, entity.maId) }.update.apply()
  }

}
