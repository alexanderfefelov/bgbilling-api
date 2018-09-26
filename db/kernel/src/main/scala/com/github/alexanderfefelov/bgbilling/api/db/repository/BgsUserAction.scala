package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsUserAction(
  uid: Int,
  mid: String,
  aid: Int) {

  def save()(implicit session: DBSession = BgsUserAction.autoSession): BgsUserAction = BgsUserAction.save(this)(session)

  def destroy()(implicit session: DBSession = BgsUserAction.autoSession): Int = BgsUserAction.destroy(this)(session)

}


object BgsUserAction extends SQLSyntaxSupport[BgsUserAction] {

  override val tableName = "bgs_user_action"

  override val columns = Seq("uid", "mid", "aid")

  def apply(bua: SyntaxProvider[BgsUserAction])(rs: WrappedResultSet): BgsUserAction = autoConstruct(rs, bua)
  def apply(bua: ResultName[BgsUserAction])(rs: WrappedResultSet): BgsUserAction = autoConstruct(rs, bua)

  val bua = BgsUserAction.syntax("bua")

  override val autoSession = AutoSession

  def find(uid: Int, mid: String, aid: Int)(implicit session: DBSession = autoSession): Option[BgsUserAction] = {
    withSQL {
      select.from(BgsUserAction as bua).where.eq(bua.uid, uid).and.eq(bua.mid, mid).and.eq(bua.aid, aid)
    }.map(BgsUserAction(bua.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsUserAction] = {
    withSQL(select.from(BgsUserAction as bua)).map(BgsUserAction(bua.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsUserAction as bua)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsUserAction] = {
    withSQL {
      select.from(BgsUserAction as bua).where.append(where)
    }.map(BgsUserAction(bua.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsUserAction] = {
    withSQL {
      select.from(BgsUserAction as bua).where.append(where)
    }.map(BgsUserAction(bua.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsUserAction as bua).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uid: Int,
    mid: String,
    aid: Int)(implicit session: DBSession = autoSession): BgsUserAction = {
    withSQL {
      insert.into(BgsUserAction).namedValues(
        column.uid -> uid,
        column.mid -> mid,
        column.aid -> aid
      )
    }.update.apply()

    BgsUserAction(
      uid = uid,
      mid = mid,
      aid = aid)
  }

  def batchInsert(entities: collection.Seq[BgsUserAction])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uid -> entity.uid,
        'mid -> entity.mid,
        'aid -> entity.aid))
    SQL("""insert into bgs_user_action(
      uid,
      mid,
      aid
    ) values (
      {uid},
      {mid},
      {aid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsUserAction)(implicit session: DBSession = autoSession): BgsUserAction = {
    withSQL {
      update(BgsUserAction).set(
        column.uid -> entity.uid,
        column.mid -> entity.mid,
        column.aid -> entity.aid
      ).where.eq(column.uid, entity.uid).and.eq(column.mid, entity.mid).and.eq(column.aid, entity.aid)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsUserAction)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsUserAction).where.eq(column.uid, entity.uid).and.eq(column.mid, entity.mid).and.eq(column.aid, entity.aid) }.update.apply()
  }

}
