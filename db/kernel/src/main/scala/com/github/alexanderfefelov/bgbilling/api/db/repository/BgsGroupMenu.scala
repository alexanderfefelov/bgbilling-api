package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsGroupMenu(
  gid: Int,
  menuId: String,
  hidden: Byte) {

  def save()(implicit session: DBSession = BgsGroupMenu.autoSession): BgsGroupMenu = BgsGroupMenu.save(this)(session)

  def destroy()(implicit session: DBSession = BgsGroupMenu.autoSession): Int = BgsGroupMenu.destroy(this)(session)

}


object BgsGroupMenu extends SQLSyntaxSupport[BgsGroupMenu] {

  override val tableName = "bgs_group_menu"

  override val columns = Seq("gid", "menu_id", "hidden")

  def apply(bgm: SyntaxProvider[BgsGroupMenu])(rs: WrappedResultSet): BgsGroupMenu = autoConstruct(rs, bgm)
  def apply(bgm: ResultName[BgsGroupMenu])(rs: WrappedResultSet): BgsGroupMenu = autoConstruct(rs, bgm)

  val bgm = BgsGroupMenu.syntax("bgm")

  override val autoSession = AutoSession

  def find(gid: Int, menuId: String)(implicit session: DBSession = autoSession): Option[BgsGroupMenu] = {
    withSQL {
      select.from(BgsGroupMenu as bgm).where.eq(bgm.gid, gid).and.eq(bgm.menuId, menuId)
    }.map(BgsGroupMenu(bgm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsGroupMenu] = {
    withSQL(select.from(BgsGroupMenu as bgm)).map(BgsGroupMenu(bgm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsGroupMenu as bgm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsGroupMenu] = {
    withSQL {
      select.from(BgsGroupMenu as bgm).where.append(where)
    }.map(BgsGroupMenu(bgm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsGroupMenu] = {
    withSQL {
      select.from(BgsGroupMenu as bgm).where.append(where)
    }.map(BgsGroupMenu(bgm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsGroupMenu as bgm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gid: Int,
    menuId: String,
    hidden: Byte)(implicit session: DBSession = autoSession): BgsGroupMenu = {
    withSQL {
      insert.into(BgsGroupMenu).namedValues(
        column.gid -> gid,
        column.menuId -> menuId,
        column.hidden -> hidden
      )
    }.update.apply()

    BgsGroupMenu(
      gid = gid,
      menuId = menuId,
      hidden = hidden)
  }

  def batchInsert(entities: collection.Seq[BgsGroupMenu])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gid -> entity.gid,
        'menuId -> entity.menuId,
        'hidden -> entity.hidden))
    SQL("""insert into bgs_group_menu(
      gid,
      menu_id,
      hidden
    ) values (
      {gid},
      {menuId},
      {hidden}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsGroupMenu)(implicit session: DBSession = autoSession): BgsGroupMenu = {
    withSQL {
      update(BgsGroupMenu).set(
        column.gid -> entity.gid,
        column.menuId -> entity.menuId,
        column.hidden -> entity.hidden
      ).where.eq(column.gid, entity.gid).and.eq(column.menuId, entity.menuId)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsGroupMenu)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsGroupMenu).where.eq(column.gid, entity.gid).and.eq(column.menuId, entity.menuId) }.update.apply()
  }

}
