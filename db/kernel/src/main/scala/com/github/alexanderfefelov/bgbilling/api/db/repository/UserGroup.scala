package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserGroup(
  grCode: Short,
  grName: Option[String] = None) {

  def save()(implicit session: DBSession = UserGroup.autoSession): UserGroup = UserGroup.save(this)(session)

  def destroy()(implicit session: DBSession = UserGroup.autoSession): Int = UserGroup.destroy(this)(session)

}


object UserGroup extends SQLSyntaxSupport[UserGroup] {

  override val tableName = "user_group"

  override val columns = Seq("gr_code", "gr_name")

  def apply(ug: SyntaxProvider[UserGroup])(rs: WrappedResultSet): UserGroup = autoConstruct(rs, ug)
  def apply(ug: ResultName[UserGroup])(rs: WrappedResultSet): UserGroup = autoConstruct(rs, ug)

  val ug = UserGroup.syntax("ug")

  override val autoSession = AutoSession

  def find(grCode: Short)(implicit session: DBSession = autoSession): Option[UserGroup] = {
    withSQL {
      select.from(UserGroup as ug).where.eq(ug.grCode, grCode)
    }.map(UserGroup(ug.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserGroup] = {
    withSQL(select.from(UserGroup as ug)).map(UserGroup(ug.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserGroup as ug)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserGroup] = {
    withSQL {
      select.from(UserGroup as ug).where.append(where)
    }.map(UserGroup(ug.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserGroup] = {
    withSQL {
      select.from(UserGroup as ug).where.append(where)
    }.map(UserGroup(ug.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserGroup as ug).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    grName: Option[String] = None)(implicit session: DBSession = autoSession): UserGroup = {
    val generatedKey = withSQL {
      insert.into(UserGroup).namedValues(
        column.grName -> grName
      )
    }.updateAndReturnGeneratedKey.apply()

    UserGroup(
      grCode = generatedKey.toShort,
      grName = grName)
  }

  def batchInsert(entities: Seq[UserGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'grName -> entity.grName))
    SQL("""insert into user_group(
      gr_name
    ) values (
      {grName}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserGroup)(implicit session: DBSession = autoSession): UserGroup = {
    withSQL {
      update(UserGroup).set(
        column.grCode -> entity.grCode,
        column.grName -> entity.grName
      ).where.eq(column.grCode, entity.grCode)
    }.update.apply()
    entity
  }

  def destroy(entity: UserGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserGroup).where.eq(column.grCode, entity.grCode) }.update.apply()
  }

}
