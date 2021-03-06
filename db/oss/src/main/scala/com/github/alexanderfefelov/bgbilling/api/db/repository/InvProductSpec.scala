package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvProductSpec(
  id: Int,
  entityid: Int,
  moduleid: Int,
  parentid: Int,
  periodic: Byte,
  notrealtime: Byte,
  priority: Int,
  title: String,
  identifier: String,
  tariffids: Option[String] = None,
  contractgroups: Option[String] = None,
  depends: Option[String] = None,
  incompatible: Option[String] = None,
  activationmodeids: Option[String] = None,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  status: Byte,
  hideforcustomer: Byte,
  hideforcontractgroups: Long,
  hideforcontractgroupsmode: Byte,
  activationbycustomer: Byte,
  deactivationbycustomer: Byte,
  comment: String,
  description: String) {

  def save()(implicit session: DBSession = InvProductSpec.autoSession): InvProductSpec = InvProductSpec.save(this)(session)

  def destroy()(implicit session: DBSession = InvProductSpec.autoSession): Int = InvProductSpec.destroy(this)(session)

}


object InvProductSpec extends SQLSyntaxSupport[InvProductSpec] {

  override val tableName = "inv_product_spec"

  override val columns = Seq("id", "entityId", "moduleId", "parentId", "periodic", "notRealtime", "priority", "title", "identifier", "tariffIds", "contractGroups", "depends", "incompatible", "activationModeIds", "dateFrom", "dateTo", "status", "hideForCustomer", "hideForContractGroups", "hideForContractGroupsMode", "activationByCustomer", "deactivationByCustomer", "comment", "description")

  def apply(ips: SyntaxProvider[InvProductSpec])(rs: WrappedResultSet): InvProductSpec = autoConstruct(rs, ips)
  def apply(ips: ResultName[InvProductSpec])(rs: WrappedResultSet): InvProductSpec = autoConstruct(rs, ips)

