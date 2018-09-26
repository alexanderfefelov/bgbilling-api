package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WebRequestCount(
  cid: Int,
  date: LocalDate,
  count: Int) {

  def save()(implicit session: DBSession = WebRequestCount.autoSession): WebRequestCount = WebRequestCount.save(this)(session)

  def destroy()(implicit session: DBSession = WebRequestCount.autoSession): Int = WebRequestCount.destroy(this)(session)

}


object WebRequestCount extends SQLSyntaxSupport[WebRequestCount] {

  override val tableName = "web_request_count"

  override val columns = Seq("cid", "date", "count")

  def apply(wrc: SyntaxProvider[WebRequestCount])(rs: WrappedResultSet): WebRequestCount = autoConstruct(rs, wrc)
  def apply(wrc: ResultName[WebRequestCount])(rs: WrappedResultSet): WebRequestCount = autoConstruct(rs, wrc)

  val wrc = WebRequestCount.syntax("wrc")

  override val autoSession = AutoSession

  def find(cid: Int, date: LocalDate, count: Int)(implicit session: DBSession = autoSession): Option[WebRequestCount] = {
    withSQL {
      select.from(WebRequestCount as wrc).where.eq(wrc.cid, cid).and.eq(wrc.date, date).and.eq(wrc.count, count)
    }.map(WebRequestCount(wrc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WebRequestCount] = {
    withSQL(select.from(WebRequestCount as wrc)).map(WebRequestCount(wrc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WebRequestCount as wrc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WebRequestCount] = {
    withSQL {
      select.from(WebRequestCount as wrc).where.append(where)
    }.map(WebRequestCount(wrc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WebRequestCount] = {
    withSQL {
      select.from(WebRequestCount as wrc).where.append(where)
    }.map(WebRequestCount(wrc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WebRequestCount as wrc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    date: LocalDate,
    count: Int)(implicit session: DBSession = autoSession): WebRequestCount = {
    withSQL {
      insert.into(WebRequestCount).namedValues(
        column.cid -> cid,
        column.date -> date,
        column.count -> count
      )
    }.update.apply()

    WebRequestCount(
      cid = cid,
      date = date,
      count = count)
  }

  def batchInsert(entities: collection.Seq[WebRequestCount])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'date -> entity.date,
        'count -> entity.count))
    SQL("""insert into web_request_count(
      cid,
      date,
      count
    ) values (
      {cid},
      {date},
      {count}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WebRequestCount)(implicit session: DBSession = autoSession): WebRequestCount = {
    withSQL {
      update(WebRequestCount).set(
        column.cid -> entity.cid,
        column.date -> entity.date,
        column.count -> entity.count
      ).where.eq(column.cid, entity.cid).and.eq(column.date, entity.date).and.eq(column.count, entity.count)
    }.update.apply()
    entity
  }

  def destroy(entity: WebRequestCount)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WebRequestCount).where.eq(column.cid, entity.cid).and.eq(column.date, entity.date).and.eq(column.count, entity.count) }.update.apply()
  }

}
