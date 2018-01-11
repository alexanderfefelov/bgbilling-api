package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}

case class InetServRestriction1(
  id: Int,
  servid: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  `type`: Int,
  serviceids: String,
  amount: BigDecimal,
  comment: String) {

  def save()(implicit session: DBSession = InetServRestriction1.autoSession): InetServRestriction1 = InetServRestriction1.save(this)(session)

  def destroy()(implicit session: DBSession = InetServRestriction1.autoSession): Int = InetServRestriction1.destroy(this)(session)

}


object InetServRestriction1 extends SQLSyntaxSupport[InetServRestriction1] with ApiDbConfig {

  override val tableName = s"inet_serv_restriction_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "servId", "dateFrom", "dateTo", "type", "serviceIds", "amount", "comment")

  def apply(isr: SyntaxProvider[InetServRestriction1])(rs: WrappedResultSet): InetServRestriction1 = autoConstruct(rs, isr)
  def apply(isr: ResultName[InetServRestriction1])(rs: WrappedResultSet): InetServRestriction1 = autoConstruct(rs, isr)

  val isr = InetServRestriction1.syntax("isr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetServRestriction1] = {
    withSQL {
      select.from(InetServRestriction1 as isr).where.eq(isr.id, id)
    }.map(InetServRestriction1(isr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetServRestriction1] = {
    withSQL(select.from(InetServRestriction1 as isr)).map(InetServRestriction1(isr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetServRestriction1 as isr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetServRestriction1] = {
    withSQL {
      select.from(InetServRestriction1 as isr).where.append(where)
    }.map(InetServRestriction1(isr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetServRestriction1] = {
    withSQL {
      select.from(InetServRestriction1 as isr).where.append(where)
    }.map(InetServRestriction1(isr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetServRestriction1 as isr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    servid: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    `type`: Int,
    serviceids: String,
    amount: BigDecimal,
    comment: String)(implicit session: DBSession = autoSession): InetServRestriction1 = {
    val generatedKey = withSQL {
      insert.into(InetServRestriction1).namedValues(
        column.servid -> servid,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.`type` -> `type`,
        column.serviceids -> serviceids,
        column.amount -> amount,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InetServRestriction1(
      id = generatedKey.toInt,
      servid = servid,
      datefrom = datefrom,
      dateto = dateto,
      `type` = `type`,
      serviceids = serviceids,
      amount = amount,
      comment = comment)
  }

  def batchInsert(entities: Seq[InetServRestriction1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'servid -> entity.servid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'type -> entity.`type`,
        'serviceids -> entity.serviceids,
        'amount -> entity.amount,
        'comment -> entity.comment))
    SQL("""insert into inet_serv_restriction_1(
      servId,
      dateFrom,
      dateTo,
      type,
      serviceIds,
      amount,
      comment
    ) values (
      {servid},
      {datefrom},
      {dateto},
      {type},
      {serviceids},
      {amount},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetServRestriction1)(implicit session: DBSession = autoSession): InetServRestriction1 = {
    withSQL {
      update(InetServRestriction1).set(
        column.id -> entity.id,
        column.servid -> entity.servid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.`type` -> entity.`type`,
        column.serviceids -> entity.serviceids,
        column.amount -> entity.amount,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetServRestriction1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetServRestriction1).where.eq(column.id, entity.id) }.update.apply()
  }

}
