package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ScheduledTasks(
  id: Int,
  mm: Long,
  dm: Int,
  dw: Byte,
  hh: Int,
  min: Long,
  prior: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  status: Byte,
  classId: Int,
  `class`: String,
  moduleId: String,
  comment: String,
  params: String) {

  def save()(implicit session: DBSession = ScheduledTasks.autoSession): ScheduledTasks = ScheduledTasks.save(this)(session)

  def destroy()(implicit session: DBSession = ScheduledTasks.autoSession): Int = ScheduledTasks.destroy(this)(session)

}


object ScheduledTasks extends SQLSyntaxSupport[ScheduledTasks] {

  override val tableName = "scheduled_tasks"

  override val columns = Seq("id", "mm", "dm", "dw", "hh", "min", "prior", "date1", "date2", "status", "class_id", "class", "module_id", "comment", "params")

  def apply(st: SyntaxProvider[ScheduledTasks])(rs: WrappedResultSet): ScheduledTasks = autoConstruct(rs, st)
  def apply(st: ResultName[ScheduledTasks])(rs: WrappedResultSet): ScheduledTasks = autoConstruct(rs, st)

  val st = ScheduledTasks.syntax("st")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScheduledTasks] = {
    withSQL {
      select.from(ScheduledTasks as st).where.eq(st.id, id)
    }.map(ScheduledTasks(st.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScheduledTasks] = {
    withSQL(select.from(ScheduledTasks as st)).map(ScheduledTasks(st.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScheduledTasks as st)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScheduledTasks] = {
    withSQL {
      select.from(ScheduledTasks as st).where.append(where)
    }.map(ScheduledTasks(st.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScheduledTasks] = {
    withSQL {
      select.from(ScheduledTasks as st).where.append(where)
    }.map(ScheduledTasks(st.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScheduledTasks as st).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mm: Long,
    dm: Int,
    dw: Byte,
    hh: Int,
    min: Long,
    prior: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    status: Byte,
    classId: Int,
    `class`: String,
    moduleId: String,
    comment: String,
    params: String)(implicit session: DBSession = autoSession): ScheduledTasks = {
    val generatedKey = withSQL {
      insert.into(ScheduledTasks).namedValues(
        column.mm -> mm,
        column.dm -> dm,
        column.dw -> dw,
        column.hh -> hh,
        column.min -> min,
        column.prior -> prior,
        column.date1 -> date1,
        column.date2 -> date2,
        column.status -> status,
        column.classId -> classId,
        column.`class` -> `class`,
        column.moduleId -> moduleId,
        column.comment -> comment,
        column.params -> params
      )
    }.updateAndReturnGeneratedKey.apply()

    ScheduledTasks(
      id = generatedKey.toInt,
      mm = mm,
      dm = dm,
      dw = dw,
      hh = hh,
      min = min,
      prior = prior,
      date1 = date1,
      date2 = date2,
      status = status,
      classId = classId,
      `class` = `class`,
      moduleId = moduleId,
      comment = comment,
      params = params)
  }

  def batchInsert(entities: collection.Seq[ScheduledTasks])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mm -> entity.mm,
        'dm -> entity.dm,
        'dw -> entity.dw,
        'hh -> entity.hh,
        'min -> entity.min,
        'prior -> entity.prior,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'status -> entity.status,
        'classId -> entity.classId,
        'class -> entity.`class`,
        'moduleId -> entity.moduleId,
        'comment -> entity.comment,
        'params -> entity.params))
    SQL("""insert into scheduled_tasks(
      mm,
      dm,
      dw,
      hh,
      min,
      prior,
      date1,
      date2,
      status,
      class_id,
      class,
      module_id,
      comment,
      params
    ) values (
      {mm},
      {dm},
      {dw},
      {hh},
      {min},
      {prior},
      {date1},
      {date2},
      {status},
      {classId},
      {class},
      {moduleId},
      {comment},
      {params}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScheduledTasks)(implicit session: DBSession = autoSession): ScheduledTasks = {
    withSQL {
      update(ScheduledTasks).set(
        column.id -> entity.id,
        column.mm -> entity.mm,
        column.dm -> entity.dm,
        column.dw -> entity.dw,
        column.hh -> entity.hh,
        column.min -> entity.min,
        column.prior -> entity.prior,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.status -> entity.status,
        column.classId -> entity.classId,
        column.`class` -> entity.`class`,
        column.moduleId -> entity.moduleId,
        column.comment -> entity.comment,
        column.params -> entity.params
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScheduledTasks)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScheduledTasks).where.eq(column.id, entity.id) }.update.apply()
  }

}
