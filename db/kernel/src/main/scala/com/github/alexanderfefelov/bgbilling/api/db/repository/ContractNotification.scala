package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractNotification(
  id: Int,
  cid: Int,
  subject: String,
  message: String,
  dt: DateTime,
  isRead: Boolean) {

  def save()(implicit session: DBSession = ContractNotification.autoSession): ContractNotification = ContractNotification.save(this)(session)

  def destroy()(implicit session: DBSession = ContractNotification.autoSession): Int = ContractNotification.destroy(this)(session)

}


object ContractNotification extends SQLSyntaxSupport[ContractNotification] {

  override val tableName = "contract_notification"

  override val columns = Seq("id", "cid", "subject", "message", "dt", "is_read")

  def apply(cn: SyntaxProvider[ContractNotification])(rs: WrappedResultSet): ContractNotification = autoConstruct(rs, cn)
  def apply(cn: ResultName[ContractNotification])(rs: WrappedResultSet): ContractNotification = autoConstruct(rs, cn)

  val cn = ContractNotification.syntax("cn")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractNotification] = {
    withSQL {
      select.from(ContractNotification as cn).where.eq(cn.id, id)
    }.map(ContractNotification(cn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractNotification] = {
    withSQL(select.from(ContractNotification as cn)).map(ContractNotification(cn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractNotification as cn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractNotification] = {
    withSQL {
      select.from(ContractNotification as cn).where.append(where)
    }.map(ContractNotification(cn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractNotification] = {
    withSQL {
      select.from(ContractNotification as cn).where.append(where)
    }.map(ContractNotification(cn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractNotification as cn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    subject: String,
    message: String,
    dt: DateTime,
    isRead: Boolean)(implicit session: DBSession = autoSession): ContractNotification = {
    val generatedKey = withSQL {
      insert.into(ContractNotification).namedValues(
        column.cid -> cid,
        column.subject -> subject,
        column.message -> message,
        column.dt -> dt,
        column.isRead -> isRead
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractNotification(
      id = generatedKey.toInt,
      cid = cid,
      subject = subject,
      message = message,
      dt = dt,
      isRead = isRead)
  }

  def batchInsert(entities: collection.Seq[ContractNotification])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'subject -> entity.subject,
        'message -> entity.message,
        'dt -> entity.dt,
        'isRead -> entity.isRead))
    SQL("""insert into contract_notification(
      cid,
      subject,
      message,
      dt,
      is_read
    ) values (
      {cid},
      {subject},
      {message},
      {dt},
      {isRead}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractNotification)(implicit session: DBSession = autoSession): ContractNotification = {
    withSQL {
      update(ContractNotification).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.subject -> entity.subject,
        column.message -> entity.message,
        column.dt -> entity.dt,
        column.isRead -> entity.isRead
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractNotification)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractNotification).where.eq(column.id, entity.id) }.update.apply()
  }

}
