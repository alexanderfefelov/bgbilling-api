package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InvDeviceGroupLink1(
  deviceid: Int,
  devicegroupid: Int) {

  def save()(implicit session: DBSession = InvDeviceGroupLink1.autoSession): InvDeviceGroupLink1 = InvDeviceGroupLink1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDeviceGroupLink1.autoSession): Int = InvDeviceGroupLink1.destroy(this)(session)

}


object InvDeviceGroupLink1 extends SQLSyntaxSupport[InvDeviceGroupLink1] {

  override val tableName = "inv_device_group_link_1"

  override val columns = Seq("deviceId", "deviceGroupId")

  def apply(idgl: SyntaxProvider[InvDeviceGroupLink1])(rs: WrappedResultSet): InvDeviceGroupLink1 = autoConstruct(rs, idgl)
  def apply(idgl: ResultName[InvDeviceGroupLink1])(rs: WrappedResultSet): InvDeviceGroupLink1 = autoConstruct(rs, idgl)

  val idgl = InvDeviceGroupLink1.syntax("idgl")

  override val autoSession = AutoSession

  def find(deviceid: Int, devicegroupid: Int)(implicit session: DBSession = autoSession): Option[InvDeviceGroupLink1] = {
    withSQL {
      select.from(InvDeviceGroupLink1 as idgl).where.eq(idgl.deviceid, deviceid).and.eq(idgl.devicegroupid, devicegroupid)
    }.map(InvDeviceGroupLink1(idgl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDeviceGroupLink1] = {
    withSQL(select.from(InvDeviceGroupLink1 as idgl)).map(InvDeviceGroupLink1(idgl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDeviceGroupLink1 as idgl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDeviceGroupLink1] = {
    withSQL {
      select.from(InvDeviceGroupLink1 as idgl).where.append(where)
    }.map(InvDeviceGroupLink1(idgl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDeviceGroupLink1] = {
    withSQL {
      select.from(InvDeviceGroupLink1 as idgl).where.append(where)
    }.map(InvDeviceGroupLink1(idgl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDeviceGroupLink1 as idgl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    devicegroupid: Int)(implicit session: DBSession = autoSession): InvDeviceGroupLink1 = {
    withSQL {
      insert.into(InvDeviceGroupLink1).namedValues(
        column.deviceid -> deviceid,
        column.devicegroupid -> devicegroupid
      )
    }.update.apply()

    InvDeviceGroupLink1(
      deviceid = deviceid,
      devicegroupid = devicegroupid)
  }

  def batchInsert(entities: Seq[InvDeviceGroupLink1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'devicegroupid -> entity.devicegroupid))
    SQL("""insert into inv_device_group_link_1(
      deviceId,
      deviceGroupId
    ) values (
      {deviceid},
      {devicegroupid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDeviceGroupLink1)(implicit session: DBSession = autoSession): InvDeviceGroupLink1 = {
    withSQL {
      update(InvDeviceGroupLink1).set(
        column.deviceid -> entity.deviceid,
        column.devicegroupid -> entity.devicegroupid
      ).where.eq(column.deviceid, entity.deviceid).and.eq(column.devicegroupid, entity.devicegroupid)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDeviceGroupLink1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDeviceGroupLink1).where.eq(column.deviceid, entity.deviceid).and.eq(column.devicegroupid, entity.devicegroupid) }.update.apply()
  }

}
