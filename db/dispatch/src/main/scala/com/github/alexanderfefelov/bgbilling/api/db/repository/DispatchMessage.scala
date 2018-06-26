package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class DispatchMessage(
  id: Int,
  dispatchId: Int,
  createDate: DateTime,
  sendDate: DateTime,
  isSent: Int,
  msgBody: String,
  msgTitle: String) {

  def save()(implicit session: DBSession = DispatchMessage.autoSession): DispatchMessage = DispatchMessage.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchMessage.autoSession): Int = DispatchMessage.destroy(this)(session)

}


object DispatchMessage extends SQLSyntaxSupport[DispatchMessage] {

  override val tableName = "dispatch_message"

  override val columns = Seq("id", "dispatch_id", "create_date", "send_date", "is_sent", "msg_body", "msg_title")

  def apply(dm: SyntaxProvider[DispatchMessage])(rs: WrappedResultSet): DispatchMessage = autoConstruct(rs, dm)
  def apply(dm: ResultName[DispatchMessage])(rs: WrappedResultSet): DispatchMessage = autoConstruct(rs, dm)

  val dm = DispatchMessage.syntax("dm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchMessage] = {
    withSQL {
      select.from(DispatchMessage as dm).where.eq(dm.id, id)
    }.map(DispatchMessage(dm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchMessage] = {
    withSQL(select.from(DispatchMessage as dm)).map(DispatchMessage(dm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchMessage as dm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchMessage] = {
    withSQL {
      select.from(DispatchMessage as dm).where.append(where)
    }.map(DispatchMessage(dm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchMessage] = {
    withSQL {
      select.from(DispatchMessage as dm).where.append(where)
    }.map(DispatchMessage(dm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchMessage as dm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dispatchId: Int,
    createDate: DateTime,
    sendDate: DateTime,
    isSent: Int,
    msgBody: String,
    msgTitle: String)(implicit session: DBSession = autoSession): DispatchMessage = {
    val generatedKey = withSQL {
      insert.into(DispatchMessage).namedValues(
        column.dispatchId -> dispatchId,
        column.createDate -> createDate,
        column.sendDate -> sendDate,
        column.isSent -> isSent,
        column.msgBody -> msgBody,
        column.msgTitle -> msgTitle
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchMessage(
      id = generatedKey.toInt,
      dispatchId = dispatchId,
      createDate = createDate,
      sendDate = sendDate,
      isSent = isSent,
      msgBody = msgBody,
      msgTitle = msgTitle)
  }

  def batchInsert(entities: Seq[DispatchMessage])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dispatchId -> entity.dispatchId,
        'createDate -> entity.createDate,
        'sendDate -> entity.sendDate,
        'isSent -> entity.isSent,
        'msgBody -> entity.msgBody,
        'msgTitle -> entity.msgTitle))
    SQL("""insert into dispatch_message(
      dispatch_id,
      create_date,
      send_date,
      is_sent,
      msg_body,
      msg_title
    ) values (
      {dispatchId},
      {createDate},
      {sendDate},
      {isSent},
      {msgBody},
      {msgTitle}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchMessage)(implicit session: DBSession = autoSession): DispatchMessage = {
    withSQL {
      update(DispatchMessage).set(
        column.id -> entity.id,
        column.dispatchId -> entity.dispatchId,
        column.createDate -> entity.createDate,
        column.sendDate -> entity.sendDate,
        column.isSent -> entity.isSent,
        column.msgBody -> entity.msgBody,
        column.msgTitle -> entity.msgTitle
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchMessage)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchMessage).where.eq(column.id, entity.id) }.update.apply()
  }

}
