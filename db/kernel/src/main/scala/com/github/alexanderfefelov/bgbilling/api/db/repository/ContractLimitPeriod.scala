package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractLimitPeriod(
  id: Int,
  uid: Int,
  cid: Int,
  dt: Option[LocalDate] = None,
  value: BigDecimal,
  status: String) {

  def save()(implicit session: DBSession = ContractLimitPeriod.autoSession): ContractLimitPeriod = ContractLimitPeriod.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLimitPeriod.autoSession): Int = ContractLimitPeriod.destroy(this)(session)

}


object ContractLimitPeriod extends SQLSyntaxSupport[ContractLimitPeriod] {

  override val tableName = "contract_limit_period"

  override val columns = Seq("id", "uid", "cid", "dt", "value", "status")

  def apply(clp: SyntaxProvider[ContractLimitPeriod])(rs: WrappedResultSet): ContractLimitPeriod = autoConstruct(rs, clp)
  def apply(clp: ResultName[ContractLimitPeriod])(rs: WrappedResultSet): ContractLimitPeriod = autoConstruct(rs, clp)

  val clp = ContractLimitPeriod.syntax("clp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractLimitPeriod] = {
    withSQL {
      select.from(ContractLimitPeriod as clp).where.eq(clp.id, id)
    }.map(ContractLimitPeriod(clp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLimitPeriod] = {
    withSQL(select.from(ContractLimitPeriod as clp)).map(ContractLimitPeriod(clp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLimitPeriod as clp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLimitPeriod] = {
    withSQL {
      select.from(ContractLimitPeriod as clp).where.append(where)
    }.map(ContractLimitPeriod(clp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLimitPeriod] = {
    withSQL {
      select.from(ContractLimitPeriod as clp).where.append(where)
    }.map(ContractLimitPeriod(clp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLimitPeriod as clp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    uid: Int,
    cid: Int,
    dt: Option[LocalDate] = None,
    value: BigDecimal,
    status: String)(implicit session: DBSession = autoSession): ContractLimitPeriod = {
    val generatedKey = withSQL {
      insert.into(ContractLimitPeriod).namedValues(
        column.uid -> uid,
        column.cid -> cid,
        column.dt -> dt,
        column.value -> value,
        column.status -> status
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractLimitPeriod(
      id = generatedKey.toInt,
      uid = uid,
      cid = cid,
      dt = dt,
      value = value,
      status = status)
  }

  def batchInsert(entities: Seq[ContractLimitPeriod])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'uid -> entity.uid,
        'cid -> entity.cid,
        'dt -> entity.dt,
        'value -> entity.value,
        'status -> entity.status))
    SQL("""insert into contract_limit_period(
      uid,
      cid,
      dt,
      value,
      status
    ) values (
      {uid},
      {cid},
      {dt},
      {value},
      {status}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLimitPeriod)(implicit session: DBSession = autoSession): ContractLimitPeriod = {
    withSQL {
      update(ContractLimitPeriod).set(
        column.id -> entity.id,
        column.uid -> entity.uid,
        column.cid -> entity.cid,
        column.dt -> entity.dt,
        column.value -> entity.value,
        column.status -> entity.status
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLimitPeriod)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLimitPeriod).where.eq(column.id, entity.id) }.update.apply()
  }

}
