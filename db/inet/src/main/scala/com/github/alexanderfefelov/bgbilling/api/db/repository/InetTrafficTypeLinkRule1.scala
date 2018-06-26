package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InetTrafficTypeLinkRule1(
  id: Int,
  linkid: Int,
  position: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  `type`: Int,
  sourceid: Int,
  interfaceid: Int,
  direction: Int,
  addressfrom: Option[Array[Byte]] = None,
  addressto: Option[Array[Byte]] = None,
  portfrom: Int,
  portto: Int,
  diffserv: Option[String] = None,
  counterrealm: String,
  counterservice: String,
  countervendor: Int,
  countertype: Int,
  counterprefix: Option[String] = None,
  traffictypeid: Int,
  comment: String) {

  def save()(implicit session: DBSession = InetTrafficTypeLinkRule1.autoSession): InetTrafficTypeLinkRule1 = InetTrafficTypeLinkRule1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTrafficTypeLinkRule1.autoSession): Int = InetTrafficTypeLinkRule1.destroy(this)(session)

}


object InetTrafficTypeLinkRule1 extends SQLSyntaxSupport[InetTrafficTypeLinkRule1] with ApiDbConfig {

  override val tableName = s"inet_traffic_type_link_rule_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "linkId", "position", "dateFrom", "dateTo", "type", "sourceId", "interfaceId", "direction", "addressFrom", "addressTo", "portFrom", "portTo", "diffServ", "counterRealm", "counterService", "counterVendor", "counterType", "counterPrefix", "trafficTypeId", "comment")

  def apply(ittlr: SyntaxProvider[InetTrafficTypeLinkRule1])(rs: WrappedResultSet): InetTrafficTypeLinkRule1 = autoConstruct(rs, ittlr)
  def apply(ittlr: ResultName[InetTrafficTypeLinkRule1])(rs: WrappedResultSet): InetTrafficTypeLinkRule1 = autoConstruct(rs, ittlr)

  val ittlr = InetTrafficTypeLinkRule1.syntax("ittlr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetTrafficTypeLinkRule1] = {
    withSQL {
      select.from(InetTrafficTypeLinkRule1 as ittlr).where.eq(ittlr.id, id)
    }.map(InetTrafficTypeLinkRule1(ittlr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTrafficTypeLinkRule1] = {
    withSQL(select.from(InetTrafficTypeLinkRule1 as ittlr)).map(InetTrafficTypeLinkRule1(ittlr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTrafficTypeLinkRule1 as ittlr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTrafficTypeLinkRule1] = {
    withSQL {
      select.from(InetTrafficTypeLinkRule1 as ittlr).where.append(where)
    }.map(InetTrafficTypeLinkRule1(ittlr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTrafficTypeLinkRule1] = {
    withSQL {
      select.from(InetTrafficTypeLinkRule1 as ittlr).where.append(where)
    }.map(InetTrafficTypeLinkRule1(ittlr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTrafficTypeLinkRule1 as ittlr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    linkid: Int,
    position: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    `type`: Int,
    sourceid: Int,
    interfaceid: Int,
    direction: Int,
    addressfrom: Option[Array[Byte]] = None,
    addressto: Option[Array[Byte]] = None,
    portfrom: Int,
    portto: Int,
    diffserv: Option[String] = None,
    counterrealm: String,
    counterservice: String,
    countervendor: Int,
    countertype: Int,
    counterprefix: Option[String] = None,
    traffictypeid: Int,
    comment: String)(implicit session: DBSession = autoSession): InetTrafficTypeLinkRule1 = {
    val generatedKey = withSQL {
      insert.into(InetTrafficTypeLinkRule1).namedValues(
        column.linkid -> linkid,
        column.position -> position,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.`type` -> `type`,
        column.sourceid -> sourceid,
        column.interfaceid -> interfaceid,
        column.direction -> direction,
        column.addressfrom -> addressfrom,
        column.addressto -> addressto,
        column.portfrom -> portfrom,
        column.portto -> portto,
        column.diffserv -> diffserv,
        column.counterrealm -> counterrealm,
        column.counterservice -> counterservice,
        column.countervendor -> countervendor,
        column.countertype -> countertype,
        column.counterprefix -> counterprefix,
        column.traffictypeid -> traffictypeid,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InetTrafficTypeLinkRule1(
      id = generatedKey.toInt,
      linkid = linkid,
      position = position,
      datefrom = datefrom,
      dateto = dateto,
      `type` = `type`,
      sourceid = sourceid,
      interfaceid = interfaceid,
      direction = direction,
      addressfrom = addressfrom,
      addressto = addressto,
      portfrom = portfrom,
      portto = portto,
      diffserv = diffserv,
      counterrealm = counterrealm,
      counterservice = counterservice,
      countervendor = countervendor,
      countertype = countertype,
      counterprefix = counterprefix,
      traffictypeid = traffictypeid,
      comment = comment)
  }

  def batchInsert(entities: Seq[InetTrafficTypeLinkRule1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'linkid -> entity.linkid,
        'position -> entity.position,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'type -> entity.`type`,
        'sourceid -> entity.sourceid,
        'interfaceid -> entity.interfaceid,
        'direction -> entity.direction,
        'addressfrom -> entity.addressfrom,
        'addressto -> entity.addressto,
        'portfrom -> entity.portfrom,
        'portto -> entity.portto,
        'diffserv -> entity.diffserv,
        'counterrealm -> entity.counterrealm,
        'counterservice -> entity.counterservice,
        'countervendor -> entity.countervendor,
        'countertype -> entity.countertype,
        'counterprefix -> entity.counterprefix,
        'traffictypeid -> entity.traffictypeid,
        'comment -> entity.comment))
    SQL("""insert into inet_traffic_type_link_rule_1(
      linkId,
      position,
      dateFrom,
      dateTo,
      type,
      sourceId,
      interfaceId,
      direction,
      addressFrom,
      addressTo,
      portFrom,
      portTo,
      diffServ,
      counterRealm,
      counterService,
      counterVendor,
      counterType,
      counterPrefix,
      trafficTypeId,
      comment
    ) values (
      {linkid},
      {position},
      {datefrom},
      {dateto},
      {type},
      {sourceid},
      {interfaceid},
      {direction},
      {addressfrom},
      {addressto},
      {portfrom},
      {portto},
      {diffserv},
      {counterrealm},
      {counterservice},
      {countervendor},
      {countertype},
      {counterprefix},
      {traffictypeid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTrafficTypeLinkRule1)(implicit session: DBSession = autoSession): InetTrafficTypeLinkRule1 = {
    withSQL {
      update(InetTrafficTypeLinkRule1).set(
        column.id -> entity.id,
        column.linkid -> entity.linkid,
        column.position -> entity.position,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.`type` -> entity.`type`,
        column.sourceid -> entity.sourceid,
        column.interfaceid -> entity.interfaceid,
        column.direction -> entity.direction,
        column.addressfrom -> entity.addressfrom,
        column.addressto -> entity.addressto,
        column.portfrom -> entity.portfrom,
        column.portto -> entity.portto,
        column.diffserv -> entity.diffserv,
        column.counterrealm -> entity.counterrealm,
        column.counterservice -> entity.counterservice,
        column.countervendor -> entity.countervendor,
        column.countertype -> entity.countertype,
        column.counterprefix -> entity.counterprefix,
        column.traffictypeid -> entity.traffictypeid,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTrafficTypeLinkRule1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTrafficTypeLinkRule1).where.eq(column.id, entity.id) }.update.apply()
  }

}
