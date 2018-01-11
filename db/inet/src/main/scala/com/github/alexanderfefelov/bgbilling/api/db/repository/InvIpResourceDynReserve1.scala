package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InvIpResourceDynReserve1(
  address: Array[Byte],
  timeout: Long,
  ipresourceid: Int) {

  def save()(implicit session: DBSession = InvIpResourceDynReserve1.autoSession): InvIpResourceDynReserve1 = InvIpResourceDynReserve1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpResourceDynReserve1.autoSession): Int = InvIpResourceDynReserve1.destroy(this)(session)

}


object InvIpResourceDynReserve1 extends SQLSyntaxSupport[InvIpResourceDynReserve1] {

  override val tableName = "inv_ip_resource_dyn_reserve_1"

  override val columns = Seq("address", "timeout", "ipResourceId")

  def apply(iirdr: SyntaxProvider[InvIpResourceDynReserve1])(rs: WrappedResultSet): InvIpResourceDynReserve1 = autoConstruct(rs, iirdr)
  def apply(iirdr: ResultName[InvIpResourceDynReserve1])(rs: WrappedResultSet): InvIpResourceDynReserve1 = autoConstruct(rs, iirdr)

  val iirdr = InvIpResourceDynReserve1.syntax("iirdr")

  override val autoSession = AutoSession

  def find(address: Array[Byte], timeout: Long, ipresourceid: Int)(implicit session: DBSession = autoSession): Option[InvIpResourceDynReserve1] = {
    withSQL {
      select.from(InvIpResourceDynReserve1 as iirdr).where.eq(iirdr.address, address).and.eq(iirdr.timeout, timeout).and.eq(iirdr.ipresourceid, ipresourceid)
    }.map(InvIpResourceDynReserve1(iirdr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpResourceDynReserve1] = {
    withSQL(select.from(InvIpResourceDynReserve1 as iirdr)).map(InvIpResourceDynReserve1(iirdr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpResourceDynReserve1 as iirdr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpResourceDynReserve1] = {
    withSQL {
      select.from(InvIpResourceDynReserve1 as iirdr).where.append(where)
    }.map(InvIpResourceDynReserve1(iirdr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpResourceDynReserve1] = {
    withSQL {
      select.from(InvIpResourceDynReserve1 as iirdr).where.append(where)
    }.map(InvIpResourceDynReserve1(iirdr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpResourceDynReserve1 as iirdr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    address: Array[Byte],
    timeout: Long,
    ipresourceid: Int)(implicit session: DBSession = autoSession): InvIpResourceDynReserve1 = {
    withSQL {
      insert.into(InvIpResourceDynReserve1).namedValues(
        column.address -> address,
        column.timeout -> timeout,
        column.ipresourceid -> ipresourceid
      )
    }.update.apply()

    InvIpResourceDynReserve1(
      address = address,
      timeout = timeout,
      ipresourceid = ipresourceid)
  }

  def batchInsert(entities: Seq[InvIpResourceDynReserve1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'address -> entity.address,
        'timeout -> entity.timeout,
        'ipresourceid -> entity.ipresourceid))
    SQL("""insert into inv_ip_resource_dyn_reserve_1(
      address,
      timeout,
      ipResourceId
    ) values (
      {address},
      {timeout},
      {ipresourceid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpResourceDynReserve1)(implicit session: DBSession = autoSession): InvIpResourceDynReserve1 = {
    withSQL {
      update(InvIpResourceDynReserve1).set(
        column.address -> entity.address,
        column.timeout -> entity.timeout,
        column.ipresourceid -> entity.ipresourceid
      ).where.eq(column.address, entity.address).and.eq(column.timeout, entity.timeout).and.eq(column.ipresourceid, entity.ipresourceid)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpResourceDynReserve1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpResourceDynReserve1).where.eq(column.address, entity.address).and.eq(column.timeout, entity.timeout).and.eq(column.ipresourceid, entity.ipresourceid) }.update.apply()
  }

}
