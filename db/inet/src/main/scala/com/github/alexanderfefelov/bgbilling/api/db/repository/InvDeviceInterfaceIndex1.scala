package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvDeviceInterfaceIndex1(
  id: Int,
  deviceid: Int,
  interfaceid: Int,
  timefrom: Option[DateTime] = None,
  timeto: Option[DateTime] = None,
  ifindex: Int) {

  def save()(implicit session: DBSession = InvDeviceInterfaceIndex1.autoSession): InvDeviceInterfaceIndex1 = InvDeviceInterfaceIndex1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDeviceInterfaceIndex1.autoSession): Int = InvDeviceInterfaceIndex1.destroy(this)(session)

}


object InvDeviceInterfaceIndex1 extends SQLSyntaxSupport[InvDeviceInterfaceIndex1] with ApiDbConfig {

  override val tableName = s"inv_device_interface_index_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "deviceId", "interfaceId", "timeFrom", "timeTo", "ifIndex")

  def apply(idii: SyntaxProvider[InvDeviceInterfaceIndex1])(rs: WrappedResultSet): InvDeviceInterfaceIndex1 = autoConstruct(rs, idii)
  def apply(idii: ResultName[InvDeviceInterfaceIndex1])(rs: WrappedResultSet): InvDeviceInterfaceIndex1 = autoConstruct(rs, idii)

  val idii = InvDeviceInterfaceIndex1.syntax("idii")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvDeviceInterfaceIndex1] = {
    withSQL {
      select.from(InvDeviceInterfaceIndex1 as idii).where.eq(idii.id, id)
    }.map(InvDeviceInterfaceIndex1(idii.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDeviceInterfaceIndex1] = {
    withSQL(select.from(InvDeviceInterfaceIndex1 as idii)).map(InvDeviceInterfaceIndex1(idii.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDeviceInterfaceIndex1 as idii)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDeviceInterfaceIndex1] = {
    withSQL {
      select.from(InvDeviceInterfaceIndex1 as idii).where.append(where)
    }.map(InvDeviceInterfaceIndex1(idii.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDeviceInterfaceIndex1] = {
    withSQL {
      select.from(InvDeviceInterfaceIndex1 as idii).where.append(where)
    }.map(InvDeviceInterfaceIndex1(idii.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDeviceInterfaceIndex1 as idii).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    deviceid: Int,
    interfaceid: Int,
    timefrom: Option[DateTime] = None,
    timeto: Option[DateTime] = None,
    ifindex: Int)(implicit session: DBSession = autoSession): InvDeviceInterfaceIndex1 = {
    val generatedKey = withSQL {
      insert.into(InvDeviceInterfaceIndex1).namedValues(
        column.deviceid -> deviceid,
        column.interfaceid -> interfaceid,
        column.timefrom -> timefrom,
        column.timeto -> timeto,
        column.ifindex -> ifindex
      )
    }.updateAndReturnGeneratedKey.apply()

    InvDeviceInterfaceIndex1(
      id = generatedKey.toInt,
      deviceid = deviceid,
      interfaceid = interfaceid,
      timefrom = timefrom,
      timeto = timeto,
      ifindex = ifindex)
  }

  def batchInsert(entities: Seq[InvDeviceInterfaceIndex1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'deviceid -> entity.deviceid,
        'interfaceid -> entity.interfaceid,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'ifindex -> entity.ifindex))
    SQL("""insert into inv_device_interface_index_1(
      deviceId,
      interfaceId,
      timeFrom,
      timeTo,
      ifIndex
    ) values (
      {deviceid},
      {interfaceid},
      {timefrom},
      {timeto},
      {ifindex}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDeviceInterfaceIndex1)(implicit session: DBSession = autoSession): InvDeviceInterfaceIndex1 = {
    withSQL {
      update(InvDeviceInterfaceIndex1).set(
        column.id -> entity.id,
        column.deviceid -> entity.deviceid,
        column.interfaceid -> entity.interfaceid,
        column.timefrom -> entity.timefrom,
        column.timeto -> entity.timeto,
        column.ifindex -> entity.ifindex
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDeviceInterfaceIndex1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDeviceInterfaceIndex1).where.eq(column.id, entity.id) }.update.apply()
  }

}
