package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetTrafficTypeLink1(
  id: Int,
  title: String) {

  def save()(implicit session: DBSession = InetTrafficTypeLink1.autoSession): InetTrafficTypeLink1 = InetTrafficTypeLink1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTrafficTypeLink1.autoSession): Int = InetTrafficTypeLink1.destroy(this)(session)

}


object InetTrafficTypeLink1 extends SQLSyntaxSupport[InetTrafficTypeLink1] with ApiDbConfig {

  override val tableName = s"inet_traffic_type_link_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "title")

  def apply(ittl: SyntaxProvider[InetTrafficTypeLink1])(rs: WrappedResultSet): InetTrafficTypeLink1 = autoConstruct(rs, ittl)
  def apply(ittl: ResultName[InetTrafficTypeLink1])(rs: WrappedResultSet): InetTrafficTypeLink1 = autoConstruct(rs, ittl)

  val ittl = InetTrafficTypeLink1.syntax("ittl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetTrafficTypeLink1] = {
    withSQL {
      select.from(InetTrafficTypeLink1 as ittl).where.eq(ittl.id, id)
    }.map(InetTrafficTypeLink1(ittl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTrafficTypeLink1] = {
    withSQL(select.from(InetTrafficTypeLink1 as ittl)).map(InetTrafficTypeLink1(ittl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTrafficTypeLink1 as ittl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTrafficTypeLink1] = {
    withSQL {
      select.from(InetTrafficTypeLink1 as ittl).where.append(where)
    }.map(InetTrafficTypeLink1(ittl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTrafficTypeLink1] = {
    withSQL {
      select.from(InetTrafficTypeLink1 as ittl).where.append(where)
    }.map(InetTrafficTypeLink1(ittl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTrafficTypeLink1 as ittl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): InetTrafficTypeLink1 = {
    val generatedKey = withSQL {
      insert.into(InetTrafficTypeLink1).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    InetTrafficTypeLink1(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: Seq[InetTrafficTypeLink1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into inet_traffic_type_link_1(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTrafficTypeLink1)(implicit session: DBSession = autoSession): InetTrafficTypeLink1 = {
    withSQL {
      update(InetTrafficTypeLink1).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTrafficTypeLink1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTrafficTypeLink1).where.eq(column.id, entity.id) }.update.apply()
  }

}
