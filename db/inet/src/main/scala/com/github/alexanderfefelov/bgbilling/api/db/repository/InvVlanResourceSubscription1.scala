package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvVlanResourceSubscription1(
  id: Int,
  vlanresid: Int,
  vlan: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  subscriberid: Int,
  subscribertitle: String) {

  def save()(implicit session: DBSession = InvVlanResourceSubscription1.autoSession): InvVlanResourceSubscription1 = InvVlanResourceSubscription1.save(this)(session)

  def destroy()(implicit session: DBSession = InvVlanResourceSubscription1.autoSession): Int = InvVlanResourceSubscription1.destroy(this)(session)

}


object InvVlanResourceSubscription1 extends SQLSyntaxSupport[InvVlanResourceSubscription1] with ApiDbConfig {

  override val tableName = s"inv_vlan_resource_subscription_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "vlanResId", "vlan", "dateFrom", "dateTo", "subscriberId", "subscriberTitle")

  def apply(ivrs: SyntaxProvider[InvVlanResourceSubscription1])(rs: WrappedResultSet): InvVlanResourceSubscription1 = autoConstruct(rs, ivrs)
  def apply(ivrs: ResultName[InvVlanResourceSubscription1])(rs: WrappedResultSet): InvVlanResourceSubscription1 = autoConstruct(rs, ivrs)

  val ivrs = InvVlanResourceSubscription1.syntax("ivrs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvVlanResourceSubscription1] = {
    withSQL {
      select.from(InvVlanResourceSubscription1 as ivrs).where.eq(ivrs.id, id)
    }.map(InvVlanResourceSubscription1(ivrs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvVlanResourceSubscription1] = {
    withSQL(select.from(InvVlanResourceSubscription1 as ivrs)).map(InvVlanResourceSubscription1(ivrs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvVlanResourceSubscription1 as ivrs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvVlanResourceSubscription1] = {
    withSQL {
      select.from(InvVlanResourceSubscription1 as ivrs).where.append(where)
    }.map(InvVlanResourceSubscription1(ivrs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvVlanResourceSubscription1] = {
    withSQL {
      select.from(InvVlanResourceSubscription1 as ivrs).where.append(where)
    }.map(InvVlanResourceSubscription1(ivrs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvVlanResourceSubscription1 as ivrs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    vlanresid: Int,
    vlan: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    subscriberid: Int,
    subscribertitle: String)(implicit session: DBSession = autoSession): InvVlanResourceSubscription1 = {
    val generatedKey = withSQL {
      insert.into(InvVlanResourceSubscription1).namedValues(
        column.vlanresid -> vlanresid,
        column.vlan -> vlan,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.subscriberid -> subscriberid,
        column.subscribertitle -> subscribertitle
      )
    }.updateAndReturnGeneratedKey.apply()

    InvVlanResourceSubscription1(
      id = generatedKey.toInt,
      vlanresid = vlanresid,
      vlan = vlan,
      datefrom = datefrom,
      dateto = dateto,
      subscriberid = subscriberid,
      subscribertitle = subscribertitle)
  }

  def batchInsert(entities: Seq[InvVlanResourceSubscription1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'vlanresid -> entity.vlanresid,
        'vlan -> entity.vlan,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'subscriberid -> entity.subscriberid,
        'subscribertitle -> entity.subscribertitle))
    SQL("""insert into inv_vlan_resource_subscription_1(
      vlanResId,
      vlan,
      dateFrom,
      dateTo,
      subscriberId,
      subscriberTitle
    ) values (
      {vlanresid},
      {vlan},
      {datefrom},
      {dateto},
      {subscriberid},
      {subscribertitle}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvVlanResourceSubscription1)(implicit session: DBSession = autoSession): InvVlanResourceSubscription1 = {
    withSQL {
      update(InvVlanResourceSubscription1).set(
        column.id -> entity.id,
        column.vlanresid -> entity.vlanresid,
        column.vlan -> entity.vlan,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.subscriberid -> entity.subscriberid,
        column.subscribertitle -> entity.subscribertitle
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvVlanResourceSubscription1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvVlanResourceSubscription1).where.eq(column.id, entity.id) }.update.apply()
  }

}
