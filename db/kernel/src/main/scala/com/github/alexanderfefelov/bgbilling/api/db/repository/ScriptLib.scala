package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ScriptLib(
  id: Int,
  name: String,
  title: String,
  script: String,
  userId: Int,
  changeTime: DateTime) {

  def save()(implicit session: DBSession = ScriptLib.autoSession): ScriptLib = ScriptLib.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptLib.autoSession): Int = ScriptLib.destroy(this)(session)

}


object ScriptLib extends SQLSyntaxSupport[ScriptLib] {

  override val tableName = "script_lib"

  override val columns = Seq("id", "name", "title", "script", "user_id", "change_time")

  def apply(sl: SyntaxProvider[ScriptLib])(rs: WrappedResultSet): ScriptLib = autoConstruct(rs, sl)
  def apply(sl: ResultName[ScriptLib])(rs: WrappedResultSet): ScriptLib = autoConstruct(rs, sl)

  val sl = ScriptLib.syntax("sl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScriptLib] = {
    withSQL {
      select.from(ScriptLib as sl).where.eq(sl.id, id)
    }.map(ScriptLib(sl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptLib] = {
    withSQL(select.from(ScriptLib as sl)).map(ScriptLib(sl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptLib as sl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptLib] = {
    withSQL {
      select.from(ScriptLib as sl).where.append(where)
    }.map(ScriptLib(sl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptLib] = {
    withSQL {
      select.from(ScriptLib as sl).where.append(where)
    }.map(ScriptLib(sl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptLib as sl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    title: String,
    script: String,
    userId: Int,
    changeTime: DateTime)(implicit session: DBSession = autoSession): ScriptLib = {
    val generatedKey = withSQL {
      insert.into(ScriptLib).namedValues(
        column.name -> name,
        column.title -> title,
        column.script -> script,
        column.userId -> userId,
        column.changeTime -> changeTime
      )
    }.updateAndReturnGeneratedKey.apply()

    ScriptLib(
      id = generatedKey.toInt,
      name = name,
      title = title,
      script = script,
      userId = userId,
      changeTime = changeTime)
  }

  def batchInsert(entities: Seq[ScriptLib])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'title -> entity.title,
        'script -> entity.script,
        'userId -> entity.userId,
        'changeTime -> entity.changeTime))
    SQL("""insert into script_lib(
      name,
      title,
      script,
      user_id,
      change_time
    ) values (
      {name},
      {title},
      {script},
      {userId},
      {changeTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptLib)(implicit session: DBSession = autoSession): ScriptLib = {
    withSQL {
      update(ScriptLib).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.title -> entity.title,
        column.script -> entity.script,
        column.userId -> entity.userId,
        column.changeTime -> entity.changeTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptLib)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptLib).where.eq(column.id, entity.id) }.update.apply()
  }

}
