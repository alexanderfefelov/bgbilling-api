package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class StatusLog(
  fromDate: DateTime,
  toDate: Option[DateTime] = None,
  cid: Int,
  mid: Int,
  status: Option[Byte] = None,
  uid: Byte,
  comment: String) {

  def save()(implicit session: DBSession = StatusLog.autoSession): StatusLog = StatusLog.save(this)(session)

  def destroy()(implicit session: DBSession = StatusLog.autoSession): Int = StatusLog.destroy(this)(session)

}


object StatusLog extends SQLSyntaxSupport[StatusLog] {

  override val tableName = "status_log"

  override val columns = Seq("from_date", "to_date", "cid", "mid", "status", "uid", "comment")

  def apply(sl: SyntaxProvider[StatusLog])(rs: WrappedResultSet): StatusLog = autoConstruct(rs, sl)
  def apply(sl: ResultName[StatusLog])(rs: WrappedResultSet): StatusLog = autoConstruct(rs, sl)

  val sl = StatusLog.syntax("sl")

  override val autoSession = AutoSession

  def find(fromDate: DateTime, toDate: Option[DateTime], cid: Int, mid: Int, status: Option[Byte], uid: Byte, comment: String)(implicit session: DBSession = autoSession): Option[StatusLog] = {
    withSQL {
      select.from(StatusLog as sl).where.eq(sl.fromDate, fromDate).and.eq(sl.toDate, toDate).and.eq(sl.cid, cid).and.eq(sl.mid, mid).and.eq(sl.status, status).and.eq(sl.uid, uid).and.eq(sl.comment, comment)
    }.map(StatusLog(sl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[StatusLog] = {
    withSQL(select.from(StatusLog as sl)).map(StatusLog(sl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(StatusLog as sl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[StatusLog] = {
    withSQL {
      select.from(StatusLog as sl).where.append(where)
    }.map(StatusLog(sl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[StatusLog] = {
    withSQL {
      select.from(StatusLog as sl).where.append(where)
    }.map(StatusLog(sl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(StatusLog as sl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    fromDate: DateTime,
    toDate: Option[DateTime] = None,
    cid: Int,
    mid: Int,
    status: Option[Byte] = None,
    uid: Byte,
    comment: String)(implicit session: DBSession = autoSession): StatusLog = {
    withSQL {
      insert.into(StatusLog).namedValues(
        column.fromDate -> fromDate,
        column.toDate -> toDate,
        column.cid -> cid,
        column.mid -> mid,
        column.status -> status,
        column.uid -> uid,
        column.comment -> comment
      )
    }.update.apply()

    StatusLog(
      fromDate = fromDate,
      toDate = toDate,
      cid = cid,
      mid = mid,
      status = status,
      uid = uid,
      comment = comment)
  }

  def batchInsert(entities: Seq[StatusLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'fromDate -> entity.fromDate,
        'toDate -> entity.toDate,
        'cid -> entity.cid,
        'mid -> entity.mid,
        'status -> entity.status,
        'uid -> entity.uid,
        'comment -> entity.comment))
    SQL("""insert into status_log(
      from_date,
      to_date,
      cid,
      mid,
      status,
      uid,
      comment
    ) values (
      {fromDate},
      {toDate},
      {cid},
      {mid},
      {status},
      {uid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: StatusLog)(implicit session: DBSession = autoSession): StatusLog = {
    withSQL {
      update(StatusLog).set(
        column.fromDate -> entity.fromDate,
        column.toDate -> entity.toDate,
        column.cid -> entity.cid,
        column.mid -> entity.mid,
        column.status -> entity.status,
        column.uid -> entity.uid,
        column.comment -> entity.comment
      ).where.eq(column.fromDate, entity.fromDate).and.eq(column.toDate, entity.toDate).and.eq(column.cid, entity.cid).and.eq(column.mid, entity.mid).and.eq(column.status, entity.status).and.eq(column.uid, entity.uid).and.eq(column.comment, entity.comment)
    }.update.apply()
    entity
  }

  def destroy(entity: StatusLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(StatusLog).where.eq(column.fromDate, entity.fromDate).and.eq(column.toDate, entity.toDate).and.eq(column.cid, entity.cid).and.eq(column.mid, entity.mid).and.eq(column.status, entity.status).and.eq(column.uid, entity.uid).and.eq(column.comment, entity.comment) }.update.apply()
  }

}
