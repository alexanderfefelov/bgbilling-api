package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvProductPeriod(
  id: Long,
  contractid: Int,
  accountid: Int,
  productspecid: Int,
  productid: Int,
  activationtime: DateTime,
  timefrom: DateTime,
  timeto: Option[DateTime] = None,
  prolongationtime: DateTime,
  flags: Int,
  version: Int) {

  def save()(implicit session: DBSession = InvProductPeriod.autoSession): InvProductPeriod = InvProductPeriod.save(this)(session)

  def destroy()(implicit session: DBSession = InvProductPeriod.autoSession): Int = InvProductPeriod.destroy(this)(session)

}


object InvProductPeriod extends SQLSyntaxSupport[InvProductPeriod] {

  override val tableName = "inv_product_period"

  override val columns = Seq("id", "contractId", "accountId", "productSpecId", "productId", "activationTime", "timeFrom", "timeTo", "prolongationTime", "flags", "version")

  def apply(ipp: SyntaxProvider[InvProductPeriod])(rs: WrappedResultSet): InvProductPeriod = autoConstruct(rs, ipp)
  def apply(ipp: ResultName[InvProductPeriod])(rs: WrappedResultSet): InvProductPeriod = autoConstruct(rs, ipp)

  val ipp = InvProductPeriod.syntax("ipp")

  override val autoSession = AutoSession

  def find(id: Long, contractid: Int, accountid: Int, productspecid: Int, productid: Int, activationtime: DateTime, timefrom: DateTime, timeto: Option[DateTime], prolongationtime: DateTime, flags: Int, version: Int)(implicit session: DBSession = autoSession): Option[InvProductPeriod] = {
    sql"""select ${ipp.result.*} from ${InvProductPeriod as ipp} where ${ipp.id} = ${id} and ${ipp.contractid} = ${contractid} and ${ipp.accountid} = ${accountid} and ${ipp.productspecid} = ${productspecid} and ${ipp.productid} = ${productid} and ${ipp.activationtime} = ${activationtime} and ${ipp.timefrom} = ${timefrom} and ${ipp.timeto} = ${timeto} and ${ipp.prolongationtime} = ${prolongationtime} and ${ipp.flags} = ${flags} and ${ipp.version} = ${version}"""
      .map(InvProductPeriod(ipp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProductPeriod] = {
    sql"""select ${ipp.result.*} from ${InvProductPeriod as ipp}""".map(InvProductPeriod(ipp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProductPeriod.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProductPeriod] = {
    sql"""select ${ipp.result.*} from ${InvProductPeriod as ipp} where ${where}"""
      .map(InvProductPeriod(ipp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProductPeriod] = {
    sql"""select ${ipp.result.*} from ${InvProductPeriod as ipp} where ${where}"""
      .map(InvProductPeriod(ipp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProductPeriod as ipp} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    accountid: Int,
    productspecid: Int,
    productid: Int,
    activationtime: DateTime,
    timefrom: DateTime,
    timeto: Option[DateTime] = None,
    prolongationtime: DateTime,
    flags: Int,
    version: Int)(implicit session: DBSession = autoSession): InvProductPeriod = {
    val generatedKey = sql"""
      insert into ${InvProductPeriod.table} (
        ${column.contractid},
        ${column.accountid},
        ${column.productspecid},
        ${column.productid},
        ${column.activationtime},
        ${column.timefrom},
        ${column.timeto},
        ${column.prolongationtime},
        ${column.flags},
        ${column.version}
      ) values (
        ${contractid},
        ${accountid},
        ${productspecid},
        ${productid},
        ${activationtime},
        ${timefrom},
        ${timeto},
        ${prolongationtime},
        ${flags},
        ${version}
      )
      """.updateAndReturnGeneratedKey.apply()

    InvProductPeriod(
      id = generatedKey,
      contractid = contractid,
      accountid = accountid,
      productspecid = productspecid,
      productid = productid,
      activationtime = activationtime,
      timefrom = timefrom,
      timeto = timeto,
      prolongationtime = prolongationtime,
      flags = flags,
      version = version)
  }

  def batchInsert(entities: collection.Seq[InvProductPeriod])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'accountid -> entity.accountid,
        'productspecid -> entity.productspecid,
        'productid -> entity.productid,
        'activationtime -> entity.activationtime,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'prolongationtime -> entity.prolongationtime,
        'flags -> entity.flags,
        'version -> entity.version))
    SQL("""insert into inv_product_period(
      contractId,
      accountId,
      productSpecId,
      productId,
      activationTime,
      timeFrom,
      timeTo,
      prolongationTime,
      flags,
      version
    ) values (
      {contractid},
      {accountid},
      {productspecid},
      {productid},
      {activationtime},
      {timefrom},
      {timeto},
      {prolongationtime},
      {flags},
      {version}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvProductPeriod)(implicit session: DBSession = autoSession): InvProductPeriod = {
    sql"""
      update
        ${InvProductPeriod.table}
      set
        ${column.id} = ${entity.id},
        ${column.contractid} = ${entity.contractid},
        ${column.accountid} = ${entity.accountid},
        ${column.productspecid} = ${entity.productspecid},
        ${column.productid} = ${entity.productid},
        ${column.activationtime} = ${entity.activationtime},
        ${column.timefrom} = ${entity.timefrom},
        ${column.timeto} = ${entity.timeto},
        ${column.prolongationtime} = ${entity.prolongationtime},
        ${column.flags} = ${entity.flags},
        ${column.version} = ${entity.version}
      where
        ${column.id} = ${entity.id} and ${column.contractid} = ${entity.contractid} and ${column.accountid} = ${entity.accountid} and ${column.productspecid} = ${entity.productspecid} and ${column.productid} = ${entity.productid} and ${column.activationtime} = ${entity.activationtime} and ${column.timefrom} = ${entity.timefrom} and ${column.timeto} = ${entity.timeto} and ${column.prolongationtime} = ${entity.prolongationtime} and ${column.flags} = ${entity.flags} and ${column.version} = ${entity.version}
      """.update.apply()
    entity
  }

  def destroy(entity: InvProductPeriod)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${InvProductPeriod.table} where ${column.id} = ${entity.id} and ${column.contractid} = ${entity.contractid} and ${column.accountid} = ${entity.accountid} and ${column.productspecid} = ${entity.productspecid} and ${column.productid} = ${entity.productid} and ${column.activationtime} = ${entity.activationtime} and ${column.timefrom} = ${entity.timefrom} and ${column.timeto} = ${entity.timeto} and ${column.prolongationtime} = ${entity.prolongationtime} and ${column.flags} = ${entity.flags} and ${column.version} = ${entity.version}""".update.apply()
  }

}
