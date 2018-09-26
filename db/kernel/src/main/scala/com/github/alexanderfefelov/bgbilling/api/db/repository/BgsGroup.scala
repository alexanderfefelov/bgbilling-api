package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsGroup(
  id: Int,
  title: String,
  comment: String,
  cgr: Long,
  pids: Option[String] = None,
  opids: Option[String] = None,
  cgrMode: Byte) {

  def save()(implicit session: DBSession = BgsGroup.autoSession): BgsGroup = BgsGroup.save(this)(session)

  def destroy()(implicit session: DBSession = BgsGroup.autoSession): Int = BgsGroup.destroy(this)(session)

}


object BgsGroup extends SQLSyntaxSupport[BgsGroup] {

  override val tableName = "bgs_group"

  override val columns = Seq("id", "title", "comment", "cgr", "pids", "opids", "cgr_mode")

  def apply(bg: SyntaxProvider[BgsGroup])(rs: WrappedResultSet): BgsGroup = autoConstruct(rs, bg)
  def apply(bg: ResultName[BgsGroup])(rs: WrappedResultSet): BgsGroup = autoConstruct(rs, bg)

  val bg = BgsGroup.syntax("bg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BgsGroup] = {
    withSQL {
      select.from(BgsGroup as bg).where.eq(bg.id, id)
    }.map(BgsGroup(bg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsGroup] = {
    withSQL(select.from(BgsGroup as bg)).map(BgsGroup(bg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsGroup as bg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsGroup] = {
    withSQL {
      select.from(BgsGroup as bg).where.append(where)
    }.map(BgsGroup(bg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsGroup] = {
    withSQL {
      select.from(BgsGroup as bg).where.append(where)
    }.map(BgsGroup(bg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsGroup as bg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    comment: String,
    cgr: Long,
    pids: Option[String] = None,
    opids: Option[String] = None,
    cgrMode: Byte)(implicit session: DBSession = autoSession): BgsGroup = {
    val generatedKey = withSQL {
      insert.into(BgsGroup).namedValues(
        column.title -> title,
        column.comment -> comment,
        column.cgr -> cgr,
        column.pids -> pids,
        column.opids -> opids,
        column.cgrMode -> cgrMode
      )
    }.updateAndReturnGeneratedKey.apply()

    BgsGroup(
      id = generatedKey.toInt,
      title = title,
      comment = comment,
      cgr = cgr,
      pids = pids,
      opids = opids,
      cgrMode = cgrMode)
  }

  def batchInsert(entities: collection.Seq[BgsGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'comment -> entity.comment,
        'cgr -> entity.cgr,
        'pids -> entity.pids,
        'opids -> entity.opids,
        'cgrMode -> entity.cgrMode))
    SQL("""insert into bgs_group(
      title,
      comment,
      cgr,
      pids,
      opids,
      cgr_mode
    ) values (
      {title},
      {comment},
      {cgr},
      {pids},
      {opids},
      {cgrMode}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsGroup)(implicit session: DBSession = autoSession): BgsGroup = {
    withSQL {
      update(BgsGroup).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.comment -> entity.comment,
        column.cgr -> entity.cgr,
        column.pids -> entity.pids,
        column.opids -> entity.opids,
        column.cgrMode -> entity.cgrMode
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
