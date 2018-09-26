package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class Script(
  id: Int,
  title: String) {

  def save()(implicit session: DBSession = Script.autoSession): Script = Script.save(this)(session)

  def destroy()(implicit session: DBSession = Script.autoSession): Int = Script.destroy(this)(session)

}


object Script extends SQLSyntaxSupport[Script] {

  override val tableName = "script"

  override val columns = Seq("id", "title")

  def apply(s: SyntaxProvider[Script])(rs: WrappedResultSet): Script = autoConstruct(rs, s)
  def apply(s: ResultName[Script])(rs: WrappedResultSet): Script = autoConstruct(rs, s)

  val s = Script.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Script] = {
    withSQL {
      select.from(Script as s).where.eq(s.id, id)
    }.map(Script(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Script] = {
    withSQL(select.from(Script as s)).map(Script(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Script as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Script] = {
    withSQL {
      select.from(Script as s).where.append(where)
    }.map(Script(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Script] = {
    withSQL {
      select.from(Script as s).where.append(where)
    }.map(Script(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Script as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): Script = {
    val generatedKey = withSQL {
      insert.into(Script).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    Script(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: collection.Seq[Script])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into script(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Script)(implicit session: DBSession = autoSession): Script = {
    withSQL {
      update(Script).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Script)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Script).where.eq(column.id, entity.id) }.update.apply()
  }

}
