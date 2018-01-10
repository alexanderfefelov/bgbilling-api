package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPatternServices(
  pid: Int,
  sid: Int) {

  def save()(implicit session: DBSession = ContractPatternServices.autoSession): ContractPatternServices = ContractPatternServices.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPatternServices.autoSession): Int = ContractPatternServices.destroy(this)(session)

}


object ContractPatternServices extends SQLSyntaxSupport[ContractPatternServices] {

  override val tableName = "contract_pattern_services"

  override val columns = Seq("pid", "sid")

  def apply(cps: SyntaxProvider[ContractPatternServices])(rs: WrappedResultSet): ContractPatternServices = autoConstruct(rs, cps)
  def apply(cps: ResultName[ContractPatternServices])(rs: WrappedResultSet): ContractPatternServices = autoConstruct(rs, cps)

  val cps = ContractPatternServices.syntax("cps")

  override val autoSession = AutoSession

  def find(pid: Int, sid: Int)(implicit session: DBSession = autoSession): Option[ContractPatternServices] = {
    withSQL {
      select.from(ContractPatternServices as cps).where.eq(cps.pid, pid).and.eq(cps.sid, sid)
    }.map(ContractPatternServices(cps.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPatternServices] = {
    withSQL(select.from(ContractPatternServices as cps)).map(ContractPatternServices(cps.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPatternServices as cps)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPatternServices] = {
    withSQL {
      select.from(ContractPatternServices as cps).where.append(where)
    }.map(ContractPatternServices(cps.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPatternServices] = {
    withSQL {
      select.from(ContractPatternServices as cps).where.append(where)
    }.map(ContractPatternServices(cps.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPatternServices as cps).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    sid: Int)(implicit session: DBSession = autoSession): ContractPatternServices = {
    withSQL {
      insert.into(ContractPatternServices).namedValues(
        column.pid -> pid,
        column.sid -> sid
      )
    }.update.apply()

    ContractPatternServices(
      pid = pid,
      sid = sid)
  }

  def batchInsert(entities: Seq[ContractPatternServices])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'sid -> entity.sid))
    SQL("""insert into contract_pattern_services(
      pid,
      sid
    ) values (
      {pid},
      {sid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPatternServices)(implicit session: DBSession = autoSession): ContractPatternServices = {
    withSQL {
      update(ContractPatternServices).set(
        column.pid -> entity.pid,
        column.sid -> entity.sid
      ).where.eq(column.pid, entity.pid).and.eq(column.sid, entity.sid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPatternServices)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPatternServices).where.eq(column.pid, entity.pid).and.eq(column.sid, entity.sid) }.update.apply()
  }

}
