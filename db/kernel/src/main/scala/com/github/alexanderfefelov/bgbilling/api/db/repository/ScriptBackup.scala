package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ScriptBackup(
  id: Int,
  title: Option[String] = None,
  scriptId: Int,
  script: Option[String] = None,
  userId: Int,
  changeTime: DateTime,
  comment: Option[String] = None,
  `type`: Byte) {

  def save()(implicit session: DBSession = ScriptBackup.autoSession): ScriptBackup = ScriptBackup.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptBackup.autoSession): Int = ScriptBackup.destroy(this)(session)

}


object ScriptBackup extends SQLSyntaxSupport[ScriptBackup] {

  override val tableName = "script_backup"

  override val columns = Seq("id", "title", "script_id", "script", "user_id", "change_time", "comment", "type")

  def apply(sb: SyntaxProvider[ScriptBackup])(rs: WrappedResultSet): ScriptBackup = autoConstruct(rs, sb)
  def apply(sb: ResultName[ScriptBackup])(rs: WrappedResultSet): ScriptBackup = autoConstruct(rs, sb)

  val sb = ScriptBackup.syntax("sb")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ScriptBackup] = {
    withSQL {
      select.from(ScriptBackup as sb).where.eq(sb.id, id)
    }.map(ScriptBackup(sb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptBackup] = {
    withSQL(select.from(ScriptBackup as sb)).map(ScriptBackup(sb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptBackup as sb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptBackup] = {
    withSQL {
      select.from(ScriptBackup as sb).where.append(where)
    }.map(ScriptBackup(sb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptBackup] = {
    withSQL {
      select.from(ScriptBackup as sb).where.append(where)
    }.map(ScriptBackup(sb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptBackup as sb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    scriptId: Int,
    script: Option[String] = None,
    userId: Int,
    changeTime: DateTime,
    comment: Option[String] = None,
    `type`: Byte)(implicit session: DBSession = autoSession): ScriptBackup = {
    val generatedKey = withSQL {
      insert.into(ScriptBackup).namedValues(
        column.title -> title,
        column.scriptId -> scriptId,
        column.script -> script,
        column.userId -> userId,
        column.changeTime -> changeTime,
        column.comment -> comment,
        column.`type` -> `type`
      )
    }.updateAndReturnGeneratedKey.apply()

    ScriptBackup(
      id = generatedKey.toInt,
      title = title,
      scriptId = scriptId,
      script = script,
      userId = userId,
      changeTime = changeTime,
      comment = comment,
      `type` = `type`)
  }

  def batchInsert(entities: collection.Seq[ScriptBackup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'scriptId -> entity.scriptId,
        'script -> entity.script,
        'userId -> entity.userId,
        'changeTime -> entity.changeTime,
        'comment -> entity.comment,
        'type -> entity.`type`))
    SQL("""insert into script_backup(
      title,
      script_id,
      script,
      user_id,
      change_time,
      comment,
      type
    ) values (
      {title},
      {scriptId},
      {script},
      {userId},
      {changeTime},
      {comment},
      {type}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptBackup)(implicit session: DBSession = autoSession): ScriptBackup = {
    withSQL {
      update(ScriptBackup).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.scriptId -> entity.scriptId,
        column.script -> entity.script,
        column.userId -> entity.userId,
        column.changeTime -> entity.changeTime,
        column.comment -> entity.comment,
        column.`type` -> entity.`type`
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptBackup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptBackup).where.eq(column.id, entity.id) }.update.apply()
  }

}
