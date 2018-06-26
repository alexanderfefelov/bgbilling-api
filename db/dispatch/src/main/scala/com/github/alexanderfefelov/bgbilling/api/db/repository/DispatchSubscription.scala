package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class DispatchSubscription(
  id: Int,
  dispatchId: Int,
  contractId: Int,
  dateFrom: Option[LocalDate] = None,
  dateTo: Option[LocalDate] = None,
  comment: String,
  data: Array[Byte],
  repeatTime: String,
  active: Boolean) {

  def save()(implicit session: DBSession = DispatchSubscription.autoSession): DispatchSubscription = DispatchSubscription.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchSubscription.autoSession): Int = DispatchSubscription.destroy(this)(session)

}


object DispatchSubscription extends SQLSyntaxSupport[DispatchSubscription] {

  override val tableName = "dispatch_subscription"

  override val columns = Seq("id", "dispatch_id", "contract_id", "date_from", "date_to", "comment", "data", "repeat_time", "active")

  def apply(ds: SyntaxProvider[DispatchSubscription])(rs: WrappedResultSet): DispatchSubscription = autoConstruct(rs, ds)
  def apply(ds: ResultName[DispatchSubscription])(rs: WrappedResultSet): DispatchSubscription = autoConstruct(rs, ds)

  val ds = DispatchSubscription.syntax("ds")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchSubscription] = {
    withSQL {
      select.from(DispatchSubscription as ds).where.eq(ds.id, id)
    }.map(DispatchSubscription(ds.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchSubscription] = {
    withSQL(select.from(DispatchSubscription as ds)).map(DispatchSubscription(ds.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchSubscription as ds)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchSubscription] = {
    withSQL {
      select.from(DispatchSubscription as ds).where.append(where)
    }.map(DispatchSubscription(ds.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchSubscription] = {
    withSQL {
      select.from(DispatchSubscription as ds).where.append(where)
    }.map(DispatchSubscription(ds.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchSubscription as ds).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dispatchId: Int,
    contractId: Int,
    dateFrom: Option[LocalDate] = None,
    dateTo: Option[LocalDate] = None,
    comment: String,
    data: Array[Byte],
    repeatTime: String,
    active: Boolean)(implicit session: DBSession = autoSession): DispatchSubscription = {
    val generatedKey = withSQL {
      insert.into(DispatchSubscription).namedValues(
        column.dispatchId -> dispatchId,
        column.contractId -> contractId,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.comment -> comment,
        column.data -> data,
        column.repeatTime -> repeatTime,
        column.active -> active
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchSubscription(
      id = generatedKey.toInt,
      dispatchId = dispatchId,
      contractId = contractId,
      dateFrom = dateFrom,
      dateTo = dateTo,
      comment = comment,
      data = data,
      repeatTime = repeatTime,
      active = active)
  }

  def batchInsert(entities: Seq[DispatchSubscription])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dispatchId -> entity.dispatchId,
        'contractId -> entity.contractId,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'comment -> entity.comment,
        'data -> entity.data,
        'repeatTime -> entity.repeatTime,
        'active -> entity.active))
    SQL("""insert into dispatch_subscription(
      dispatch_id,
      contract_id,
      date_from,
      date_to,
      comment,
      data,
      repeat_time,
      active
    ) values (
      {dispatchId},
      {contractId},
      {dateFrom},
      {dateTo},
      {comment},
      {data},
      {repeatTime},
      {active}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchSubscription)(implicit session: DBSession = autoSession): DispatchSubscription = {
    withSQL {
      update(DispatchSubscription).set(
        column.id -> entity.id,
        column.dispatchId -> entity.dispatchId,
        column.contractId -> entity.contractId,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.comment -> entity.comment,
        column.data -> entity.data,
        column.repeatTime -> entity.repeatTime,
        column.active -> entity.active
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchSubscription)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchSubscription).where.eq(column.id, entity.id) }.update.apply()
  }

}
