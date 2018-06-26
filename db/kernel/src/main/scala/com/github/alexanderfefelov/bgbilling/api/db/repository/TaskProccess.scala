package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class TaskProccess(
  id: Int,
  mid: Int,
  title: String,
  dt: DateTime,
  param: Int,
  startProcessTime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = TaskProccess.autoSession): TaskProccess = TaskProccess.save(this)(session)

  def destroy()(implicit session: DBSession = TaskProccess.autoSession): Int = TaskProccess.destroy(this)(session)

}


object TaskProccess extends SQLSyntaxSupport[TaskProccess] {

  override val tableName = "task_proccess"

  override val columns = Seq("id", "mid", "title", "dt", "param", "start_process_time")

  def apply(tp: SyntaxProvider[TaskProccess])(rs: WrappedResultSet): TaskProccess = autoConstruct(rs, tp)
  def apply(tp: ResultName[TaskProccess])(rs: WrappedResultSet): TaskProccess = autoConstruct(rs, tp)

  val tp = TaskProccess.syntax("tp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TaskProccess] = {
    withSQL {
      select.from(TaskProccess as tp).where.eq(tp.id, id)
    }.map(TaskProccess(tp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TaskProccess] = {
    withSQL(select.from(TaskProccess as tp)).map(TaskProccess(tp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TaskProccess as tp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TaskProccess] = {
    withSQL {
      select.from(TaskProccess as tp).where.append(where)
    }.map(TaskProccess(tp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TaskProccess] = {
    withSQL {
      select.from(TaskProccess as tp).where.append(where)
    }.map(TaskProccess(tp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TaskProccess as tp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: Int,
    title: String,
    dt: DateTime,
    param: Int,
    startProcessTime: Option[DateTime] = None)(implicit session: DBSession = autoSession): TaskProccess = {
    val generatedKey = withSQL {
      insert.into(TaskProccess).namedValues(
        column.mid -> mid,
        column.title -> title,
        column.dt -> dt,
        column.param -> param,
        column.startProcessTime -> startProcessTime
      )
    }.updateAndReturnGeneratedKey.apply()

    TaskProccess(
      id = generatedKey.toInt,
      mid = mid,
      title = title,
      dt = dt,
      param = param,
      startProcessTime = startProcessTime)
  }

  def batchInsert(entities: Seq[TaskProccess])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'title -> entity.title,
        'dt -> entity.dt,
        'param -> entity.param,
        'startProcessTime -> entity.startProcessTime))
    SQL("""insert into task_proccess(
      mid,
      title,
      dt,
      param,
      start_process_time
    ) values (
      {mid},
      {title},
      {dt},
      {param},
      {startProcessTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TaskProccess)(implicit session: DBSession = autoSession): TaskProccess = {
    withSQL {
      update(TaskProccess).set(
        column.id -> entity.id,
        column.mid -> entity.mid,
        column.title -> entity.title,
        column.dt -> entity.dt,
        column.param -> entity.param,
        column.startProcessTime -> entity.startProcessTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TaskProccess)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TaskProccess).where.eq(column.id, entity.id) }.update.apply()
  }

}
