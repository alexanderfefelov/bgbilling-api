package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvIpResource1(
  id: Int,
  categoryid: Int,
  addressfrom: Array[Byte],
  addressto: Array[Byte],
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  router: String,
  subnetmask: String,
  dns: String,
  comment: String,
  dynamic: Option[Boolean] = None,
  config: String) {

  def save()(implicit session: DBSession = InvIpResource1.autoSession): InvIpResource1 = InvIpResource1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpResource1.autoSession): Int = InvIpResource1.destroy(this)(session)

}


object InvIpResource1 extends SQLSyntaxSupport[InvIpResource1] with ApiDbConfig {

  override val tableName = s"inv_ip_resource_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "categoryId", "addressFrom", "addressTo", "dateFrom", "dateTo", "router", "subnetMask", "dns", "comment", "dynamic", "config")

  def apply(iir: SyntaxProvider[InvIpResource1])(rs: WrappedResultSet): InvIpResource1 = autoConstruct(rs, iir)
  def apply(iir: ResultName[InvIpResource1])(rs: WrappedResultSet): InvIpResource1 = autoConstruct(rs, iir)

  val iir = InvIpResource1.syntax("iir")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvIpResource1] = {
    withSQL {
      select.from(InvIpResource1 as iir).where.eq(iir.id, id)
    }.map(InvIpResource1(iir.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpResource1] = {
    withSQL(select.from(InvIpResource1 as iir)).map(InvIpResource1(iir.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpResource1 as iir)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpResource1] = {
    withSQL {
      select.from(InvIpResource1 as iir).where.append(where)
    }.map(InvIpResource1(iir.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpResource1] = {
    withSQL {
      select.from(InvIpResource1 as iir).where.append(where)
    }.map(InvIpResource1(iir.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpResource1 as iir).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    categoryid: Int,
    addressfrom: Array[Byte],
    addressto: Array[Byte],
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    router: String,
    subnetmask: String,
    dns: String,
    comment: String,
    dynamic: Option[Boolean] = None,
    config: String)(implicit session: DBSession = autoSession): InvIpResource1 = {
    val generatedKey = withSQL {
      insert.into(InvIpResource1).namedValues(
        column.categoryid -> categoryid,
        column.addressfrom -> addressfrom,
        column.addressto -> addressto,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.router -> router,
        column.subnetmask -> subnetmask,
        column.dns -> dns,
        column.comment -> comment,
        column.dynamic -> dynamic,
        column.config -> config
      )
    }.updateAndReturnGeneratedKey.apply()

    InvIpResource1(
      id = generatedKey.toInt,
      categoryid = categoryid,
      addressfrom = addressfrom,
      addressto = addressto,
      datefrom = datefrom,
      dateto = dateto,
      router = router,
      subnetmask = subnetmask,
      dns = dns,
      comment = comment,
      dynamic = dynamic,
      config = config)
  }

  def batchInsert(entities: Seq[InvIpResource1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'categoryid -> entity.categoryid,
        'addressfrom -> entity.addressfrom,
        'addressto -> entity.addressto,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'router -> entity.router,
        'subnetmask -> entity.subnetmask,
        'dns -> entity.dns,
        'comment -> entity.comment,
        'dynamic -> entity.dynamic,
        'config -> entity.config))
    SQL("""insert into inv_ip_resource_1(
      categoryId,
      addressFrom,
      addressTo,
      dateFrom,
      dateTo,
      router,
      subnetMask,
      dns,
      comment,
      dynamic,
      config
    ) values (
      {categoryid},
      {addressfrom},
      {addressto},
      {datefrom},
      {dateto},
      {router},
      {subnetmask},
      {dns},
      {comment},
      {dynamic},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpResource1)(implicit session: DBSession = autoSession): InvIpResource1 = {
    withSQL {
      update(InvIpResource1).set(
        column.id -> entity.id,
        column.categoryid -> entity.categoryid,
        column.addressfrom -> entity.addressfrom,
        column.addressto -> entity.addressto,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.router -> entity.router,
        column.subnetmask -> entity.subnetmask,
        column.dns -> entity.dns,
        column.comment -> entity.comment,
        column.dynamic -> entity.dynamic,
        column.config -> entity.config
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpResource1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpResource1).where.eq(column.id, entity.id) }.update.apply()
  }

}
