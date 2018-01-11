package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class InvDevicePortSubscription1(
  id: Int,
  deviceid: Int,
  port: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  subscriberid: Int,
  subscribertitle: Option[String] = None) {

  def save()(implicit session: DBSession = InvDevicePortSubscription1.autoSession): InvDevicePortSubscription1 = InvDevicePortSubscription1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDevicePortSubscription1.autoSession): Int = InvDevicePortSubscription1.destroy(this)(session)

}


object InvDevicePortSubscription1 extends SQLSyntaxSupport[InvDevicePortSubscription1] {

  override val tableName = "inv_device_port_subscription_1"

  override val columns = Seq("id", "deviceId", "port", "dateFrom", "dateTo", "subscriberId", "subscriberTitle")

  def apply(idps: SyntaxProvider[InvDevicePortSubscription1])(rs: WrappedResultSet): InvDevicePortSubscription1 = autoConstruct(rs, idps)
  def apply(idps: ResultName[InvDevicePortSubscription1])(rs: WrappedResultSet): InvDevicePortSubscription1 = autoConstruct(rs, idps)

  val idps = InvDevicePortSubscription1.syntax("idps")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvDevicePortSubscription1] = {
    withSQL {
      select.from(InvDevicePortSubscription1 as idps).where.eq(idps.id, id)
    }.map(InvDevicePortSubscription1(idps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDevicePortSubscription1] = {
    withSQL(select.from(InvDevicePortSubscription1 as idps)).map(InvDevicePortSubscription1(idps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDevicePortSubscription1 as idps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDevicePortSubscription1] = {
    withSQL {
      select.from(InvDevicePortSubscription1 as idps).where.append(where)
    }.map(InvDevicePortSubscription1(idps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDevicePortSubscription1] = {
    withSQL {
      select.from(InvDevicePortSubscription1 as idps).where.append(where)
    }.map(InvDevicePortSubscription1(idps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDevicePortSubscription1 as idps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    port: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    subscriberid: Int,
    subscribertitle: Option[String] = None)(implicit session: DBSession = autoSession): InvDevicePortSubscription1 = {
    val generatedKey = withSQL {
      insert.into(InvDevicePortSubscription1).namedValues(
        column.deviceid -> deviceid,
        column.port -> port,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.subscriberid -> subscriberid,
        column.subscribertitle -> subscribertitle
      )
    }.updateAndReturnGeneratedKey.apply()

    InvDevicePortSubscription1(
      id = generatedKey.toInt,
      deviceid = deviceid,
      port = port,
      datefrom = datefrom,
      dateto = dateto,
      subscriberid = subscriberid,
      subscribertitle = subscribertitle)
  }

  def batchInsert(entities: Seq[InvDevicePortSubscription1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'port -> entity.port,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'subscriberid -> entity.subscriberid,
        'subscribertitle -> entity.subscribertitle))
    SQL("""insert into inv_device_port_subscription_1(
      deviceId,
      port,
      dateFrom,
      dateTo,
      subscriberId,
      subscriberTitle
    ) values (
      {deviceid},
      {port},
      {datefrom},
      {dateto},
      {subscriberid},
      {subscribertitle}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDevicePortSubscription1)(implicit session: DBSession = autoSession): InvDevicePortSubscription1 = {
    withSQL {
      update(InvDevicePortSubscription1).set(
        column.id -> entity.id,
        column.deviceid -> entity.deviceid,
        column.port -> entity.port,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.subscriberid -> entity.subscriberid,
        column.subscribertitle -> entity.subscribertitle
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDevicePortSubscription1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDevicePortSubscription1).where.eq(column.id, entity.id) }.update.apply()
  }

}
