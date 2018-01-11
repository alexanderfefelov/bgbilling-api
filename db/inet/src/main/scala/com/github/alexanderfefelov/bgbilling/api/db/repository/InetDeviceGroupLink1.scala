package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetDeviceGroupLink1(
  deviceid: Int,
  devicegroupid: Int) {

  def save()(implicit session: DBSession = InetDeviceGroupLink1.autoSession): InetDeviceGroupLink1 = InetDeviceGroupLink1.save(this)(session)

  def destroy()(implicit session: DBSession = InetDeviceGroupLink1.autoSession): Int = InetDeviceGroupLink1.destroy(this)(session)

}


object InetDeviceGroupLink1 extends SQLSyntaxSupport[InetDeviceGroupLink1] {

  override val tableName = "inet_device_group_link_1"

  override val columns = Seq("deviceId", "deviceGroupId")

  def apply(idgl: SyntaxProvider[InetDeviceGroupLink1])(rs: WrappedResultSet): InetDeviceGroupLink1 = autoConstruct(rs, idgl)
  def apply(idgl: ResultName[InetDeviceGroupLink1])(rs: WrappedResultSet): InetDeviceGroupLink1 = autoConstruct(rs, idgl)

  val idgl = InetDeviceGroupLink1.syntax("idgl")

  override val autoSession = AutoSession

  def find(deviceid: Int, devicegroupid: Int)(implicit session: DBSession = autoSession): Option[InetDeviceGroupLink1] = {
    withSQL {
      select.from(InetDeviceGroupLink1 as idgl).where.eq(idgl.deviceid, deviceid).and.eq(idgl.devicegroupid, devicegroupid)
    }.map(InetDeviceGroupLink1(idgl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetDeviceGroupLink1] = {
    withSQL(select.from(InetDeviceGroupLink1 as idgl)).map(InetDeviceGroupLink1(idgl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetDeviceGroupLink1 as idgl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetDeviceGroupLink1] = {
    withSQL {
      select.from(InetDeviceGroupLink1 as idgl).where.append(where)
    }.map(InetDeviceGroupLink1(idgl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetDeviceGroupLink1] = {
    withSQL {
      select.from(InetDeviceGroupLink1 as idgl).where.append(where)
    }.map(InetDeviceGroupLink1(idgl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetDeviceGroupLink1 as idgl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    devicegroupid: Int)(implicit session: DBSession = autoSession): InetDeviceGroupLink1 = {
    withSQL {
      insert.into(InetDeviceGroupLink1).namedValues(
        column.deviceid -> deviceid,
        column.devicegroupid -> devicegroupid
      )
    }.update.apply()

    InetDeviceGroupLink1(
      deviceid = deviceid,
      devicegroupid = devicegroupid)
  }

  def batchInsert(entities: Seq[InetDeviceGroupLink1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'devicegroupid -> entity.devicegroupid))
    SQL("""insert into inet_device_group_link_1(
      deviceId,
      deviceGroupId
    ) values (
      {deviceid},
      {devicegroupid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetDeviceGroupLink1)(implicit session: DBSession = autoSession): InetDeviceGroupLink1 = {
    withSQL {
      update(InetDeviceGroupLink1).set(
        column.deviceid -> entity.deviceid,
        column.devicegroupid -> entity.devicegroupid
      ).where.eq(column.deviceid, entity.deviceid).and.eq(column.devicegroupid, entity.devicegroupid)
    }.update.apply()
    entity
  }

  def destroy(entity: InetDeviceGroupLink1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetDeviceGroupLink1).where.eq(column.deviceid, entity.deviceid).and.eq(column.devicegroupid, entity.devicegroupid) }.update.apply()
  }

}
