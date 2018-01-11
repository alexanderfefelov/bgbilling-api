package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InvDevicePort1(
  deviceid: Int,
  port: Int,
  title: String,
  status: Int,
  ipcategoryid: Int,
  comment: String) {

  def save()(implicit session: DBSession = InvDevicePort1.autoSession): InvDevicePort1 = InvDevicePort1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDevicePort1.autoSession): Int = InvDevicePort1.destroy(this)(session)

}


object InvDevicePort1 extends SQLSyntaxSupport[InvDevicePort1] with ApiDbConfig {

  override val tableName = s"inv_device_port_${bgBillingModuleId("inet")}"

  override val columns = Seq("deviceId", "port", "title", "status", "ipCategoryId", "comment")

  def apply(idp: SyntaxProvider[InvDevicePort1])(rs: WrappedResultSet): InvDevicePort1 = autoConstruct(rs, idp)
  def apply(idp: ResultName[InvDevicePort1])(rs: WrappedResultSet): InvDevicePort1 = autoConstruct(rs, idp)

  val idp = InvDevicePort1.syntax("idp")

  override val autoSession = AutoSession

  def find(deviceid: Int, port: Int)(implicit session: DBSession = autoSession): Option[InvDevicePort1] = {
    withSQL {
      select.from(InvDevicePort1 as idp).where.eq(idp.deviceid, deviceid).and.eq(idp.port, port)
    }.map(InvDevicePort1(idp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDevicePort1] = {
    withSQL(select.from(InvDevicePort1 as idp)).map(InvDevicePort1(idp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDevicePort1 as idp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDevicePort1] = {
    withSQL {
      select.from(InvDevicePort1 as idp).where.append(where)
    }.map(InvDevicePort1(idp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDevicePort1] = {
    withSQL {
      select.from(InvDevicePort1 as idp).where.append(where)
    }.map(InvDevicePort1(idp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDevicePort1 as idp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    port: Int,
    title: String,
    status: Int,
    ipcategoryid: Int,
    comment: String)(implicit session: DBSession = autoSession): InvDevicePort1 = {
    withSQL {
      insert.into(InvDevicePort1).namedValues(
        column.deviceid -> deviceid,
        column.port -> port,
        column.title -> title,
        column.status -> status,
        column.ipcategoryid -> ipcategoryid,
        column.comment -> comment
      )
    }.update.apply()

    InvDevicePort1(
      deviceid = deviceid,
      port = port,
      title = title,
      status = status,
      ipcategoryid = ipcategoryid,
      comment = comment)
  }

  def batchInsert(entities: Seq[InvDevicePort1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'port -> entity.port,
        'title -> entity.title,
        'status -> entity.status,
        'ipcategoryid -> entity.ipcategoryid,
        'comment -> entity.comment))
    SQL("""insert into inv_device_port_1(
      deviceId,
      port,
      title,
      status,
      ipCategoryId,
      comment
    ) values (
      {deviceid},
      {port},
      {title},
      {status},
      {ipcategoryid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDevicePort1)(implicit session: DBSession = autoSession): InvDevicePort1 = {
    withSQL {
      update(InvDevicePort1).set(
        column.deviceid -> entity.deviceid,
        column.port -> entity.port,
        column.title -> entity.title,
        column.status -> entity.status,
        column.ipcategoryid -> entity.ipcategoryid,
        column.comment -> entity.comment
      ).where.eq(column.deviceid, entity.deviceid).and.eq(column.port, entity.port)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDevicePort1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDevicePort1).where.eq(column.deviceid, entity.deviceid).and.eq(column.port, entity.port) }.update.apply()
  }

}
