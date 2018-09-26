package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScriptFunctionEventType(
  fid: Int,
  mid: String,
  eventId: String) {

  def save()(implicit session: DBSession = ScriptFunctionEventType.autoSession): ScriptFunctionEventType = ScriptFunctionEventType.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptFunctionEventType.autoSession): Int = ScriptFunctionEventType.destroy(this)(session)

}


object ScriptFunctionEventType extends SQLSyntaxSupport[ScriptFunctionEventType] {

  override val tableName = "script_function_event_type"

  override val columns = Seq("fid", "mid", "event_id")

  def apply(sfet: SyntaxProvider[ScriptFunctionEventType])(rs: WrappedResultSet): ScriptFunctionEventType = autoConstruct(rs, sfet)
  def apply(sfet: ResultName[ScriptFunctionEventType])(rs: WrappedResultSet): ScriptFunctionEventType = autoConstruct(rs, sfet)

  val sfet = ScriptFunctionEventType.syntax("sfet")

  override val autoSession = AutoSession

  def find(fid: Int, mid: String, eventId: String)(implicit session: DBSession = autoSession): Option[ScriptFunctionEventType] = {
    withSQL {
      select.from(ScriptFunctionEventType as sfet).where.eq(sfet.fid, fid).and.eq(sfet.mid, mid).and.eq(sfet.eventId, eventId)
    }.map(ScriptFunctionEventType(sfet.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptFunctionEventType] = {
    withSQL(select.from(ScriptFunctionEventType as sfet)).map(ScriptFunctionEventType(sfet.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptFunctionEventType as sfet)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptFunctionEventType] = {
    withSQL {
      select.from(ScriptFunctionEventType as sfet).where.append(where)
    }.map(ScriptFunctionEventType(sfet.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptFunctionEventType] = {
    withSQL {
      select.from(ScriptFunctionEventType as sfet).where.append(where)
    }.map(ScriptFunctionEventType(sfet.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptFunctionEventType as sfet).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    fid: Int,
    mid: String,
    eventId: String)(implicit session: DBSession = autoSession): ScriptFunctionEventType = {
    withSQL {
      insert.into(ScriptFunctionEventType).namedValues(
        column.fid -> fid,
        column.mid -> mid,
        column.eventId -> eventId
      )
    }.update.apply()

    ScriptFunctionEventType(
      fid = fid,
      mid = mid,
      eventId = eventId)
  }

  def batchInsert(entities: collection.Seq[ScriptFunctionEventType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'fid -> entity.fid,
        'mid -> entity.mid,
        'eventId -> entity.eventId))
    SQL("""insert into script_function_event_type(
      fid,
      mid,
      event_id
    ) values (
      {fid},
      {mid},
      {eventId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptFunctionEventType)(implicit session: DBSession = autoSession): ScriptFunctionEventType = {
    withSQL {
      update(ScriptFunctionEventType).set(
        column.fid -> entity.fid,
        column.mid -> entity.mid,
        column.eventId -> entity.eventId
      ).where.eq(column.fid, entity.fid).and.eq(column.mid, entity.mid).and.eq(column.eventId, entity.eventId)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptFunctionEventType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptFunctionEventType).where.eq(column.fid, entity.fid).and.eq(column.mid, entity.mid).and.eq(column.eventId, entity.eventId) }.update.apply()
  }

}
