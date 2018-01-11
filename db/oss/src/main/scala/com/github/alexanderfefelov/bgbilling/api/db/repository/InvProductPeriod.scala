package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

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
    withSQL {
      select.from(InvProductPeriod as ipp).where.eq(ipp.id, id).and.eq(ipp.contractid, contractid).and.eq(ipp.accountid, accountid).and.eq(ipp.productspecid, productspecid).and.eq(ipp.productid, productid).and.eq(ipp.activationtime, activationtime).and.eq(ipp.timefrom, timefrom).and.eq(ipp.timeto, timeto).and.eq(ipp.prolongationtime, prolongationtime).and.eq(ipp.flags, flags).and.eq(ipp.version, version)
    }.map(InvProductPeriod(ipp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProductPeriod] = {
    withSQL(select.from(InvProductPeriod as ipp)).map(InvProductPeriod(ipp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvProductPeriod as ipp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProductPeriod] = {
    withSQL {
      select.from(InvProductPeriod as ipp).where.append(where)
    }.map(InvProductPeriod(ipp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProductPeriod] = {
    withSQL {
      select.from(InvProductPeriod as ipp).where.append(where)
    }.map(InvProductPeriod(ipp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvProductPeriod as ipp).where.append(where)
    }.map(_.long(1)).single.apply().get
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
    val generatedKey = withSQL {
      insert.into(InvProductPeriod).namedValues(
        column.contractid -> contractid,
        column.accountid -> accountid,
        column.productspecid -> productspecid,
        column.productid -> productid,
        column.activationtime -> activationtime,
        column.timefrom -> timefrom,
        column.timeto -> timeto,
        column.prolongationtime -> prolongationtime,
        column.flags -> flags,
        column.version -> version
      )
    }.updateAndReturnGeneratedKey.apply()

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

  def batchInsert(entities: Seq[InvProductPeriod])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
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
    withSQL {
      update(InvProductPeriod).set(
        column.id -> entity.id,
        column.contractid -> entity.contractid,
        column.accountid -> entity.accountid,
        column.productspecid -> entity.productspecid,
        column.productid -> entity.productid,
        column.activationtime -> entity.activationtime,
        column.timefrom -> entity.timefrom,
        column.timeto -> entity.timeto,
        column.prolongationtime -> entity.prolongationtime,
        column.flags -> entity.flags,
        column.version -> entity.version
      ).where.eq(column.id, entity.id).and.eq(column.contractid, entity.contractid).and.eq(column.accountid, entity.accountid).and.eq(column.productspecid, entity.productspecid).and.eq(column.productid, entity.productid).and.eq(column.activationtime, entity.activationtime).and.eq(column.timefrom, entity.timefrom).and.eq(column.timeto, entity.timeto).and.eq(column.prolongationtime, entity.prolongationtime).and.eq(column.flags, entity.flags).and.eq(column.version, entity.version)
    }.update.apply()
    entity
  }

  def destroy(entity: InvProductPeriod)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvProductPeriod).where.eq(column.id, entity.id).and.eq(column.contractid, entity.contractid).and.eq(column.accountid, entity.accountid).and.eq(column.productspecid, entity.productspecid).and.eq(column.productid, entity.productid).and.eq(column.activationtime, entity.activationtime).and.eq(column.timefrom, entity.timefrom).and.eq(column.timeto, entity.timeto).and.eq(column.prolongationtime, entity.prolongationtime).and.eq(column.flags, entity.flags).and.eq(column.version, entity.version) }.update.apply()
  }

}
