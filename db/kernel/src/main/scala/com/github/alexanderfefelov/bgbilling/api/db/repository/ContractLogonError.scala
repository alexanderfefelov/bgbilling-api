package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractLogonError(
  lu: DateTime,
  cid: Int,
  login: String,
  dt: DateTime,
  ip: String,
  errorCode: Int,
  requestData: String) {

  def save()(implicit session: DBSession = ContractLogonError.autoSession): ContractLogonError = ContractLogonError.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLogonError.autoSession): Int = ContractLogonError.destroy(this)(session)

}


object ContractLogonError extends SQLSyntaxSupport[ContractLogonError] {

  override val tableName = "contract_logon_error"

  override val columns = Seq("lu", "cid", "login", "dt", "ip", "error_code", "request_data")

  def apply(cle: SyntaxProvider[ContractLogonError])(rs: WrappedResultSet): ContractLogonError = autoConstruct(rs, cle)
  def apply(cle: ResultName[ContractLogonError])(rs: WrappedResultSet): ContractLogonError = autoConstruct(rs, cle)

  val cle = ContractLogonError.syntax("cle")

  override val autoSession = AutoSession

  def find(lu: DateTime, cid: Int, login: String, dt: DateTime, ip: String, errorCode: Int, requestData: String)(implicit session: DBSession = autoSession): Option[ContractLogonError] = {
    withSQL {
      select.from(ContractLogonError as cle).where.eq(cle.lu, lu).and.eq(cle.cid, cid).and.eq(cle.login, login).and.eq(cle.dt, dt).and.eq(cle.ip, ip).and.eq(cle.errorCode, errorCode).and.eq(cle.requestData, requestData)
    }.map(ContractLogonError(cle.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLogonError] = {
    withSQL(select.from(ContractLogonError as cle)).map(ContractLogonError(cle.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLogonError as cle)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLogonError] = {
    withSQL {
      select.from(ContractLogonError as cle).where.append(where)
    }.map(ContractLogonError(cle.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLogonError] = {
    withSQL {
      select.from(ContractLogonError as cle).where.append(where)
    }.map(ContractLogonError(cle.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLogonError as cle).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lu: DateTime,
    cid: Int,
    login: String,
    dt: DateTime,
    ip: String,
    errorCode: Int,
    requestData: String)(implicit session: DBSession = autoSession): ContractLogonError = {
    withSQL {
      insert.into(ContractLogonError).namedValues(
        column.lu -> lu,
        column.cid -> cid,
        column.login -> login,
        column.dt -> dt,
        column.ip -> ip,
        column.errorCode -> errorCode,
        column.requestData -> requestData
      )
    }.update.apply()

    ContractLogonError(
      lu = lu,
      cid = cid,
      login = login,
      dt = dt,
      ip = ip,
      errorCode = errorCode,
      requestData = requestData)
  }

  def batchInsert(entities: Seq[ContractLogonError])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'lu -> entity.lu,
        'cid -> entity.cid,
        'login -> entity.login,
        'dt -> entity.dt,
        'ip -> entity.ip,
        'errorCode -> entity.errorCode,
        'requestData -> entity.requestData))
    SQL("""insert into contract_logon_error(
      lu,
      cid,
      login,
      dt,
      ip,
      error_code,
      request_data
    ) values (
      {lu},
      {cid},
      {login},
      {dt},
      {ip},
      {errorCode},
      {requestData}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLogonError)(implicit session: DBSession = autoSession): ContractLogonError = {
    withSQL {
      update(ContractLogonError).set(
        column.lu -> entity.lu,
        column.cid -> entity.cid,
        column.login -> entity.login,
        column.dt -> entity.dt,
        column.ip -> entity.ip,
        column.errorCode -> entity.errorCode,
        column.requestData -> entity.requestData
      ).where.eq(column.lu, entity.lu).and.eq(column.cid, entity.cid).and.eq(column.login, entity.login).and.eq(column.dt, entity.dt).and.eq(column.ip, entity.ip).and.eq(column.errorCode, entity.errorCode).and.eq(column.requestData, entity.requestData)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLogonError)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLogonError).where.eq(column.lu, entity.lu).and.eq(column.cid, entity.cid).and.eq(column.login, entity.login).and.eq(column.dt, entity.dt).and.eq(column.ip, entity.ip).and.eq(column.errorCode, entity.errorCode).and.eq(column.requestData, entity.requestData) }.update.apply()
  }

}
