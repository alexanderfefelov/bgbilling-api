package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class DispatchContactType(
  id: Int,
  title: String,
  pattern: String,
  description: String,
  pid: Int) {

  def save()(implicit session: DBSession = DispatchContactType.autoSession): DispatchContactType = DispatchContactType.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchContactType.autoSession): Int = DispatchContactType.destroy(this)(session)

}


object DispatchContactType extends SQLSyntaxSupport[DispatchContactType] {

  override val tableName = "dispatch_contact_type"

  override val columns = Seq("id", "title", "pattern", "description", "pid")

  def apply(dct: SyntaxProvider[DispatchContactType])(rs: WrappedResultSet): DispatchContactType = autoConstruct(rs, dct)
  def apply(dct: ResultName[DispatchContactType])(rs: WrappedResultSet): DispatchContactType = autoConstruct(rs, dct)

  val dct = DispatchContactType.syntax("dct")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchContactType] = {
    withSQL {
      select.from(DispatchContactType as dct).where.eq(dct.id, id)
    }.map(DispatchContactType(dct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchContactType] = {
    withSQL(select.from(DispatchContactType as dct)).map(DispatchContactType(dct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchContactType as dct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchContactType] = {
    withSQL {
      select.from(DispatchContactType as dct).where.append(where)
    }.map(DispatchContactType(dct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchContactType] = {
    withSQL {
      select.from(DispatchContactType as dct).where.append(where)
    }.map(DispatchContactType(dct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchContactType as dct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    pattern: String,
    description: String,
    pid: Int)(implicit session: DBSession = autoSession): DispatchContactType = {
    val generatedKey = withSQL {
      insert.into(DispatchContactType).namedValues(
        column.title -> title,
        column.pattern -> pattern,
        column.description -> description,
        column.pid -> pid
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchContactType(
      id = generatedKey.toInt,
      title = title,
      pattern = pattern,
      description = description,
      pid = pid)
  }

  def batchInsert(entities: Seq[DispatchContactType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'pattern -> entity.pattern,
        'description -> entity.description,
        'pid -> entity.pid))
    SQL("""insert into dispatch_contact_type(
      title,
      pattern,
      description,
      pid
    ) values (
      {title},
      {pattern},
      {description},
      {pid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchContactType)(implicit session: DBSession = autoSession): DispatchContactType = {
    withSQL {
      update(DispatchContactType).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.pattern -> entity.pattern,
        column.description -> entity.description,
        column.pid -> entity.pid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchContactType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchContactType).where.eq(column.id, entity.id) }.update.apply()
  }

}
