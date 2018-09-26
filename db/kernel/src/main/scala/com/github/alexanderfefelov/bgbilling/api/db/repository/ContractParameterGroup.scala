package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterGroup(
  gid: Int,
  pid: Int) {

  def save()(implicit session: DBSession = ContractParameterGroup.autoSession): ContractParameterGroup = ContractParameterGroup.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterGroup.autoSession): Int = ContractParameterGroup.destroy(this)(session)

}


object ContractParameterGroup extends SQLSyntaxSupport[ContractParameterGroup] {

  override val tableName = "contract_parameter_group"

  override val columns = Seq("gid", "pid")

  def apply(cpg: SyntaxProvider[ContractParameterGroup])(rs: WrappedResultSet): ContractParameterGroup = autoConstruct(rs, cpg)
  def apply(cpg: ResultName[ContractParameterGroup])(rs: WrappedResultSet): ContractParameterGroup = autoConstruct(rs, cpg)

  val cpg = ContractParameterGroup.syntax("cpg")

  override val autoSession = AutoSession

  def find(gid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterGroup] = {
    withSQL {
      select.from(ContractParameterGroup as cpg).where.eq(cpg.gid, gid).and.eq(cpg.pid, pid)
    }.map(ContractParameterGroup(cpg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterGroup] = {
    withSQL(select.from(ContractParameterGroup as cpg)).map(ContractParameterGroup(cpg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterGroup as cpg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterGroup] = {
    withSQL {
      select.from(ContractParameterGroup as cpg).where.append(where)
    }.map(ContractParameterGroup(cpg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterGroup] = {
    withSQL {
      select.from(ContractParameterGroup as cpg).where.append(where)
    }.map(ContractParameterGroup(cpg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterGroup as cpg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    gid: Int,
    pid: Int)(implicit session: DBSession = autoSession): ContractParameterGroup = {
    withSQL {
      insert.into(ContractParameterGroup).namedValues(
        column.gid -> gid,
        column.pid -> pid
      )
    }.update.apply()

    ContractParameterGroup(
      gid = gid,
      pid = pid)
  }

  def batchInsert(entities: collection.Seq[ContractParameterGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'gid -> entity.gid,
        'pid -> entity.pid))
    SQL("""insert into contract_parameter_group(
      gid,
      pid
    ) values (
      {gid},
      {pid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterGroup)(implicit session: DBSession = autoSession): ContractParameterGroup = {
    withSQL {
      update(ContractParameterGroup).set(
        column.gid -> entity.gid,
        column.pid -> entity.pid
      ).where.eq(column.gid, entity.gid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterGroup).where.eq(column.gid, entity.gid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
