package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScriptClasses(
  name: String,
  lastMod: Long,
  data: Array[Byte]) {

  def save()(implicit session: DBSession = ScriptClasses.autoSession): ScriptClasses = ScriptClasses.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptClasses.autoSession): Int = ScriptClasses.destroy(this)(session)

}


object ScriptClasses extends SQLSyntaxSupport[ScriptClasses] {

  override val tableName = "script_classes"

  override val columns = Seq("name", "last_mod", "data")

  def apply(sc: SyntaxProvider[ScriptClasses])(rs: WrappedResultSet): ScriptClasses = autoConstruct(rs, sc)
  def apply(sc: ResultName[ScriptClasses])(rs: WrappedResultSet): ScriptClasses = autoConstruct(rs, sc)

  val sc = ScriptClasses.syntax("sc")

  override val autoSession = AutoSession

  def find(name: String)(implicit session: DBSession = autoSession): Option[ScriptClasses] = {
    withSQL {
      select.from(ScriptClasses as sc).where.eq(sc.name, name)
    }.map(ScriptClasses(sc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptClasses] = {
    withSQL(select.from(ScriptClasses as sc)).map(ScriptClasses(sc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptClasses as sc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptClasses] = {
    withSQL {
      select.from(ScriptClasses as sc).where.append(where)
    }.map(ScriptClasses(sc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptClasses] = {
    withSQL {
      select.from(ScriptClasses as sc).where.append(where)
    }.map(ScriptClasses(sc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptClasses as sc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    lastMod: Long,
    data: Array[Byte])(implicit session: DBSession = autoSession): ScriptClasses = {
    withSQL {
      insert.into(ScriptClasses).namedValues(
        column.name -> name,
        column.lastMod -> lastMod,
        column.data -> data
      )
    }.update.apply()

    ScriptClasses(
      name = name,
      lastMod = lastMod,
      data = data)
  }

  def batchInsert(entities: collection.Seq[ScriptClasses])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'lastMod -> entity.lastMod,
        'data -> entity.data))
    SQL("""insert into script_classes(
      name,
      last_mod,
      data
    ) values (
      {name},
      {lastMod},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptClasses)(implicit session: DBSession = autoSession): ScriptClasses = {
    withSQL {
      update(ScriptClasses).set(
        column.name -> entity.name,
        column.lastMod -> entity.lastMod,
        column.data -> entity.data
      ).where.eq(column.name, entity.name)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptClasses)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptClasses).where.eq(column.name, entity.name) }.update.apply()
  }

}
