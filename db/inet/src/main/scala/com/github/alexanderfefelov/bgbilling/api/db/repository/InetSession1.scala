package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}

case class InetSession1(
  id: Long,
  parentid: Long,
  splittedid: Long,
  connectionid: Long,
  sessionstart: DateTime,
  sessionstop: Option[DateTime] = None,
  lastactive: DateTime,
  sessiontime: Long,
  sessioncost: BigDecimal,
  devicestate: Short,
  status: Short) {

  def save()(implicit session: DBSession = InetSession1.autoSession): InetSession1 = InetSession1.save(this)(session)

  def destroy()(implicit session: DBSession = InetSession1.autoSession): Int = InetSession1.destroy(this)(session)

}


object InetSession1 extends SQLSyntaxSupport[InetSession1] with ApiDbConfig {

  override val tableName = s"inet_session_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "splittedId", "connectionId", "sessionStart", "sessionStop", "lastActive", "sessionTime", "sessionCost", "deviceState", "status")

  def apply(is: SyntaxProvider[InetSession1])(rs: WrappedResultSet): InetSession1 = autoConstruct(rs, is)
  def apply(is: ResultName[InetSession1])(rs: WrappedResultSet): InetSession1 = autoConstruct(rs, is)

  val is = InetSession1.syntax("is")

  override val autoSession = AutoSession

  def find(id: Long, parentid: Long, splittedid: Long, connectionid: Long, sessionstart: DateTime, sessionstop: Option[DateTime], lastactive: DateTime, sessiontime: Long, sessioncost: BigDecimal, devicestate: Short, status: Short)(implicit session: DBSession = autoSession): Option[InetSession1] = {
    withSQL {
      select.from(InetSession1 as is).where.eq(is.id, id).and.eq(is.parentid, parentid).and.eq(is.splittedid, splittedid).and.eq(is.connectionid, connectionid).and.eq(is.sessionstart, sessionstart).and.eq(is.sessionstop, sessionstop).and.eq(is.lastactive, lastactive).and.eq(is.sessiontime, sessiontime).and.eq(is.sessioncost, sessioncost).and.eq(is.devicestate, devicestate).and.eq(is.status, status)
    }.map(InetSession1(is.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetSession1] = {
    withSQL(select.from(InetSession1 as is)).map(InetSession1(is.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetSession1 as is)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetSession1] = {
    withSQL {
      select.from(InetSession1 as is).where.append(where)
    }.map(InetSession1(is.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetSession1] = {
    withSQL {
      select.from(InetSession1 as is).where.append(where)
    }.map(InetSession1(is.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetSession1 as is).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Long,
    splittedid: Long,
    connectionid: Long,
    sessionstart: DateTime,
    sessionstop: Option[DateTime] = None,
    lastactive: DateTime,
    sessiontime: Long,
    sessioncost: BigDecimal,
    devicestate: Short,
    status: Short)(implicit session: DBSession = autoSession): InetSession1 = {
    val generatedKey = withSQL {
      insert.into(InetSession1).namedValues(
        column.parentid -> parentid,
        column.splittedid -> splittedid,
        column.connectionid -> connectionid,
        column.sessionstart -> sessionstart,
        column.sessionstop -> sessionstop,
        column.lastactive -> lastactive,
        column.sessiontime -> sessiontime,
        column.sessioncost -> sessioncost,
        column.devicestate -> devicestate,
        column.status -> status
      )
    }.updateAndReturnGeneratedKey.apply()

    InetSession1(
      id = generatedKey,
      parentid = parentid,
      splittedid = splittedid,
      connectionid = connectionid,
      sessionstart = sessionstart,
      sessionstop = sessionstop,
      lastactive = lastactive,
      sessiontime = sessiontime,
      sessioncost = sessioncost,
      devicestate = devicestate,
      status = status)
  }

  def batchInsert(entities: Seq[InetSession1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'splittedid -> entity.splittedid,
        'connectionid -> entity.connectionid,
        'sessionstart -> entity.sessionstart,
        'sessionstop -> entity.sessionstop,
        'lastactive -> entity.lastactive,
        'sessiontime -> entity.sessiontime,
        'sessioncost -> entity.sessioncost,
        'devicestate -> entity.devicestate,
        'status -> entity.status))
    SQL("""insert into inet_session_1(
      parentId,
      splittedId,
      connectionId,
      sessionStart,
      sessionStop,
      lastActive,
      sessionTime,
      sessionCost,
      deviceState,
      status
    ) values (
      {parentid},
      {splittedid},
      {connectionid},
      {sessionstart},
      {sessionstop},
      {lastactive},
      {sessiontime},
      {sessioncost},
      {devicestate},
      {status}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetSession1)(implicit session: DBSession = autoSession): InetSession1 = {
    withSQL {
      update(InetSession1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.splittedid -> entity.splittedid,
        column.connectionid -> entity.connectionid,
        column.sessionstart -> entity.sessionstart,
        column.sessionstop -> entity.sessionstop,
        column.lastactive -> entity.lastactive,
        column.sessiontime -> entity.sessiontime,
        column.sessioncost -> entity.sessioncost,
        column.devicestate -> entity.devicestate,
        column.status -> entity.status
      ).where.eq(column.id, entity.id).and.eq(column.parentid, entity.parentid).and.eq(column.splittedid, entity.splittedid).and.eq(column.connectionid, entity.connectionid).and.eq(column.sessionstart, entity.sessionstart).and.eq(column.sessionstop, entity.sessionstop).and.eq(column.lastactive, entity.lastactive).and.eq(column.sessiontime, entity.sessiontime).and.eq(column.sessioncost, entity.sessioncost).and.eq(column.devicestate, entity.devicestate).and.eq(column.status, entity.status)
    }.update.apply()
    entity
  }

  def destroy(entity: InetSession1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetSession1).where.eq(column.id, entity.id).and.eq(column.parentid, entity.parentid).and.eq(column.splittedid, entity.splittedid).and.eq(column.connectionid, entity.connectionid).and.eq(column.sessionstart, entity.sessionstart).and.eq(column.sessionstop, entity.sessionstop).and.eq(column.lastactive, entity.lastactive).and.eq(column.sessiontime, entity.sessiontime).and.eq(column.sessioncost, entity.sessioncost).and.eq(column.devicestate, entity.devicestate).and.eq(column.status, entity.status) }.update.apply()
  }

}
