package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsUserGroup(
  uid: Int,
  gid: Int) {

  def save()(implicit session: DBSession = BgsUserGroup.autoSession): BgsUserGroup = BgsUserGroup.save(this)(session)

  def destroy()(implicit session: DBSession = BgsUserGroup.autoSession): Int = BgsUserGroup.destroy(this)(session)

}


object BgsUserGroup extends SQLSyntaxSupport[BgsUserGroup] {

  override val tableName = "bgs_user_group"

  override val columns = Seq("uid", "gid")

  def apply(bug: SyntaxProvider[BgsUserGroup])(rs: WrappedResultSet): BgsUserGroup = autoConstruct(rs, bug)
  def apply(bug: ResultName[BgsUserGroup])(rs: WrappedResultSet): BgsUserGroup = autoConstruct(rs, bug)

  val bug = BgsUserGroup.syntax("bug")

  override val autoSession = AutoSession

  def find(uid: Int, gid: Int)(implicit session: DBSession = autoSession): Option[BgsUserGroup] = {
    withSQL {
      select.from(BgsUserGroup as bug).where.eq(bug.uid, uid).and.eq(bug.gid, gid)
    }.map(BgsUserGroup(bug.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsUserGroup] = {
    withSQL(select.from(BgsUserGroup as bug)).map(BgsUserGroup(bug.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsUserGroup as bug)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsUserGroup] = {
    withSQL {
      select.from(BgsUserGroup as bug).where.append(where)
    }.map(BgsUserGroup(bug.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsUserGroup] = {
    withSQL {
      select.from(BgsUserGroup as bug).where.append(where)
    }.map(BgsUserGroup(bug.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsUserGroup as bug).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uid: Int,
    gid: Int)(implicit session: DBSession = autoSession): BgsUserGroup = {
    withSQL {
      insert.into(BgsUserGroup).namedValues(
        column.uid -> uid,
        column.gid -> gid
      )
    }.update.apply()

    BgsUserGroup(
      uid = uid,
      gid = gid)
  }

  def batchInsert(entities: Seq[BgsUserGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uid -> entity.uid,
        'gid -> entity.gid))
    SQL("""insert into bgs_user_group(
      uid,
      gid
    ) values (
      {uid},
      {gid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsUserGroup)(implicit session: DBSession = autoSession): BgsUserGroup = {
    withSQL {
      update(BgsUserGroup).set(
        column.uid -> entity.uid,
        column.gid -> entity.gid
      ).where.eq(column.uid, entity.uid).and.eq(column.gid, entity.gid)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsUserGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsUserGroup).where.eq(column.uid, entity.uid).and.eq(column.gid, entity.gid) }.update.apply()
  }

}
