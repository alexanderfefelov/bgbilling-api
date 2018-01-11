package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}

case class InvDevice1(
  id: Int,
  entityid: Int,
  parentid: Int,
  devicetypeid: Int,
  title: String,
  identifier: String,
  uptime: String,
  uptimetime: Option[DateTime] = None,
  host: String,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  ordermanagerdisabled: Byte,
  username: String,
  password: String,
  secret: String,
  config: String,
  comment: String) {

  def save()(implicit session: DBSession = InvDevice1.autoSession): InvDevice1 = InvDevice1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDevice1.autoSession): Int = InvDevice1.destroy(this)(session)

}


object InvDevice1 extends SQLSyntaxSupport[InvDevice1] {

  override val tableName = "inv_device_1"

  override val columns = Seq("id", "entityId", "parentId", "deviceTypeId", "title", "identifier", "uptime", "uptimeTime", "host", "dateFrom", "dateTo", "orderManagerDisabled", "username", "password", "secret", "config", "comment")

  def apply(id: SyntaxProvider[InvDevice1])(rs: WrappedResultSet): InvDevice1 = autoConstruct(rs, id)
  def apply(id: ResultName[InvDevice1])(rs: WrappedResultSet): InvDevice1 = autoConstruct(rs, id)

  val id_ = InvDevice1.syntax("id") /* alexanderfefelov
                                        type mismatch;
                                         found   : Int
                                         required: scalikejdbc.QuerySQLSyntaxProvider[scalikejdbc.SQLSyntaxSupport[...] */

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvDevice1] = {
    withSQL {
      select.from(InvDevice1 as id_).where.eq(id_.id, id)
    }.map(InvDevice1(id_.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDevice1] = {
    withSQL(select.from(InvDevice1 as id_)).map(InvDevice1(id_.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDevice1 as id_)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDevice1] = {
    withSQL {
      select.from(InvDevice1 as id_).where.append(where)
    }.map(InvDevice1(id_.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDevice1] = {
    withSQL {
      select.from(InvDevice1 as id_).where.append(where)
    }.map(InvDevice1(id_.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDevice1 as id_).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    parentid: Int,
    devicetypeid: Int,
    title: String,
    identifier: String,
    uptime: String,
    uptimetime: Option[DateTime] = None,
    host: String,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    ordermanagerdisabled: Byte,
    username: String,
    password: String,
    secret: String,
    config: String,
    comment: String)(implicit session: DBSession = autoSession): InvDevice1 = {
    val generatedKey = withSQL {
      insert.into(InvDevice1).namedValues(
        column.entityid -> entityid,
        column.parentid -> parentid,
        column.devicetypeid -> devicetypeid,
        column.title -> title,
        column.identifier -> identifier,
        column.uptime -> uptime,
        column.uptimetime -> uptimetime,
        column.host -> host,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.ordermanagerdisabled -> ordermanagerdisabled,
        column.username -> username,
        column.password -> password,
        column.secret -> secret,
        column.config -> config,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InvDevice1(
      id = generatedKey.toInt,
      entityid = entityid,
      parentid = parentid,
      devicetypeid = devicetypeid,
      title = title,
      identifier = identifier,
      uptime = uptime,
      uptimetime = uptimetime,
      host = host,
      datefrom = datefrom,
      dateto = dateto,
      ordermanagerdisabled = ordermanagerdisabled,
      username = username,
      password = password,
      secret = secret,
      config = config,
      comment = comment)
  }

  def batchInsert(entities: Seq[InvDevice1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'parentid -> entity.parentid,
        'devicetypeid -> entity.devicetypeid,
        'title -> entity.title,
        'identifier -> entity.identifier,
        'uptime -> entity.uptime,
        'uptimetime -> entity.uptimetime,
        'host -> entity.host,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'ordermanagerdisabled -> entity.ordermanagerdisabled,
        'username -> entity.username,
        'password -> entity.password,
        'secret -> entity.secret,
        'config -> entity.config,
        'comment -> entity.comment))
    SQL("""insert into inv_device_1(
      entityId,
      parentId,
      deviceTypeId,
      title,
      identifier,
      uptime,
      uptimeTime,
      host,
      dateFrom,
      dateTo,
      orderManagerDisabled,
      username,
      password,
      secret,
      config,
      comment
    ) values (
      {entityid},
      {parentid},
      {devicetypeid},
      {title},
      {identifier},
      {uptime},
      {uptimetime},
      {host},
      {datefrom},
      {dateto},
      {ordermanagerdisabled},
      {username},
      {password},
      {secret},
      {config},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDevice1)(implicit session: DBSession = autoSession): InvDevice1 = {
    withSQL {
      update(InvDevice1).set(
        column.id -> entity.id,
        column.entityid -> entity.entityid,
        column.parentid -> entity.parentid,
        column.devicetypeid -> entity.devicetypeid,
        column.title -> entity.title,
        column.identifier -> entity.identifier,
        column.uptime -> entity.uptime,
        column.uptimetime -> entity.uptimetime,
        column.host -> entity.host,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.ordermanagerdisabled -> entity.ordermanagerdisabled,
        column.username -> entity.username,
        column.password -> entity.password,
        column.secret -> entity.secret,
        column.config -> entity.config,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDevice1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDevice1).where.eq(column.id, entity.id) }.update.apply()
  }

}
