package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class SqlTemplate(
  id: Int,
  userId: Int,
  title: String,
  text: String) {

  def save()(implicit session: DBSession = SqlTemplate.autoSession): SqlTemplate = SqlTemplate.save(this)(session)

  def destroy()(implicit session: DBSession = SqlTemplate.autoSession): Int = SqlTemplate.destroy(this)(session)

}


object SqlTemplate extends SQLSyntaxSupport[SqlTemplate] {

  override val tableName = "sql_template"

  override val columns = Seq("id", "user_id", "title", "text")

  def apply(st: SyntaxProvider[SqlTemplate])(rs: WrappedResultSet): SqlTemplate = autoConstruct(rs, st)
  def apply(st: ResultName[SqlTemplate])(rs: WrappedResultSet): SqlTemplate = autoConstruct(rs, st)

  val st = SqlTemplate.syntax("st")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[SqlTemplate] = {
    withSQL {
      select.from(SqlTemplate as st).where.eq(st.id, id)
    }.map(SqlTemplate(st.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SqlTemplate] = {
    withSQL(select.from(SqlTemplate as st)).map(SqlTemplate(st.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SqlTemplate as st)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SqlTemplate] = {
    withSQL {
      select.from(SqlTemplate as st).where.append(where)
    }.map(SqlTemplate(st.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SqlTemplate] = {
    withSQL {
      select.from(SqlTemplate as st).where.append(where)
    }.map(SqlTemplate(st.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SqlTemplate as st).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    title: String,
    text: String)(implicit session: DBSession = autoSession): SqlTemplate = {
    val generatedKey = withSQL {
      insert.into(SqlTemplate).namedValues(
        column.userId -> userId,
        column.title -> title,
        column.text -> text
      )
    }.updateAndReturnGeneratedKey.apply()

    SqlTemplate(
      id = generatedKey.toInt,
      userId = userId,
      title = title,
      text = text)
  }

  def batchInsert(entities: collection.Seq[SqlTemplate])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'title -> entity.title,
        'text -> entity.text))
    SQL("""insert into sql_template(
      user_id,
      title,
      text
    ) values (
      {userId},
      {title},
      {text}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SqlTemplate)(implicit session: DBSession = autoSession): SqlTemplate = {
    withSQL {
      update(SqlTemplate).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.title -> entity.title,
        column.text -> entity.text
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: SqlTemplate)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SqlTemplate).where.eq(column.id, entity.id) }.update.apply()
  }

}
