package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserMenu(
  uid: Int,
  menuId: String,
  hidden: Byte) {

  def save()(implicit session: DBSession = UserMenu.autoSession): UserMenu = UserMenu.save(this)(session)

  def destroy()(implicit session: DBSession = UserMenu.autoSession): Int = UserMenu.destroy(this)(session)

}


object UserMenu extends SQLSyntaxSupport[UserMenu] {

  override val tableName = "user_menu"

  override val columns = Seq("uid", "menu_id", "hidden")

  def apply(um: SyntaxProvider[UserMenu])(rs: WrappedResultSet): UserMenu = autoConstruct(rs, um)
  def apply(um: ResultName[UserMenu])(rs: WrappedResultSet): UserMenu = autoConstruct(rs, um)

  val um = UserMenu.syntax("um")

  override val autoSession = AutoSession

  def find(menuId: String, uid: Int)(implicit session: DBSession = autoSession): Option[UserMenu] = {
    withSQL {
      select.from(UserMenu as um).where.eq(um.menuId, menuId).and.eq(um.uid, uid)
    }.map(UserMenu(um.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserMenu] = {
    withSQL(select.from(UserMenu as um)).map(UserMenu(um.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserMenu as um)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserMenu] = {
    withSQL {
      select.from(UserMenu as um).where.append(where)
    }.map(UserMenu(um.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserMenu] = {
    withSQL {
      select.from(UserMenu as um).where.append(where)
    }.map(UserMenu(um.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserMenu as um).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uid: Int,
    menuId: String,
    hidden: Byte)(implicit session: DBSession = autoSession): UserMenu = {
    withSQL {
      insert.into(UserMenu).namedValues(
        column.uid -> uid,
        column.menuId -> menuId,
        column.hidden -> hidden
      )
    }.update.apply()

    UserMenu(
      uid = uid,
      menuId = menuId,
      hidden = hidden)
  }

  def batchInsert(entities: Seq[UserMenu])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uid -> entity.uid,
        'menuId -> entity.menuId,
        'hidden -> entity.hidden))
    SQL("""insert into user_menu(
      uid,
      menu_id,
      hidden
    ) values (
      {uid},
      {menuId},
      {hidden}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserMenu)(implicit session: DBSession = autoSession): UserMenu = {
    withSQL {
      update(UserMenu).set(
        column.uid -> entity.uid,
        column.menuId -> entity.menuId,
        column.hidden -> entity.hidden
      ).where.eq(column.menuId, entity.menuId).and.eq(column.uid, entity.uid)
    }.update.apply()
    entity
  }

  def destroy(entity: UserMenu)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserMenu).where.eq(column.menuId, entity.menuId).and.eq(column.uid, entity.uid) }.update.apply()
  }

}
