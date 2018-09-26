package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class TaskLoad(
  id: Int,
  title: String,
  dt: DateTime,
  param: Int,
  count: Int,
  le: DateTime) {

  def save()(implicit session: DBSession = TaskLoad.autoSession): TaskLoad = TaskLoad.save(this)(session)

  def destroy()(implicit session: DBSession = TaskLoad.autoSession): Int = TaskLoad.destroy(this)(session)

}


object TaskLoad extends SQLSyntaxSupport[TaskLoad] {

  override val tableName = "task_load"

  override val columns = Seq("id", "title", "dt", "param", "count", "le")

  def apply(tl: SyntaxProvider[TaskLoad])(rs: WrappedResultSet): TaskLoad = autoConstruct(rs, tl)
  def apply(tl: ResultName[TaskLoad])(rs: WrappedResultSet): TaskLoad = autoConstruct(rs, tl)

  val tl = TaskLoad.syntax("tl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TaskLoad] = {
    withSQL {
      select.from(TaskLoad as tl).where.eq(tl.id, id)
    }.map(TaskLoad(tl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TaskLoad] = {
    withSQL(select.from(TaskLoad as tl)).map(TaskLoad(tl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TaskLoad as tl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TaskLoad] = {
    withSQL {
      select.from(TaskLoad as tl).where.append(where)
    }.map(TaskLoad(tl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TaskLoad] = {
    withSQL {
      select.from(TaskLoad as tl).where.append(where)
    }.map(TaskLoad(tl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TaskLoad as tl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    dt: DateTime,
    param: Int,
    count: Int,
    le: DateTime)(implicit session: DBSession = autoSession): TaskLoad = {
    val generatedKey = withSQL {
      insert.into(TaskLoad).namedValues(
        column.title -> title,
        column.dt -> dt,
        column.param -> param,
        column.count -> count,
        column.le -> le
      )
    }.updateAndReturnGeneratedKey.apply()

    TaskLoad(
      id = generatedKey.toInt,
      title = title,
      dt = dt,
      param = param,
      count = count,
      le = le)
  }

  def batchInsert(entities: collection.Seq[TaskLoad])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'dt -> entity.dt,
        'param -> entity.param,
        'count -> entity.count,
        'le -> entity.le))
    SQL("""insert into task_load(
      title,
      dt,
      param,
      count,
      le
    ) values (
      {title},
      {dt},
      {param},
      {count},
      {le}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TaskLoad)(implicit session: DBSession = autoSession): TaskLoad = {
    withSQL {
      update(TaskLoad).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.dt -> entity.dt,
        column.param -> entity.param,
        column.count -> entity.count,
        column.le -> entity.le
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TaskLoad)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TaskLoad).where.eq(column.id, entity.id) }.update.apply()
  }

}
