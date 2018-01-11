package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}

case class InetTaskProcess1(
  id: Int,
  deviceid: Int,
  day: DateTime,
  starttime: Option[DateTime] = None,
  curenthour: Int,
  `type`: Option[Byte] = None) {

  def save()(implicit session: DBSession = InetTaskProcess1.autoSession): InetTaskProcess1 = InetTaskProcess1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTaskProcess1.autoSession): Int = InetTaskProcess1.destroy(this)(session)

}


object InetTaskProcess1 extends SQLSyntaxSupport[InetTaskProcess1] with ApiDbConfig {

  override val tableName = s"inet_task_process_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "deviceId", "day", "startTime", "curentHour", "type")

  def apply(itp: SyntaxProvider[InetTaskProcess1])(rs: WrappedResultSet): InetTaskProcess1 = autoConstruct(rs, itp)
  def apply(itp: ResultName[InetTaskProcess1])(rs: WrappedResultSet): InetTaskProcess1 = autoConstruct(rs, itp)

  val itp = InetTaskProcess1.syntax("itp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetTaskProcess1] = {
    withSQL {
      select.from(InetTaskProcess1 as itp).where.eq(itp.id, id)
    }.map(InetTaskProcess1(itp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTaskProcess1] = {
    withSQL(select.from(InetTaskProcess1 as itp)).map(InetTaskProcess1(itp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTaskProcess1 as itp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTaskProcess1] = {
    withSQL {
      select.from(InetTaskProcess1 as itp).where.append(where)
    }.map(InetTaskProcess1(itp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTaskProcess1] = {
    withSQL {
      select.from(InetTaskProcess1 as itp).where.append(where)
    }.map(InetTaskProcess1(itp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTaskProcess1 as itp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    day: DateTime,
    starttime: Option[DateTime] = None,
    curenthour: Int,
    `type`: Option[Byte] = None)(implicit session: DBSession = autoSession): InetTaskProcess1 = {
    val generatedKey = withSQL {
      insert.into(InetTaskProcess1).namedValues(
        column.deviceid -> deviceid,
        column.day -> day,
        column.starttime -> starttime,
        column.curenthour -> curenthour,
        column.`type` -> `type`
      )
    }.updateAndReturnGeneratedKey.apply()

    InetTaskProcess1(
      id = generatedKey.toInt,
      deviceid = deviceid,
      day = day,
      starttime = starttime,
      curenthour = curenthour,
      `type` = `type`)
  }

  def batchInsert(entities: Seq[InetTaskProcess1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'day -> entity.day,
        'starttime -> entity.starttime,
        'curenthour -> entity.curenthour,
        'type -> entity.`type`))
    SQL("""insert into inet_task_process_1(
      deviceId,
      day,
      startTime,
      curentHour,
      type
    ) values (
      {deviceid},
      {day},
      {starttime},
      {curenthour},
      {type}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTaskProcess1)(implicit session: DBSession = autoSession): InetTaskProcess1 = {
    withSQL {
      update(InetTaskProcess1).set(
        column.id -> entity.id,
        column.deviceid -> entity.deviceid,
        column.day -> entity.day,
        column.starttime -> entity.starttime,
        column.curenthour -> entity.curenthour,
        column.`type` -> entity.`type`
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTaskProcess1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTaskProcess1).where.eq(column.id, entity.id) }.update.apply()
  }

}
