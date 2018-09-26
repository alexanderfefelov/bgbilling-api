package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TimeType(
  id: Int,
  title: String,
  data: String) {

  def save()(implicit session: DBSession = TimeType.autoSession): TimeType = TimeType.save(this)(session)

  def destroy()(implicit session: DBSession = TimeType.autoSession): Int = TimeType.destroy(this)(session)

}


object TimeType extends SQLSyntaxSupport[TimeType] {

  override val tableName = "time_type"

  override val columns = Seq("id", "title", "data")

  def apply(tt: SyntaxProvider[TimeType])(rs: WrappedResultSet): TimeType = autoConstruct(rs, tt)
  def apply(tt: ResultName[TimeType])(rs: WrappedResultSet): TimeType = autoConstruct(rs, tt)

  val tt = TimeType.syntax("tt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TimeType] = {
    withSQL {
      select.from(TimeType as tt).where.eq(tt.id, id)
    }.map(TimeType(tt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TimeType] = {
    withSQL(select.from(TimeType as tt)).map(TimeType(tt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TimeType as tt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TimeType] = {
    withSQL {
      select.from(TimeType as tt).where.append(where)
    }.map(TimeType(tt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TimeType] = {
    withSQL {
      select.from(TimeType as tt).where.append(where)
    }.map(TimeType(tt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TimeType as tt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    data: String)(implicit session: DBSession = autoSession): TimeType = {
    val generatedKey = withSQL {
      insert.into(TimeType).namedValues(
        column.title -> title,
        column.data -> data
      )
    }.updateAndReturnGeneratedKey.apply()

    TimeType(
      id = generatedKey.toInt,
      title = title,
      data = data)
  }

  def batchInsert(entities: collection.Seq[TimeType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'data -> entity.data))
    SQL("""insert into time_type(
      title,
      data
    ) values (
      {title},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TimeType)(implicit session: DBSession = autoSession): TimeType = {
    withSQL {
      update(TimeType).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.data -> entity.data
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TimeType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TimeType).where.eq(column.id, entity.id) }.update.apply()
  }

}
