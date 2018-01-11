package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}

case class InvIpResourceSubscriptionReal1(
  id: Int,
  subscriberid: Int,
  subscribertitle: Option[String] = None,
  addressfrom: Array[Byte],
  addressto: Array[Byte],
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = InvIpResourceSubscriptionReal1.autoSession): InvIpResourceSubscriptionReal1 = InvIpResourceSubscriptionReal1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpResourceSubscriptionReal1.autoSession): Int = InvIpResourceSubscriptionReal1.destroy(this)(session)

}


object InvIpResourceSubscriptionReal1 extends SQLSyntaxSupport[InvIpResourceSubscriptionReal1] with ApiDbConfig {

  override val tableName = s"inv_ip_resource_subscription_real_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "subscriberId", "subscriberTitle", "addressFrom", "addressTo", "dateFrom", "dateTo")

  def apply(iirsr: SyntaxProvider[InvIpResourceSubscriptionReal1])(rs: WrappedResultSet): InvIpResourceSubscriptionReal1 = autoConstruct(rs, iirsr)
  def apply(iirsr: ResultName[InvIpResourceSubscriptionReal1])(rs: WrappedResultSet): InvIpResourceSubscriptionReal1 = autoConstruct(rs, iirsr)

  val iirsr = InvIpResourceSubscriptionReal1.syntax("iirsr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscriptionReal1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionReal1 as iirsr).where.eq(iirsr.id, id)
    }.map(InvIpResourceSubscriptionReal1(iirsr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpResourceSubscriptionReal1] = {
    withSQL(select.from(InvIpResourceSubscriptionReal1 as iirsr)).map(InvIpResourceSubscriptionReal1(iirsr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpResourceSubscriptionReal1 as iirsr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscriptionReal1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionReal1 as iirsr).where.append(where)
    }.map(InvIpResourceSubscriptionReal1(iirsr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpResourceSubscriptionReal1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionReal1 as iirsr).where.append(where)
    }.map(InvIpResourceSubscriptionReal1(iirsr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpResourceSubscriptionReal1 as iirsr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    subscriberid: Int,
    subscribertitle: Option[String] = None,
    addressfrom: Array[Byte],
    addressto: Array[Byte],
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None)(implicit session: DBSession = autoSession): InvIpResourceSubscriptionReal1 = {
    val generatedKey = withSQL {
      insert.into(InvIpResourceSubscriptionReal1).namedValues(
        column.subscriberid -> subscriberid,
        column.subscribertitle -> subscribertitle,
        column.addressfrom -> addressfrom,
        column.addressto -> addressto,
        column.datefrom -> datefrom,
        column.dateto -> dateto
      )
    }.updateAndReturnGeneratedKey.apply()

    InvIpResourceSubscriptionReal1(
      id = generatedKey.toInt,
      subscriberid = subscriberid,
      subscribertitle = subscribertitle,
      addressfrom = addressfrom,
      addressto = addressto,
      datefrom = datefrom,
      dateto = dateto)
  }

  def batchInsert(entities: Seq[InvIpResourceSubscriptionReal1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'subscriberid -> entity.subscriberid,
        'subscribertitle -> entity.subscribertitle,
        'addressfrom -> entity.addressfrom,
        'addressto -> entity.addressto,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto))
    SQL("""insert into inv_ip_resource_subscription_real_1(
      subscriberId,
      subscriberTitle,
      addressFrom,
      addressTo,
      dateFrom,
      dateTo
    ) values (
      {subscriberid},
      {subscribertitle},
      {addressfrom},
      {addressto},
      {datefrom},
      {dateto}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpResourceSubscriptionReal1)(implicit session: DBSession = autoSession): InvIpResourceSubscriptionReal1 = {
    withSQL {
      update(InvIpResourceSubscriptionReal1).set(
        column.id -> entity.id,
        column.subscriberid -> entity.subscriberid,
        column.subscribertitle -> entity.subscribertitle,
        column.addressfrom -> entity.addressfrom,
        column.addressto -> entity.addressto,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpResourceSubscriptionReal1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpResourceSubscriptionReal1).where.eq(column.id, entity.id) }.update.apply()
  }

}
