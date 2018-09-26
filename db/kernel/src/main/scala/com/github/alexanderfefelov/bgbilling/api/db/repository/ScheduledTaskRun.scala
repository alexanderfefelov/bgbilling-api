package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ScheduledTaskRun(
  id: Int,
  data: Option[Array[Byte]] = None,
  description: Option[String] = None,
  executed: Option[Byte] = None,
  startTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = ScheduledTaskRun.autoSession): ScheduledTaskRun = ScheduledTaskRun.save(this)(session)

  def destroy()(implicit session: DBSession = ScheduledTaskRun.autoSession): Int = ScheduledTaskRun.destroy(this)(session)

}


object ScheduledTaskRun extends SQLSyntaxSupport[ScheduledTaskRun] {

  override val tableName = "scheduled_task_run"

  override val columns = Seq("id", "data", "description", "executed", "start_time")

  def apply(str: SyntaxProvider[ScheduledTaskRun])(rs: WrappedResultSet): ScheduledTaskRun = autoConstruct(rs, str)
  def apply(str: ResultName[ScheduledTaskRun])(rs: WrappedResultSet): ScheduledTaskRun = autoConstruct(rs, str)

  val str = ScheduledTaskRun.syntax("str")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScheduledTaskRun] = {
    withSQL {
      select.from(ScheduledTaskRun as str).where.eq(str.id, id)
    }.map(ScheduledTaskRun(str.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScheduledTaskRun] = {
    withSQL(select.from(ScheduledTaskRun as str)).map(ScheduledTaskRun(str.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScheduledTaskRun as str)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScheduledTaskRun] = {
    withSQL {
      select.from(ScheduledTaskRun as str).where.append(where)
    }.map(ScheduledTaskRun(str.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScheduledTaskRun] = {
    withSQL {
      select.from(ScheduledTaskRun as str).where.append(where)
    }.map(ScheduledTaskRun(str.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScheduledTaskRun as str).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    data: Option[Array[Byte]] = None,
    description: Option[String] = None,
    executed: Option[Byte] = None,
    startTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): ScheduledTaskRun = {
    val generatedKey = withSQL {
      insert.into(ScheduledTaskRun).namedValues(
        column.data -> data,
        column.description -> description,
        column.executed -> executed,
        column.startTime -> startTime
      )
    }.updateAndReturnGeneratedKey.apply()

    ScheduledTaskRun(
      id = generatedKey.toInt,
      data = data,
      description = description,
      executed = executed,
      startTime = startTime)
  }

  def batchInsert(entities: collection.Seq[ScheduledTaskRun])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'data -> entity.data,
        'description -> entity.description,
        'executed -> entity.executed,
        'startTime -> entity.startTime))
    SQL("""insert into scheduled_task_run(
      data,
      description,
      executed,
      start_time
    ) values (
      {data},
      {description},
      {executed},
      {startTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScheduledTaskRun)(implicit session: DBSession = autoSession): ScheduledTaskRun = {
    withSQL {
      update(ScheduledTaskRun).set(
        column.id -> entity.id,
        column.data -> entity.data,
        column.description -> entity.description,
        column.executed -> entity.executed,
        column.startTime -> entity.startTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScheduledTaskRun)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScheduledTaskRun).where.eq(column.id, entity.id) }.update.apply()
  }

}
