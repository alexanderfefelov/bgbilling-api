package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractHistoryOpenLog(
  userId: Int,
  contractId: Int,
  lastOpen: DateTime) {

  def save()(implicit session: DBSession = ContractHistoryOpenLog.autoSession): ContractHistoryOpenLog = ContractHistoryOpenLog.save(this)(session)

  def destroy()(implicit session: DBSession = ContractHistoryOpenLog.autoSession): Int = ContractHistoryOpenLog.destroy(this)(session)

}


object ContractHistoryOpenLog extends SQLSyntaxSupport[ContractHistoryOpenLog] {

  override val tableName = "contract_history_open_log"

  override val columns = Seq("user_id", "contract_id", "last_open")

  def apply(chol: SyntaxProvider[ContractHistoryOpenLog])(rs: WrappedResultSet): ContractHistoryOpenLog = autoConstruct(rs, chol)
  def apply(chol: ResultName[ContractHistoryOpenLog])(rs: WrappedResultSet): ContractHistoryOpenLog = autoConstruct(rs, chol)

  val chol = ContractHistoryOpenLog.syntax("chol")

  override val autoSession = AutoSession

  def find(contractId: Int, userId: Int)(implicit session: DBSession = autoSession): Option[ContractHistoryOpenLog] = {
    withSQL {
      select.from(ContractHistoryOpenLog as chol).where.eq(chol.contractId, contractId).and.eq(chol.userId, userId)
    }.map(ContractHistoryOpenLog(chol.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractHistoryOpenLog] = {
    withSQL(select.from(ContractHistoryOpenLog as chol)).map(ContractHistoryOpenLog(chol.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractHistoryOpenLog as chol)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractHistoryOpenLog] = {
    withSQL {
      select.from(ContractHistoryOpenLog as chol).where.append(where)
    }.map(ContractHistoryOpenLog(chol.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractHistoryOpenLog] = {
    withSQL {
      select.from(ContractHistoryOpenLog as chol).where.append(where)
    }.map(ContractHistoryOpenLog(chol.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractHistoryOpenLog as chol).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    contractId: Int,
    lastOpen: DateTime)(implicit session: DBSession = autoSession): ContractHistoryOpenLog = {
    withSQL {
      insert.into(ContractHistoryOpenLog).namedValues(
        column.userId -> userId,
        column.contractId -> contractId,
        column.lastOpen -> lastOpen
      )
    }.update.apply()

    ContractHistoryOpenLog(
      userId = userId,
      contractId = contractId,
      lastOpen = lastOpen)
  }

  def batchInsert(entities: Seq[ContractHistoryOpenLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'userId -> entity.userId,
        'contractId -> entity.contractId,
        'lastOpen -> entity.lastOpen))
    SQL("""insert into contract_history_open_log(
      user_id,
      contract_id,
      last_open
    ) values (
      {userId},
      {contractId},
      {lastOpen}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractHistoryOpenLog)(implicit session: DBSession = autoSession): ContractHistoryOpenLog = {
    withSQL {
      update(ContractHistoryOpenLog).set(
        column.userId -> entity.userId,
        column.contractId -> entity.contractId,
        column.lastOpen -> entity.lastOpen
      ).where.eq(column.contractId, entity.contractId).and.eq(column.userId, entity.userId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractHistoryOpenLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractHistoryOpenLog).where.eq(column.contractId, entity.contractId).and.eq(column.userId, entity.userId) }.update.apply()
  }

}
