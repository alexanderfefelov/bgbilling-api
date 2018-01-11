package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserGroupMember(
  userId: Option[Short] = None,
  grCode: Option[Short] = None) {

  def save()(implicit session: DBSession = UserGroupMember.autoSession): UserGroupMember = UserGroupMember.save(this)(session)

  def destroy()(implicit session: DBSession = UserGroupMember.autoSession): Int = UserGroupMember.destroy(this)(session)

}


object UserGroupMember extends SQLSyntaxSupport[UserGroupMember] {

  override val tableName = "user_group_member"

  override val columns = Seq("user_id", "gr_code")

  def apply(ugm: SyntaxProvider[UserGroupMember])(rs: WrappedResultSet): UserGroupMember = autoConstruct(rs, ugm)
  def apply(ugm: ResultName[UserGroupMember])(rs: WrappedResultSet): UserGroupMember = autoConstruct(rs, ugm)

  val ugm = UserGroupMember.syntax("ugm")

  override val autoSession = AutoSession

  def find(userId: Option[Short], grCode: Option[Short])(implicit session: DBSession = autoSession): Option[UserGroupMember] = {
    withSQL {
      select.from(UserGroupMember as ugm).where.eq(ugm.userId, userId).and.eq(ugm.grCode, grCode)
    }.map(UserGroupMember(ugm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserGroupMember] = {
    withSQL(select.from(UserGroupMember as ugm)).map(UserGroupMember(ugm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserGroupMember as ugm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserGroupMember] = {
    withSQL {
      select.from(UserGroupMember as ugm).where.append(where)
    }.map(UserGroupMember(ugm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserGroupMember] = {
    withSQL {
      select.from(UserGroupMember as ugm).where.append(where)
    }.map(UserGroupMember(ugm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserGroupMember as ugm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Option[Short] = None,
    grCode: Option[Short] = None)(implicit session: DBSession = autoSession): UserGroupMember = {
    withSQL {
      insert.into(UserGroupMember).namedValues(
        column.userId -> userId,
        column.grCode -> grCode
      )
    }.update.apply()

    UserGroupMember(
      userId = userId,
      grCode = grCode)
  }

  def batchInsert(entities: Seq[UserGroupMember])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'grCode -> entity.grCode))
    SQL("""insert into user_group_member(
      user_id,
      gr_code
    ) values (
      {userId},
      {grCode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserGroupMember)(implicit session: DBSession = autoSession): UserGroupMember = {
    withSQL {
      update(UserGroupMember).set(
        column.userId -> entity.userId,
        column.grCode -> entity.grCode
      ).where.eq(column.userId, entity.userId).and.eq(column.grCode, entity.grCode)
    }.update.apply()
    entity
  }

  def destroy(entity: UserGroupMember)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserGroupMember).where.eq(column.userId, entity.userId).and.eq(column.grCode, entity.grCode) }.update.apply()
  }

}
