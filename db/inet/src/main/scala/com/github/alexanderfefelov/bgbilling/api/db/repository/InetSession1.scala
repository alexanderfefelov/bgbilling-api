package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

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

  def find(connectionid: Long, id: Long)(implicit session: DBSession = autoSession): Option[InetSession1] = {
    withSQL {
      select.from(InetSession1 as is).where.eq(is.connectionid, connectionid).and.eq(is.id, id)
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
    status: Short)(implicit session: DBSession = autoSession): InetSession1 = {
    withSQL {
      insert.into(InetSession1).namedValues(
        column.id -> id,
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
    }.update.apply()

    InetSession1(
      id = id,
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
        'id -> entity.id,
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
      id,
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
      {id},
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
      ).where.eq(column.connectionid, entity.connectionid).and.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetSession1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetSession1).where.eq(column.connectionid, entity.connectionid).and.eq(column.id, entity.id) }.update.apply()
  }

}
