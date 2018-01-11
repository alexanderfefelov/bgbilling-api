package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class InvIpResourceSubscription1(
  id: Int,
  subscriberid: Int,
  ipresourceid: Int,
  addressfrom: Array[Byte],
  addressto: Array[Byte],
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  subscribertitle: String) {

  def save()(implicit session: DBSession = InvIpResourceSubscription1.autoSession): InvIpResourceSubscription1 = InvIpResourceSubscription1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpResourceSubscription1.autoSession): Int = InvIpResourceSubscription1.destroy(this)(session)

}


object InvIpResourceSubscription1 extends SQLSyntaxSupport[InvIpResourceSubscription1] {

  override val tableName = "inv_ip_resource_subscription_1"

  override val columns = Seq("id", "subscriberId", "ipResourceId", "addressFrom", "addressTo", "dateFrom", "dateTo", "subscriberTitle")

  def apply(iirs: SyntaxProvider[InvIpResourceSubscription1])(rs: WrappedResultSet): InvIpResourceSubscription1 = autoConstruct(rs, iirs)
  def apply(iirs: ResultName[InvIpResourceSubscription1])(rs: WrappedResultSet): InvIpResourceSubscription1 = autoConstruct(rs, iirs)

  val iirs = InvIpResourceSubscription1.syntax("iirs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscription1] = {
    withSQL {
      select.from(InvIpResourceSubscription1 as iirs).where.eq(iirs.id, id)
    }.map(InvIpResourceSubscription1(iirs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpResourceSubscription1] = {
    withSQL(select.from(InvIpResourceSubscription1 as iirs)).map(InvIpResourceSubscription1(iirs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpResourceSubscription1 as iirs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpResourceSubscription1] = {
    withSQL {
      select.from(InvIpResourceSubscription1 as iirs).where.append(where)
    }.map(InvIpResourceSubscription1(iirs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpResourceSubscription1] = {
    withSQL {
      select.from(InvIpResourceSubscription1 as iirs).where.append(where)
    }.map(InvIpResourceSubscription1(iirs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpResourceSubscription1 as iirs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    subscriberid: Int,
    ipresourceid: Int,
    addressfrom: Array[Byte],
    addressto: Array[Byte],
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    subscribertitle: String)(implicit session: DBSession = autoSession): InvIpResourceSubscription1 = {
    val generatedKey = withSQL {
      insert.into(InvIpResourceSubscription1).namedValues(
        column.subscriberid -> subscriberid,
        column.ipresourceid -> ipresourceid,
        column.addressfrom -> addressfrom,
        column.addressto -> addressto,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.subscribertitle -> subscribertitle
      )
    }.updateAndReturnGeneratedKey.apply()

    InvIpResourceSubscription1(
      id = generatedKey.toInt,
      subscriberid = subscriberid,
      ipresourceid = ipresourceid,
      addressfrom = addressfrom,
      addressto = addressto,
      datefrom = datefrom,
      dateto = dateto,
      subscribertitle = subscribertitle)
  }

  def batchInsert(entities: Seq[InvIpResourceSubscription1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'subscriberid -> entity.subscriberid,
        'ipresourceid -> entity.ipresourceid,
        'addressfrom -> entity.addressfrom,
        'addressto -> entity.addressto,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'subscribertitle -> entity.subscribertitle))
    SQL("""insert into inv_ip_resource_subscription_1(
      subscriberId,
      ipResourceId,
      addressFrom,
      addressTo,
      dateFrom,
      dateTo,
      subscriberTitle
    ) values (
      {subscriberid},
      {ipresourceid},
      {addressfrom},
      {addressto},
      {datefrom},
      {dateto},
      {subscribertitle}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpResourceSubscription1)(implicit session: DBSession = autoSession): InvIpResourceSubscription1 = {
    withSQL {
      update(InvIpResourceSubscription1).set(
        column.id -> entity.id,
        column.subscriberid -> entity.subscriberid,
        column.ipresourceid -> entity.ipresourceid,
        column.addressfrom -> entity.addressfrom,
        column.addressto -> entity.addressto,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.subscribertitle -> entity.subscribertitle
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpResourceSubscription1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpResourceSubscription1).where.eq(column.id, entity.id) }.update.apply()
  }

}