  val ips = InvProductSpec.syntax("ips")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvProductSpec] = {
    sql"""select ${ips.result.*} from ${InvProductSpec as ips} where ${ips.id} = ${id}"""
      .map(InvProductSpec(ips.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProductSpec] = {
    sql"""select ${ips.result.*} from ${InvProductSpec as ips}""".map(InvProductSpec(ips.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProductSpec.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProductSpec] = {
    sql"""select ${ips.result.*} from ${InvProductSpec as ips} where ${where}"""
      .map(InvProductSpec(ips.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProductSpec] = {
    sql"""select ${ips.result.*} from ${InvProductSpec as ips} where ${where}"""
      .map(InvProductSpec(ips.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${InvProductSpec as ips} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    moduleid: Int,
    parentid: Int,
    periodic: Byte,
    notrealtime: Byte,
    priority: Int,
    title: String,
    identifier: String,
    tariffids: Option[String] = None,
    contractgroups: Option[String] = None,
    depends: Option[String] = None,
    incompatible: Option[String] = None,
    activationmodeids: Option[String] = None,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    status: Byte,
    hideforcustomer: Byte,
    hideforcontractgroups: Long,
    hideforcontractgroupsmode: Byte,
    activationbycustomer: Byte,
    deactivationbycustomer: Byte,
    comment: String,
    description: String)(implicit session: DBSession = autoSession): InvProductSpec = {
    val generatedKey = sql"""
      insert into ${InvProductSpec.table} (
        ${column.entityid},
        ${column.moduleid},
        ${column.parentid},
        ${column.periodic},
        ${column.notrealtime},
        ${column.priority},
        ${column.title},
        ${column.identifier},
        ${column.tariffids},
        ${column.contractgroups},
        ${column.depends},
        ${column.incompatible},
        ${column.activationmodeids},
        ${column.datefrom},
        ${column.dateto},
        ${column.status},
        ${column.hideforcustomer},
        ${column.hideforcontractgroups},
        ${column.hideforcontractgroupsmode},
        ${column.activationbycustomer},
        ${column.deactivationbycustomer},
        ${column.comment},
        ${column.description}
      ) values (
        ${entityid},
        ${moduleid},
        ${parentid},
        ${periodic},
        ${notrealtime},
        ${priority},
        ${title},
        ${identifier},
        ${tariffids},
        ${contractgroups},
        ${depends},
        ${incompatible},
        ${activationmodeids},
        ${datefrom},
        ${dateto},
        ${status},
        ${hideforcustomer},
        ${hideforcontractgroups},
        ${hideforcontractgroupsmode},
        ${activationbycustomer},
        ${deactivationbycustomer},
        ${comment},
        ${description}
      )
      """.updateAndReturnGeneratedKey.apply()

    InvProductSpec(
      id = generatedKey.toInt,
      entityid = entityid,
      moduleid = moduleid,
      parentid = parentid,
      periodic = periodic,
      notrealtime = notrealtime,
      priority = priority,
      title = title,
      identifier = identifier,
      tariffids = tariffids,
      contractgroups = contractgroups,
      depends = depends,
      incompatible = incompatible,
      activationmodeids = activationmodeids,
      datefrom = datefrom,
      dateto = dateto,
      status = status,
      hideforcustomer = hideforcustomer,
      hideforcontractgroups = hideforcontractgroups,
      hideforcontractgroupsmode = hideforcontractgroupsmode,
      activationbycustomer = activationbycustomer,
      deactivationbycustomer = deactivationbycustomer,
      comment = comment,
      description = description)
  }

  def batchInsert(entities: collection.Seq[InvProductSpec])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'moduleid -> entity.moduleid,
        'parentid -> entity.parentid,
        'periodic -> entity.periodic,
        'notrealtime -> entity.notrealtime,
        'priority -> entity.priority,
        'title -> entity.title,
        'identifier -> entity.identifier,
        'tariffids -> entity.tariffids,
        'contractgroups -> entity.contractgroups,
        'depends -> entity.depends,
        'incompatible -> entity.incompatible,
        'activationmodeids -> entity.activationmodeids,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'status -> entity.status,
        'hideforcustomer -> entity.hideforcustomer,
        'hideforcontractgroups -> entity.hideforcontractgroups,
        'hideforcontractgroupsmode -> entity.hideforcontractgroupsmode,
        'activationbycustomer -> entity.activationbycustomer,
        'deactivationbycustomer -> entity.deactivationbycustomer,
        'comment -> entity.comment,
        'description -> entity.description))
    SQL("""insert into inv_product_spec(
      entityId,
      moduleId,
      parentId,
      periodic,
      notRealtime,
      priority,
      title,
      identifier,
      tariffIds,
      contractGroups,
      depends,
      incompatible,
      activationModeIds,
      dateFrom,
      dateTo,
      status,
      hideForCustomer,
      hideForContractGroups,
      hideForContractGroupsMode,
      activationByCustomer,
      deactivationByCustomer,
      comment,
      description
    ) values (
      {entityid},
      {moduleid},
      {parentid},
      {periodic},
      {notrealtime},
      {priority},
      {title},
      {identifier},
      {tariffids},
      {contractgroups},
      {depends},
      {incompatible},
      {activationmodeids},
      {datefrom},
      {dateto},
      {status},
      {hideforcustomer},
      {hideforcontractgroups},
      {hideforcontractgroupsmode},
      {activationbycustomer},
      {deactivationbycustomer},
      {comment},
      {description}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvProductSpec)(implicit session: DBSession = autoSession): InvProductSpec = {
    sql"""
      update
        ${InvProductSpec.table}
      set
        ${column.id} = ${entity.id},
        ${column.entityid} = ${entity.entityid},
        ${column.moduleid} = ${entity.moduleid},
        ${column.parentid} = ${entity.parentid},
        ${column.periodic} = ${entity.periodic},
        ${column.notrealtime} = ${entity.notrealtime},
        ${column.priority} = ${entity.priority},
        ${column.title} = ${entity.title},
        ${column.identifier} = ${entity.identifier},
        ${column.tariffids} = ${entity.tariffids},
        ${column.contractgroups} = ${entity.contractgroups},
        ${column.depends} = ${entity.depends},
        ${column.incompatible} = ${entity.incompatible},
        ${column.activationmodeids} = ${entity.activationmodeids},
        ${column.datefrom} = ${entity.datefrom},
        ${column.dateto} = ${entity.dateto},
        ${column.status} = ${entity.status},
        ${column.hideforcustomer} = ${entity.hideforcustomer},
        ${column.hideforcontractgroups} = ${entity.hideforcontractgroups},
        ${column.hideforcontractgroupsmode} = ${entity.hideforcontractgroupsmode},
        ${column.activationbycustomer} = ${entity.activationbycustomer},
        ${column.deactivationbycustomer} = ${entity.deactivationbycustomer},
        ${column.comment} = ${entity.comment},
        ${column.description} = ${entity.description}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: InvProductSpec)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${InvProductSpec.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
