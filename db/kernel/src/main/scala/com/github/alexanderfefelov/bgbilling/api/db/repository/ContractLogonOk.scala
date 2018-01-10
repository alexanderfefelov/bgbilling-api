package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractLogonOk(
  lu: DateTime,
  cid: Int,
  dt: DateTime,
  ip: String,
  sessionId: String,
  user: String) {

  def save()(implicit session: DBSession = ContractLogonOk.autoSession): ContractLogonOk = ContractLogonOk.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLogonOk.autoSession): Int = ContractLogonOk.destroy(this)(session)

}


object ContractLogonOk extends SQLSyntaxSupport[ContractLogonOk] {

  override val tableName = "contract_logon_ok"

  override val columns = Seq("lu", "cid", "dt", "ip", "session_id", "user")

  def apply(clo: SyntaxProvider[ContractLogonOk])(rs: WrappedResultSet): ContractLogonOk = autoConstruct(rs, clo)
  def apply(clo: ResultName[ContractLogonOk])(rs: WrappedResultSet): ContractLogonOk = autoConstruct(rs, clo)

  val clo = ContractLogonOk.syntax("clo")

  override val autoSession = AutoSession

  def find(lu: DateTime, cid: Int, dt: DateTime, ip: String, sessionId: String, user: String)(implicit session: DBSession = autoSession): Option[ContractLogonOk] = {
    withSQL {
      select.from(ContractLogonOk as clo).where.eq(clo.lu, lu).and.eq(clo.cid, cid).and.eq(clo.dt, dt).and.eq(clo.ip, ip).and.eq(clo.sessionId, sessionId).and.eq(clo.user, user)
    }.map(ContractLogonOk(clo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLogonOk] = {
    withSQL(select.from(ContractLogonOk as clo)).map(ContractLogonOk(clo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLogonOk as clo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLogonOk] = {
    withSQL {
      select.from(ContractLogonOk as clo).where.append(where)
    }.map(ContractLogonOk(clo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLogonOk] = {
    withSQL {
      select.from(ContractLogonOk as clo).where.append(where)
    }.map(ContractLogonOk(clo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLogonOk as clo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lu: DateTime,
    cid: Int,
    dt: DateTime,
    ip: String,
    sessionId: String,
    user: String)(implicit session: DBSession = autoSession): ContractLogonOk = {
    withSQL {
      insert.into(ContractLogonOk).namedValues(
        column.lu -> lu,
        column.cid -> cid,
        column.dt -> dt,
        column.ip -> ip,
        column.sessionId -> sessionId,
        column.user -> user
      )
    }.update.apply()

    ContractLogonOk(
      lu = lu,
      cid = cid,
      dt = dt,
      ip = ip,
      sessionId = sessionId,
      user = user)
  }

  def batchInsert(entities: Seq[ContractLogonOk])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'lu -> entity.lu,
        'cid -> entity.cid,
        'dt -> entity.dt,
        'ip -> entity.ip,
        'sessionId -> entity.sessionId,
        'user -> entity.user))
    SQL("""insert into contract_logon_ok(
      lu,
      cid,
      dt,
      ip,
      session_id,
      user
    ) values (
      {lu},
      {cid},
      {dt},
      {ip},
      {sessionId},
      {user}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLogonOk)(implicit session: DBSession = autoSession): ContractLogonOk = {
    withSQL {
      update(ContractLogonOk).set(
        column.lu -> entity.lu,
        column.cid -> entity.cid,
        column.dt -> entity.dt,
        column.ip -> entity.ip,
        column.sessionId -> entity.sessionId,
        column.user -> entity.user
      ).where.eq(column.lu, entity.lu).and.eq(column.cid, entity.cid).and.eq(column.dt, entity.dt).and.eq(column.ip, entity.ip).and.eq(column.sessionId, entity.sessionId).and.eq(column.user, entity.user)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLogonOk)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLogonOk).where.eq(column.lu, entity.lu).and.eq(column.cid, entity.cid).and.eq(column.dt, entity.dt).and.eq(column.ip, entity.ip).and.eq(column.sessionId, entity.sessionId).and.eq(column.user, entity.user) }.update.apply()
  }

}
