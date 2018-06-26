package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Service(
  id: Int,
  title: String,
  mid: Int,
  parentid: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  comment: String,
  description: String,
  lm: DateTime,
  isusing: Option[Boolean] = None,
  unit: Int) {

  def save()(implicit session: DBSession = Service.autoSession): Service = Service.save(this)(session)

  def destroy()(implicit session: DBSession = Service.autoSession): Int = Service.destroy(this)(session)

}


object Service extends SQLSyntaxSupport[Service] {

  override val tableName = "service"

  override val columns = Seq("id", "title", "mid", "parentId", "dateFrom", "dateTo", "comment", "description", "lm", "isusing", "unit")

  def apply(s: SyntaxProvider[Service])(rs: WrappedResultSet): Service = autoConstruct(rs, s)
  def apply(s: ResultName[Service])(rs: WrappedResultSet): Service = autoConstruct(rs, s)

  val s = Service.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Service] = {
    withSQL {
      select.from(Service as s).where.eq(s.id, id)
    }.map(Service(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Service] = {
    withSQL(select.from(Service as s)).map(Service(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Service as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Service] = {
    withSQL {
      select.from(Service as s).where.append(where)
    }.map(Service(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Service] = {
    withSQL {
      select.from(Service as s).where.append(where)
    }.map(Service(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Service as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    mid: Int,
    parentid: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    comment: String,
    description: String,
    lm: DateTime,
    isusing: Option[Boolean] = None,
    unit: Int)(implicit session: DBSession = autoSession): Service = {
    val generatedKey = withSQL {
      insert.into(Service).namedValues(
        column.title -> title,
        column.mid -> mid,
        column.parentid -> parentid,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.comment -> comment,
        column.description -> description,
        column.lm -> lm,
        column.isusing -> isusing,
        column.unit -> unit
      )
    }.updateAndReturnGeneratedKey.apply()

    Service(
      id = generatedKey.toInt,
      title = title,
      mid = mid,
      parentid = parentid,
      datefrom = datefrom,
      dateto = dateto,
      comment = comment,
      description = description,
      lm = lm,
      isusing = isusing,
      unit = unit)
  }

  def batchInsert(entities: Seq[Service])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'mid -> entity.mid,
        'parentid -> entity.parentid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'comment -> entity.comment,
        'description -> entity.description,
        'lm -> entity.lm,
        'isusing -> entity.isusing,
        'unit -> entity.unit))
    SQL("""insert into service(
      title,
      mid,
      parentId,
      dateFrom,
      dateTo,
      comment,
      description,
      lm,
      isusing,
      unit
    ) values (
      {title},
      {mid},
      {parentid},
      {datefrom},
      {dateto},
      {comment},
      {description},
      {lm},
      {isusing},
      {unit}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Service)(implicit session: DBSession = autoSession): Service = {
    withSQL {
      update(Service).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.mid -> entity.mid,
        column.parentid -> entity.parentid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.comment -> entity.comment,
        column.description -> entity.description,
        column.lm -> entity.lm,
        column.isusing -> entity.isusing,
        column.unit -> entity.unit
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Service)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Service).where.eq(column.id, entity.id) }.update.apply()
  }

}
