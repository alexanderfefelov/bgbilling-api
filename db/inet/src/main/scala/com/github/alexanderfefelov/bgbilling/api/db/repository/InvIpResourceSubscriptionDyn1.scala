package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvIpResourceSubscriptionDyn1(
  id: Long,
  ipresourceid: Int,
  connectionid: Long,
  address: Array[Byte],
  timefrom: DateTime,
  timeto: Option[DateTime] = None,
  subscriberid: Int,
  subscribertype: Int,
  subscribertitle: String) {

  def save()(implicit session: DBSession = InvIpResourceSubscriptionDyn1.autoSession): InvIpResourceSubscriptionDyn1 = InvIpResourceSubscriptionDyn1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpResourceSubscriptionDyn1.autoSession): Int = InvIpResourceSubscriptionDyn1.destroy(this)(session)

}


object InvIpResourceSubscriptionDyn1 extends SQLSyntaxSupport[InvIpResourceSubscriptionDyn1] with ApiDbConfig {

  override val tableName = s"inv_ip_resource_subscription_dyn_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "ipResourceId", "connectionId", "address", "timeFrom", "timeTo", "subscriberId", "subscriberType", "subscriberTitle")

  def apply(iirsd: SyntaxProvider[InvIpResourceSubscriptionDyn1])(rs: WrappedResultSet): InvIpResourceSubscriptionDyn1 = autoConstruct(rs, iirsd)
  def apply(iirsd: ResultName[InvIpResourceSubscriptionDyn1])(rs: WrappedResultSet): InvIpResourceSubscriptionDyn1 = autoConstruct(rs, iirsd)

  val iirsd = InvIpResourceSubscriptionDyn1.syntax("iirsd")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscriptionDyn1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionDyn1 as iirsd).where.eq(iirsd.id, id)
    }.map(InvIpResourceSubscriptionDyn1(iirsd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpResourceSubscriptionDyn1] = {
    withSQL(select.from(InvIpResourceSubscriptionDyn1 as iirsd)).map(InvIpResourceSubscriptionDyn1(iirsd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpResourceSubscriptionDyn1 as iirsd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscriptionDyn1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionDyn1 as iirsd).where.append(where)
    }.map(InvIpResourceSubscriptionDyn1(iirsd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpResourceSubscriptionDyn1] = {
    withSQL {
      select.from(InvIpResourceSubscriptionDyn1 as iirsd).where.append(where)
    }.map(InvIpResourceSubscriptionDyn1(iirsd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpResourceSubscriptionDyn1 as iirsd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    ipresourceid: Int,
    connectionid: Long,
    address: Array[Byte],
    timefrom: DateTime,
    timeto: Option[DateTime] = None,
    subscriberid: Int,
    subscribertype: Int,
    subscribertitle: String)(implicit session: DBSession = autoSession): InvIpResourceSubscriptionDyn1 = {
    val generatedKey = withSQL {
      insert.into(InvIpResourceSubscriptionDyn1).namedValues(
        column.ipresourceid -> ipresourceid,
        column.connectionid -> connectionid,
        column.address -> address,
        column.timefrom -> timefrom,
        column.timeto -> timeto,
        column.subscriberid -> subscriberid,
        column.subscribertype -> subscribertype,
        column.subscribertitle -> subscribertitle
      )
    }.updateAndReturnGeneratedKey.apply()

    InvIpResourceSubscriptionDyn1(
      id = generatedKey,
      ipresourceid = ipresourceid,
      connectionid = connectionid,
      address = address,
      timefrom = timefrom,
      timeto = timeto,
      subscriberid = subscriberid,
      subscribertype = subscribertype,
      subscribertitle = subscribertitle)
  }

  def batchInsert(entities: Seq[InvIpResourceSubscriptionDyn1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'ipresourceid -> entity.ipresourceid,
        'connectionid -> entity.connectionid,
        'address -> entity.address,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'subscriberid -> entity.subscriberid,
        'subscribertype -> entity.subscribertype,
        'subscribertitle -> entity.subscribertitle))
    SQL("""insert into inv_ip_resource_subscription_dyn_1(
      ipResourceId,
      connectionId,
      address,
      timeFrom,
      timeTo,
      subscriberId,
      subscriberType,
      subscriberTitle
    ) values (
      {ipresourceid},
      {connectionid},
      {address},
      {timefrom},
      {timeto},
      {subscriberid},
      {subscribertype},
      {subscribertitle}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpResourceSubscriptionDyn1)(implicit session: DBSession = autoSession): InvIpResourceSubscriptionDyn1 = {
    withSQL {
      update(InvIpResourceSubscriptionDyn1).set(
        column.id -> entity.id,
        column.ipresourceid -> entity.ipresourceid,
        column.connectionid -> entity.connectionid,
        column.address -> entity.address,
        column.timefrom -> entity.timefrom,
        column.timeto -> entity.timeto,
        column.subscriberid -> entity.subscriberid,
        column.subscribertype -> entity.subscribertype,
        column.subscribertitle -> entity.subscribertitle
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpResourceSubscriptionDyn1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpResourceSubscriptionDyn1).where.eq(column.id, entity.id) }.update.apply()
  }

}
