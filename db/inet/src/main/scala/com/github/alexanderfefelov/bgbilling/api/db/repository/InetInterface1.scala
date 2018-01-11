package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetInterface1(
  id: Int,
  title: String,
  devicetypeid: Int) {

  def save()(implicit session: DBSession = InetInterface1.autoSession): InetInterface1 = InetInterface1.save(this)(session)

  def destroy()(implicit session: DBSession = InetInterface1.autoSession): Int = InetInterface1.destroy(this)(session)

}


object InetInterface1 extends SQLSyntaxSupport[InetInterface1] with ApiDbConfig {

  override val tableName = s"inet_interface_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "title", "deviceTypeId")

  def apply(ii: SyntaxProvider[InetInterface1])(rs: WrappedResultSet): InetInterface1 = autoConstruct(rs, ii)
  def apply(ii: ResultName[InetInterface1])(rs: WrappedResultSet): InetInterface1 = autoConstruct(rs, ii)

  val ii = InetInterface1.syntax("ii")

  override val autoSession = AutoSession

  def find(devicetypeid: Int, id: Int)(implicit session: DBSession = autoSession): Option[InetInterface1] = {
    withSQL {
      select.from(InetInterface1 as ii).where.eq(ii.devicetypeid, devicetypeid).and.eq(ii.id, id)
    }.map(InetInterface1(ii.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetInterface1] = {
    withSQL(select.from(InetInterface1 as ii)).map(InetInterface1(ii.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetInterface1 as ii)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetInterface1] = {
    withSQL {
      select.from(InetInterface1 as ii).where.append(where)
    }.map(InetInterface1(ii.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetInterface1] = {
    withSQL {
      select.from(InetInterface1 as ii).where.append(where)
    }.map(InetInterface1(ii.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetInterface1 as ii).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    title: String,
    devicetypeid: Int)(implicit session: DBSession = autoSession): InetInterface1 = {
    withSQL {
      insert.into(InetInterface1).namedValues(
        column.id -> id,
        column.title -> title,
        column.devicetypeid -> devicetypeid
      )
    }.update.apply()

    InetInterface1(
      id = id,
      title = title,
      devicetypeid = devicetypeid)
  }

  def batchInsert(entities: Seq[InetInterface1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'title -> entity.title,
        'devicetypeid -> entity.devicetypeid))
    SQL("""insert into inet_interface_1(
      id,
      title,
      deviceTypeId
    ) values (
      {id},
      {title},
      {devicetypeid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetInterface1)(implicit session: DBSession = autoSession): InetInterface1 = {
    withSQL {
      update(InetInterface1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.devicetypeid -> entity.devicetypeid
      ).where.eq(column.devicetypeid, entity.devicetypeid).and.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetInterface1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetInterface1).where.eq(column.devicetypeid, entity.devicetypeid).and.eq(column.id, entity.id) }.update.apply()
  }

}
