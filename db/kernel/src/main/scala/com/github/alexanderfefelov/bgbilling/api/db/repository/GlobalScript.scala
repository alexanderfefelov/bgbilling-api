package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class GlobalScript(
  id: Int,
  title: Option[String] = None,
  script: Option[String] = None,
  userId: Int,
  changeTime: DateTime) {

  def save()(implicit session: DBSession = GlobalScript.autoSession): GlobalScript = GlobalScript.save(this)(session)

  def destroy()(implicit session: DBSession = GlobalScript.autoSession): Int = GlobalScript.destroy(this)(session)

}


object GlobalScript extends SQLSyntaxSupport[GlobalScript] {

  override val tableName = "global_script"

  override val columns = Seq("id", "title", "script", "user_id", "change_time")

  def apply(gs: SyntaxProvider[GlobalScript])(rs: WrappedResultSet): GlobalScript = autoConstruct(rs, gs)
  def apply(gs: ResultName[GlobalScript])(rs: WrappedResultSet): GlobalScript = autoConstruct(rs, gs)

  val gs = GlobalScript.syntax("gs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[GlobalScript] = {
    withSQL {
      select.from(GlobalScript as gs).where.eq(gs.id, id)
    }.map(GlobalScript(gs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GlobalScript] = {
    withSQL(select.from(GlobalScript as gs)).map(GlobalScript(gs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GlobalScript as gs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GlobalScript] = {
    withSQL {
      select.from(GlobalScript as gs).where.append(where)
    }.map(GlobalScript(gs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GlobalScript] = {
    withSQL {
      select.from(GlobalScript as gs).where.append(where)
    }.map(GlobalScript(gs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GlobalScript as gs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    script: Option[String] = None,
    userId: Int,
    changeTime: DateTime)(implicit session: DBSession = autoSession): GlobalScript = {
    val generatedKey = withSQL {
      insert.into(GlobalScript).namedValues(
        column.title -> title,
        column.script -> script,
        column.userId -> userId,
        column.changeTime -> changeTime
      )
    }.updateAndReturnGeneratedKey.apply()

    GlobalScript(
      id = generatedKey.toInt,
      title = title,
      script = script,
      userId = userId,
      changeTime = changeTime)
  }

  def batchInsert(entities: Seq[GlobalScript])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'script -> entity.script,
        'userId -> entity.userId,
        'changeTime -> entity.changeTime))
    SQL("""insert into global_script(
      title,
      script,
      user_id,
      change_time
    ) values (
      {title},
      {script},
      {userId},
      {changeTime}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: GlobalScript)(implicit session: DBSession = autoSession): GlobalScript = {
    withSQL {
      update(GlobalScript).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.script -> entity.script,
        column.userId -> entity.userId,
        column.changeTime -> entity.changeTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: GlobalScript)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(GlobalScript).where.eq(column.id, entity.id) }.update.apply()
  }

}
