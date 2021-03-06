package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvProduct(
  id: Int,
  contractid: Int,
  accountid: Int,
  productspecid: Int,
  timefrom: DateTime,
  timeto: Option[DateTime] = None,
  activationmodeid: Int,
  activationtime: DateTime,
  activationprice: Option[BigDecimal] = None,
  deactivationtime: Option[DateTime] = None,
  userid: Int,
  deviceproductid: Option[String] = None,
  devicestate: Byte,
  comment: String,
  description: String) {

  def save()(implicit session: DBSession = InvProduct.autoSession): InvProduct = InvProduct.save(this)(session)

  def destroy()(implicit session: DBSession = InvProduct.autoSession): Int = InvProduct.destroy(this)(session)

}


object InvProduct extends SQLSyntaxSupport[InvProduct] {

  override val tableName = "inv_product"

  override val columns = Seq("id", "contractId", "accountId", "productSpecId", "timeFrom", "timeTo", "activationModeId", "activationTime", "activationPrice", "deactivationTime", "userId", "deviceProductId", "deviceState", "comment", "description")

  def apply(ip: SyntaxProvider[InvProduct])(rs: WrappedResultSet): InvProduct = autoConstruct(rs, ip)
  def apply(ip: ResultName[InvProduct])(rs: WrappedResultSet): InvProduct = autoConstruct(rs, ip)

  val ip = InvProduct.syntax("ip")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvProduct] = {
    sql"""select ${ip.result.*} from ${InvProduct as ip} where ${ip.id} = ${id}"""
      .map(InvProduct(ip.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProduct] = {
    sql"""select ${ip.result.*} from ${InvProduct as ip}""".map(InvProduct(ip.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProduct.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProduct] = {
    sql"""select ${ip.result.*} from ${InvProduct as ip} where ${where}"""
      .map(InvProduct(ip.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProduct] = {
    sql"""select ${ip.result.*} from ${InvProduct as ip} where ${where}"""
      .map(InvProduct(ip.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProduct as ip} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    accountid: Int,
    productspecid: Int,
    timefrom: DateTime,
    timeto: Option[DateTime] = None,
    activationmodeid: Int,
    activationtime: DateTime,
    activationprice: Option[BigDecimal] = None,
    deactivationtime: Option[DateTime] = None,
    userid: Int,
    deviceproductid: Option[String] = None,
    devicestate: Byte,
    comment: String,
    description: String)(implicit session: DBSession = autoSession): InvProduct = {
    val generatedKey = sql"""
      insert into ${InvProduct.table} (
        ${column.contractid},
        ${column.accountid},
        ${column.productspecid},
        ${column.timefrom},
        ${column.timeto},
        ${column.activationmodeid},
        ${column.activationtime},
        ${column.activationprice},
        ${column.deactivationtime},
        ${column.userid},
        ${column.deviceproductid},
        ${column.devicestate},
        ${column.comment},
        ${column.description}
      ) values (
        ${contractid},
        ${accountid},
        ${productspecid},
        ${timefrom},
        ${timeto},
        ${activationmodeid},
        ${activationtime},
        ${activationprice},
        ${deactivationtime},
        ${userid},
        ${deviceproductid},
        ${devicestate},
        ${comment},
        ${description}
      )
      """.updateAndReturnGeneratedKey.apply()

    InvProduct(
      id = generatedKey.toInt,
      contractid = contractid,
      accountid = accountid,
      productspecid = productspecid,
      timefrom = timefrom,
      timeto = timeto,
      activationmodeid = activationmodeid,
      activationtime = activationtime,
      activationprice = activationprice,
      deactivationtime = deactivationtime,
      userid = userid,
      deviceproductid = deviceproductid,
      devicestate = devicestate,
      comment = comment,
      description = description)
  }

  def batchInsert(entities: collection.Seq[InvProduct])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'accountid -> entity.accountid,
        'productspecid -> entity.productspecid,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'activationmodeid -> entity.activationmodeid,
        'activationtime -> entity.activationtime,
        'activationprice -> entity.activationprice,
        'deactivationtime -> entity.deactivationtime,
        'userid -> entity.userid,
        'deviceproductid -> entity.deviceproductid,
        'devicestate -> entity.devicestate,
        'comment -> entity.comment,
        'description -> entity.description))
    SQL("""insert into inv_product(
      contractId,
      accountId,
      productSpecId,
      timeFrom,
      timeTo,
      activationModeId,
      activationTime,
      activationPrice,
      deactivationTime,
      userId,
      deviceProductId,
      deviceState,
      comment,
      description
    ) values (
      {contractid},
      {accountid},
      {productspecid},
      {timefrom},
      {timeto},
      {activationmodeid},
      {activationtime},
      {activationprice},
      {deactivationtime},
      {userid},
      {deviceproductid},
      {devicestate},
      {comment},
      {description}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvProduct)(implicit session: DBSession = autoSession): InvProduct = {
    sql"""
      update
        ${InvProduct.table}
      set
        ${column.id} = ${entity.id},
        ${column.contractid} = ${entity.contractid},
        ${column.accountid} = ${entity.accountid},
        ${column.productspecid} = ${entity.productspecid},
        ${column.timefrom} = ${entity.timefrom},
        ${column.timeto} = ${entity.timeto},
        ${column.activationmodeid} = ${entity.activationmodeid},
        ${column.activationtime} = ${entity.activationtime},
        ${column.activationprice} = ${entity.activationprice},
        ${column.deactivationtime} = ${entity.deactivationtime},
        ${column.userid} = ${entity.userid},
        ${column.deviceproductid} = ${entity.deviceproductid},
        ${column.devicestate} = ${entity.devicestate},
        ${column.comment} = ${entity.comment},
        ${column.description} = ${entity.description}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: InvProduct)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${InvProduct.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
