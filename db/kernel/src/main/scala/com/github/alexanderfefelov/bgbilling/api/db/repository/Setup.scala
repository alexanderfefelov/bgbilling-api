package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class Setup(
  id: String,
  value: String) {

  def save()(implicit session: DBSession = Setup.autoSession): Setup = Setup.save(this)(session)

  def destroy()(implicit session: DBSession = Setup.autoSession): Int = Setup.destroy(this)(session)

}


object Setup extends SQLSyntaxSupport[Setup] {

  override val tableName = "setup"

  override val columns = Seq("id", "value")

  def apply(s: SyntaxProvider[Setup])(rs: WrappedResultSet): Setup = autoConstruct(rs, s)
  def apply(s: ResultName[Setup])(rs: WrappedResultSet): Setup = autoConstruct(rs, s)

  val s = Setup.syntax("s")

  override val autoSession = AutoSession

  def find(id: String)(implicit session: DBSession = autoSession): Option[Setup] = {
    withSQL {
      select.from(Setup as s).where.eq(s.id, id)
    }.map(Setup(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Setup] = {
    withSQL(select.from(Setup as s)).map(Setup(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Setup as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Setup] = {
    withSQL {
      select.from(Setup as s).where.append(where)
    }.map(Setup(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Setup] = {
    withSQL {
      select.from(Setup as s).where.append(where)
    }.map(Setup(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Setup as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: String,
    value: String)(implicit session: DBSession = autoSession): Setup = {
    withSQL {
      insert.into(Setup).namedValues(
        column.id -> id,
        column.value -> value
      )
    }.update.apply()

    Setup(
      id = id,
      value = value)
  }

  def batchInsert(entities: Seq[Setup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'value -> entity.value))
    SQL("""insert into setup(
      id,
      value
    ) values (
      {id},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Setup)(implicit session: DBSession = autoSession): Setup = {
    withSQL {
      update(Setup).set(
        column.id -> entity.id,
        column.value -> entity.value
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Setup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Setup).where.eq(column.id, entity.id) }.update.apply()
  }

}
