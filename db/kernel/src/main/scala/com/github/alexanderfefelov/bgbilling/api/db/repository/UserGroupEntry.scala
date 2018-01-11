package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserGroupEntry(
  grCode: Short,
  parentGrCode: Short) {

  def save()(implicit session: DBSession = UserGroupEntry.autoSession): UserGroupEntry = UserGroupEntry.save(this)(session)

  def destroy()(implicit session: DBSession = UserGroupEntry.autoSession): Int = UserGroupEntry.destroy(this)(session)

}


object UserGroupEntry extends SQLSyntaxSupport[UserGroupEntry] {

  override val tableName = "user_group_entry"

  override val columns = Seq("gr_code", "parent_gr_code")

  def apply(uge: SyntaxProvider[UserGroupEntry])(rs: WrappedResultSet): UserGroupEntry = autoConstruct(rs, uge)
  def apply(uge: ResultName[UserGroupEntry])(rs: WrappedResultSet): UserGroupEntry = autoConstruct(rs, uge)

  val uge = UserGroupEntry.syntax("uge")

  override val autoSession = AutoSession

  def find(grCode: Short, parentGrCode: Short)(implicit session: DBSession = autoSession): Option[UserGroupEntry] = {
    withSQL {
      select.from(UserGroupEntry as uge).where.eq(uge.grCode, grCode).and.eq(uge.parentGrCode, parentGrCode)
    }.map(UserGroupEntry(uge.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserGroupEntry] = {
    withSQL(select.from(UserGroupEntry as uge)).map(UserGroupEntry(uge.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserGroupEntry as uge)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserGroupEntry] = {
    withSQL {
      select.from(UserGroupEntry as uge).where.append(where)
    }.map(UserGroupEntry(uge.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserGroupEntry] = {
    withSQL {
      select.from(UserGroupEntry as uge).where.append(where)
    }.map(UserGroupEntry(uge.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserGroupEntry as uge).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    grCode: Short,
    parentGrCode: Short)(implicit session: DBSession = autoSession): UserGroupEntry = {
    withSQL {
      insert.into(UserGroupEntry).namedValues(
        column.grCode -> grCode,
        column.parentGrCode -> parentGrCode
      )
    }.update.apply()

    UserGroupEntry(
      grCode = grCode,
      parentGrCode = parentGrCode)
  }

  def batchInsert(entities: Seq[UserGroupEntry])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'grCode -> entity.grCode,
        'parentGrCode -> entity.parentGrCode))
    SQL("""insert into user_group_entry(
      gr_code,
      parent_gr_code
    ) values (
      {grCode},
      {parentGrCode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserGroupEntry)(implicit session: DBSession = autoSession): UserGroupEntry = {
    withSQL {
      update(UserGroupEntry).set(
        column.grCode -> entity.grCode,
        column.parentGrCode -> entity.parentGrCode
      ).where.eq(column.grCode, entity.grCode).and.eq(column.parentGrCode, entity.parentGrCode)
    }.update.apply()
    entity
  }

  def destroy(entity: UserGroupEntry)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserGroupEntry).where.eq(column.grCode, entity.grCode).and.eq(column.parentGrCode, entity.parentGrCode) }.update.apply()
  }

}
