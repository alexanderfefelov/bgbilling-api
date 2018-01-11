package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class SubscriptionJobTrigger4(
  id: Long,
  jobid: Long,
  timefrommillis: Long,
  timetomillis: Long,
  persistent: Option[Byte] = None,
  `type`: Int,
  data: Option[Array[Byte]] = None,
  firetimeprevmillis: Long,
  firetimenextmillis: Long,
  firecount: Long,
  version: Long) {

  def save()(implicit session: DBSession = SubscriptionJobTrigger4.autoSession): SubscriptionJobTrigger4 = SubscriptionJobTrigger4.save(this)(session)

  def destroy()(implicit session: DBSession = SubscriptionJobTrigger4.autoSession): Int = SubscriptionJobTrigger4.destroy(this)(session)

}


object SubscriptionJobTrigger4 extends SQLSyntaxSupport[SubscriptionJobTrigger4] with ApiDbConfig {

  override val tableName = s"subscription_job_trigger_${bgBillingModuleId("subscription")}"

  override val columns = Seq("id", "jobId", "timeFromMillis", "timeToMillis", "persistent", "type", "data", "fireTimePrevMillis", "fireTimeNextMillis", "fireCount", "version")

  def apply(sjt: SyntaxProvider[SubscriptionJobTrigger4])(rs: WrappedResultSet): SubscriptionJobTrigger4 = autoConstruct(rs, sjt)
  def apply(sjt: ResultName[SubscriptionJobTrigger4])(rs: WrappedResultSet): SubscriptionJobTrigger4 = autoConstruct(rs, sjt)

  val sjt = SubscriptionJobTrigger4.syntax("sjt")

  override val autoSession = AutoSession

  def find(id: Long, jobid: Long)(implicit session: DBSession = autoSession): Option[SubscriptionJobTrigger4] = {
    withSQL {
      select.from(SubscriptionJobTrigger4 as sjt).where.eq(sjt.id, id).and.eq(sjt.jobid, jobid)
    }.map(SubscriptionJobTrigger4(sjt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SubscriptionJobTrigger4] = {
    withSQL(select.from(SubscriptionJobTrigger4 as sjt)).map(SubscriptionJobTrigger4(sjt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SubscriptionJobTrigger4 as sjt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SubscriptionJobTrigger4] = {
    withSQL {
      select.from(SubscriptionJobTrigger4 as sjt).where.append(where)
    }.map(SubscriptionJobTrigger4(sjt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SubscriptionJobTrigger4] = {
    withSQL {
      select.from(SubscriptionJobTrigger4 as sjt).where.append(where)
    }.map(SubscriptionJobTrigger4(sjt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SubscriptionJobTrigger4 as sjt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    jobid: Long,
    timefrommillis: Long,
    timetomillis: Long,
    persistent: Option[Byte] = None,
    `type`: Int,
    data: Option[Array[Byte]] = None,
    firetimeprevmillis: Long,
    firetimenextmillis: Long,
    firecount: Long,
    version: Long)(implicit session: DBSession = autoSession): SubscriptionJobTrigger4 = {
    val generatedKey = withSQL {
      insert.into(SubscriptionJobTrigger4).namedValues(
        column.jobid -> jobid,
        column.timefrommillis -> timefrommillis,
        column.timetomillis -> timetomillis,
        column.persistent -> persistent,
        column.`type` -> `type`,
        column.data -> data,
        column.firetimeprevmillis -> firetimeprevmillis,
        column.firetimenextmillis -> firetimenextmillis,
        column.firecount -> firecount,
        column.version -> version
      )
    }.updateAndReturnGeneratedKey.apply()

    SubscriptionJobTrigger4(
      id = generatedKey,
      jobid = jobid,
      timefrommillis = timefrommillis,
      timetomillis = timetomillis,
      persistent = persistent,
      `type` = `type`,
      data = data,
      firetimeprevmillis = firetimeprevmillis,
      firetimenextmillis = firetimenextmillis,
      firecount = firecount,
      version = version)
  }

  def batchInsert(entities: Seq[SubscriptionJobTrigger4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'jobid -> entity.jobid,
        'timefrommillis -> entity.timefrommillis,
        'timetomillis -> entity.timetomillis,
        'persistent -> entity.persistent,
        'type -> entity.`type`,
        'data -> entity.data,
        'firetimeprevmillis -> entity.firetimeprevmillis,
        'firetimenextmillis -> entity.firetimenextmillis,
        'firecount -> entity.firecount,
        'version -> entity.version))
    SQL("""insert into subscription_job_trigger_4(
      jobId,
      timeFromMillis,
      timeToMillis,
      persistent,
      type,
      data,
      fireTimePrevMillis,
      fireTimeNextMillis,
      fireCount,
      version
    ) values (
      {jobid},
      {timefrommillis},
      {timetomillis},
      {persistent},
      {type},
      {data},
      {firetimeprevmillis},
      {firetimenextmillis},
      {firecount},
      {version}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SubscriptionJobTrigger4)(implicit session: DBSession = autoSession): SubscriptionJobTrigger4 = {
    withSQL {
      update(SubscriptionJobTrigger4).set(
        column.id -> entity.id,
        column.jobid -> entity.jobid,
        column.timefrommillis -> entity.timefrommillis,
        column.timetomillis -> entity.timetomillis,
        column.persistent -> entity.persistent,
        column.`type` -> entity.`type`,
        column.data -> entity.data,
        column.firetimeprevmillis -> entity.firetimeprevmillis,
        column.firetimenextmillis -> entity.firetimenextmillis,
        column.firecount -> entity.firecount,
        column.version -> entity.version
      ).where.eq(column.id, entity.id).and.eq(column.jobid, entity.jobid)
    }.update.apply()
    entity
  }

  def destroy(entity: SubscriptionJobTrigger4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SubscriptionJobTrigger4).where.eq(column.id, entity.id).and.eq(column.jobid, entity.jobid) }.update.apply()
  }

}
