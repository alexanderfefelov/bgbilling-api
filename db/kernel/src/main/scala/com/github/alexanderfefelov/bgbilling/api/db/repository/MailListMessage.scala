package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class MailListMessage(
  id: Int,
  subject: Option[String] = None,
  text: Option[String] = None) {

  def save()(implicit session: DBSession = MailListMessage.autoSession): MailListMessage = MailListMessage.save(this)(session)

  def destroy()(implicit session: DBSession = MailListMessage.autoSession): Int = MailListMessage.destroy(this)(session)

}


object MailListMessage extends SQLSyntaxSupport[MailListMessage] {

  override val tableName = "mail_list_message"

  override val columns = Seq("id", "subject", "text")

  def apply(mlm: SyntaxProvider[MailListMessage])(rs: WrappedResultSet): MailListMessage = autoConstruct(rs, mlm)
  def apply(mlm: ResultName[MailListMessage])(rs: WrappedResultSet): MailListMessage = autoConstruct(rs, mlm)

  val mlm = MailListMessage.syntax("mlm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MailListMessage] = {
    withSQL {
      select.from(MailListMessage as mlm).where.eq(mlm.id, id)
    }.map(MailListMessage(mlm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MailListMessage] = {
    withSQL(select.from(MailListMessage as mlm)).map(MailListMessage(mlm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MailListMessage as mlm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MailListMessage] = {
    withSQL {
      select.from(MailListMessage as mlm).where.append(where)
    }.map(MailListMessage(mlm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MailListMessage] = {
    withSQL {
      select.from(MailListMessage as mlm).where.append(where)
    }.map(MailListMessage(mlm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MailListMessage as mlm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    subject: Option[String] = None,
    text: Option[String] = None)(implicit session: DBSession = autoSession): MailListMessage = {
    withSQL {
      insert.into(MailListMessage).namedValues(
        column.id -> id,
        column.subject -> subject,
        column.text -> text
      )
    }.update.apply()

    MailListMessage(
      id = id,
      subject = subject,
      text = text)
  }

  def batchInsert(entities: Seq[MailListMessage])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'subject -> entity.subject,
        'text -> entity.text))
    SQL("""insert into mail_list_message(
      id,
      subject,
      text
    ) values (
      {id},
      {subject},
      {text}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MailListMessage)(implicit session: DBSession = autoSession): MailListMessage = {
    withSQL {
      update(MailListMessage).set(
        column.id -> entity.id,
        column.subject -> entity.subject,
        column.text -> entity.text
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MailListMessage)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MailListMessage).where.eq(column.id, entity.id) }.update.apply()
  }

}
