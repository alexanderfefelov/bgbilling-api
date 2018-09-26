package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvServiceSpec(
  id: Int,
  entityid: Int,
  moduleid: Int,
  parentid: Int,
  title: String,
  identifier: String,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  comment: String,
  description: String) {

  def save()(implicit session: DBSession = InvServiceSpec.autoSession): InvServiceSpec = InvServiceSpec.save(this)(session)

  def destroy()(implicit session: DBSession = InvServiceSpec.autoSession): Int = InvServiceSpec.destroy(this)(session)

}


object InvServiceSpec extends SQLSyntaxSupport[InvServiceSpec] {

  override val tableName = "inv_service_spec"

  override val columns = Seq("id", "entityId", "moduleId", "parentId", "title", "identifier", "dateFrom", "dateTo", "comment", "description")

  def apply(iss: SyntaxProvider[InvServiceSpec])(rs: WrappedResultSet): InvServiceSpec = autoConstruct(rs, iss)
  def apply(iss: ResultName[InvServiceSpec])(rs: WrappedResultSet): InvServiceSpec = autoConstruct(rs, iss)

  val iss = InvServiceSpec.syntax("iss")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvServiceSpec] = {
    withSQL {
      select.from(InvServiceSpec as iss).where.eq(iss.id, id)
    }.map(InvServiceSpec(iss.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvServiceSpec] = {
    withSQL(select.from(InvServiceSpec as iss)).map(InvServiceSpec(iss.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvServiceSpec as iss)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvServiceSpec] = {
    withSQL {
      select.from(InvServiceSpec as iss).where.append(where)
    }.map(InvServiceSpec(iss.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvServiceSpec] = {
    withSQL {
      select.from(InvServiceSpec as iss).where.append(where)
    }.map(InvServiceSpec(iss.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvServiceSpec as iss).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    moduleid: Int,
    parentid: Int,
    title: String,
    identifier: String,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    comment: String,
    description: String)(implicit session: DBSession = autoSession): InvServiceSpec = {
    val generatedKey = withSQL {
      insert.into(InvServiceSpec).namedValues(
        column.entityid -> entityid,
        column.moduleid -> moduleid,
        column.parentid -> parentid,
        column.title -> title,
        column.identifier -> identifier,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.comment -> comment,
        column.description -> description
      )
    }.updateAndReturnGeneratedKey.apply()

    InvServiceSpec(
      id = generatedKey.toInt,
      entityid = entityid,
      moduleid = moduleid,
      parentid = parentid,
      title = title,
      identifier = identifier,
      datefrom = datefrom,
      dateto = dateto,
      comment = comment,
      description = description)
  }

  def batchInsert(entities: collection.Seq[InvServiceSpec])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'moduleid -> entity.moduleid,
        'parentid -> entity.parentid,
        'title -> entity.title,
        'identifier -> entity.identifier,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'comment -> entity.comment,
        'description -> entity.description))
    SQL("""insert into inv_service_spec(
      entityId,
      moduleId,
      parentId,
      title,
      identifier,
      dateFrom,
      dateTo,
      comment,
      description
    ) values (
      {entityid},
      {moduleid},
      {parentid},
      {title},
      {identifier},
      {datefrom},
      {dateto},
      {comment},
      {description}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvServiceSpec)(implicit session: DBSession = autoSession): InvServiceSpec = {
    withSQL {
      update(InvServiceSpec).set(
        column.id -> entity.id,
        column.entityid -> entity.entityid,
        column.moduleid -> entity.moduleid,
        column.parentid -> entity.parentid,
        column.title -> entity.title,
        column.identifier -> entity.identifier,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.comment -> entity.comment,
        column.description -> entity.description
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvServiceSpec)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvServiceSpec).where.eq(column.id, entity.id) }.update.apply()
  }

}
