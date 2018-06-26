package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class DispatchSubscriptionContact(
  subscriptionId: Int,
  contactId: Int,
  hash: Option[String] = None) {

  def save()(implicit session: DBSession = DispatchSubscriptionContact.autoSession): DispatchSubscriptionContact = DispatchSubscriptionContact.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchSubscriptionContact.autoSession): Int = DispatchSubscriptionContact.destroy(this)(session)

}


object DispatchSubscriptionContact extends SQLSyntaxSupport[DispatchSubscriptionContact] {

  override val tableName = "dispatch_subscription_contact"

  override val columns = Seq("subscription_id", "contact_id", "hash")

  def apply(dsc: SyntaxProvider[DispatchSubscriptionContact])(rs: WrappedResultSet): DispatchSubscriptionContact = autoConstruct(rs, dsc)
  def apply(dsc: ResultName[DispatchSubscriptionContact])(rs: WrappedResultSet): DispatchSubscriptionContact = autoConstruct(rs, dsc)

  val dsc = DispatchSubscriptionContact.syntax("dsc")

  override val autoSession = AutoSession

  def find(contactId: Int, subscriptionId: Int)(implicit session: DBSession = autoSession): Option[DispatchSubscriptionContact] = {
    withSQL {
      select.from(DispatchSubscriptionContact as dsc).where.eq(dsc.contactId, contactId).and.eq(dsc.subscriptionId, subscriptionId)
    }.map(DispatchSubscriptionContact(dsc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchSubscriptionContact] = {
    withSQL(select.from(DispatchSubscriptionContact as dsc)).map(DispatchSubscriptionContact(dsc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchSubscriptionContact as dsc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchSubscriptionContact] = {
    withSQL {
      select.from(DispatchSubscriptionContact as dsc).where.append(where)
    }.map(DispatchSubscriptionContact(dsc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchSubscriptionContact] = {
    withSQL {
      select.from(DispatchSubscriptionContact as dsc).where.append(where)
    }.map(DispatchSubscriptionContact(dsc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchSubscriptionContact as dsc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    subscriptionId: Int,
    contactId: Int,
    hash: Option[String] = None)(implicit session: DBSession = autoSession): DispatchSubscriptionContact = {
    withSQL {
      insert.into(DispatchSubscriptionContact).namedValues(
        column.subscriptionId -> subscriptionId,
        column.contactId -> contactId,
        column.hash -> hash
      )
    }.update.apply()

    DispatchSubscriptionContact(
      subscriptionId = subscriptionId,
      contactId = contactId,
      hash = hash)
  }

  def batchInsert(entities: Seq[DispatchSubscriptionContact])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'subscriptionId -> entity.subscriptionId,
        'contactId -> entity.contactId,
        'hash -> entity.hash))
    SQL("""insert into dispatch_subscription_contact(
      subscription_id,
      contact_id,
      hash
    ) values (
      {subscriptionId},
      {contactId},
      {hash}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchSubscriptionContact)(implicit session: DBSession = autoSession): DispatchSubscriptionContact = {
    withSQL {
      update(DispatchSubscriptionContact).set(
        column.subscriptionId -> entity.subscriptionId,
        column.contactId -> entity.contactId,
        column.hash -> entity.hash
      ).where.eq(column.contactId, entity.contactId).and.eq(column.subscriptionId, entity.subscriptionId)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchSubscriptionContact)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchSubscriptionContact).where.eq(column.contactId, entity.contactId).and.eq(column.subscriptionId, entity.subscriptionId) }.update.apply()
  }

}
