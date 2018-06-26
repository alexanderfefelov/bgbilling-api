package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InetServ1(
  id: Int,
  parentid: Int,
  contractid: Int,
  typeid: Int,
  deviceid: Int,
  interfaceid: Int,
  vlan: Int,
  identifier: Option[String] = None,
  macaddress: Option[Array[Byte]] = None,
  ipresourceid: Option[Int] = None,
  ipresourcesubscriptionid: Int,
  addressfrom: Option[Array[Byte]] = None,
  addressto: Option[Array[Byte]] = None,
  login: String,
  password: Option[String] = None,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  contractobjectid: Int,
  status: Int,
  sessioncountlimit: Int,
  devicestate: Int,
  accesscode: Int,
  config: Option[String] = None,
  deviceoptions: String,
  comment: String,
  title: Option[String] = None) {

  def save()(implicit session: DBSession = InetServ1.autoSession): InetServ1 = InetServ1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServ1.autoSession): Int = InetServ1.destroy(this)(session)

}


object InetServ1 extends SQLSyntaxSupport[InetServ1] with ApiDbConfig {

  override val tableName = s"inet_serv_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "contractId", "typeId", "deviceId", "interfaceId", "vlan", "identifier", "macAddress", "ipResourceId", "ipResourceSubscriptionId", "addressFrom", "addressTo", "login", "password", "dateFrom", "dateTo", "contractObjectId", "status", "sessionCountLimit", "deviceState", "accessCode", "config", "deviceOptions", "comment", "title")

  def apply(is: SyntaxProvider[InetServ1])(rs: WrappedResultSet): InetServ1 = autoConstruct(rs, is)
  def apply(is: ResultName[InetServ1])(rs: WrappedResultSet): InetServ1 = autoConstruct(rs, is)

