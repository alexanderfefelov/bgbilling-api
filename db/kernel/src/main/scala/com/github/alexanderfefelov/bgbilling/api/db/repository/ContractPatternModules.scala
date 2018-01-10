package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPatternModules(
  pid: Int,
  mid: Int) {

  def save()(implicit session: DBSession = ContractPatternModules.autoSession): ContractPatternModules = ContractPatternModules.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPatternModules.autoSession): Int = ContractPatternModules.destroy(this)(session)

}


object ContractPatternModules extends SQLSyntaxSupport[ContractPatternModules] {

  override val tableName = "contract_pattern_modules"

  override val columns = Seq("pid", "mid")

  def apply(cpm: SyntaxProvider[ContractPatternModules])(rs: WrappedResultSet): ContractPatternModules = autoConstruct(rs, cpm)
  def apply(cpm: ResultName[ContractPatternModules])(rs: WrappedResultSet): ContractPatternModules = autoConstruct(rs, cpm)

  val cpm = ContractPatternModules.syntax("cpm")

  override val autoSession = AutoSession

  def find(pid: Int, mid: Int)(implicit session: DBSession = autoSession): Option[ContractPatternModules] = {
    withSQL {
      select.from(ContractPatternModules as cpm).where.eq(cpm.pid, pid).and.eq(cpm.mid, mid)
    }.map(ContractPatternModules(cpm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPatternModules] = {
    withSQL(select.from(ContractPatternModules as cpm)).map(ContractPatternModules(cpm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPatternModules as cpm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPatternModules] = {
    withSQL {
      select.from(ContractPatternModules as cpm).where.append(where)
    }.map(ContractPatternModules(cpm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPatternModules] = {
    withSQL {
      select.from(ContractPatternModules as cpm).where.append(where)
    }.map(ContractPatternModules(cpm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPatternModules as cpm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    mid: Int)(implicit session: DBSession = autoSession): ContractPatternModules = {
    withSQL {
      insert.into(ContractPatternModules).namedValues(
        column.pid -> pid,
        column.mid -> mid
      )
    }.update.apply()

    ContractPatternModules(
      pid = pid,
      mid = mid)
  }

  def batchInsert(entities: Seq[ContractPatternModules])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'mid -> entity.mid))
    SQL("""insert into contract_pattern_modules(
      pid,
      mid
    ) values (
      {pid},
      {mid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPatternModules)(implicit session: DBSession = autoSession): ContractPatternModules = {
    withSQL {
      update(ContractPatternModules).set(
        column.pid -> entity.pid,
        column.mid -> entity.mid
      ).where.eq(column.pid, entity.pid).and.eq(column.mid, entity.mid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPatternModules)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPatternModules).where.eq(column.pid, entity.pid).and.eq(column.mid, entity.mid) }.update.apply()
  }

}
