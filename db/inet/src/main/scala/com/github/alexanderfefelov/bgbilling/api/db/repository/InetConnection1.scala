package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InetConnection1(
  id: Long,
  parentid: Long,
  deviceid: Int,
  deviceport: Int,
  agentdeviceid: Int,
  circuitid: Option[String] = None,
  acctsessionid: Option[String] = None,
  username: Option[String] = None,
  `type`: Int,
  accesscode: Short,
  servid: Int,
  calledstationid: Option[String] = None,
  callingstationid: Option[String] = None,
  ipresourceid: Int,
  ipaddress: Option[Array[Byte]] = None,
  connectionstart: DateTime,
  devicestate: Short,
  deviceoptions: String,
  status: Short) {

  def save()(implicit session: DBSession = InetConnection1.autoSession): InetConnection1 = InetConnection1.save(this)(session)

  def destroy()(implicit session: DBSession = InetConnection1.autoSession): Int = InetConnection1.destroy(this)(session)

}


object InetConnection1 extends SQLSyntaxSupport[InetConnection1] with ApiDbConfig {

  override val tableName = s"inet_connection_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "deviceId", "devicePort", "agentDeviceId", "circuitId", "acctSessionId", "username", "type", "accessCode", "servId", "calledStationId", "callingStationId", "ipResourceId", "ipAddress", "connectionStart", "deviceState", "deviceOptions", "status")

  def apply(ic: SyntaxProvider[InetConnection1])(rs: WrappedResultSet): InetConnection1 = autoConstruct(rs, ic)
  def apply(ic: ResultName[InetConnection1])(rs: WrappedResultSet): InetConnection1 = autoConstruct(rs, ic)

  val ic = InetConnection1.syntax("ic")

  override val autoSession = AutoSession

  def find(deviceid: Int, id: Long)(implicit session: DBSession = autoSession): Option[InetConnection1] = {
    withSQL {
      select.from(InetConnection1 as ic).where.eq(ic.deviceid, deviceid).and.eq(ic.id, id)
    }.map(InetConnection1(ic.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetConnection1] = {
    withSQL(select.from(InetConnection1 as ic)).map(InetConnection1(ic.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetConnection1 as ic)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetConnection1] = {
    withSQL {
      select.from(InetConnection1 as ic).where.append(where)
    }.map(InetConnection1(ic.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetConnection1] = {
    withSQL {
      select.from(InetConnection1 as ic).where.append(where)
    }.map(InetConnection1(ic.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetConnection1 as ic).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Long,
    parentid: Long,
    deviceid: Int,
    deviceport: Int,
    agentdeviceid: Int,
    circuitid: Option[String] = None,
    acctsessionid: Option[String] = None,
    username: Option[String] = None,
    `type`: Int,
    accesscode: Short,
    servid: Int,
    calledstationid: Option[String] = None,
    callingstationid: Option[String] = None,
    ipresourceid: Int,
    ipaddress: Option[Array[Byte]] = None,
    connectionstart: DateTime,
    devicestate: Short,
    deviceoptions: String,
    status: Short)(implicit session: DBSession = autoSession): InetConnection1 = {
    withSQL {
      insert.into(InetConnection1).namedValues(
        column.id -> id,
        column.parentid -> parentid,
        column.deviceid -> deviceid,
        column.deviceport -> deviceport,
        column.agentdeviceid -> agentdeviceid,
        column.circuitid -> circuitid,
        column.acctsessionid -> acctsessionid,
        column.username -> username,
        column.`type` -> `type`,
        column.accesscode -> accesscode,
        column.servid -> servid,
        column.calledstationid -> calledstationid,
        column.callingstationid -> callingstationid,
        column.ipresourceid -> ipresourceid,
        column.ipaddress -> ipaddress,
        column.connectionstart -> connectionstart,
        column.devicestate -> devicestate,
        column.deviceoptions -> deviceoptions,
        column.status -> status
      )
    }.update.apply()

    InetConnection1(
      id = id,
      parentid = parentid,
      deviceid = deviceid,
      deviceport = deviceport,
      agentdeviceid = agentdeviceid,
      circuitid = circuitid,
      acctsessionid = acctsessionid,
      username = username,
      `type` = `type`,
      accesscode = accesscode,
      servid = servid,
      calledstationid = calledstationid,
      callingstationid = callingstationid,
      ipresourceid = ipresourceid,
      ipaddress = ipaddress,
      connectionstart = connectionstart,
      devicestate = devicestate,
      deviceoptions = deviceoptions,
      status = status)
  }

  def batchInsert(entities: Seq[InetConnection1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'parentid -> entity.parentid,
        'deviceid -> entity.deviceid,
        'deviceport -> entity.deviceport,
        'agentdeviceid -> entity.agentdeviceid,
        'circuitid -> entity.circuitid,
        'acctsessionid -> entity.acctsessionid,
        'username -> entity.username,
        'type -> entity.`type`,
        'accesscode -> entity.accesscode,
        'servid -> entity.servid,
        'calledstationid -> entity.calledstationid,
        'callingstationid -> entity.callingstationid,
        'ipresourceid -> entity.ipresourceid,
        'ipaddress -> entity.ipaddress,
        'connectionstart -> entity.connectionstart,
        'devicestate -> entity.devicestate,
        'deviceoptions -> entity.deviceoptions,
        'status -> entity.status))
    SQL("""insert into inet_connection_1(
      id,
      parentId,
      deviceId,
      devicePort,
      agentDeviceId,
      circuitId,
      acctSessionId,
      username,
      type,
      accessCode,
      servId,
      calledStationId,
      callingStationId,
      ipResourceId,
      ipAddress,
      connectionStart,
      deviceState,
      deviceOptions,
      status
    ) values (
      {id},
      {parentid},
      {deviceid},
      {deviceport},
      {agentdeviceid},
      {circuitid},
      {acctsessionid},
      {username},
      {type},
      {accesscode},
      {servid},
      {calledstationid},
      {callingstationid},
      {ipresourceid},
      {ipaddress},
      {connectionstart},
      {devicestate},
      {deviceoptions},
      {status}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetConnection1)(implicit session: DBSession = autoSession): InetConnection1 = {
    withSQL {
      update(InetConnection1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.deviceid -> entity.deviceid,
        column.deviceport -> entity.deviceport,
        column.agentdeviceid -> entity.agentdeviceid,
        column.circuitid -> entity.circuitid,
        column.acctsessionid -> entity.acctsessionid,
        column.username -> entity.username,
        column.`type` -> entity.`type`,
        column.accesscode -> entity.accesscode,
        column.servid -> entity.servid,
        column.calledstationid -> entity.calledstationid,
        column.callingstationid -> entity.callingstationid,
        column.ipresourceid -> entity.ipresourceid,
        column.ipaddress -> entity.ipaddress,
        column.connectionstart -> entity.connectionstart,
        column.devicestate -> entity.devicestate,
        column.deviceoptions -> entity.deviceoptions,
        column.status -> entity.status
      ).where.eq(column.deviceid, entity.deviceid).and.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetConnection1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetConnection1).where.eq(column.deviceid, entity.deviceid).and.eq(column.id, entity.id) }.update.apply()
  }

}
