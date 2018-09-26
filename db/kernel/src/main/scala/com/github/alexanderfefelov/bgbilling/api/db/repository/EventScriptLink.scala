package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EventScriptLink(
  id: Int,
  title: String,
  className: String,
  eventKey: String,
  scriptId: Int) {

  def save()(implicit session: DBSession = EventScriptLink.autoSession): EventScriptLink = EventScriptLink.save(this)(session)

  def destroy()(implicit session: DBSession = EventScriptLink.autoSession): Int = EventScriptLink.destroy(this)(session)

}


object EventScriptLink extends SQLSyntaxSupport[EventScriptLink] {

  override val tableName = "event_script_link"

  override val columns = Seq("id", "title", "class_name", "event_key", "script_id")

  def apply(esl: SyntaxProvider[EventScriptLink])(rs: WrappedResultSet): EventScriptLink = autoConstruct(rs, esl)
  def apply(esl: ResultName[EventScriptLink])(rs: WrappedResultSet): EventScriptLink = autoConstruct(rs, esl)

  val esl = EventScriptLink.syntax("esl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[EventScriptLink] = {
    withSQL {
      select.from(EventScriptLink as esl).where.eq(esl.id, id)
    }.map(EventScriptLink(esl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EventScriptLink] = {
    withSQL(select.from(EventScriptLink as esl)).map(EventScriptLink(esl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EventScriptLink as esl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EventScriptLink] = {
    withSQL {
      select.from(EventScriptLink as esl).where.append(where)
    }.map(EventScriptLink(esl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EventScriptLink] = {
    withSQL {
      select.from(EventScriptLink as esl).where.append(where)
    }.map(EventScriptLink(esl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EventScriptLink as esl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    className: String,
    eventKey: String,
    scriptId: Int)(implicit session: DBSession = autoSession): EventScriptLink = {
    val generatedKey = withSQL {
      insert.into(EventScriptLink).namedValues(
        column.title -> title,
        column.className -> className,
        column.eventKey -> eventKey,
        column.scriptId -> scriptId
      )
    }.updateAndReturnGeneratedKey.apply()

    EventScriptLink(
      id = generatedKey.toInt,
      title = title,
      className = className,
      eventKey = eventKey,
      scriptId = scriptId)
  }

  def batchInsert(entities: collection.Seq[EventScriptLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'className -> entity.className,
        'eventKey -> entity.eventKey,
        'scriptId -> entity.scriptId))
    SQL("""insert into event_script_link(
      title,
      class_name,
      event_key,
      script_id
    ) values (
      {title},
      {className},
      {eventKey},
      {scriptId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EventScriptLink)(implicit session: DBSession = autoSession): EventScriptLink = {
    withSQL {
      update(EventScriptLink).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.className -> entity.className,
        column.eventKey -> entity.eventKey,
        column.scriptId -> entity.scriptId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: EventScriptLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EventScriptLink).where.eq(column.id, entity.id) }.update.apply()
  }

}
