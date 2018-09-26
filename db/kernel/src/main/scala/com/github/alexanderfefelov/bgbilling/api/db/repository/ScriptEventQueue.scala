package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScriptEventQueue(
  id: Int,
  event: Array[Byte]) {

  def save()(implicit session: DBSession = ScriptEventQueue.autoSession): ScriptEventQueue = ScriptEventQueue.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptEventQueue.autoSession): Int = ScriptEventQueue.destroy(this)(session)

}


object ScriptEventQueue extends SQLSyntaxSupport[ScriptEventQueue] {

  override val tableName = "script_event_queue"

  override val columns = Seq("id", "event")

  def apply(seq: SyntaxProvider[ScriptEventQueue])(rs: WrappedResultSet): ScriptEventQueue = autoConstruct(rs, seq)
  def apply(seq: ResultName[ScriptEventQueue])(rs: WrappedResultSet): ScriptEventQueue = autoConstruct(rs, seq)

  val seq = ScriptEventQueue.syntax("seq")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScriptEventQueue] = {
    withSQL {
      select.from(ScriptEventQueue as seq).where.eq(seq.id, id)
    }.map(ScriptEventQueue(seq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptEventQueue] = {
    withSQL(select.from(ScriptEventQueue as seq)).map(ScriptEventQueue(seq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptEventQueue as seq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptEventQueue] = {
    withSQL {
      select.from(ScriptEventQueue as seq).where.append(where)
    }.map(ScriptEventQueue(seq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptEventQueue] = {
    withSQL {
      select.from(ScriptEventQueue as seq).where.append(where)
    }.map(ScriptEventQueue(seq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptEventQueue as seq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    event: Array[Byte])(implicit session: DBSession = autoSession): ScriptEventQueue = {
    val generatedKey = withSQL {
      insert.into(ScriptEventQueue).namedValues(
        column.event -> event
      )
    }.updateAndReturnGeneratedKey.apply()

    ScriptEventQueue(
      id = generatedKey.toInt,
      event = event)
  }

  def batchInsert(entities: collection.Seq[ScriptEventQueue])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'event -> entity.event))
    SQL("""insert into script_event_queue(
      event
    ) values (
      {event}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptEventQueue)(implicit session: DBSession = autoSession): ScriptEventQueue = {
    withSQL {
      update(ScriptEventQueue).set(
        column.id -> entity.id,
        column.event -> entity.event
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptEventQueue)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptEventQueue).where.eq(column.id, entity.id) }.update.apply()
  }

}
