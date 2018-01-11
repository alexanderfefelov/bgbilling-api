package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetSessionDetail1(
  sessionid: Long,
  day: Byte,
  hour: Byte,
  traffictypeid: Int,
  deviceid: Int,
  amount: Long) {

  def save()(implicit session: DBSession = InetSessionDetail1.autoSession): InetSessionDetail1 = InetSessionDetail1.save(this)(session)

  def destroy()(implicit session: DBSession = InetSessionDetail1.autoSession): Int = InetSessionDetail1.destroy(this)(session)

}


object InetSessionDetail1 extends SQLSyntaxSupport[InetSessionDetail1] {

  override val tableName = "inet_session_detail_1"

  override val columns = Seq("sessionId", "day", "hour", "trafficTypeId", "deviceId", "amount")

  def apply(isd: SyntaxProvider[InetSessionDetail1])(rs: WrappedResultSet): InetSessionDetail1 = autoConstruct(rs, isd)
  def apply(isd: ResultName[InetSessionDetail1])(rs: WrappedResultSet): InetSessionDetail1 = autoConstruct(rs, isd)

  val isd = InetSessionDetail1.syntax("isd")

  override val autoSession = AutoSession

  def find(day: Byte, deviceid: Int, hour: Byte, sessionid: Long, traffictypeid: Int)(implicit session: DBSession = autoSession): Option[InetSessionDetail1] = {
    withSQL {
      select.from(InetSessionDetail1 as isd).where.eq(isd.day, day).and.eq(isd.deviceid, deviceid).and.eq(isd.hour, hour).and.eq(isd.sessionid, sessionid).and.eq(isd.traffictypeid, traffictypeid)
    }.map(InetSessionDetail1(isd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetSessionDetail1] = {
    withSQL(select.from(InetSessionDetail1 as isd)).map(InetSessionDetail1(isd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetSessionDetail1 as isd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetSessionDetail1] = {
    withSQL {
      select.from(InetSessionDetail1 as isd).where.append(where)
    }.map(InetSessionDetail1(isd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetSessionDetail1] = {
    withSQL {
      select.from(InetSessionDetail1 as isd).where.append(where)
    }.map(InetSessionDetail1(isd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetSessionDetail1 as isd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    sessionid: Long,
    day: Byte,
    hour: Byte,
    traffictypeid: Int,
    deviceid: Int,
    amount: Long)(implicit session: DBSession = autoSession): InetSessionDetail1 = {
    withSQL {
      insert.into(InetSessionDetail1).namedValues(
        column.sessionid -> sessionid,
        column.day -> day,
        column.hour -> hour,
        column.traffictypeid -> traffictypeid,
        column.deviceid -> deviceid,
        column.amount -> amount
      )
    }.update.apply()

    InetSessionDetail1(
      sessionid = sessionid,
      day = day,
      hour = hour,
      traffictypeid = traffictypeid,
      deviceid = deviceid,
      amount = amount)
  }

  def batchInsert(entities: Seq[InetSessionDetail1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'sessionid -> entity.sessionid,
        'day -> entity.day,
        'hour -> entity.hour,
        'traffictypeid -> entity.traffictypeid,
        'deviceid -> entity.deviceid,
        'amount -> entity.amount))
    SQL("""insert into inet_session_detail_1(
      sessionId,
      day,
      hour,
      trafficTypeId,
      deviceId,
      amount
    ) values (
      {sessionid},
      {day},
      {hour},
      {traffictypeid},
      {deviceid},
      {amount}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetSessionDetail1)(implicit session: DBSession = autoSession): InetSessionDetail1 = {
    withSQL {
      update(InetSessionDetail1).set(
        column.sessionid -> entity.sessionid,
        column.day -> entity.day,
        column.hour -> entity.hour,
        column.traffictypeid -> entity.traffictypeid,
        column.deviceid -> entity.deviceid,
        column.amount -> entity.amount
      ).where.eq(column.day, entity.day).and.eq(column.deviceid, entity.deviceid).and.eq(column.hour, entity.hour).and.eq(column.sessionid, entity.sessionid).and.eq(column.traffictypeid, entity.traffictypeid)
    }.update.apply()
    entity
  }

  def destroy(entity: InetSessionDetail1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetSessionDetail1).where.eq(column.day, entity.day).and.eq(column.deviceid, entity.deviceid).and.eq(column.hour, entity.hour).and.eq(column.sessionid, entity.sessionid).and.eq(column.traffictypeid, entity.traffictypeid) }.update.apply()
  }

}
