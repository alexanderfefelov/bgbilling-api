package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractError(
  id: Int,
  dt: LocalDate,
  hh: Byte,
  cid: Int,
  msgTitle: String,
  msgData: String) {

  def save()(implicit session: DBSession = ContractError.autoSession): ContractError = ContractError.save(this)(session)

  def destroy()(implicit session: DBSession = ContractError.autoSession): Int = ContractError.destroy(this)(session)

}


object ContractError extends SQLSyntaxSupport[ContractError] {

  override val tableName = "contract_error"

  override val columns = Seq("id", "dt", "hh", "cid", "msg_title", "msg_data")

  def apply(ce: SyntaxProvider[ContractError])(rs: WrappedResultSet): ContractError = autoConstruct(rs, ce)
  def apply(ce: ResultName[ContractError])(rs: WrappedResultSet): ContractError = autoConstruct(rs, ce)

  val ce = ContractError.syntax("ce")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractError] = {
    withSQL {
      select.from(ContractError as ce).where.eq(ce.id, id)
    }.map(ContractError(ce.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractError] = {
    withSQL(select.from(ContractError as ce)).map(ContractError(ce.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractError as ce)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractError] = {
    withSQL {
      select.from(ContractError as ce).where.append(where)
    }.map(ContractError(ce.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractError] = {
    withSQL {
      select.from(ContractError as ce).where.append(where)
    }.map(ContractError(ce.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractError as ce).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: LocalDate,
    hh: Byte,
    cid: Int,
    msgTitle: String,
    msgData: String)(implicit session: DBSession = autoSession): ContractError = {
    val generatedKey = withSQL {
      insert.into(ContractError).namedValues(
        column.dt -> dt,
        column.hh -> hh,
        column.cid -> cid,
        column.msgTitle -> msgTitle,
        column.msgData -> msgData
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractError(
      id = generatedKey.toInt,
      dt = dt,
      hh = hh,
      cid = cid,
      msgTitle = msgTitle,
      msgData = msgData)
  }

  def batchInsert(entities: Seq[ContractError])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'hh -> entity.hh,
        'cid -> entity.cid,
        'msgTitle -> entity.msgTitle,
        'msgData -> entity.msgData))
    SQL("""insert into contract_error(
      dt,
      hh,
      cid,
      msg_title,
      msg_data
    ) values (
      {dt},
      {hh},
      {cid},
      {msgTitle},
      {msgData}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractError)(implicit session: DBSession = autoSession): ContractError = {
    withSQL {
      update(ContractError).set(
        column.id -> entity.id,
        column.dt -> entity.dt,
        column.hh -> entity.hh,
        column.cid -> entity.cid,
        column.msgTitle -> entity.msgTitle,
        column.msgData -> entity.msgData
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractError)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractError).where.eq(column.id, entity.id) }.update.apply()
  }

}
