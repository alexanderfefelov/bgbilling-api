package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class DispatchDispatch(
  id: Int,
  title: String,
  senderTypeId: Int,
  active: Boolean,
  personal: Boolean,
  doNotMarkSended: Boolean,
  onlyOneContact: Boolean,
  user: Option[Int] = None,
  createDate: Option[DateTime] = None,
  lastModifyDate: Option[DateTime] = None,
  repeatTime: String,
  contactTypeId: Int,
  conditions: Array[Byte],
  woContactType: Boolean) {

  def save()(implicit session: DBSession = DispatchDispatch.autoSession): DispatchDispatch = DispatchDispatch.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchDispatch.autoSession): Int = DispatchDispatch.destroy(this)(session)

}


object DispatchDispatch extends SQLSyntaxSupport[DispatchDispatch] {

  override val tableName = "dispatch_dispatch"

  override val columns = Seq("id", "title", "sender_type_id", "active", "personal", "do_not_mark_sended", "only_one_contact", "user", "create_date", "last_modify_date", "repeat_time", "contact_type_id", "conditions", "wo_contact_type")

  def apply(dd: SyntaxProvider[DispatchDispatch])(rs: WrappedResultSet): DispatchDispatch = autoConstruct(rs, dd)
  def apply(dd: ResultName[DispatchDispatch])(rs: WrappedResultSet): DispatchDispatch = autoConstruct(rs, dd)

  val dd = DispatchDispatch.syntax("dd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchDispatch] = {
    withSQL {
      select.from(DispatchDispatch as dd).where.eq(dd.id, id)
    }.map(DispatchDispatch(dd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchDispatch] = {
    withSQL(select.from(DispatchDispatch as dd)).map(DispatchDispatch(dd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchDispatch as dd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchDispatch] = {
    withSQL {
      select.from(DispatchDispatch as dd).where.append(where)
    }.map(DispatchDispatch(dd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchDispatch] = {
    withSQL {
      select.from(DispatchDispatch as dd).where.append(where)
    }.map(DispatchDispatch(dd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchDispatch as dd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    senderTypeId: Int,
    active: Boolean,
    personal: Boolean,
    doNotMarkSended: Boolean,
    onlyOneContact: Boolean,
    user: Option[Int] = None,
    createDate: Option[DateTime] = None,
    lastModifyDate: Option[DateTime] = None,
    repeatTime: String,
    contactTypeId: Int,
    conditions: Array[Byte],
    woContactType: Boolean)(implicit session: DBSession = autoSession): DispatchDispatch = {
    val generatedKey = withSQL {
      insert.into(DispatchDispatch).namedValues(
        column.title -> title,
        column.senderTypeId -> senderTypeId,
        column.active -> active,
        column.personal -> personal,
        column.doNotMarkSended -> doNotMarkSended,
        column.onlyOneContact -> onlyOneContact,
        column.user -> user,
        column.createDate -> createDate,
        column.lastModifyDate -> lastModifyDate,
        column.repeatTime -> repeatTime,
        column.contactTypeId -> contactTypeId,
        column.conditions -> conditions,
        column.woContactType -> woContactType
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchDispatch(
      id = generatedKey.toInt,
      title = title,
      senderTypeId = senderTypeId,
      active = active,
      personal = personal,
      doNotMarkSended = doNotMarkSended,
      onlyOneContact = onlyOneContact,
      user = user,
      createDate = createDate,
      lastModifyDate = lastModifyDate,
      repeatTime = repeatTime,
      contactTypeId = contactTypeId,
      conditions = conditions,
      woContactType = woContactType)
  }

  def batchInsert(entities: Seq[DispatchDispatch])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'senderTypeId -> entity.senderTypeId,
        'active -> entity.active,
        'personal -> entity.personal,
        'doNotMarkSended -> entity.doNotMarkSended,
        'onlyOneContact -> entity.onlyOneContact,
        'user -> entity.user,
        'createDate -> entity.createDate,
        'lastModifyDate -> entity.lastModifyDate,
        'repeatTime -> entity.repeatTime,
        'contactTypeId -> entity.contactTypeId,
        'conditions -> entity.conditions,
        'woContactType -> entity.woContactType))
    SQL("""insert into dispatch_dispatch(
      title,
      sender_type_id,
      active,
      personal,
      do_not_mark_sended,
      only_one_contact,
      user,
      create_date,
      last_modify_date,
      repeat_time,
      contact_type_id,
      conditions,
      wo_contact_type
    ) values (
      {title},
      {senderTypeId},
      {active},
      {personal},
      {doNotMarkSended},
      {onlyOneContact},
      {user},
      {createDate},
      {lastModifyDate},
      {repeatTime},
      {contactTypeId},
      {conditions},
      {woContactType}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchDispatch)(implicit session: DBSession = autoSession): DispatchDispatch = {
    withSQL {
      update(DispatchDispatch).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.senderTypeId -> entity.senderTypeId,
        column.active -> entity.active,
        column.personal -> entity.personal,
        column.doNotMarkSended -> entity.doNotMarkSended,
        column.onlyOneContact -> entity.onlyOneContact,
        column.user -> entity.user,
        column.createDate -> entity.createDate,
        column.lastModifyDate -> entity.lastModifyDate,
        column.repeatTime -> entity.repeatTime,
        column.contactTypeId -> entity.contactTypeId,
        column.conditions -> entity.conditions,
        column.woContactType -> entity.woContactType
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchDispatch)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchDispatch).where.eq(column.id, entity.id) }.update.apply()
  }

}
