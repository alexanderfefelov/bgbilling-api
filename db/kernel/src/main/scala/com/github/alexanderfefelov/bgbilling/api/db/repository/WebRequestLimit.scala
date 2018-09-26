package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class WebRequestLimit(
  cid: Int,
  lim: Option[Int] = None) {

  def save()(implicit session: DBSession = WebRequestLimit.autoSession): WebRequestLimit = WebRequestLimit.save(this)(session)

  def destroy()(implicit session: DBSession = WebRequestLimit.autoSession): Int = WebRequestLimit.destroy(this)(session)

}


object WebRequestLimit extends SQLSyntaxSupport[WebRequestLimit] {

  override val tableName = "web_request_limit"

  override val columns = Seq("cid", "lim")

  def apply(wrl: SyntaxProvider[WebRequestLimit])(rs: WrappedResultSet): WebRequestLimit = autoConstruct(rs, wrl)
  def apply(wrl: ResultName[WebRequestLimit])(rs: WrappedResultSet): WebRequestLimit = autoConstruct(rs, wrl)

  val wrl = WebRequestLimit.syntax("wrl")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[WebRequestLimit] = {
    withSQL {
      select.from(WebRequestLimit as wrl).where.eq(wrl.cid, cid)
    }.map(WebRequestLimit(wrl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WebRequestLimit] = {
    withSQL(select.from(WebRequestLimit as wrl)).map(WebRequestLimit(wrl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WebRequestLimit as wrl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WebRequestLimit] = {
    withSQL {
      select.from(WebRequestLimit as wrl).where.append(where)
    }.map(WebRequestLimit(wrl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WebRequestLimit] = {
    withSQL {
      select.from(WebRequestLimit as wrl).where.append(where)
    }.map(WebRequestLimit(wrl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WebRequestLimit as wrl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    lim: Option[Int] = None)(implicit session: DBSession = autoSession): WebRequestLimit = {
    withSQL {
      insert.into(WebRequestLimit).namedValues(
        column.cid -> cid,
        column.lim -> lim
      )
    }.update.apply()

    WebRequestLimit(
      cid = cid,
      lim = lim)
  }

  def batchInsert(entities: collection.Seq[WebRequestLimit])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'lim -> entity.lim))
    SQL("""insert into web_request_limit(
      cid,
      lim
    ) values (
      {cid},
      {lim}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WebRequestLimit)(implicit session: DBSession = autoSession): WebRequestLimit = {
    withSQL {
      update(WebRequestLimit).set(
        column.cid -> entity.cid,
        column.lim -> entity.lim
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: WebRequestLimit)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WebRequestLimit).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
