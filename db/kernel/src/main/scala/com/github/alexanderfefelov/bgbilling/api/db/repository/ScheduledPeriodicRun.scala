package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScheduledPeriodicRun(
  description: Option[String] = None,
  id: Int,
  amount: Option[Int] = None) {

  def save()(implicit session: DBSession = ScheduledPeriodicRun.autoSession): ScheduledPeriodicRun = ScheduledPeriodicRun.save(this)(session)

  def destroy()(implicit session: DBSession = ScheduledPeriodicRun.autoSession): Int = ScheduledPeriodicRun.destroy(this)(session)

}


object ScheduledPeriodicRun extends SQLSyntaxSupport[ScheduledPeriodicRun] {

  override val tableName = "scheduled_periodic_run"

  override val columns = Seq("description", "id", "amount")

  def apply(spr: SyntaxProvider[ScheduledPeriodicRun])(rs: WrappedResultSet): ScheduledPeriodicRun = autoConstruct(rs, spr)
  def apply(spr: ResultName[ScheduledPeriodicRun])(rs: WrappedResultSet): ScheduledPeriodicRun = autoConstruct(rs, spr)

  val spr = ScheduledPeriodicRun.syntax("spr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScheduledPeriodicRun] = {
    withSQL {
      select.from(ScheduledPeriodicRun as spr).where.eq(spr.id, id)
    }.map(ScheduledPeriodicRun(spr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScheduledPeriodicRun] = {
    withSQL(select.from(ScheduledPeriodicRun as spr)).map(ScheduledPeriodicRun(spr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScheduledPeriodicRun as spr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScheduledPeriodicRun] = {
    withSQL {
      select.from(ScheduledPeriodicRun as spr).where.append(where)
    }.map(ScheduledPeriodicRun(spr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScheduledPeriodicRun] = {
    withSQL {
      select.from(ScheduledPeriodicRun as spr).where.append(where)
    }.map(ScheduledPeriodicRun(spr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScheduledPeriodicRun as spr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    description: Option[String] = None,
    amount: Option[Int] = None)(implicit session: DBSession = autoSession): ScheduledPeriodicRun = {
    val generatedKey = withSQL {
      insert.into(ScheduledPeriodicRun).namedValues(
        column.description -> description,
        column.amount -> amount
      )
    }.updateAndReturnGeneratedKey.apply()

    ScheduledPeriodicRun(
      id = generatedKey.toInt,
      description = description,
      amount = amount)
  }

  def batchInsert(entities: Seq[ScheduledPeriodicRun])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'description -> entity.description,
        'amount -> entity.amount))
    SQL("""insert into scheduled_periodic_run(
      description,
      amount
    ) values (
      {description},
      {amount}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScheduledPeriodicRun)(implicit session: DBSession = autoSession): ScheduledPeriodicRun = {
    withSQL {
      update(ScheduledPeriodicRun).set(
        column.description -> entity.description,
        column.id -> entity.id,
        column.amount -> entity.amount
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScheduledPeriodicRun)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScheduledPeriodicRun).where.eq(column.id, entity.id) }.update.apply()
  }

}
