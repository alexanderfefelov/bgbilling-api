package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class SequentialIds(
  mid: String,
  value: Long) {

  def save()(implicit session: DBSession = SequentialIds.autoSession): SequentialIds = SequentialIds.save(this)(session)

  def destroy()(implicit session: DBSession = SequentialIds.autoSession): Int = SequentialIds.destroy(this)(session)

}


object SequentialIds extends SQLSyntaxSupport[SequentialIds] {

  override val tableName = "sequential_ids"

  override val columns = Seq("mid", "value")

  def apply(si: SyntaxProvider[SequentialIds])(rs: WrappedResultSet): SequentialIds = autoConstruct(rs, si)
  def apply(si: ResultName[SequentialIds])(rs: WrappedResultSet): SequentialIds = autoConstruct(rs, si)

  val si = SequentialIds.syntax("si")

  override val autoSession = AutoSession

  def find(mid: String)(implicit session: DBSession = autoSession): Option[SequentialIds] = {
    withSQL {
      select.from(SequentialIds as si).where.eq(si.mid, mid)
    }.map(SequentialIds(si.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SequentialIds] = {
    withSQL(select.from(SequentialIds as si)).map(SequentialIds(si.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SequentialIds as si)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SequentialIds] = {
    withSQL {
      select.from(SequentialIds as si).where.append(where)
    }.map(SequentialIds(si.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SequentialIds] = {
    withSQL {
      select.from(SequentialIds as si).where.append(where)
    }.map(SequentialIds(si.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SequentialIds as si).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: String,
    value: Long)(implicit session: DBSession = autoSession): SequentialIds = {
    withSQL {
      insert.into(SequentialIds).namedValues(
        column.mid -> mid,
        column.value -> value
      )
    }.update.apply()

    SequentialIds(
      mid = mid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[SequentialIds])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'value -> entity.value))
    SQL("""insert into sequential_ids(
      mid,
      value
    ) values (
      {mid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SequentialIds)(implicit session: DBSession = autoSession): SequentialIds = {
    withSQL {
      update(SequentialIds).set(
        column.mid -> entity.mid,
        column.value -> entity.value
      ).where.eq(column.mid, entity.mid)
    }.update.apply()
    entity
  }

  def destroy(entity: SequentialIds)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SequentialIds).where.eq(column.mid, entity.mid) }.update.apply()
  }

}
