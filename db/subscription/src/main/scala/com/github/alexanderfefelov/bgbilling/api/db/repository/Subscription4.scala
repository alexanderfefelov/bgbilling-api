package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Subscription4(
  id: Int,
  contractId: Option[Int] = None,
  typeId: Option[Int] = None,
  dateFrom: Option[DateTime] = None,
  dateTo: Option[DateTime] = None,
  registrationPeriodId: Option[Int] = None,
  avtoCommit: Option[Boolean] = None,
  jobId: Long,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = Subscription4.autoSession): Subscription4 = Subscription4.save(this)(session)

  def destroy()(implicit session: DBSession = Subscription4.autoSession): Int = Subscription4.destroy(this)(session)

}


object Subscription4 extends SQLSyntaxSupport[Subscription4] with ApiDbConfig {

  override val tableName = s"subscription_${bgBillingModuleId("subscription")}"

  override val columns = Seq("id", "contract_id", "type_id", "date_from", "date_to", "registration_period_id", "avto_commit", "job_id", "comment")

  def apply(s: SyntaxProvider[Subscription4])(rs: WrappedResultSet): Subscription4 = autoConstruct(rs, s)
  def apply(s: ResultName[Subscription4])(rs: WrappedResultSet): Subscription4 = autoConstruct(rs, s)

  val s = Subscription4.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Subscription4] = {
    withSQL {
      select.from(Subscription4 as s).where.eq(s.id, id)
    }.map(Subscription4(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Subscription4] = {
    withSQL(select.from(Subscription4 as s)).map(Subscription4(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Subscription4 as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Subscription4] = {
    withSQL {
      select.from(Subscription4 as s).where.append(where)
    }.map(Subscription4(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Subscription4] = {
    withSQL {
      select.from(Subscription4 as s).where.append(where)
    }.map(Subscription4(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Subscription4 as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Option[Int] = None,
    typeId: Option[Int] = None,
    dateFrom: Option[DateTime] = None,
    dateTo: Option[DateTime] = None,
    registrationPeriodId: Option[Int] = None,
    avtoCommit: Option[Boolean] = None,
    jobId: Long,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): Subscription4 = {
    val generatedKey = withSQL {
      insert.into(Subscription4).namedValues(
        column.contractId -> contractId,
        column.typeId -> typeId,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.registrationPeriodId -> registrationPeriodId,
        column.avtoCommit -> avtoCommit,
        column.jobId -> jobId,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    Subscription4(
      id = generatedKey.toInt,
      contractId = contractId,
      typeId = typeId,
      dateFrom = dateFrom,
      dateTo = dateTo,
      registrationPeriodId = registrationPeriodId,
      avtoCommit = avtoCommit,
      jobId = jobId,
      comment = comment)
  }

  def batchInsert(entities: Seq[Subscription4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'typeId -> entity.typeId,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'registrationPeriodId -> entity.registrationPeriodId,
        'avtoCommit -> entity.avtoCommit,
        'jobId -> entity.jobId,
        'comment -> entity.comment))
    SQL("""insert into subscription_4(
      contract_id,
      type_id,
      date_from,
      date_to,
      registration_period_id,
      avto_commit,
      job_id,
      comment
    ) values (
      {contractId},
      {typeId},
      {dateFrom},
      {dateTo},
      {registrationPeriodId},
      {avtoCommit},
      {jobId},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Subscription4)(implicit session: DBSession = autoSession): Subscription4 = {
    withSQL {
      update(Subscription4).set(
        column.id -> entity.id,
        column.contractId -> entity.contractId,
        column.typeId -> entity.typeId,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.registrationPeriodId -> entity.registrationPeriodId,
        column.avtoCommit -> entity.avtoCommit,
        column.jobId -> entity.jobId,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Subscription4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Subscription4).where.eq(column.id, entity.id) }.update.apply()
  }

}
