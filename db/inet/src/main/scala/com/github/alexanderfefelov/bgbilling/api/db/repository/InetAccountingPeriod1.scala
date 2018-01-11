package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}

case class InetAccountingPeriod1(
  id: Int,
  contractid: Int,
  timefrom: DateTime,
  timeto: DateTime,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  userid: Int) {

  def save()(implicit session: DBSession = InetAccountingPeriod1.autoSession): InetAccountingPeriod1 = InetAccountingPeriod1.save(this)(session)

  def destroy()(implicit session: DBSession = InetAccountingPeriod1.autoSession): Int = InetAccountingPeriod1.destroy(this)(session)

}


object InetAccountingPeriod1 extends SQLSyntaxSupport[InetAccountingPeriod1] {

  override val tableName = "inet_accounting_period_1"

  override val columns = Seq("id", "contractId", "timeFrom", "timeTo", "dateFrom", "dateTo", "userId")

  def apply(iap: SyntaxProvider[InetAccountingPeriod1])(rs: WrappedResultSet): InetAccountingPeriod1 = autoConstruct(rs, iap)
  def apply(iap: ResultName[InetAccountingPeriod1])(rs: WrappedResultSet): InetAccountingPeriod1 = autoConstruct(rs, iap)

  val iap = InetAccountingPeriod1.syntax("iap")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetAccountingPeriod1] = {
    withSQL {
      select.from(InetAccountingPeriod1 as iap).where.eq(iap.id, id)
    }.map(InetAccountingPeriod1(iap.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetAccountingPeriod1] = {
    withSQL(select.from(InetAccountingPeriod1 as iap)).map(InetAccountingPeriod1(iap.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetAccountingPeriod1 as iap)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetAccountingPeriod1] = {
    withSQL {
      select.from(InetAccountingPeriod1 as iap).where.append(where)
    }.map(InetAccountingPeriod1(iap.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetAccountingPeriod1] = {
    withSQL {
      select.from(InetAccountingPeriod1 as iap).where.append(where)
    }.map(InetAccountingPeriod1(iap.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetAccountingPeriod1 as iap).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    timefrom: DateTime,
    timeto: DateTime,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    userid: Int)(implicit session: DBSession = autoSession): InetAccountingPeriod1 = {
    val generatedKey = withSQL {
      insert.into(InetAccountingPeriod1).namedValues(
        column.contractid -> contractid,
        column.timefrom -> timefrom,
        column.timeto -> timeto,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.userid -> userid
      )
    }.updateAndReturnGeneratedKey.apply()

    InetAccountingPeriod1(
      id = generatedKey.toInt,
      contractid = contractid,
      timefrom = timefrom,
      timeto = timeto,
      datefrom = datefrom,
      dateto = dateto,
      userid = userid)
  }

  def batchInsert(entities: Seq[InetAccountingPeriod1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'timefrom -> entity.timefrom,
        'timeto -> entity.timeto,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'userid -> entity.userid))
    SQL("""insert into inet_accounting_period_1(
      contractId,
      timeFrom,
      timeTo,
      dateFrom,
      dateTo,
      userId
    ) values (
      {contractid},
      {timefrom},
      {timeto},
      {datefrom},
      {dateto},
      {userid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetAccountingPeriod1)(implicit session: DBSession = autoSession): InetAccountingPeriod1 = {
    withSQL {
      update(InetAccountingPeriod1).set(
        column.id -> entity.id,
        column.contractid -> entity.contractid,
        column.timefrom -> entity.timefrom,
        column.timeto -> entity.timeto,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.userid -> entity.userid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetAccountingPeriod1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetAccountingPeriod1).where.eq(column.id, entity.id) }.update.apply()
  }

}
