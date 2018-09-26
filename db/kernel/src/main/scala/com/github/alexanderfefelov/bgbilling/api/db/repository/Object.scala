package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Object(
  id: Int,
  cid: Int,
  title: String,
  typeId: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  pos: Option[Int] = None) {

  def save()(implicit session: DBSession = Object.autoSession): Object = Object.save(this)(session)

  def destroy()(implicit session: DBSession = Object.autoSession): Int = Object.destroy(this)(session)

}


object Object extends SQLSyntaxSupport[Object] {

  override val tableName = "object"

  override val columns = Seq("id", "cid", "title", "type_id", "date1", "date2", "pos")

  def apply(o: SyntaxProvider[Object])(rs: WrappedResultSet): Object = autoConstruct(rs, o)
  def apply(o: ResultName[Object])(rs: WrappedResultSet): Object = autoConstruct(rs, o)

  val o = Object.syntax("o")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Object] = {
    withSQL {
      select.from(Object as o).where.eq(o.id, id)
    }.map(Object(o.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Object] = {
    withSQL(select.from(Object as o)).map(Object(o.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Object as o)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Object] = {
    withSQL {
      select.from(Object as o).where.append(where)
    }.map(Object(o.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Object] = {
    withSQL {
      select.from(Object as o).where.append(where)
    }.map(Object(o.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Object as o).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    title: String,
    typeId: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    pos: Option[Int] = None)(implicit session: DBSession = autoSession): Object = {
    val generatedKey = withSQL {
      insert.into(Object).namedValues(
        column.cid -> cid,
        column.title -> title,
        column.typeId -> typeId,
        column.date1 -> date1,
        column.date2 -> date2,
        column.pos -> pos
      )
    }.updateAndReturnGeneratedKey.apply()

    Object(
      id = generatedKey.toInt,
      cid = cid,
      title = title,
      typeId = typeId,
      date1 = date1,
      date2 = date2,
      pos = pos)
  }

  def batchInsert(entities: collection.Seq[Object])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'title -> entity.title,
        'typeId -> entity.typeId,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'pos -> entity.pos))
    SQL("""insert into object(
      cid,
      title,
      type_id,
      date1,
      date2,
      pos
    ) values (
      {cid},
      {title},
      {typeId},
      {date1},
      {date2},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Object)(implicit session: DBSession = autoSession): Object = {
    withSQL {
      update(Object).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.title -> entity.title,
        column.typeId -> entity.typeId,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.pos -> entity.pos
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Object)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Object).where.eq(column.id, entity.id) }.update.apply()
  }

}
