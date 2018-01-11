package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

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


object InetConnection1 extends SQLSyntaxSupport[InetConnection1] {

  override val tableName = "inet_connection_1"

  override val columns = Seq("id", "parentId", "deviceId", "devicePort", "agentDeviceId", "circuitId", "acctSessionId", "username", "type", "accessCode", "servId", "calledStationId", "callingStationId", "ipResourceId", "ipAddress", "connectionStart", "deviceState", "deviceOptions", "status")

  def apply(ic: SyntaxProvider[InetConnection1])(rs: WrappedResultSet): InetConnection1 = autoConstruct(rs, ic)
  def apply(ic: ResultName[InetConnection1])(rs: WrappedResultSet): InetConnection1 = autoConstruct(rs, ic)

  val ic = InetConnection1.syntax("ic")

  override val autoSession = AutoSession

  def find(id: Long, parentid: Long, deviceid: Int, deviceport: Int, agentdeviceid: Int, circuitid: Option[String], acctsessionid: Option[String], username: Option[String], `type`: Int, accesscode: Short, servid: Int, calledstationid: Option[String], callingstationid: Option[String], ipresourceid: Int, ipaddress: Option[Array[Byte]], connectionstart: DateTime, devicestate: Short, deviceoptions: String, status: Short)(implicit session: DBSession = autoSession): Option[InetConnection1] = {
    withSQL {
      select.from(InetConnection1 as ic).where.eq(ic.id, id).and.eq(ic.parentid, parentid).and.eq(ic.deviceid, deviceid).and.eq(ic.deviceport, deviceport).and.eq(ic.agentdeviceid, agentdeviceid).and.eq(ic.circuitid, circuitid).and.eq(ic.acctsessionid, acctsessionid).and.eq(ic.username, username).and.eq(ic.`type`, `type`).and.eq(ic.accesscode, accesscode).and.eq(ic.servid, servid).and.eq(ic.calledstationid, calledstationid).and.eq(ic.callingstationid, callingstationid).and.eq(ic.ipresourceid, ipresourceid).and.eq(ic.ipaddress, ipaddress).and.eq(ic.connectionstart, connectionstart).and.eq(ic.devicestate, devicestate).and.eq(ic.deviceoptions, deviceoptions).and.eq(ic.status, status)
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
    val generatedKey = withSQL {
      insert.into(InetConnection1).namedValues(
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
    }.updateAndReturnGeneratedKey.apply()

    InetConnection1(
      id = generatedKey,
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
      ).where.eq(column.id, entity.id).and.eq(column.parentid, entity.parentid).and.eq(column.deviceid, entity.deviceid).and.eq(column.deviceport, entity.deviceport).and.eq(column.agentdeviceid, entity.agentdeviceid).and.eq(column.circuitid, entity.circuitid).and.eq(column.acctsessionid, entity.acctsessionid).and.eq(column.username, entity.username).and.eq(column.`type`, entity.`type`).and.eq(column.accesscode, entity.accesscode).and.eq(column.servid, entity.servid).and.eq(column.calledstationid, entity.calledstationid).and.eq(column.callingstationid, entity.callingstationid).and.eq(column.ipresourceid, entity.ipresourceid).and.eq(column.ipaddress, entity.ipaddress).and.eq(column.connectionstart, entity.connectionstart).and.eq(column.devicestate, entity.devicestate).and.eq(column.deviceoptions, entity.deviceoptions).and.eq(column.status, entity.status)
    }.update.apply()
    entity
  }

  def destroy(entity: InetConnection1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetConnection1).where.eq(column.id, entity.id).and.eq(column.parentid, entity.parentid).and.eq(column.deviceid, entity.deviceid).and.eq(column.deviceport, entity.deviceport).and.eq(column.agentdeviceid, entity.agentdeviceid).and.eq(column.circuitid, entity.circuitid).and.eq(column.acctsessionid, entity.acctsessionid).and.eq(column.username, entity.username).and.eq(column.`type`, entity.`type`).and.eq(column.accesscode, entity.accesscode).and.eq(column.servid, entity.servid).and.eq(column.calledstationid, entity.calledstationid).and.eq(column.callingstationid, entity.callingstationid).and.eq(column.ipresourceid, entity.ipresourceid).and.eq(column.ipaddress, entity.ipaddress).and.eq(column.connectionstart, entity.connectionstart).and.eq(column.devicestate, entity.devicestate).and.eq(column.deviceoptions, entity.deviceoptions).and.eq(column.status, entity.status) }.update.apply()
  }

}
