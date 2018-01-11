package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetServTypeDeviceGroupLink1(
  inetservid: Int,
  devicegroupid: Int) {

  def save()(implicit session: DBSession = InetServTypeDeviceGroupLink1.autoSession): InetServTypeDeviceGroupLink1 = InetServTypeDeviceGroupLink1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServTypeDeviceGroupLink1.autoSession): Int = InetServTypeDeviceGroupLink1.destroy(this)(session)

}


object InetServTypeDeviceGroupLink1 extends SQLSyntaxSupport[InetServTypeDeviceGroupLink1] {

  override val tableName = "inet_serv_type_device_group_link_1"

  override val columns = Seq("inetServId", "deviceGroupId")

  def apply(istdgl: SyntaxProvider[InetServTypeDeviceGroupLink1])(rs: WrappedResultSet): InetServTypeDeviceGroupLink1 = autoConstruct(rs, istdgl)
  def apply(istdgl: ResultName[InetServTypeDeviceGroupLink1])(rs: WrappedResultSet): InetServTypeDeviceGroupLink1 = autoConstruct(rs, istdgl)

  val istdgl = InetServTypeDeviceGroupLink1.syntax("istdgl")

  override val autoSession = AutoSession

  def find(inetservid: Int, devicegroupid: Int)(implicit session: DBSession = autoSession): Option[InetServTypeDeviceGroupLink1] = {
    withSQL {
      select.from(InetServTypeDeviceGroupLink1 as istdgl).where.eq(istdgl.inetservid, inetservid).and.eq(istdgl.devicegroupid, devicegroupid)
    }.map(InetServTypeDeviceGroupLink1(istdgl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServTypeDeviceGroupLink1] = {
    withSQL(select.from(InetServTypeDeviceGroupLink1 as istdgl)).map(InetServTypeDeviceGroupLink1(istdgl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServTypeDeviceGroupLink1 as istdgl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServTypeDeviceGroupLink1] = {
    withSQL {
      select.from(InetServTypeDeviceGroupLink1 as istdgl).where.append(where)
    }.map(InetServTypeDeviceGroupLink1(istdgl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServTypeDeviceGroupLink1] = {
    withSQL {
      select.from(InetServTypeDeviceGroupLink1 as istdgl).where.append(where)
    }.map(InetServTypeDeviceGroupLink1(istdgl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServTypeDeviceGroupLink1 as istdgl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    inetservid: Int,
    devicegroupid: Int)(implicit session: DBSession = autoSession): InetServTypeDeviceGroupLink1 = {
    withSQL {
      insert.into(InetServTypeDeviceGroupLink1).namedValues(
        column.inetservid -> inetservid,
        column.devicegroupid -> devicegroupid
      )
    }.update.apply()

    InetServTypeDeviceGroupLink1(
      inetservid = inetservid,
      devicegroupid = devicegroupid)
  }

  def batchInsert(entities: Seq[InetServTypeDeviceGroupLink1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'inetservid -> entity.inetservid,
        'devicegroupid -> entity.devicegroupid))
    SQL("""insert into inet_serv_type_device_group_link_1(
      inetServId,
      deviceGroupId
    ) values (
      {inetservid},
      {devicegroupid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServTypeDeviceGroupLink1)(implicit session: DBSession = autoSession): InetServTypeDeviceGroupLink1 = {
    withSQL {
      update(InetServTypeDeviceGroupLink1).set(
        column.inetservid -> entity.inetservid,
        column.devicegroupid -> entity.devicegroupid
      ).where.eq(column.inetservid, entity.inetservid).and.eq(column.devicegroupid, entity.devicegroupid)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServTypeDeviceGroupLink1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServTypeDeviceGroupLink1).where.eq(column.inetservid, entity.inetservid).and.eq(column.devicegroupid, entity.devicegroupid) }.update.apply()
  }

}
