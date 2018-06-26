package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class MessageForUsers(
  id: Int,
  dateFrom: DateTime,
  dateTo: DateTime,
  text: String,
  gr: String,
  title: String,
  users: String) {

  def save()(implicit session: DBSession = MessageForUsers.autoSession): MessageForUsers = MessageForUsers.save(this)(session)

  def destroy()(implicit session: DBSession = MessageForUsers.autoSession): Int = MessageForUsers.destroy(this)(session)

}


object MessageForUsers extends SQLSyntaxSupport[MessageForUsers] {

  override val tableName = "message_for_users"

  override val columns = Seq("id", "date_from", "date_to", "text", "gr", "title", "users")

  def apply(mfu: SyntaxProvider[MessageForUsers])(rs: WrappedResultSet): MessageForUsers = autoConstruct(rs, mfu)
  def apply(mfu: ResultName[MessageForUsers])(rs: WrappedResultSet): MessageForUsers = autoConstruct(rs, mfu)

  val mfu = MessageForUsers.syntax("mfu")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MessageForUsers] = {
    withSQL {
      select.from(MessageForUsers as mfu).where.eq(mfu.id, id)
    }.map(MessageForUsers(mfu.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MessageForUsers] = {
    withSQL(select.from(MessageForUsers as mfu)).map(MessageForUsers(mfu.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MessageForUsers as mfu)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MessageForUsers] = {
    withSQL {
      select.from(MessageForUsers as mfu).where.append(where)
    }.map(MessageForUsers(mfu.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MessageForUsers] = {
    withSQL {
      select.from(MessageForUsers as mfu).where.append(where)
    }.map(MessageForUsers(mfu.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MessageForUsers as mfu).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dateFrom: DateTime,
    dateTo: DateTime,
    text: String,
    gr: String,
    title: String,
    users: String)(implicit session: DBSession = autoSession): MessageForUsers = {
    val generatedKey = withSQL {
      insert.into(MessageForUsers).namedValues(
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.text -> text,
        column.gr -> gr,
        column.title -> title,
        column.users -> users
      )
    }.updateAndReturnGeneratedKey.apply()

    MessageForUsers(
      id = generatedKey.toInt,
      dateFrom = dateFrom,
      dateTo = dateTo,
      text = text,
      gr = gr,
      title = title,
      users = users)
  }

  def batchInsert(entities: Seq[MessageForUsers])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'text -> entity.text,
        'gr -> entity.gr,
        'title -> entity.title,
        'users -> entity.users))
    SQL("""insert into message_for_users(
      date_from,
      date_to,
      text,
      gr,
      title,
      users
    ) values (
      {dateFrom},
      {dateTo},
      {text},
      {gr},
      {title},
      {users}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MessageForUsers)(implicit session: DBSession = autoSession): MessageForUsers = {
    withSQL {
      update(MessageForUsers).set(
        column.id -> entity.id,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.text -> entity.text,
        column.gr -> entity.gr,
        column.title -> entity.title,
        column.users -> entity.users
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MessageForUsers)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MessageForUsers).where.eq(column.id, entity.id) }.update.apply()
  }

}
