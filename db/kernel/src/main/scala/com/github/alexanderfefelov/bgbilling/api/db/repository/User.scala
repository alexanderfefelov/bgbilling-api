package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class User(
  id: Int,
  login: String,
  name: String,
  email: String,
  descr: String,
  pswd: Option[String] = None,
  dt: DateTime,
  gr: Long,
  status: Byte,
  cgr: Long,
  pids: Option[String] = None,
  opids: Option[String] = None,
  contractPid: Int,
  contractCid: Int,
  config: String,
  crmUserId: Int,
  cgrMode: Byte,
  chPswd: Byte,
  domainids: String) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Int = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  override val tableName = "user"

  override val columns = Seq("id", "login", "name", "email", "descr", "pswd", "dt", "gr", "status", "cgr", "pids", "opids", "contract_pid", "contract_cid", "config", "crm_user_id", "cgr_mode", "ch_pswd", "domainIds")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = autoConstruct(rs, u)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = autoConstruct(rs, u)

  val u = User.syntax("u")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.id, id)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    login: String,
    name: String,
    email: String,
    descr: String,
    pswd: Option[String] = None,
    dt: DateTime,
    gr: Long,
    status: Byte,
    cgr: Long,
    pids: Option[String] = None,
    opids: Option[String] = None,
    contractPid: Int,
    contractCid: Int,
    config: String,
    crmUserId: Int,
    cgrMode: Byte,
    chPswd: Byte,
    domainids: String)(implicit session: DBSession = autoSession): User = {
    val generatedKey = withSQL {
      insert.into(User).namedValues(
        column.login -> login,
        column.name -> name,
        column.email -> email,
        column.descr -> descr,
        column.pswd -> pswd,
        column.dt -> dt,
        column.gr -> gr,
        column.status -> status,
        column.cgr -> cgr,
        column.pids -> pids,
        column.opids -> opids,
        column.contractPid -> contractPid,
        column.contractCid -> contractCid,
        column.config -> config,
        column.crmUserId -> crmUserId,
        column.cgrMode -> cgrMode,
        column.chPswd -> chPswd,
        column.domainids -> domainids
      )
    }.updateAndReturnGeneratedKey.apply()

    User(
      id = generatedKey.toInt,
      login = login,
      name = name,
      email = email,
      descr = descr,
      pswd = pswd,
      dt = dt,
      gr = gr,
      status = status,
      cgr = cgr,
      pids = pids,
      opids = opids,
      contractPid = contractPid,
      contractCid = contractCid,
      config = config,
      crmUserId = crmUserId,
      cgrMode = cgrMode,
      chPswd = chPswd,
      domainids = domainids)
  }

  def batchInsert(entities: Seq[User])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'login -> entity.login,
        'name -> entity.name,
        'email -> entity.email,
        'descr -> entity.descr,
        'pswd -> entity.pswd,
        'dt -> entity.dt,
        'gr -> entity.gr,
        'status -> entity.status,
        'cgr -> entity.cgr,
        'pids -> entity.pids,
        'opids -> entity.opids,
        'contractPid -> entity.contractPid,
        'contractCid -> entity.contractCid,
        'config -> entity.config,
        'crmUserId -> entity.crmUserId,
        'cgrMode -> entity.cgrMode,
        'chPswd -> entity.chPswd,
        'domainids -> entity.domainids))
    SQL("""insert into user(
      login,
      name,
      email,
      descr,
      pswd,
      dt,
      gr,
      status,
      cgr,
      pids,
      opids,
      contract_pid,
      contract_cid,
      config,
      crm_user_id,
      cgr_mode,
      ch_pswd,
      domainIds
    ) values (
      {login},
      {name},
      {email},
      {descr},
      {pswd},
      {dt},
      {gr},
      {status},
      {cgr},
      {pids},
      {opids},
      {contractPid},
      {contractCid},
      {config},
      {crmUserId},
      {cgrMode},
      {chPswd},
      {domainids}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.id -> entity.id,
        column.login -> entity.login,
        column.name -> entity.name,
        column.email -> entity.email,
        column.descr -> entity.descr,
        column.pswd -> entity.pswd,
        column.dt -> entity.dt,
        column.gr -> entity.gr,
        column.status -> entity.status,
        column.cgr -> entity.cgr,
        column.pids -> entity.pids,
        column.opids -> entity.opids,
        column.contractPid -> entity.contractPid,
        column.contractCid -> entity.contractCid,
        column.config -> entity.config,
        column.crmUserId -> entity.crmUserId,
        column.cgrMode -> entity.cgrMode,
        column.chPswd -> entity.chPswd,
        column.domainids -> entity.domainids
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(User).where.eq(column.id, entity.id) }.update.apply()
  }

}
