package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetDeviceTree1(
  id: Int,
  invdeviceid: String,
  parentid: Int,
  title: String,
  devicetypeid: Int,
  identifier: Option[String] = None,
  host: Option[String] = None,
  config: String) {

  def save()(implicit session: DBSession = InetDeviceTree1.autoSession): InetDeviceTree1 = InetDeviceTree1.save(this)(session)

  def destroy()(implicit session: DBSession = InetDeviceTree1.autoSession): Int = InetDeviceTree1.destroy(this)(session)

}


object InetDeviceTree1 extends SQLSyntaxSupport[InetDeviceTree1] with ApiDbConfig {

  override val tableName = s"inet_device_tree_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "invDeviceId", "parentId", "title", "deviceTypeId", "identifier", "host", "config")

  def apply(idt: SyntaxProvider[InetDeviceTree1])(rs: WrappedResultSet): InetDeviceTree1 = autoConstruct(rs, idt)
  def apply(idt: ResultName[InetDeviceTree1])(rs: WrappedResultSet): InetDeviceTree1 = autoConstruct(rs, idt)

  val idt = InetDeviceTree1.syntax("idt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetDeviceTree1] = {
    withSQL {
      select.from(InetDeviceTree1 as idt).where.eq(idt.id, id)
    }.map(InetDeviceTree1(idt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetDeviceTree1] = {
    withSQL(select.from(InetDeviceTree1 as idt)).map(InetDeviceTree1(idt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetDeviceTree1 as idt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetDeviceTree1] = {
    withSQL {
      select.from(InetDeviceTree1 as idt).where.append(where)
    }.map(InetDeviceTree1(idt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetDeviceTree1] = {
    withSQL {
      select.from(InetDeviceTree1 as idt).where.append(where)
    }.map(InetDeviceTree1(idt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetDeviceTree1 as idt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    invdeviceid: String,
    parentid: Int,
    title: String,
    devicetypeid: Int,
    identifier: Option[String] = None,
    host: Option[String] = None,
    config: String)(implicit session: DBSession = autoSession): InetDeviceTree1 = {
    val generatedKey = withSQL {
      insert.into(InetDeviceTree1).namedValues(
        column.invdeviceid -> invdeviceid,
        column.parentid -> parentid,
        column.title -> title,
        column.devicetypeid -> devicetypeid,
        column.identifier -> identifier,
        column.host -> host,
        column.config -> config
      )
    }.updateAndReturnGeneratedKey.apply()

    InetDeviceTree1(
      id = generatedKey.toInt,
      invdeviceid = invdeviceid,
      parentid = parentid,
      title = title,
      devicetypeid = devicetypeid,
      identifier = identifier,
      host = host,
      config = config)
  }

  def batchInsert(entities: Seq[InetDeviceTree1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'invdeviceid -> entity.invdeviceid,
        'parentid -> entity.parentid,
        'title -> entity.title,
        'devicetypeid -> entity.devicetypeid,
        'identifier -> entity.identifier,
        'host -> entity.host,
        'config -> entity.config))
    SQL("""insert into inet_device_tree_1(
      invDeviceId,
      parentId,
      title,
      deviceTypeId,
      identifier,
      host,
      config
    ) values (
      {invdeviceid},
      {parentid},
      {title},
      {devicetypeid},
      {identifier},
      {host},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetDeviceTree1)(implicit session: DBSession = autoSession): InetDeviceTree1 = {
    withSQL {
      update(InetDeviceTree1).set(
        column.id -> entity.id,
        column.invdeviceid -> entity.invdeviceid,
        column.parentid -> entity.parentid,
        column.title -> entity.title,
        column.devicetypeid -> entity.devicetypeid,
        column.identifier -> entity.identifier,
        column.host -> entity.host,
        column.config -> entity.config
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetDeviceTree1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetDeviceTree1).where.eq(column.id, entity.id) }.update.apply()
  }

}
