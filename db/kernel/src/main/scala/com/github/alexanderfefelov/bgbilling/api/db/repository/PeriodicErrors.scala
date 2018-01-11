package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class PeriodicErrors(
  id: Int,
  marker: String,
  calcDate: Option[LocalDate] = None,
  subject: String,
  text: Option[String] = None,
  regTime: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = PeriodicErrors.autoSession): PeriodicErrors = PeriodicErrors.save(this)(session)

  def destroy()(implicit session: DBSession = PeriodicErrors.autoSession): Int = PeriodicErrors.destroy(this)(session)

}


object PeriodicErrors extends SQLSyntaxSupport[PeriodicErrors] {

  override val tableName = "periodic_errors"

  override val columns = Seq("id", "marker", "calc_date", "subject", "text", "reg_time")

  def apply(pe: SyntaxProvider[PeriodicErrors])(rs: WrappedResultSet): PeriodicErrors = autoConstruct(rs, pe)
  def apply(pe: ResultName[PeriodicErrors])(rs: WrappedResultSet): PeriodicErrors = autoConstruct(rs, pe)

  val pe = PeriodicErrors.syntax("pe")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PeriodicErrors] = {
    withSQL {
      select.from(PeriodicErrors as pe).where.eq(pe.id, id)
    }.map(PeriodicErrors(pe.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PeriodicErrors] = {
    withSQL(select.from(PeriodicErrors as pe)).map(PeriodicErrors(pe.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PeriodicErrors as pe)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PeriodicErrors] = {
    withSQL {
      select.from(PeriodicErrors as pe).where.append(where)
    }.map(PeriodicErrors(pe.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PeriodicErrors] = {
    withSQL {
      select.from(PeriodicErrors as pe).where.append(where)
    }.map(PeriodicErrors(pe.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PeriodicErrors as pe).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    marker: String,
    calcDate: Option[LocalDate] = None,
    subject: String,
    text: Option[String] = None,
    regTime: Option[LocalDate] = None)(implicit session: DBSession = autoSession): PeriodicErrors = {
    val generatedKey = withSQL {
      insert.into(PeriodicErrors).namedValues(
        column.marker -> marker,
        column.calcDate -> calcDate,
        column.subject -> subject,
        column.text -> text,
        column.regTime -> regTime
      )
    }.updateAndReturnGeneratedKey.apply()

    PeriodicErrors(
      id = generatedKey.toInt,
      marker = marker,
      calcDate = calcDate,
      subject = subject,
      text = text,
      regTime = regTime)
  }

  def batchInsert(entities: Seq[PeriodicErrors])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'marker -> entity.marker,
        'calcDate -> entity.calcDate,
        'subject -> entity.subject,
        'text -> entity.text,
        'regTime -> entity.regTime))
    SQL("""insert into periodic_errors(
      marker,
      calc_date,
      subject,
      text,
      reg_time
    ) values (
      {marker},
      {calcDate},
      {subject},
      {text},
      {regTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PeriodicErrors)(implicit session: DBSession = autoSession): PeriodicErrors = {
    withSQL {
      update(PeriodicErrors).set(
        column.id -> entity.id,
        column.marker -> entity.marker,
        column.calcDate -> entity.calcDate,
        column.subject -> entity.subject,
        column.text -> entity.text,
        column.regTime -> entity.regTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PeriodicErrors)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PeriodicErrors).where.eq(column.id, entity.id) }.update.apply()
  }

}
