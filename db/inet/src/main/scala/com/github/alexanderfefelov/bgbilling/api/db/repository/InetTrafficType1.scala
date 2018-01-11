package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InetTrafficType1(
  id: Int,
  title: String,
  unit: Int) {

  def save()(implicit session: DBSession = InetTrafficType1.autoSession): InetTrafficType1 = InetTrafficType1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTrafficType1.autoSession): Int = InetTrafficType1.destroy(this)(session)

}


object InetTrafficType1 extends SQLSyntaxSupport[InetTrafficType1] {

  override val tableName = "inet_traffic_type_1"

  override val columns = Seq("id", "title", "unit")

  def apply(itt: SyntaxProvider[InetTrafficType1])(rs: WrappedResultSet): InetTrafficType1 = autoConstruct(rs, itt)
  def apply(itt: ResultName[InetTrafficType1])(rs: WrappedResultSet): InetTrafficType1 = autoConstruct(rs, itt)

  val itt = InetTrafficType1.syntax("itt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetTrafficType1] = {
    withSQL {
      select.from(InetTrafficType1 as itt).where.eq(itt.id, id)
    }.map(InetTrafficType1(itt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTrafficType1] = {
    withSQL(select.from(InetTrafficType1 as itt)).map(InetTrafficType1(itt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTrafficType1 as itt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTrafficType1] = {
    withSQL {
      select.from(InetTrafficType1 as itt).where.append(where)
    }.map(InetTrafficType1(itt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTrafficType1] = {
    withSQL {
      select.from(InetTrafficType1 as itt).where.append(where)
    }.map(InetTrafficType1(itt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTrafficType1 as itt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    unit: Int)(implicit session: DBSession = autoSession): InetTrafficType1 = {
    val generatedKey = withSQL {
      insert.into(InetTrafficType1).namedValues(
        column.title -> title,
        column.unit -> unit
      )
    }.updateAndReturnGeneratedKey.apply()

    InetTrafficType1(
      id = generatedKey.toInt,
      title = title,
      unit = unit)
  }

  def batchInsert(entities: Seq[InetTrafficType1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'unit -> entity.unit))
    SQL("""insert into inet_traffic_type_1(
      title,
      unit
    ) values (
      {title},
      {unit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTrafficType1)(implicit session: DBSession = autoSession): InetTrafficType1 = {
    withSQL {
      update(InetTrafficType1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.unit -> entity.unit
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTrafficType1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTrafficType1).where.eq(column.id, entity.id) }.update.apply()
  }

}