  val is = InetServ1.syntax("is")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetServ1] = {
    withSQL {
      select.from(InetServ1 as is).where.eq(is.id, id)
    }.map(InetServ1(is.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServ1] = {
    withSQL(select.from(InetServ1 as is)).map(InetServ1(is.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServ1 as is)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServ1] = {
    withSQL {
      select.from(InetServ1 as is).where.append(where)
    }.map(InetServ1(is.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServ1] = {
    withSQL {
      select.from(InetServ1 as is).where.append(where)
    }.map(InetServ1(is.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServ1 as is).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    contractid: Int,
    typeid: Int,
    deviceid: Int,
    interfaceid: Int,
    vlan: Int,
    identifier: Option[String] = None,
    macaddress: Option[Array[Byte]] = None,
    ipresourceid: Option[Int] = None,
    ipresourcesubscriptionid: Int,
    addressfrom: Option[Array[Byte]] = None,
    addressto: Option[Array[Byte]] = None,
    login: String,
    password: Option[String] = None,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    contractobjectid: Int,
    status: Int,
    sessioncountlimit: Int,
    devicestate: Int,
    accesscode: Int,
    config: Option[String] = None,
    deviceoptions: String,
    comment: String,
    title: Option[String] = None)(implicit session: DBSession = autoSession): InetServ1 = {
    val generatedKey = withSQL {
      insert.into(InetServ1).namedValues(
        column.parentid -> parentid,
        column.contractid -> contractid,
        column.typeid -> typeid,
        column.deviceid -> deviceid,
        column.interfaceid -> interfaceid,
        column.vlan -> vlan,
        column.identifier -> identifier,
        column.macaddress -> macaddress,
        column.ipresourceid -> ipresourceid,
        column.ipresourcesubscriptionid -> ipresourcesubscriptionid,
        column.addressfrom -> addressfrom,
        column.addressto -> addressto,
        column.login -> login,
        column.password -> password,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.contractobjectid -> contractobjectid,
        column.status -> status,
        column.sessioncountlimit -> sessioncountlimit,
        column.devicestate -> devicestate,
        column.accesscode -> accesscode,
        column.config -> config,
        column.deviceoptions -> deviceoptions,
        column.comment -> comment,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    new InetServ1(
      id = generatedKey.toInt,
      parentid = parentid,
      contractid = contractid,
      typeid = typeid,
      deviceid = deviceid,
      interfaceid = interfaceid,
      vlan = vlan,
      identifier = identifier,
      macaddress = macaddress,
      ipresourceid = ipresourceid,
      ipresourcesubscriptionid = ipresourcesubscriptionid,
      addressfrom = addressfrom,
      addressto = addressto,
      login = login,
      password = password,
      datefrom = datefrom,
      dateto = dateto,
      contractobjectid = contractobjectid,
      status = status,
      sessioncountlimit = sessioncountlimit,
      devicestate = devicestate,
      accesscode = accesscode,
      config = config,
      deviceoptions = deviceoptions,
      comment = comment,
      title = title)
  }

  def batchInsert(entities: Seq[InetServ1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'contractid -> entity.contractid,
        'typeid -> entity.typeid,
        'deviceid -> entity.deviceid,
        'interfaceid -> entity.interfaceid,
        'vlan -> entity.vlan,
        'identifier -> entity.identifier,
        'macaddress -> entity.macaddress,
        'ipresourceid -> entity.ipresourceid,
        'ipresourcesubscriptionid -> entity.ipresourcesubscriptionid,
        'addressfrom -> entity.addressfrom,
        'addressto -> entity.addressto,
        'login -> entity.login,
        'password -> entity.password,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'contractobjectid -> entity.contractobjectid,
        'status -> entity.status,
        'sessioncountlimit -> entity.sessioncountlimit,
        'devicestate -> entity.devicestate,
        'accesscode -> entity.accesscode,
        'config -> entity.config,
        'deviceoptions -> entity.deviceoptions,
        'comment -> entity.comment,
        'title -> entity.title))
    SQL("""insert into inet_serv_1(
      parentId,
      contractId,
      typeId,
      deviceId,
      interfaceId,
      vlan,
      identifier,
      macAddress,
      ipResourceId,
      ipResourceSubscriptionId,
      addressFrom,
      addressTo,
      login,
      password,
      dateFrom,
      dateTo,
      contractObjectId,
      status,
      sessionCountLimit,
      deviceState,
      accessCode,
      config,
      deviceOptions,
      comment,
      title
    ) values (
      {parentid},
      {contractid},
      {typeid},
      {deviceid},
      {interfaceid},
      {vlan},
      {identifier},
      {macaddress},
      {ipresourceid},
      {ipresourcesubscriptionid},
      {addressfrom},
      {addressto},
      {login},
      {password},
      {datefrom},
      {dateto},
      {contractobjectid},
      {status},
      {sessioncountlimit},
      {devicestate},
      {accesscode},
      {config},
      {deviceoptions},
      {comment},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServ1)(implicit session: DBSession = autoSession): InetServ1 = {
    withSQL {
      update(InetServ1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.contractid -> entity.contractid,
        column.typeid -> entity.typeid,
        column.deviceid -> entity.deviceid,
        column.interfaceid -> entity.interfaceid,
        column.vlan -> entity.vlan,
        column.identifier -> entity.identifier,
        column.macaddress -> entity.macaddress,
        column.ipresourceid -> entity.ipresourceid,
        column.ipresourcesubscriptionid -> entity.ipresourcesubscriptionid,
        column.addressfrom -> entity.addressfrom,
        column.addressto -> entity.addressto,
        column.login -> entity.login,
        column.password -> entity.password,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.contractobjectid -> entity.contractobjectid,
        column.status -> entity.status,
        column.sessioncountlimit -> entity.sessioncountlimit,
        column.devicestate -> entity.devicestate,
        column.accesscode -> entity.accesscode,
        column.config -> entity.config,
        column.deviceoptions -> entity.deviceoptions,
        column.comment -> entity.comment,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServ1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServ1).where.eq(column.id, entity.id) }.update.apply()
  }

}
