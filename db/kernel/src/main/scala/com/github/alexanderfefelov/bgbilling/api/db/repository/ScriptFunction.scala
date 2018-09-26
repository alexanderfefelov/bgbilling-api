package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ScriptFunction(
  id: Int,
  scriptId: Int,
  title: String,
  code: Option[String] = None,
  userId: Int,
  changeTime: DateTime) {

  def save()(implicit session: DBSession = ScriptFunction.autoSession): ScriptFunction = ScriptFunction.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptFunction.autoSession): Int = ScriptFunction.destroy(this)(session)

}


object ScriptFunction extends SQLSyntaxSupport[ScriptFunction] {

  override val tableName = "script_function"

  override val columns = Seq("id", "script_id", "title", "code", "user_id", "change_time")

  def apply(sf: SyntaxProvider[ScriptFunction])(rs: WrappedResultSet): ScriptFunction = autoConstruct(rs, sf)
  def apply(sf: ResultName[ScriptFunction])(rs: WrappedResultSet): ScriptFunction = autoConstruct(rs, sf)

  val sf = ScriptFunction.syntax("sf")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScriptFunction] = {
    withSQL {
      select.from(ScriptFunction as sf).where.eq(sf.id, id)
    }.map(ScriptFunction(sf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptFunction] = {
    withSQL(select.from(ScriptFunction as sf)).map(ScriptFunction(sf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptFunction as sf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptFunction] = {
    withSQL {
      select.from(ScriptFunction as sf).where.append(where)
    }.map(ScriptFunction(sf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptFunction] = {
    withSQL {
      select.from(ScriptFunction as sf).where.append(where)
    }.map(ScriptFunction(sf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptFunction as sf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    scriptId: Int,
    title: String,
    code: Option[String] = None,
    userId: Int,
    changeTime: DateTime)(implicit session: DBSession = autoSession): ScriptFunction = {
    val generatedKey = withSQL {
      insert.into(ScriptFunction).namedValues(
        column.scriptId -> scriptId,
        column.title -> title,
        column.code -> code,
        column.userId -> userId,
        column.changeTime -> changeTime
      )
    }.updateAndReturnGeneratedKey.apply()

    ScriptFunction(
      id = generatedKey.toInt,
      scriptId = scriptId,
      title = title,
      code = code,
      userId = userId,
      changeTime = changeTime)
  }

  def batchInsert(entities: collection.Seq[ScriptFunction])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'scriptId -> entity.scriptId,
        'title -> entity.title,
        'code -> entity.code,
        'userId -> entity.userId,
        'changeTime -> entity.changeTime))
    SQL("""insert into script_function(
      script_id,
      title,
      code,
      user_id,
      change_time
    ) values (
      {scriptId},
      {title},
      {code},
      {userId},
      {changeTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptFunction)(implicit session: DBSession = autoSession): ScriptFunction = {
    withSQL {
      update(ScriptFunction).set(
        column.id -> entity.id,
        column.scriptId -> entity.scriptId,
        column.title -> entity.title,
        column.code -> entity.code,
        column.userId -> entity.userId,
        column.changeTime -> entity.changeTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptFunction)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptFunction).where.eq(column.id, entity.id) }.update.apply()
  }

}
