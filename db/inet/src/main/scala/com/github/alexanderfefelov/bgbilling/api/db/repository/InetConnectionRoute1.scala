package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetConnectionRoute1(
  id: Int,
  connectionid: Long,
  subnet: Array[Byte],
  mask: Int) {

  def save()(implicit session: DBSession = InetConnectionRoute1.autoSession): InetConnectionRoute1 = InetConnectionRoute1.save(this)(session)

  def destroy()(implicit session: DBSession = InetConnectionRoute1.autoSession): Int = InetConnectionRoute1.destroy(this)(session)

}


object InetConnectionRoute1 extends SQLSyntaxSupport[InetConnectionRoute1] with ApiDbConfig {

  override val tableName = s"inet_connection_route_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "connectionId", "subnet", "mask")

  def apply(icr: SyntaxProvider[InetConnectionRoute1])(rs: WrappedResultSet): InetConnectionRoute1 = autoConstruct(rs, icr)
  def apply(icr: ResultName[InetConnectionRoute1])(rs: WrappedResultSet): InetConnectionRoute1 = autoConstruct(rs, icr)

  val icr = InetConnectionRoute1.syntax("icr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetConnectionRoute1] = {
    withSQL {
      select.from(InetConnectionRoute1 as icr).where.eq(icr.id, id)
    }.map(InetConnectionRoute1(icr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetConnectionRoute1] = {
    withSQL(select.from(InetConnectionRoute1 as icr)).map(InetConnectionRoute1(icr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetConnectionRoute1 as icr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetConnectionRoute1] = {
    withSQL {
      select.from(InetConnectionRoute1 as icr).where.append(where)
    }.map(InetConnectionRoute1(icr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetConnectionRoute1] = {
    withSQL {
      select.from(InetConnectionRoute1 as icr).where.append(where)
    }.map(InetConnectionRoute1(icr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetConnectionRoute1 as icr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    connectionid: Long,
    subnet: Array[Byte],
    mask: Int)(implicit session: DBSession = autoSession): InetConnectionRoute1 = {
    val generatedKey = withSQL {
      insert.into(InetConnectionRoute1).namedValues(
        column.connectionid -> connectionid,
        column.subnet -> subnet,
        column.mask -> mask
      )
    }.updateAndReturnGeneratedKey.apply()

    InetConnectionRoute1(
      id = generatedKey.toInt,
      connectionid = connectionid,
      subnet = subnet,
      mask = mask)
  }

  def batchInsert(entities: Seq[InetConnectionRoute1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'connectionid -> entity.connectionid,
        'subnet -> entity.subnet,
        'mask -> entity.mask))
    SQL("""insert into inet_connection_route_1(
      connectionId,
      subnet,
      mask
    ) values (
      {connectionid},
      {subnet},
      {mask}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetConnectionRoute1)(implicit session: DBSession = autoSession): InetConnectionRoute1 = {
    withSQL {
      update(InetConnectionRoute1).set(
        column.id -> entity.id,
        column.connectionid -> entity.connectionid,
        column.subnet -> entity.subnet,
        column.mask -> entity.mask
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetConnectionRoute1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetConnectionRoute1).where.eq(column.id, entity.id) }.update.apply()
  }

}
