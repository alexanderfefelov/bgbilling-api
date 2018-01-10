package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class MailList(
  id: Int,
  title: Option[String] = None,
  flag: Option[Int] = None,
  `type`: Option[Boolean] = None,
  up: Option[Int] = None) {

  def save()(implicit session: DBSession = MailList.autoSession): MailList = MailList.save(this)(session)

  def destroy()(implicit session: DBSession = MailList.autoSession): Int = MailList.destroy(this)(session)

}


object MailList extends SQLSyntaxSupport[MailList] {

  override val tableName = "mail_list"

  override val columns = Seq("id", "title", "flag", "type", "up")

  def apply(ml: SyntaxProvider[MailList])(rs: WrappedResultSet): MailList = autoConstruct(rs, ml)
  def apply(ml: ResultName[MailList])(rs: WrappedResultSet): MailList = autoConstruct(rs, ml)

  val ml = MailList.syntax("ml")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MailList] = {
    withSQL {
      select.from(MailList as ml).where.eq(ml.id, id)
    }.map(MailList(ml.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MailList] = {
    withSQL(select.from(MailList as ml)).map(MailList(ml.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MailList as ml)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MailList] = {
    withSQL {
      select.from(MailList as ml).where.append(where)
    }.map(MailList(ml.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MailList] = {
    withSQL {
      select.from(MailList as ml).where.append(where)
    }.map(MailList(ml.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MailList as ml).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    flag: Option[Int] = None,
    `type`: Option[Boolean] = None,
    up: Option[Int] = None)(implicit session: DBSession = autoSession): MailList = {
    val generatedKey = withSQL {
      insert.into(MailList).namedValues(
        column.title -> title,
        column.flag -> flag,
        column.`type` -> `type`,
        column.up -> up
      )
    }.updateAndReturnGeneratedKey.apply()

    MailList(
      id = generatedKey.toInt,
      title = title,
      flag = flag,
      `type` = `type`,
      up = up)
  }

  def batchInsert(entities: Seq[MailList])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'flag -> entity.flag,
        'type -> entity.`type`,
        'up -> entity.up))
    SQL("""insert into mail_list(
      title,
      flag,
      type,
      up
    ) values (
      {title},
      {flag},
      {type},
      {up}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MailList)(implicit session: DBSession = autoSession): MailList = {
    withSQL {
      update(MailList).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.flag -> entity.flag,
        column.`type` -> entity.`type`,
        column.up -> entity.up
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MailList)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MailList).where.eq(column.id, entity.id) }.update.apply()
  }

}
