package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScriptEventType(
  id: Int,
  mid: String,
  eventMode: Byte,
  eventId: String,
  title: String) {

  def save()(implicit session: DBSession = ScriptEventType.autoSession): ScriptEventType = ScriptEventType.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptEventType.autoSession): Int = ScriptEventType.destroy(this)(session)

}


object ScriptEventType extends SQLSyntaxSupport[ScriptEventType] {

  override val tableName = "script_event_type"

  override val columns = Seq("id", "mid", "event_mode", "event_id", "title")

  def apply(set: SyntaxProvider[ScriptEventType])(rs: WrappedResultSet): ScriptEventType = autoConstruct(rs, set)
  def apply(set: ResultName[ScriptEventType])(rs: WrappedResultSet): ScriptEventType = autoConstruct(rs, set)

  val set = ScriptEventType.syntax("set")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScriptEventType] = {
    withSQL {
      select.from(ScriptEventType as set).where.eq(set.id, id)
    }.map(ScriptEventType(set.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptEventType] = {
    withSQL(select.from(ScriptEventType as set)).map(ScriptEventType(set.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptEventType as set)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptEventType] = {
    withSQL {
      select.from(ScriptEventType as set).where.append(where)
    }.map(ScriptEventType(set.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptEventType] = {
    withSQL {
      select.from(ScriptEventType as set).where.append(where)
    }.map(ScriptEventType(set.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptEventType as set).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: String,
    eventMode: Byte,
    eventId: String,
    title: String)(implicit session: DBSession = autoSession): ScriptEventType = {
    val generatedKey = withSQL {
      insert.into(ScriptEventType).namedValues(
        column.mid -> mid,
        column.eventMode -> eventMode,
        column.eventId -> eventId,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ScriptEventType(
      id = generatedKey.toInt,
      mid = mid,
      eventMode = eventMode,
      eventId = eventId,
      title = title)
  }

  def batchInsert(entities: collection.Seq[ScriptEventType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'eventMode -> entity.eventMode,
        'eventId -> entity.eventId,
        'title -> entity.title))
    SQL("""insert into script_event_type(
      mid,
      event_mode,
      event_id,
      title
    ) values (
      {mid},
      {eventMode},
      {eventId},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptEventType)(implicit session: DBSession = autoSession): ScriptEventType = {
    withSQL {
      update(ScriptEventType).set(
        column.id -> entity.id,
        column.mid -> entity.mid,
        column.eventMode -> entity.eventMode,
        column.eventId -> entity.eventId,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptEventType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptEventType).where.eq(column.id, entity.id) }.update.apply()
  }

}
