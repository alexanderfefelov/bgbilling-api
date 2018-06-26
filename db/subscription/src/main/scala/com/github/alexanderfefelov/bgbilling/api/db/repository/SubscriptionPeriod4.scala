package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class SubscriptionPeriod4(
  id: Int,
  subscriptionId: Option[Int] = None,
  dateFrom: Option[DateTime] = None,
  dateTo: Option[DateTime] = None,
  dateCreate: Option[DateTime] = None,
  whoCreate: Option[Int] = None) {

  def save()(implicit session: DBSession = SubscriptionPeriod4.autoSession): SubscriptionPeriod4 = SubscriptionPeriod4.save(this)(session)

  def destroy()(implicit session: DBSession = SubscriptionPeriod4.autoSession): Int = SubscriptionPeriod4.destroy(this)(session)

}


object SubscriptionPeriod4 extends SQLSyntaxSupport[SubscriptionPeriod4] with ApiDbConfig {

  override val tableName = s"subscription_period_${bgBillingModuleId("subscription")}"

  override val columns = Seq("id", "subscription_id", "date_from", "date_to", "date_create", "who_create")

  def apply(sp: SyntaxProvider[SubscriptionPeriod4])(rs: WrappedResultSet): SubscriptionPeriod4 = autoConstruct(rs, sp)
  def apply(sp: ResultName[SubscriptionPeriod4])(rs: WrappedResultSet): SubscriptionPeriod4 = autoConstruct(rs, sp)

  val sp = SubscriptionPeriod4.syntax("sp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[SubscriptionPeriod4] = {
    withSQL {
      select.from(SubscriptionPeriod4 as sp).where.eq(sp.id, id)
    }.map(SubscriptionPeriod4(sp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SubscriptionPeriod4] = {
    withSQL(select.from(SubscriptionPeriod4 as sp)).map(SubscriptionPeriod4(sp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SubscriptionPeriod4 as sp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SubscriptionPeriod4] = {
    withSQL {
      select.from(SubscriptionPeriod4 as sp).where.append(where)
    }.map(SubscriptionPeriod4(sp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SubscriptionPeriod4] = {
    withSQL {
      select.from(SubscriptionPeriod4 as sp).where.append(where)
    }.map(SubscriptionPeriod4(sp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SubscriptionPeriod4 as sp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    subscriptionId: Option[Int] = None,
    dateFrom: Option[DateTime] = None,
    dateTo: Option[DateTime] = None,
    dateCreate: Option[DateTime] = None,
    whoCreate: Option[Int] = None)(implicit session: DBSession = autoSession): SubscriptionPeriod4 = {
    val generatedKey = withSQL {
      insert.into(SubscriptionPeriod4).namedValues(
        column.subscriptionId -> subscriptionId,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.dateCreate -> dateCreate,
        column.whoCreate -> whoCreate
      )
    }.updateAndReturnGeneratedKey.apply()

    SubscriptionPeriod4(
      id = generatedKey.toInt,
      subscriptionId = subscriptionId,
      dateFrom = dateFrom,
      dateTo = dateTo,
      dateCreate = dateCreate,
      whoCreate = whoCreate)
  }

  def batchInsert(entities: Seq[SubscriptionPeriod4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'subscriptionId -> entity.subscriptionId,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'dateCreate -> entity.dateCreate,
        'whoCreate -> entity.whoCreate))
    SQL("""insert into subscription_period_4(
      subscription_id,
      date_from,
      date_to,
      date_create,
      who_create
    ) values (
      {subscriptionId},
      {dateFrom},
      {dateTo},
      {dateCreate},
      {whoCreate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SubscriptionPeriod4)(implicit session: DBSession = autoSession): SubscriptionPeriod4 = {
    withSQL {
      update(SubscriptionPeriod4).set(
        column.id -> entity.id,
        column.subscriptionId -> entity.subscriptionId,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.dateCreate -> entity.dateCreate,
        column.whoCreate -> entity.whoCreate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: SubscriptionPeriod4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SubscriptionPeriod4).where.eq(column.id, entity.id) }.update.apply()
  }

}
