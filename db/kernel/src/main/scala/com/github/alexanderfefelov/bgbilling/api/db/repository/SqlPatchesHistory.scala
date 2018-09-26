package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class SqlPatchesHistory(
  mid: String,
  versions: Option[String] = None) {

  def save()(implicit session: DBSession = SqlPatchesHistory.autoSession): SqlPatchesHistory = SqlPatchesHistory.save(this)(session)

  def destroy()(implicit session: DBSession = SqlPatchesHistory.autoSession): Int = SqlPatchesHistory.destroy(this)(session)

}


object SqlPatchesHistory extends SQLSyntaxSupport[SqlPatchesHistory] {

  override val tableName = "sql_patches_history"

  override val columns = Seq("mid", "versions")

  def apply(sph: SyntaxProvider[SqlPatchesHistory])(rs: WrappedResultSet): SqlPatchesHistory = autoConstruct(rs, sph)
  def apply(sph: ResultName[SqlPatchesHistory])(rs: WrappedResultSet): SqlPatchesHistory = autoConstruct(rs, sph)

  val sph = SqlPatchesHistory.syntax("sph")

  override val autoSession = AutoSession

  def find(mid: String)(implicit session: DBSession = autoSession): Option[SqlPatchesHistory] = {
    withSQL {
      select.from(SqlPatchesHistory as sph).where.eq(sph.mid, mid)
    }.map(SqlPatchesHistory(sph.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SqlPatchesHistory] = {
    withSQL(select.from(SqlPatchesHistory as sph)).map(SqlPatchesHistory(sph.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SqlPatchesHistory as sph)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SqlPatchesHistory] = {
    withSQL {
      select.from(SqlPatchesHistory as sph).where.append(where)
    }.map(SqlPatchesHistory(sph.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SqlPatchesHistory] = {
    withSQL {
      select.from(SqlPatchesHistory as sph).where.append(where)
    }.map(SqlPatchesHistory(sph.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SqlPatchesHistory as sph).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: String,
    versions: Option[String] = None)(implicit session: DBSession = autoSession): SqlPatchesHistory = {
    withSQL {
      insert.into(SqlPatchesHistory).namedValues(
        column.mid -> mid,
        column.versions -> versions
      )
    }.update.apply()

    SqlPatchesHistory(
      mid = mid,
      versions = versions)
  }

  def batchInsert(entities: collection.Seq[SqlPatchesHistory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'versions -> entity.versions))
    SQL("""insert into sql_patches_history(
      mid,
      versions
    ) values (
      {mid},
      {versions}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SqlPatchesHistory)(implicit session: DBSession = autoSession): SqlPatchesHistory = {
    withSQL {
      update(SqlPatchesHistory).set(
        column.mid -> entity.mid,
        column.versions -> entity.versions
      ).where.eq(column.mid, entity.mid)
    }.update.apply()
    entity
  }

  def destroy(entity: SqlPatchesHistory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SqlPatchesHistory).where.eq(column.mid, entity.mid) }.update.apply()
  }

}
