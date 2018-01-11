package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetTariffTrafficRangeSpec1(
  id: Int,
  title: String,
  titleforcustomer: String,
  mode: Byte,
  `type`: Byte,
  tariffoptionid: Int,
  maxamount: Long,
  maxamounttype: Byte,
  tariffoptiondeactivate: Byte,
  comment: String) {

  def save()(implicit session: DBSession = InetTariffTrafficRangeSpec1.autoSession): InetTariffTrafficRangeSpec1 = InetTariffTrafficRangeSpec1.save(this)(session)

  def destroy()(implicit session: DBSession = InetTariffTrafficRangeSpec1.autoSession): Int = InetTariffTrafficRangeSpec1.destroy(this)(session)

}


object InetTariffTrafficRangeSpec1 extends SQLSyntaxSupport[InetTariffTrafficRangeSpec1] with ApiDbConfig {

  override val tableName = s"inet_tariff_traffic_range_spec_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "title", "titleForCustomer", "mode", "type", "tariffOptionId", "maxAmount", "maxAmountType", "tariffOptionDeactivate", "comment")

  def apply(ittrs: SyntaxProvider[InetTariffTrafficRangeSpec1])(rs: WrappedResultSet): InetTariffTrafficRangeSpec1 = autoConstruct(rs, ittrs)
  def apply(ittrs: ResultName[InetTariffTrafficRangeSpec1])(rs: WrappedResultSet): InetTariffTrafficRangeSpec1 = autoConstruct(rs, ittrs)

  val ittrs = InetTariffTrafficRangeSpec1.syntax("ittrs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetTariffTrafficRangeSpec1] = {
    withSQL {
      select.from(InetTariffTrafficRangeSpec1 as ittrs).where.eq(ittrs.id, id)
    }.map(InetTariffTrafficRangeSpec1(ittrs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetTariffTrafficRangeSpec1] = {
    withSQL(select.from(InetTariffTrafficRangeSpec1 as ittrs)).map(InetTariffTrafficRangeSpec1(ittrs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetTariffTrafficRangeSpec1 as ittrs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetTariffTrafficRangeSpec1] = {
    withSQL {
      select.from(InetTariffTrafficRangeSpec1 as ittrs).where.append(where)
    }.map(InetTariffTrafficRangeSpec1(ittrs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetTariffTrafficRangeSpec1] = {
    withSQL {
      select.from(InetTariffTrafficRangeSpec1 as ittrs).where.append(where)
    }.map(InetTariffTrafficRangeSpec1(ittrs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetTariffTrafficRangeSpec1 as ittrs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    titleforcustomer: String,
    mode: Byte,
    `type`: Byte,
    tariffoptionid: Int,
    maxamount: Long,
    maxamounttype: Byte,
    tariffoptiondeactivate: Byte,
    comment: String)(implicit session: DBSession = autoSession): InetTariffTrafficRangeSpec1 = {
    val generatedKey = withSQL {
      insert.into(InetTariffTrafficRangeSpec1).namedValues(
        column.title -> title,
        column.titleforcustomer -> titleforcustomer,
        column.mode -> mode,
        column.`type` -> `type`,
        column.tariffoptionid -> tariffoptionid,
        column.maxamount -> maxamount,
        column.maxamounttype -> maxamounttype,
        column.tariffoptiondeactivate -> tariffoptiondeactivate,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InetTariffTrafficRangeSpec1(
      id = generatedKey.toInt,
      title = title,
      titleforcustomer = titleforcustomer,
      mode = mode,
      `type` = `type`,
      tariffoptionid = tariffoptionid,
      maxamount = maxamount,
      maxamounttype = maxamounttype,
      tariffoptiondeactivate = tariffoptiondeactivate,
      comment = comment)
  }

  def batchInsert(entities: Seq[InetTariffTrafficRangeSpec1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'titleforcustomer -> entity.titleforcustomer,
        'mode -> entity.mode,
        'type -> entity.`type`,
        'tariffoptionid -> entity.tariffoptionid,
        'maxamount -> entity.maxamount,
        'maxamounttype -> entity.maxamounttype,
        'tariffoptiondeactivate -> entity.tariffoptiondeactivate,
        'comment -> entity.comment))
    SQL("""insert into inet_tariff_traffic_range_spec_1(
      title,
      titleForCustomer,
      mode,
      type,
      tariffOptionId,
      maxAmount,
      maxAmountType,
      tariffOptionDeactivate,
      comment
    ) values (
      {title},
      {titleforcustomer},
      {mode},
      {type},
      {tariffoptionid},
      {maxamount},
      {maxamounttype},
      {tariffoptiondeactivate},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetTariffTrafficRangeSpec1)(implicit session: DBSession = autoSession): InetTariffTrafficRangeSpec1 = {
    withSQL {
      update(InetTariffTrafficRangeSpec1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.titleforcustomer -> entity.titleforcustomer,
        column.mode -> entity.mode,
        column.`type` -> entity.`type`,
        column.tariffoptionid -> entity.tariffoptionid,
        column.maxamount -> entity.maxamount,
        column.maxamounttype -> entity.maxamounttype,
        column.tariffoptiondeactivate -> entity.tariffoptiondeactivate,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetTariffTrafficRangeSpec1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetTariffTrafficRangeSpec1).where.eq(column.id, entity.id) }.update.apply()
  }

}
