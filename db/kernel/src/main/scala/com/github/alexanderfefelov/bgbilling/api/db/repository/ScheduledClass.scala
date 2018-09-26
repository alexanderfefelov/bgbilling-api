package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScheduledClass(
  id: Int,
  title: Option[String] = None,
  `class`: Option[String] = None) {

  def save()(implicit session: DBSession = ScheduledClass.autoSession): ScheduledClass = ScheduledClass.save(this)(session)

  def destroy()(implicit session: DBSession = ScheduledClass.autoSession): Int = ScheduledClass.destroy(this)(session)

}


object ScheduledClass extends SQLSyntaxSupport[ScheduledClass] {

  override val tableName = "scheduled_class"

  override val columns = Seq("id", "title", "class")

  def apply(sc: SyntaxProvider[ScheduledClass])(rs: WrappedResultSet): ScheduledClass = autoConstruct(rs, sc)
  def apply(sc: ResultName[ScheduledClass])(rs: WrappedResultSet): ScheduledClass = autoConstruct(rs, sc)

  val sc = ScheduledClass.syntax("sc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScheduledClass] = {
    withSQL {
      select.from(ScheduledClass as sc).where.eq(sc.id, id)
    }.map(ScheduledClass(sc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScheduledClass] = {
    withSQL(select.from(ScheduledClass as sc)).map(ScheduledClass(sc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScheduledClass as sc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScheduledClass] = {
    withSQL {
      select.from(ScheduledClass as sc).where.append(where)
    }.map(ScheduledClass(sc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScheduledClass] = {
    withSQL {
      select.from(ScheduledClass as sc).where.append(where)
    }.map(ScheduledClass(sc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScheduledClass as sc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    `class`: Option[String] = None)(implicit session: DBSession = autoSession): ScheduledClass = {
    val generatedKey = withSQL {
      insert.into(ScheduledClass).namedValues(
        column.title -> title,
        column.`class` -> `class`
      )
    }.updateAndReturnGeneratedKey.apply()

    ScheduledClass(
      id = generatedKey.toInt,
      title = title,
      `class` = `class`)
  }

  def batchInsert(entities: collection.Seq[ScheduledClass])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'class -> entity.`class`))
    SQL("""insert into scheduled_class(
      title,
      class
    ) values (
      {title},
      {class}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScheduledClass)(implicit session: DBSession = autoSession): ScheduledClass = {
    withSQL {
      update(ScheduledClass).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.`class` -> entity.`class`
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScheduledClass)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScheduledClass).where.eq(column.id, entity.id) }.update.apply()
  }

}
