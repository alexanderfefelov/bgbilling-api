package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsGroupAction(
  gid: Int,
  mid: String,
  aid: Int) {

  def save()(implicit session: DBSession = BgsGroupAction.autoSession): BgsGroupAction = BgsGroupAction.save(this)(session)

  def destroy()(implicit session: DBSession = BgsGroupAction.autoSession): Int = BgsGroupAction.destroy(this)(session)

}


object BgsGroupAction extends SQLSyntaxSupport[BgsGroupAction] {

  override val tableName = "bgs_group_action"

  override val columns = Seq("gid", "mid", "aid")

  def apply(bga: SyntaxProvider[BgsGroupAction])(rs: WrappedResultSet): BgsGroupAction = autoConstruct(rs, bga)
  def apply(bga: ResultName[BgsGroupAction])(rs: WrappedResultSet): BgsGroupAction = autoConstruct(rs, bga)

  val bga = BgsGroupAction.syntax("bga")

  override val autoSession = AutoSession

  def find(gid: Int, mid: String, aid: Int)(implicit session: DBSession = autoSession): Option[BgsGroupAction] = {
    withSQL {
      select.from(BgsGroupAction as bga).where.eq(bga.gid, gid).and.eq(bga.mid, mid).and.eq(bga.aid, aid)
    }.map(BgsGroupAction(bga.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsGroupAction] = {
    withSQL(select.from(BgsGroupAction as bga)).map(BgsGroupAction(bga.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsGroupAction as bga)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsGroupAction] = {
    withSQL {
      select.from(BgsGroupAction as bga).where.append(where)
    }.map(BgsGroupAction(bga.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsGroupAction] = {
    withSQL {
      select.from(BgsGroupAction as bga).where.append(where)
    }.map(BgsGroupAction(bga.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsGroupAction as bga).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gid: Int,
    mid: String,
    aid: Int)(implicit session: DBSession = autoSession): BgsGroupAction = {
    withSQL {
      insert.into(BgsGroupAction).namedValues(
        column.gid -> gid,
        column.mid -> mid,
        column.aid -> aid
      )
    }.update.apply()

    BgsGroupAction(
      gid = gid,
      mid = mid,
      aid = aid)
  }

  def batchInsert(entities: collection.Seq[BgsGroupAction])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gid -> entity.gid,
        'mid -> entity.mid,
        'aid -> entity.aid))
    SQL("""insert into bgs_group_action(
      gid,
      mid,
      aid
    ) values (
      {gid},
      {mid},
      {aid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsGroupAction)(implicit session: DBSession = autoSession): BgsGroupAction = {
    withSQL {
      update(BgsGroupAction).set(
        column.gid -> entity.gid,
        column.mid -> entity.mid,
        column.aid -> entity.aid
      ).where.eq(column.gid, entity.gid).and.eq(column.mid, entity.mid).and.eq(column.aid, entity.aid)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsGroupAction)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsGroupAction).where.eq(column.gid, entity.gid).and.eq(column.mid, entity.mid).and.eq(column.aid, entity.aid) }.update.apply()
  }

}
