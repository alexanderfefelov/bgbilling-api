package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Source(
  id: Int,
  mid: Int,
  title: String,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  comment: String,
  sourceType: Byte,
  hostOrDir: String,
  user: String,
  pswd: String,
  config: String,
  lm: DateTime) {

  def save()(implicit session: DBSession = Source.autoSession): Source = Source.save(this)(session)

  def destroy()(implicit session: DBSession = Source.autoSession): Int = Source.destroy(this)(session)

}


object Source extends SQLSyntaxSupport[Source] {

  override val tableName = "source"

  override val columns = Seq("id", "mid", "title", "date1", "date2", "comment", "source_type", "host_or_dir", "user", "pswd", "config", "lm")

  def apply(s: SyntaxProvider[Source])(rs: WrappedResultSet): Source = autoConstruct(rs, s)
  def apply(s: ResultName[Source])(rs: WrappedResultSet): Source = autoConstruct(rs, s)

  val s = Source.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Source] = {
    withSQL {
      select.from(Source as s).where.eq(s.id, id)
    }.map(Source(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Source] = {
    withSQL(select.from(Source as s)).map(Source(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Source as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Source] = {
    withSQL {
      select.from(Source as s).where.append(where)
    }.map(Source(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Source] = {
    withSQL {
      select.from(Source as s).where.append(where)
    }.map(Source(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Source as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: Int,
    title: String,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    comment: String,
    sourceType: Byte,
    hostOrDir: String,
    user: String,
    pswd: String,
    config: String,
    lm: DateTime)(implicit session: DBSession = autoSession): Source = {
    val generatedKey = withSQL {
      insert.into(Source).namedValues(
        column.mid -> mid,
        column.title -> title,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment,
        column.sourceType -> sourceType,
        column.hostOrDir -> hostOrDir,
        column.user -> user,
        column.pswd -> pswd,
        column.config -> config,
        column.lm -> lm
      )
    }.updateAndReturnGeneratedKey.apply()

    Source(
      id = generatedKey.toInt,
      mid = mid,
      title = title,
      date1 = date1,
      date2 = date2,
      comment = comment,
      sourceType = sourceType,
      hostOrDir = hostOrDir,
      user = user,
      pswd = pswd,
      config = config,
      lm = lm)
  }

  def batchInsert(entities: collection.Seq[Source])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'title -> entity.title,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment,
        'sourceType -> entity.sourceType,
        'hostOrDir -> entity.hostOrDir,
        'user -> entity.user,
        'pswd -> entity.pswd,
        'config -> entity.config,
        'lm -> entity.lm))
    SQL("""insert into source(
      mid,
      title,
      date1,
      date2,
      comment,
      source_type,
      host_or_dir,
      user,
      pswd,
      config,
      lm
    ) values (
      {mid},
      {title},
      {date1},
      {date2},
      {comment},
      {sourceType},
      {hostOrDir},
      {user},
      {pswd},
      {config},
      {lm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Source)(implicit session: DBSession = autoSession): Source = {
    withSQL {
      update(Source).set(
        column.id -> entity.id,
        column.mid -> entity.mid,
        column.title -> entity.title,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment,
        column.sourceType -> entity.sourceType,
        column.hostOrDir -> entity.hostOrDir,
        column.user -> entity.user,
        column.pswd -> entity.pswd,
        column.config -> entity.config,
        column.lm -> entity.lm
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Source)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Source).where.eq(column.id, entity.id) }.update.apply()
  }

}
