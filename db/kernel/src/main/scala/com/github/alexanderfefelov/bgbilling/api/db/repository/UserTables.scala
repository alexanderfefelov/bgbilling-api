package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class UserTables(
  userid: Option[Int] = None,
  tableModule: Option[String] = None,
  tableId: Option[String] = None,
  widths: Option[String] = None,
  positions: Option[String] = None,
  hiddens: Option[String] = None) {

  def save()(implicit session: DBSession = UserTables.autoSession): UserTables = UserTables.save(this)(session)

  def destroy()(implicit session: DBSession = UserTables.autoSession): Int = UserTables.destroy(this)(session)

}


object UserTables extends SQLSyntaxSupport[UserTables] {

  override val tableName = "user_tables"

  override val columns = Seq("userId", "table_module", "table_id", "widths", "positions", "hiddens")

  def apply(ut: SyntaxProvider[UserTables])(rs: WrappedResultSet): UserTables = autoConstruct(rs, ut)
  def apply(ut: ResultName[UserTables])(rs: WrappedResultSet): UserTables = autoConstruct(rs, ut)

  val ut = UserTables.syntax("ut")

  override val autoSession = AutoSession

  def find(userid: Option[Int], tableModule: Option[String], tableId: Option[String], widths: Option[String], positions: Option[String], hiddens: Option[String])(implicit session: DBSession = autoSession): Option[UserTables] = {
    withSQL {
      select.from(UserTables as ut).where.eq(ut.userid, userid).and.eq(ut.tableModule, tableModule).and.eq(ut.tableId, tableId).and.eq(ut.widths, widths).and.eq(ut.positions, positions).and.eq(ut.hiddens, hiddens)
    }.map(UserTables(ut.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[UserTables] = {
    withSQL(select.from(UserTables as ut)).map(UserTables(ut.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(UserTables as ut)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[UserTables] = {
    withSQL {
      select.from(UserTables as ut).where.append(where)
    }.map(UserTables(ut.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[UserTables] = {
    withSQL {
      select.from(UserTables as ut).where.append(where)
    }.map(UserTables(ut.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(UserTables as ut).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userid: Option[Int] = None,
    tableModule: Option[String] = None,
    tableId: Option[String] = None,
    widths: Option[String] = None,
    positions: Option[String] = None,
    hiddens: Option[String] = None)(implicit session: DBSession = autoSession): UserTables = {
    withSQL {
      insert.into(UserTables).namedValues(
        column.userid -> userid,
        column.tableModule -> tableModule,
        column.tableId -> tableId,
        column.widths -> widths,
        column.positions -> positions,
        column.hiddens -> hiddens
      )
    }.update.apply()

    UserTables(
      userid = userid,
      tableModule = tableModule,
      tableId = tableId,
      widths = widths,
      positions = positions,
      hiddens = hiddens)
  }

  def batchInsert(entities: Seq[UserTables])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userid -> entity.userid,
        'tableModule -> entity.tableModule,
        'tableId -> entity.tableId,
        'widths -> entity.widths,
        'positions -> entity.positions,
        'hiddens -> entity.hiddens))
    SQL("""insert into user_tables(
      userId,
      table_module,
      table_id,
      widths,
      positions,
      hiddens
    ) values (
      {userid},
      {tableModule},
      {tableId},
      {widths},
      {positions},
      {hiddens}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: UserTables)(implicit session: DBSession = autoSession): UserTables = {
    withSQL {
      update(UserTables).set(
        column.userid -> entity.userid,
        column.tableModule -> entity.tableModule,
        column.tableId -> entity.tableId,
        column.widths -> entity.widths,
        column.positions -> entity.positions,
        column.hiddens -> entity.hiddens
      ).where.eq(column.userid, entity.userid).and.eq(column.tableModule, entity.tableModule).and.eq(column.tableId, entity.tableId).and.eq(column.widths, entity.widths).and.eq(column.positions, entity.positions).and.eq(column.hiddens, entity.hiddens)
    }.update.apply()
    entity
  }

  def destroy(entity: UserTables)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(UserTables).where.eq(column.userid, entity.userid).and.eq(column.tableModule, entity.tableModule).and.eq(column.tableId, entity.tableId).and.eq(column.widths, entity.widths).and.eq(column.positions, entity.positions).and.eq(column.hiddens, entity.hiddens) }.update.apply()
  }

}
