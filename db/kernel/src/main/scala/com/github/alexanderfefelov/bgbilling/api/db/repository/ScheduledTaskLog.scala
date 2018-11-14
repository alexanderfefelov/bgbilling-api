package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScheduledTaskLog(
  id: Int,
  taskId: Int,
  start: Long,
  finish: Long) {

  def save()(implicit session: DBSession = ScheduledTaskLog.autoSession): ScheduledTaskLog = ScheduledTaskLog.save(this)(session)

  def destroy()(implicit session: DBSession = ScheduledTaskLog.autoSession): Int = ScheduledTaskLog.destroy(this)(session)

}


object ScheduledTaskLog extends SQLSyntaxSupport[ScheduledTaskLog] {

  override val tableName = "scheduled_task_log"

  override val columns = Seq("id", "task_id", "start", "finish")

  def apply(stl: SyntaxProvider[ScheduledTaskLog])(rs: WrappedResultSet): ScheduledTaskLog = autoConstruct(rs, stl)
  def apply(stl: ResultName[ScheduledTaskLog])(rs: WrappedResultSet): ScheduledTaskLog = autoConstruct(rs, stl)

  val stl = ScheduledTaskLog.syntax("stl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScheduledTaskLog] = {
    withSQL {
      select.from(ScheduledTaskLog as stl).where.eq(stl.id, id)
    }.map(ScheduledTaskLog(stl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScheduledTaskLog] = {
    withSQL(select.from(ScheduledTaskLog as stl)).map(ScheduledTaskLog(stl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScheduledTaskLog as stl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScheduledTaskLog] = {
    withSQL {
      select.from(ScheduledTaskLog as stl).where.append(where)
    }.map(ScheduledTaskLog(stl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScheduledTaskLog] = {
    withSQL {
      select.from(ScheduledTaskLog as stl).where.append(where)
    }.map(ScheduledTaskLog(stl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScheduledTaskLog as stl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    taskId: Int,
    start: Long,
    finish: Long)(implicit session: DBSession = autoSession): ScheduledTaskLog = {
    val generatedKey = withSQL {
      insert.into(ScheduledTaskLog).namedValues(
        column.taskId -> taskId,
        column.start -> start,
        column.finish -> finish
      )
    }.updateAndReturnGeneratedKey.apply()

    ScheduledTaskLog(
      id = generatedKey.toInt,
      taskId = taskId,
      start = start,
      finish = finish)
  }

  def batchInsert(entities: collection.Seq[ScheduledTaskLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'taskId -> entity.taskId,
        'start -> entity.start,
        'finish -> entity.finish))
    SQL("""insert into scheduled_task_log(
      task_id,
      start,
      finish
    ) values (
      {taskId},
      {start},
      {finish}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScheduledTaskLog)(implicit session: DBSession = autoSession): ScheduledTaskLog = {
    withSQL {
      update(ScheduledTaskLog).set(
        column.id -> entity.id,
        column.taskId -> entity.taskId,
        column.start -> entity.start,
        column.finish -> entity.finish
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScheduledTaskLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScheduledTaskLog).where.eq(column.id, entity.id) }.update.apply()
  }

}
