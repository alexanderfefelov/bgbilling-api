package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class InetAccountingPeriod1BakSec(
  id: Int,
  contractid: Int,
  datefrom: LocalDate,
  dateto: LocalDate,
  userid: Int) {

  def save()(implicit session: DBSession = InetAccountingPeriod1BakSec.autoSession): InetAccountingPeriod1BakSec = InetAccountingPeriod1BakSec.save(this)(session)

  def destroy()(implicit session: DBSession = InetAccountingPeriod1BakSec.autoSession): Int = InetAccountingPeriod1BakSec.destroy(this)(session)

}


object InetAccountingPeriod1BakSec extends SQLSyntaxSupport[InetAccountingPeriod1BakSec] {

  override val tableName = "inet_accounting_period_1_bak_sec"

  override val columns = Seq("id", "contractId", "dateFrom", "dateTo", "userId")

  def apply(iapbs: SyntaxProvider[InetAccountingPeriod1BakSec])(rs: WrappedResultSet): InetAccountingPeriod1BakSec = autoConstruct(rs, iapbs)
  def apply(iapbs: ResultName[InetAccountingPeriod1BakSec])(rs: WrappedResultSet): InetAccountingPeriod1BakSec = autoConstruct(rs, iapbs)

  val iapbs = InetAccountingPeriod1BakSec.syntax("iapbs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InetAccountingPeriod1BakSec] = {
    withSQL {
      select.from(InetAccountingPeriod1BakSec as iapbs).where.eq(iapbs.id, id)
    }.map(InetAccountingPeriod1BakSec(iapbs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetAccountingPeriod1BakSec] = {
    withSQL(select.from(InetAccountingPeriod1BakSec as iapbs)).map(InetAccountingPeriod1BakSec(iapbs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetAccountingPeriod1BakSec as iapbs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetAccountingPeriod1BakSec] = {
    withSQL {
      select.from(InetAccountingPeriod1BakSec as iapbs).where.append(where)
    }.map(InetAccountingPeriod1BakSec(iapbs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetAccountingPeriod1BakSec] = {
    withSQL {
      select.from(InetAccountingPeriod1BakSec as iapbs).where.append(where)
    }.map(InetAccountingPeriod1BakSec(iapbs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetAccountingPeriod1BakSec as iapbs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    datefrom: LocalDate,
    dateto: LocalDate,
    userid: Int)(implicit session: DBSession = autoSession): InetAccountingPeriod1BakSec = {
    val generatedKey = withSQL {
      insert.into(InetAccountingPeriod1BakSec).namedValues(
        column.contractid -> contractid,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.userid -> userid
      )
    }.updateAndReturnGeneratedKey.apply()

    InetAccountingPeriod1BakSec(
      id = generatedKey.toInt,
      contractid = contractid,
      datefrom = datefrom,
      dateto = dateto,
      userid = userid)
  }

  def batchInsert(entities: Seq[InetAccountingPeriod1BakSec])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'userid -> entity.userid))
    SQL("""insert into inet_accounting_period_1_bak_sec(
      contractId,
      dateFrom,
      dateTo,
      userId
    ) values (
      {contractid},
      {datefrom},
      {dateto},
      {userid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetAccountingPeriod1BakSec)(implicit session: DBSession = autoSession): InetAccountingPeriod1BakSec = {
    withSQL {
      update(InetAccountingPeriod1BakSec).set(
        column.id -> entity.id,
        column.contractid -> entity.contractid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.userid -> entity.userid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InetAccountingPeriod1BakSec)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetAccountingPeriod1BakSec).where.eq(column.id, entity.id) }.update.apply()
  }

}
