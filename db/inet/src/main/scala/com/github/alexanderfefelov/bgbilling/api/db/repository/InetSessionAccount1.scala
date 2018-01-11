package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InetSessionAccount1(
  contractid: Int,
  sessionid: Long,
  serviceid: Int,
  amount: Long,
  account: BigDecimal) {

  def save()(implicit session: DBSession = InetSessionAccount1.autoSession): InetSessionAccount1 = InetSessionAccount1.save(this)(session)

  def destroy()(implicit session: DBSession = InetSessionAccount1.autoSession): Int = InetSessionAccount1.destroy(this)(session)

}


object InetSessionAccount1 extends SQLSyntaxSupport[InetSessionAccount1] with ApiDbConfig {

  override val tableName = s"inet_session_account_${bgBillingModuleId("inet")}"

  override val columns = Seq("contractId", "sessionId", "serviceId", "amount", "account")

  def apply(isa: SyntaxProvider[InetSessionAccount1])(rs: WrappedResultSet): InetSessionAccount1 = autoConstruct(rs, isa)
  def apply(isa: ResultName[InetSessionAccount1])(rs: WrappedResultSet): InetSessionAccount1 = autoConstruct(rs, isa)

  val isa = InetSessionAccount1.syntax("isa")

  override val autoSession = AutoSession

  def find(contractid: Int, serviceid: Int, sessionid: Long)(implicit session: DBSession = autoSession): Option[InetSessionAccount1] = {
    withSQL {
      select.from(InetSessionAccount1 as isa).where.eq(isa.contractid, contractid).and.eq(isa.serviceid, serviceid).and.eq(isa.sessionid, sessionid)
    }.map(InetSessionAccount1(isa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InetSessionAccount1] = {
    withSQL(select.from(InetSessionAccount1 as isa)).map(InetSessionAccount1(isa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InetSessionAccount1 as isa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InetSessionAccount1] = {
    withSQL {
      select.from(InetSessionAccount1 as isa).where.append(where)
    }.map(InetSessionAccount1(isa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InetSessionAccount1] = {
    withSQL {
      select.from(InetSessionAccount1 as isa).where.append(where)
    }.map(InetSessionAccount1(isa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InetSessionAccount1 as isa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractid: Int,
    sessionid: Long,
    serviceid: Int,
    amount: Long,
    account: BigDecimal)(implicit session: DBSession = autoSession): InetSessionAccount1 = {
    withSQL {
      insert.into(InetSessionAccount1).namedValues(
        column.contractid -> contractid,
        column.sessionid -> sessionid,
        column.serviceid -> serviceid,
        column.amount -> amount,
        column.account -> account
      )
    }.update.apply()

    InetSessionAccount1(
      contractid = contractid,
      sessionid = sessionid,
      serviceid = serviceid,
      amount = amount,
      account = account)
  }

  def batchInsert(entities: Seq[InetSessionAccount1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractid -> entity.contractid,
        'sessionid -> entity.sessionid,
        'serviceid -> entity.serviceid,
        'amount -> entity.amount,
        'account -> entity.account))
    SQL("""insert into inet_session_account_1(
      contractId,
      sessionId,
      serviceId,
      amount,
      account
    ) values (
      {contractid},
      {sessionid},
      {serviceid},
      {amount},
      {account}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InetSessionAccount1)(implicit session: DBSession = autoSession): InetSessionAccount1 = {
    withSQL {
      update(InetSessionAccount1).set(
        column.contractid -> entity.contractid,
        column.sessionid -> entity.sessionid,
        column.serviceid -> entity.serviceid,
        column.amount -> entity.amount,
        column.account -> entity.account
      ).where.eq(column.contractid, entity.contractid).and.eq(column.serviceid, entity.serviceid).and.eq(column.sessionid, entity.sessionid)
    }.update.apply()
    entity
  }

  def destroy(entity: InetSessionAccount1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InetSessionAccount1).where.eq(column.contractid, entity.contractid).and.eq(column.serviceid, entity.serviceid).and.eq(column.sessionid, entity.sessionid) }.update.apply()
  }

}
