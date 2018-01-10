package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}

case class ContractLimitManage(
  id: Int,
  cid: Int,
  clpId: Option[Int] = None,
  summ: BigDecimal,
  date1: DateTime,
  date2: Option[LocalDate] = None,
  pids: Option[String] = None,
  rest: Float,
  status: Option[Int] = None) {

  def save()(implicit session: DBSession = ContractLimitManage.autoSession): ContractLimitManage = ContractLimitManage.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLimitManage.autoSession): Int = ContractLimitManage.destroy(this)(session)

}


object ContractLimitManage extends SQLSyntaxSupport[ContractLimitManage] {

  override val tableName = "contract_limit_manage"

  override val columns = Seq("id", "cid", "clp_id", "summ", "date1", "date2", "pids", "rest", "status")

  def apply(clm: SyntaxProvider[ContractLimitManage])(rs: WrappedResultSet): ContractLimitManage = autoConstruct(rs, clm)
  def apply(clm: ResultName[ContractLimitManage])(rs: WrappedResultSet): ContractLimitManage = autoConstruct(rs, clm)

  val clm = ContractLimitManage.syntax("clm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractLimitManage] = {
    withSQL {
      select.from(ContractLimitManage as clm).where.eq(clm.id, id)
    }.map(ContractLimitManage(clm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLimitManage] = {
    withSQL(select.from(ContractLimitManage as clm)).map(ContractLimitManage(clm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLimitManage as clm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLimitManage] = {
    withSQL {
      select.from(ContractLimitManage as clm).where.append(where)
    }.map(ContractLimitManage(clm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLimitManage] = {
    withSQL {
      select.from(ContractLimitManage as clm).where.append(where)
    }.map(ContractLimitManage(clm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLimitManage as clm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    clpId: Option[Int] = None,
    summ: BigDecimal,
    date1: DateTime,
    date2: Option[LocalDate] = None,
    pids: Option[String] = None,
    rest: Float,
    status: Option[Int] = None)(implicit session: DBSession = autoSession): ContractLimitManage = {
    val generatedKey = withSQL {
      insert.into(ContractLimitManage).namedValues(
        column.cid -> cid,
        column.clpId -> clpId,
        column.summ -> summ,
        column.date1 -> date1,
        column.date2 -> date2,
        column.pids -> pids,
        column.rest -> rest,
        column.status -> status
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractLimitManage(
      id = generatedKey.toInt,
      cid = cid,
      clpId = clpId,
      summ = summ,
      date1 = date1,
      date2 = date2,
      pids = pids,
      rest = rest,
      status = status)
  }

  def batchInsert(entities: Seq[ContractLimitManage])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'clpId -> entity.clpId,
        'summ -> entity.summ,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'pids -> entity.pids,
        'rest -> entity.rest,
        'status -> entity.status))
    SQL("""insert into contract_limit_manage(
      cid,
      clp_id,
      summ,
      date1,
      date2,
      pids,
      rest,
      status
    ) values (
      {cid},
      {clpId},
      {summ},
      {date1},
      {date2},
      {pids},
      {rest},
      {status}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLimitManage)(implicit session: DBSession = autoSession): ContractLimitManage = {
    withSQL {
      update(ContractLimitManage).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.clpId -> entity.clpId,
        column.summ -> entity.summ,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.pids -> entity.pids,
        column.rest -> entity.rest,
        column.status -> entity.status
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLimitManage)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLimitManage).where.eq(column.id, entity.id) }.update.apply()
  }

}
