package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractLogonLast(
  lu: DateTime,
  cid: Int,
  dt: DateTime,
  n: Int,
  ip: String) {

  def save()(implicit session: DBSession = ContractLogonLast.autoSession): ContractLogonLast = ContractLogonLast.save(this)(session)

  def destroy()(implicit session: DBSession = ContractLogonLast.autoSession): Int = ContractLogonLast.destroy(this)(session)

}


object ContractLogonLast extends SQLSyntaxSupport[ContractLogonLast] {

  override val tableName = "contract_logon_last"

  override val columns = Seq("lu", "cid", "dt", "n", "ip")

  def apply(cll: SyntaxProvider[ContractLogonLast])(rs: WrappedResultSet): ContractLogonLast = autoConstruct(rs, cll)
  def apply(cll: ResultName[ContractLogonLast])(rs: WrappedResultSet): ContractLogonLast = autoConstruct(rs, cll)

  val cll = ContractLogonLast.syntax("cll")

  override val autoSession = AutoSession

  def find(cid: Int)(implicit session: DBSession = autoSession): Option[ContractLogonLast] = {
    withSQL {
      select.from(ContractLogonLast as cll).where.eq(cll.cid, cid)
    }.map(ContractLogonLast(cll.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractLogonLast] = {
    withSQL(select.from(ContractLogonLast as cll)).map(ContractLogonLast(cll.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractLogonLast as cll)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractLogonLast] = {
    withSQL {
      select.from(ContractLogonLast as cll).where.append(where)
    }.map(ContractLogonLast(cll.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractLogonLast] = {
    withSQL {
      select.from(ContractLogonLast as cll).where.append(where)
    }.map(ContractLogonLast(cll.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractLogonLast as cll).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lu: DateTime,
    cid: Int,
    dt: DateTime,
    n: Int,
    ip: String)(implicit session: DBSession = autoSession): ContractLogonLast = {
    withSQL {
      insert.into(ContractLogonLast).namedValues(
        column.lu -> lu,
        column.cid -> cid,
        column.dt -> dt,
        column.n -> n,
        column.ip -> ip
      )
    }.update.apply()

    ContractLogonLast(
      lu = lu,
      cid = cid,
      dt = dt,
      n = n,
      ip = ip)
  }

  def batchInsert(entities: Seq[ContractLogonLast])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'lu -> entity.lu,
        'cid -> entity.cid,
        'dt -> entity.dt,
        'n -> entity.n,
        'ip -> entity.ip))
    SQL("""insert into contract_logon_last(
      lu,
      cid,
      dt,
      n,
      ip
    ) values (
      {lu},
      {cid},
      {dt},
      {n},
      {ip}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractLogonLast)(implicit session: DBSession = autoSession): ContractLogonLast = {
    withSQL {
      update(ContractLogonLast).set(
        column.lu -> entity.lu,
        column.cid -> entity.cid,
        column.dt -> entity.dt,
        column.n -> entity.n,
        column.ip -> entity.ip
      ).where.eq(column.cid, entity.cid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractLogonLast)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractLogonLast).where.eq(column.cid, entity.cid) }.update.apply()
  }

}
