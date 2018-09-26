package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class News(
  id: Int,
  gr: Long,
  dt: DateTime,
  title: String,
  txt: String) {

  def save()(implicit session: DBSession = News.autoSession): News = News.save(this)(session)

  def destroy()(implicit session: DBSession = News.autoSession): Int = News.destroy(this)(session)

}


object News extends SQLSyntaxSupport[News] {

  override val tableName = "news"

  override val columns = Seq("id", "gr", "dt", "title", "txt")

  def apply(n: SyntaxProvider[News])(rs: WrappedResultSet): News = autoConstruct(rs, n)
  def apply(n: ResultName[News])(rs: WrappedResultSet): News = autoConstruct(rs, n)

  val n = News.syntax("n")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[News] = {
    withSQL {
      select.from(News as n).where.eq(n.id, id)
    }.map(News(n.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[News] = {
    withSQL(select.from(News as n)).map(News(n.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(News as n)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[News] = {
    withSQL {
      select.from(News as n).where.append(where)
    }.map(News(n.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[News] = {
    withSQL {
      select.from(News as n).where.append(where)
    }.map(News(n.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(News as n).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gr: Long,
    dt: DateTime,
    title: String,
    txt: String)(implicit session: DBSession = autoSession): News = {
    val generatedKey = withSQL {
      insert.into(News).namedValues(
        column.gr -> gr,
        column.dt -> dt,
        column.title -> title,
        column.txt -> txt
      )
    }.updateAndReturnGeneratedKey.apply()

    News(
      id = generatedKey.toInt,
      gr = gr,
      dt = dt,
      title = title,
      txt = txt)
  }

  def batchInsert(entities: collection.Seq[News])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gr -> entity.gr,
        'dt -> entity.dt,
        'title -> entity.title,
        'txt -> entity.txt))
    SQL("""insert into news(
      gr,
      dt,
      title,
      txt
    ) values (
      {gr},
      {dt},
      {title},
      {txt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: News)(implicit session: DBSession = autoSession): News = {
    withSQL {
      update(News).set(
        column.id -> entity.id,
        column.gr -> entity.gr,
        column.dt -> entity.dt,
        column.title -> entity.title,
        column.txt -> entity.txt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: News)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(News).where.eq(column.id, entity.id) }.update.apply()
  }

}
