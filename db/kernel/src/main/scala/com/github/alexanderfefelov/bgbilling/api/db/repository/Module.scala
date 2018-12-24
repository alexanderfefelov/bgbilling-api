package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class Module(
  id: Int,
  title: String,
  name: String) {

  def save()(implicit session: DBSession = Module.autoSession): Module = Module.save(this)(session)

  def destroy()(implicit session: DBSession = Module.autoSession): Int = Module.destroy(this)(session)

}


object Module extends SQLSyntaxSupport[Module] {

  override val tableName = "module"

  override val columns = Seq("id", "title", "name")

  def apply(m: SyntaxProvider[Module])(rs: WrappedResultSet): Module = autoConstruct(rs, m)
  def apply(m: ResultName[Module])(rs: WrappedResultSet): Module = autoConstruct(rs, m)

  val m = Module.syntax("m")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Module] = {
    withSQL {
      select.from(Module as m).where.eq(m.id, id)
    }.map(Module(m.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Module] = {
    withSQL(select.from(Module as m)).map(Module(m.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Module as m)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Module] = {
    withSQL {
      select.from(Module as m).where.append(where)
    }.map(Module(m.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Module] = {
    withSQL {
      select.from(Module as m).where.append(where)
    }.map(Module(m.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Module as m).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    name: String)(implicit session: DBSession = autoSession): Module = {
    val generatedKey = withSQL {
      insert.into(Module).namedValues(
        column.title -> title,
        column.name -> name
      )
    }.updateAndReturnGeneratedKey.apply()

    Module(
      id = generatedKey.toInt,
      title = title,
      name = name)
  }

  def batchInsert(entities: collection.Seq[Module])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'name -> entity.name))
    SQL("""insert into module(
      title,
      name
    ) values (
      {title},
      {name}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Module)(implicit session: DBSession = autoSession): Module = {
    withSQL {
      update(Module).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.name -> entity.name
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Module)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Module).where.eq(column.id, entity.id) }.update.apply()
  }

}
