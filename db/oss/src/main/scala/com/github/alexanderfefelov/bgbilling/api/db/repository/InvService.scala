package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvService(
  id: Int,
  contractid: Int,
  accountid: Int,
  productid: Int,
  servicespecid: Int,
  timefrom: DateTime,
  timeto: Option[DateTime] = None,
  devicestate: Byte) {

  def save()(implicit session: DBSession = InvService.autoSession): InvService = InvService.save(this)(session)

  def destroy()(implicit session: DBSession = InvService.autoSession): Int = InvService.destroy(this)(session)

}


object InvService extends SQLSyntaxSupport[InvService] {

  override val tableName = "inv_service"

  override val columns = Seq("id", "contractId", "accountId", "productId", "serviceSpecId", "timeFrom", "timeTo", "deviceState")

  def apply(is: SyntaxProvider[InvService])(rs: WrappedResultSet): InvService = autoConstruct(rs, is)
  def apply(is: ResultName[InvService])(rs: WrappedResultSet): InvService = autoConstruct(rs, is)

  val is = InvService.syntax("is")

  override val autoSession = AutoSession

  def find(contractid: Int, id: Int)(implicit session: DBSession = autoSession): Option[InvService] = {
    sql"""select ${is.result.*} from ${InvService as is} where ${is.contractid} = ${contractid} and ${is.id} = ${id}"""
      .map(InvService(is.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvService] = {
    sql"""select ${is.result.*} from ${InvService as is}""".map(InvService(is.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvService.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvService] = {
    sql"""select ${is.result.*} from ${InvService as is} where ${where}"""
      .map(InvService(is.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvService] = {
    sql"""select ${is.result.*} from ${InvService as is} where ${where}"""
      .map(InvService(is.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvService as is} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    accountid: Int,
    productid: Int,
    servicespecid: Int,
    timefrom: DateTime,
    timeto: Option[DateTime] = None,
    devicestate: Byte)(implicit session: DBSession = autoSession): InvService = {
    val generatedKey = sql"""
      insert into ${InvService.table} (
        ${column.contractid},
        ${column.accountid},
        ${column.productid},
        ${column.servicespecid},
        ${column.timefrom},
        ${column.timeto},
        ${column.devicestate}
      ) values (
        ${contractid},
        ${accountid},
        ${productid},
        ${servicespecid},
        ${timefrom},
        ${timeto},
        ${devicestate}
      )
      """.updateAndReturnGeneratedKey.apply()

    InvService(
      id = generatedKey.toInt,
      contractid = contractid,
      accountid = accountid,
      productid = productid,
      servicespecid = servicespecid,
      timefrom = timefrom,
      timeto = timeto,
      devicestate = devicestate)
  }

  def batchInsert(entities: collection.Seq[InvService])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'accountid -> entity.accountid,
        'productid -> entity.productid,
        'servicespecid -> entity.servicespecid,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'devicestate -> entity.devicestate))
    SQL("""insert into inv_service(
      contractId,
      accountId,
      productId,
      serviceSpecId,
      timeFrom,
      timeTo,
      deviceState
    ) values (
      {contractid},
      {accountid},
      {productid},
      {servicespecid},
      {timefrom},
      {timeto},
      {devicestate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvService)(implicit session: DBSession = autoSession): InvService = {
    sql"""
      update
        ${InvService.table}
      set
        ${column.id} = ${entity.id},
        ${column.contractid} = ${entity.contractid},
        ${column.accountid} = ${entity.accountid},
        ${column.productid} = ${entity.productid},
        ${column.servicespecid} = ${entity.servicespecid},
        ${column.timefrom} = ${entity.timefrom},
        ${column.timeto} = ${entity.timeto},
        ${column.devicestate} = ${entity.devicestate}
      where
        ${column.contractid} = ${entity.contractid} and ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: InvService)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${InvService.table} where ${column.contractid} = ${entity.contractid} and ${column.id} = ${entity.id}""".update.apply()
  }

}
