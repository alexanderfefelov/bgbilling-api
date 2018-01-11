package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetZone1(
  id: Int,
  title: String) {

  def save()(implicit session: DBSession = InetZone1.autoSession): InetZone1 = InetZone1.save(this)(session)

  def destroy()(implicit session: DBSession = InetZone1.autoSession): Int = InetZone1.destroy(this)(session)

}


object InetZone1 extends SQLSyntaxSupport[InetZone1] {

  override val tableName = "inet_zone_1"

  override val columns = Seq("id", "title")

  def apply(iz: SyntaxProvider[InetZone1])(rs: WrappedResultSet): InetZone1 = autoConstruct(rs, iz)
  def apply(iz: ResultName[InetZone1])(rs: WrappedResultSet): InetZone1 = autoConstruct(rs, iz)

  val iz = InetZone1.syntax("iz")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetZone1] = {
    withSQL {
      select.from(InetZone1 as iz).where.eq(iz.id, id)
    }.map(InetZone1(iz.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetZone1] = {
    withSQL(select.from(InetZone1 as iz)).map(InetZone1(iz.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetZone1 as iz)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetZone1] = {
    withSQL {
      select.from(InetZone1 as iz).where.append(where)
    }.map(InetZone1(iz.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetZone1] = {
    withSQL {
      select.from(InetZone1 as iz).where.append(where)
    }.map(InetZone1(iz.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetZone1 as iz).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): InetZone1 = {
    val generatedKey = withSQL {
      insert.into(InetZone1).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    InetZone1(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: Seq[InetZone1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into inet_zone_1(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetZone1)(implicit session: DBSession = autoSession): InetZone1 = {
    withSQL {
      update(InetZone1).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetZone1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetZone1).where.eq(column.id, entity.id) }.update.apply()
  }

}
