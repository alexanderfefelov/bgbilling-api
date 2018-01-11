package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetDeviceType1(
  id: Int,
  title: String,
  configid: Int,
  config: String,
  protocolhandlerclass: Option[String] = None,
  sahandlerclass: Option[String] = None,
  devicemanagerclass: Option[String] = None,
  uniqueinterfaces: Byte,
  scriptid: Int,
  sascript: Option[String] = None,
  eventscript: Option[String] = None,
  comment: String,
  source: Option[Boolean] = None,
  deviceentityspecid: Int) {

  def save()(implicit session: DBSession = InetDeviceType1.autoSession): InetDeviceType1 = InetDeviceType1.save(this)(session)

  def destroy()(implicit session: DBSession = InetDeviceType1.autoSession): Int = InetDeviceType1.destroy(this)(session)

}


object InetDeviceType1 extends SQLSyntaxSupport[InetDeviceType1] with ApiDbConfig {

  override val tableName = s"inet_device_type_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "title", "configId", "config", "protocolHandlerClass", "saHandlerClass", "deviceManagerClass", "uniqueInterfaces", "scriptId", "saScript", "eventScript", "comment", "source", "deviceEntitySpecId")

  def apply(idt: SyntaxProvider[InetDeviceType1])(rs: WrappedResultSet): InetDeviceType1 = autoConstruct(rs, idt)
  def apply(idt: ResultName[InetDeviceType1])(rs: WrappedResultSet): InetDeviceType1 = autoConstruct(rs, idt)

  val idt = InetDeviceType1.syntax("idt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetDeviceType1] = {
    withSQL {
      select.from(InetDeviceType1 as idt).where.eq(idt.id, id)
    }.map(InetDeviceType1(idt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetDeviceType1] = {
    withSQL(select.from(InetDeviceType1 as idt)).map(InetDeviceType1(idt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetDeviceType1 as idt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetDeviceType1] = {
    withSQL {
      select.from(InetDeviceType1 as idt).where.append(where)
    }.map(InetDeviceType1(idt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetDeviceType1] = {
    withSQL {
      select.from(InetDeviceType1 as idt).where.append(where)
    }.map(InetDeviceType1(idt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetDeviceType1 as idt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    configid: Int,
    config: String,
    protocolhandlerclass: Option[String] = None,
    sahandlerclass: Option[String] = None,
    devicemanagerclass: Option[String] = None,
    uniqueinterfaces: Byte,
    scriptid: Int,
    sascript: Option[String] = None,
    eventscript: Option[String] = None,
    comment: String,
    source: Option[Boolean] = None,
    deviceentityspecid: Int)(implicit session: DBSession = autoSession): InetDeviceType1 = {
    val generatedKey = withSQL {
      insert.into(InetDeviceType1).namedValues(
        column.title -> title,
        column.configid -> configid,
        column.config -> config,
        column.protocolhandlerclass -> protocolhandlerclass,
        column.sahandlerclass -> sahandlerclass,
        column.devicemanagerclass -> devicemanagerclass,
        column.uniqueinterfaces -> uniqueinterfaces,
        column.scriptid -> scriptid,
        column.sascript -> sascript,
        column.eventscript -> eventscript,
        column.comment -> comment,
        column.source -> source,
        column.deviceentityspecid -> deviceentityspecid
      )
    }.updateAndReturnGeneratedKey.apply()

    InetDeviceType1(
      id = generatedKey.toInt,
      title = title,
      configid = configid,
      config = config,
      protocolhandlerclass = protocolhandlerclass,
      sahandlerclass = sahandlerclass,
      devicemanagerclass = devicemanagerclass,
      uniqueinterfaces = uniqueinterfaces,
      scriptid = scriptid,
      sascript = sascript,
      eventscript = eventscript,
      comment = comment,
      source = source,
      deviceentityspecid = deviceentityspecid)
  }

  def batchInsert(entities: Seq[InetDeviceType1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'configid -> entity.configid,
        'config -> entity.config,
        'protocolhandlerclass -> entity.protocolhandlerclass,
        'sahandlerclass -> entity.sahandlerclass,
        'devicemanagerclass -> entity.devicemanagerclass,
        'uniqueinterfaces -> entity.uniqueinterfaces,
        'scriptid -> entity.scriptid,
        'sascript -> entity.sascript,
        'eventscript -> entity.eventscript,
        'comment -> entity.comment,
        'source -> entity.source,
        'deviceentityspecid -> entity.deviceentityspecid))
    SQL("""insert into inet_device_type_1(
      title,
      configId,
      config,
      protocolHandlerClass,
      saHandlerClass,
      deviceManagerClass,
      uniqueInterfaces,
      scriptId,
      saScript,
      eventScript,
      comment,
      source,
      deviceEntitySpecId
    ) values (
      {title},
      {configid},
      {config},
      {protocolhandlerclass},
      {sahandlerclass},
      {devicemanagerclass},
      {uniqueinterfaces},
      {scriptid},
      {sascript},
      {eventscript},
      {comment},
      {source},
      {deviceentityspecid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetDeviceType1)(implicit session: DBSession = autoSession): InetDeviceType1 = {
    withSQL {
      update(InetDeviceType1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.configid -> entity.configid,
        column.config -> entity.config,
        column.protocolhandlerclass -> entity.protocolhandlerclass,
        column.sahandlerclass -> entity.sahandlerclass,
        column.devicemanagerclass -> entity.devicemanagerclass,
        column.uniqueinterfaces -> entity.uniqueinterfaces,
        column.scriptid -> entity.scriptid,
        column.sascript -> entity.sascript,
        column.eventscript -> entity.eventscript,
        column.comment -> entity.comment,
        column.source -> entity.source,
        column.deviceentityspecid -> entity.deviceentityspecid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetDeviceType1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetDeviceType1).where.eq(column.id, entity.id) }.update.apply()
  }

}
