package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class GlobalScriptLink(
  id: Int,
  title: String,
  className: String) {

  def save()(implicit session: DBSession = GlobalScriptLink.autoSession): GlobalScriptLink = GlobalScriptLink.save(this)(session)

  def destroy()(implicit session: DBSession = GlobalScriptLink.autoSession): Int = GlobalScriptLink.destroy(this)(session)

}


object GlobalScriptLink extends SQLSyntaxSupport[GlobalScriptLink] {

  override val tableName = "global_script_link"

  override val columns = Seq("id", "title", "class_name")

  def apply(gsl: SyntaxProvider[GlobalScriptLink])(rs: WrappedResultSet): GlobalScriptLink = autoConstruct(rs, gsl)
  def apply(gsl: ResultName[GlobalScriptLink])(rs: WrappedResultSet): GlobalScriptLink = autoConstruct(rs, gsl)

  val gsl = GlobalScriptLink.syntax("gsl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GlobalScriptLink] = {
    withSQL {
      select.from(GlobalScriptLink as gsl).where.eq(gsl.id, id)
    }.map(GlobalScriptLink(gsl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GlobalScriptLink] = {
    withSQL(select.from(GlobalScriptLink as gsl)).map(GlobalScriptLink(gsl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GlobalScriptLink as gsl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GlobalScriptLink] = {
    withSQL {
      select.from(GlobalScriptLink as gsl).where.append(where)
    }.map(GlobalScriptLink(gsl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GlobalScriptLink] = {
    withSQL {
      select.from(GlobalScriptLink as gsl).where.append(where)
    }.map(GlobalScriptLink(gsl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GlobalScriptLink as gsl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    className: String)(implicit session: DBSession = autoSession): GlobalScriptLink = {
    val generatedKey = withSQL {
      insert.into(GlobalScriptLink).namedValues(
        column.title -> title,
        column.className -> className
      )
    }.updateAndReturnGeneratedKey.apply()

    GlobalScriptLink(
      id = generatedKey.toInt,
      title = title,
      className = className)
  }

  def batchInsert(entities: Seq[GlobalScriptLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'className -> entity.className))
    SQL("""insert into global_script_link(
      title,
      class_name
    ) values (
      {title},
      {className}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: GlobalScriptLink)(implicit session: DBSession = autoSession): GlobalScriptLink = {
    withSQL {
      update(GlobalScriptLink).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.className -> entity.className
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: GlobalScriptLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GlobalScriptLink).where.eq(column.id, entity.id) }.update.apply()
  }

}
