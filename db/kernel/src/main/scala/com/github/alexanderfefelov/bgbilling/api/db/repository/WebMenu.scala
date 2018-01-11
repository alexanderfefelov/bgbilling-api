package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class WebMenu(
  id: Int,
  default: Boolean,
  title: String,
  data: String) {

  def save()(implicit session: DBSession = WebMenu.autoSession): WebMenu = WebMenu.save(this)(session)

  def destroy()(implicit session: DBSession = WebMenu.autoSession): Int = WebMenu.destroy(this)(session)

}


object WebMenu extends SQLSyntaxSupport[WebMenu] {

  override val tableName = "web_menu"

  override val columns = Seq("id", "default", "title", "data")

  def apply(wm: SyntaxProvider[WebMenu])(rs: WrappedResultSet): WebMenu = autoConstruct(rs, wm)
  def apply(wm: ResultName[WebMenu])(rs: WrappedResultSet): WebMenu = autoConstruct(rs, wm)

  val wm = WebMenu.syntax("wm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WebMenu] = {
    withSQL {
      select.from(WebMenu as wm).where.eq(wm.id, id)
    }.map(WebMenu(wm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WebMenu] = {
    withSQL(select.from(WebMenu as wm)).map(WebMenu(wm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WebMenu as wm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WebMenu] = {
    withSQL {
      select.from(WebMenu as wm).where.append(where)
    }.map(WebMenu(wm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WebMenu] = {
    withSQL {
      select.from(WebMenu as wm).where.append(where)
    }.map(WebMenu(wm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WebMenu as wm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    default: Boolean,
    title: String,
    data: String)(implicit session: DBSession = autoSession): WebMenu = {
    val generatedKey = withSQL {
      insert.into(WebMenu).namedValues(
        column.default -> default,
        column.title -> title,
        column.data -> data
      )
    }.updateAndReturnGeneratedKey.apply()

    WebMenu(
      id = generatedKey.toInt,
      default = default,
      title = title,
      data = data)
  }

  def batchInsert(entities: Seq[WebMenu])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'default -> entity.default,
        'title -> entity.title,
        'data -> entity.data))
    SQL("""insert into web_menu(
      default,
      title,
      data
    ) values (
      {default},
      {title},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WebMenu)(implicit session: DBSession = autoSession): WebMenu = {
    withSQL {
      update(WebMenu).set(
        column.id -> entity.id,
        column.default -> entity.default,
        column.title -> entity.title,
        column.data -> entity.data
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WebMenu)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WebMenu).where.eq(column.id, entity.id) }.update.apply()
  }

}
