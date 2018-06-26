package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class SubscriptionType4(
  id: Int,
  serviceId: Option[Int] = None,
  title: Option[String] = None,
  comment: Option[String] = None,
  dateFrom: Option[DateTime] = None,
  dateTo: Option[DateTime] = None,
  durationValue: Option[Long] = None,
  durationUnit: Option[String] = None,
  delayValue: Option[Long] = None,
  delayUnit: Option[String] = None,
  roundValue: Option[Long] = None,
  roundUnit: Option[String] = None,
  avtoCommit: Option[Boolean] = None,
  config: Option[String] = None) {

  def save()(implicit session: DBSession = SubscriptionType4.autoSession): SubscriptionType4 = SubscriptionType4.save(this)(session)

  def destroy()(implicit session: DBSession = SubscriptionType4.autoSession): Int = SubscriptionType4.destroy(this)(session)

}


object SubscriptionType4 extends SQLSyntaxSupport[SubscriptionType4] with ApiDbConfig {

  override val tableName = s"subscription_type_${bgBillingModuleId("subscription")}"

  override val columns = Seq("id", "service_id", "title", "comment", "date_from", "date_to", "duration_value", "duration_unit", "delay_value", "delay_unit", "round_value", "round_unit", "avto_commit", "config")

  def apply(st: SyntaxProvider[SubscriptionType4])(rs: WrappedResultSet): SubscriptionType4 = autoConstruct(rs, st)
  def apply(st: ResultName[SubscriptionType4])(rs: WrappedResultSet): SubscriptionType4 = autoConstruct(rs, st)

  val st = SubscriptionType4.syntax("st")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[SubscriptionType4] = {
    withSQL {
      select.from(SubscriptionType4 as st).where.eq(st.id, id)
    }.map(SubscriptionType4(st.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SubscriptionType4] = {
    withSQL(select.from(SubscriptionType4 as st)).map(SubscriptionType4(st.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SubscriptionType4 as st)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SubscriptionType4] = {
    withSQL {
      select.from(SubscriptionType4 as st).where.append(where)
    }.map(SubscriptionType4(st.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SubscriptionType4] = {
    withSQL {
      select.from(SubscriptionType4 as st).where.append(where)
    }.map(SubscriptionType4(st.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SubscriptionType4 as st).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    serviceId: Option[Int] = None,
    title: Option[String] = None,
    comment: Option[String] = None,
    dateFrom: Option[DateTime] = None,
    dateTo: Option[DateTime] = None,
    durationValue: Option[Long] = None,
    durationUnit: Option[String] = None,
    delayValue: Option[Long] = None,
    delayUnit: Option[String] = None,
    roundValue: Option[Long] = None,
    roundUnit: Option[String] = None,
    avtoCommit: Option[Boolean] = None,
    config: Option[String] = None)(implicit session: DBSession = autoSession): SubscriptionType4 = {
    val generatedKey = withSQL {
      insert.into(SubscriptionType4).namedValues(
        column.serviceId -> serviceId,
        column.title -> title,
        column.comment -> comment,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.durationValue -> durationValue,
        column.durationUnit -> durationUnit,
        column.delayValue -> delayValue,
        column.delayUnit -> delayUnit,
        column.roundValue -> roundValue,
        column.roundUnit -> roundUnit,
        column.avtoCommit -> avtoCommit,
        column.config -> config
      )
    }.updateAndReturnGeneratedKey.apply()

    SubscriptionType4(
      id = generatedKey.toInt,
      serviceId = serviceId,
      title = title,
      comment = comment,
      dateFrom = dateFrom,
      dateTo = dateTo,
      durationValue = durationValue,
      durationUnit = durationUnit,
      delayValue = delayValue,
      delayUnit = delayUnit,
      roundValue = roundValue,
      roundUnit = roundUnit,
      avtoCommit = avtoCommit,
      config = config)
  }

  def batchInsert(entities: Seq[SubscriptionType4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'serviceId -> entity.serviceId,
        'title -> entity.title,
        'comment -> entity.comment,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'durationValue -> entity.durationValue,
        'durationUnit -> entity.durationUnit,
        'delayValue -> entity.delayValue,
        'delayUnit -> entity.delayUnit,
        'roundValue -> entity.roundValue,
        'roundUnit -> entity.roundUnit,
        'avtoCommit -> entity.avtoCommit,
        'config -> entity.config))
    SQL("""insert into subscription_type_4(
      service_id,
      title,
      comment,
      date_from,
      date_to,
      duration_value,
      duration_unit,
      delay_value,
      delay_unit,
      round_value,
      round_unit,
      avto_commit,
      config
    ) values (
      {serviceId},
      {title},
      {comment},
      {dateFrom},
      {dateTo},
      {durationValue},
      {durationUnit},
      {delayValue},
      {delayUnit},
      {roundValue},
      {roundUnit},
      {avtoCommit},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SubscriptionType4)(implicit session: DBSession = autoSession): SubscriptionType4 = {
    withSQL {
      update(SubscriptionType4).set(
        column.id -> entity.id,
        column.serviceId -> entity.serviceId,
        column.title -> entity.title,
        column.comment -> entity.comment,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.durationValue -> entity.durationValue,
        column.durationUnit -> entity.durationUnit,
        column.delayValue -> entity.delayValue,
        column.delayUnit -> entity.delayUnit,
        column.roundValue -> entity.roundValue,
        column.roundUnit -> entity.roundUnit,
        column.avtoCommit -> entity.avtoCommit,
        column.config -> entity.config
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: SubscriptionType4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SubscriptionType4).where.eq(column.id, entity.id) }.update.apply()
  }

}
