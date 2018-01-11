package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class SubscrContact(
  id: Int,
  cid: Int,
  name: String,
  email: String,
  subject: String) {

  def save()(implicit session: DBSession = SubscrContact.autoSession): SubscrContact = SubscrContact.save(this)(session)

  def destroy()(implicit session: DBSession = SubscrContact.autoSession): Int = SubscrContact.destroy(this)(session)

}


object SubscrContact extends SQLSyntaxSupport[SubscrContact] {

  override val tableName = "subscr_contact"

  override val columns = Seq("id", "cid", "name", "email", "subject")

  def apply(sc: SyntaxProvider[SubscrContact])(rs: WrappedResultSet): SubscrContact = autoConstruct(rs, sc)
  def apply(sc: ResultName[SubscrContact])(rs: WrappedResultSet): SubscrContact = autoConstruct(rs, sc)

  val sc = SubscrContact.syntax("sc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[SubscrContact] = {
    withSQL {
      select.from(SubscrContact as sc).where.eq(sc.id, id)
    }.map(SubscrContact(sc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SubscrContact] = {
    withSQL(select.from(SubscrContact as sc)).map(SubscrContact(sc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SubscrContact as sc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SubscrContact] = {
    withSQL {
      select.from(SubscrContact as sc).where.append(where)
    }.map(SubscrContact(sc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SubscrContact] = {
    withSQL {
      select.from(SubscrContact as sc).where.append(where)
    }.map(SubscrContact(sc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SubscrContact as sc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    name: String,
    email: String,
    subject: String)(implicit session: DBSession = autoSession): SubscrContact = {
    val generatedKey = withSQL {
      insert.into(SubscrContact).namedValues(
        column.cid -> cid,
        column.name -> name,
        column.email -> email,
        column.subject -> subject
      )
    }.updateAndReturnGeneratedKey.apply()

    SubscrContact(
      id = generatedKey.toInt,
      cid = cid,
      name = name,
      email = email,
      subject = subject)
  }

  def batchInsert(entities: Seq[SubscrContact])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'name -> entity.name,
        'email -> entity.email,
        'subject -> entity.subject))
    SQL("""insert into subscr_contact(
      cid,
      name,
      email,
      subject
    ) values (
      {cid},
      {name},
      {email},
      {subject}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SubscrContact)(implicit session: DBSession = autoSession): SubscrContact = {
    withSQL {
      update(SubscrContact).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.name -> entity.name,
        column.email -> entity.email,
        column.subject -> entity.subject
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: SubscrContact)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SubscrContact).where.eq(column.id, entity.id) }.update.apply()
  }

}
