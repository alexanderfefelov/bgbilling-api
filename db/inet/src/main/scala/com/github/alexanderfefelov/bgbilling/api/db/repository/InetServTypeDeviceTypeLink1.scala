package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetServTypeDeviceTypeLink1(
  inetservid: Int,
  devicetypeid: Int) {

  def save()(implicit session: DBSession = InetServTypeDeviceTypeLink1.autoSession): InetServTypeDeviceTypeLink1 = InetServTypeDeviceTypeLink1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServTypeDeviceTypeLink1.autoSession): Int = InetServTypeDeviceTypeLink1.destroy(this)(session)

}


object InetServTypeDeviceTypeLink1 extends SQLSyntaxSupport[InetServTypeDeviceTypeLink1] {

  override val tableName = "inet_serv_type_device_type_link_1"

  override val columns = Seq("inetServId", "deviceTypeId")

  def apply(istdtl: SyntaxProvider[InetServTypeDeviceTypeLink1])(rs: WrappedResultSet): InetServTypeDeviceTypeLink1 = autoConstruct(rs, istdtl)
  def apply(istdtl: ResultName[InetServTypeDeviceTypeLink1])(rs: WrappedResultSet): InetServTypeDeviceTypeLink1 = autoConstruct(rs, istdtl)

  val istdtl = InetServTypeDeviceTypeLink1.syntax("istdtl")

  override val autoSession = AutoSession

  def find(inetservid: Int, devicetypeid: Int)(implicit session: DBSession = autoSession): Option[InetServTypeDeviceTypeLink1] = {
    withSQL {
      select.from(InetServTypeDeviceTypeLink1 as istdtl).where.eq(istdtl.inetservid, inetservid).and.eq(istdtl.devicetypeid, devicetypeid)
    }.map(InetServTypeDeviceTypeLink1(istdtl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServTypeDeviceTypeLink1] = {
    withSQL(select.from(InetServTypeDeviceTypeLink1 as istdtl)).map(InetServTypeDeviceTypeLink1(istdtl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServTypeDeviceTypeLink1 as istdtl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServTypeDeviceTypeLink1] = {
    withSQL {
      select.from(InetServTypeDeviceTypeLink1 as istdtl).where.append(where)
    }.map(InetServTypeDeviceTypeLink1(istdtl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServTypeDeviceTypeLink1] = {
    withSQL {
      select.from(InetServTypeDeviceTypeLink1 as istdtl).where.append(where)
    }.map(InetServTypeDeviceTypeLink1(istdtl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServTypeDeviceTypeLink1 as istdtl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    inetservid: Int,
    devicetypeid: Int)(implicit session: DBSession = autoSession): InetServTypeDeviceTypeLink1 = {
    withSQL {
      insert.into(InetServTypeDeviceTypeLink1).namedValues(
        column.inetservid -> inetservid,
        column.devicetypeid -> devicetypeid
      )
    }.update.apply()

    InetServTypeDeviceTypeLink1(
      inetservid = inetservid,
      devicetypeid = devicetypeid)
  }

  def batchInsert(entities: Seq[InetServTypeDeviceTypeLink1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'inetservid -> entity.inetservid,
        'devicetypeid -> entity.devicetypeid))
    SQL("""insert into inet_serv_type_device_type_link_1(
      inetServId,
      deviceTypeId
    ) values (
      {inetservid},
      {devicetypeid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServTypeDeviceTypeLink1)(implicit session: DBSession = autoSession): InetServTypeDeviceTypeLink1 = {
    withSQL {
      update(InetServTypeDeviceTypeLink1).set(
        column.inetservid -> entity.inetservid,
        column.devicetypeid -> entity.devicetypeid
      ).where.eq(column.inetservid, entity.inetservid).and.eq(column.devicetypeid, entity.devicetypeid)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServTypeDeviceTypeLink1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServTypeDeviceTypeLink1).where.eq(column.inetservid, entity.inetservid).and.eq(column.devicetypeid, entity.devicetypeid) }.update.apply()
  }

}
