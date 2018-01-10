package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterTypeMultilist(
  cid: Int,
  pid: Int,
  `val`: String) {

  def save()(implicit session: DBSession = ContractParameterTypeMultilist.autoSession): ContractParameterTypeMultilist = ContractParameterTypeMultilist.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypeMultilist.autoSession): Int = ContractParameterTypeMultilist.destroy(this)(session)

}


object ContractParameterTypeMultilist extends SQLSyntaxSupport[ContractParameterTypeMultilist] {

  override val tableName = "contract_parameter_type_multilist"

  override val columns = Seq("cid", "pid", "val")

  def apply(cptm: SyntaxProvider[ContractParameterTypeMultilist])(rs: WrappedResultSet): ContractParameterTypeMultilist = autoConstruct(rs, cptm)
  def apply(cptm: ResultName[ContractParameterTypeMultilist])(rs: WrappedResultSet): ContractParameterTypeMultilist = autoConstruct(rs, cptm)

  val cptm = ContractParameterTypeMultilist.syntax("cptm")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilist] = {
    withSQL {
      select.from(ContractParameterTypeMultilist as cptm).where.eq(cptm.cid, cid).and.eq(cptm.pid, pid)
    }.map(ContractParameterTypeMultilist(cptm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilist] = {
    withSQL(select.from(ContractParameterTypeMultilist as cptm)).map(ContractParameterTypeMultilist(cptm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypeMultilist as cptm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilist] = {
    withSQL {
      select.from(ContractParameterTypeMultilist as cptm).where.append(where)
    }.map(ContractParameterTypeMultilist(cptm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilist] = {
    withSQL {
      select.from(ContractParameterTypeMultilist as cptm).where.append(where)
    }.map(ContractParameterTypeMultilist(cptm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypeMultilist as cptm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    pid: Int,
    `val`: String)(implicit session: DBSession = autoSession): ContractParameterTypeMultilist = {
    withSQL {
      insert.into(ContractParameterTypeMultilist).namedValues(
        column.cid -> cid,
        column.pid -> pid,
        column.`val` -> `val`
      )
    }.update.apply()

    ContractParameterTypeMultilist(
      cid = cid,
      pid = pid,
      `val` = `val`)
  }

  def batchInsert(entities: Seq[ContractParameterTypeMultilist])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'pid -> entity.pid,
        'val -> entity.`val`))
    SQL("""insert into contract_parameter_type_multilist(
      cid,
      pid,
      val
    ) values (
      {cid},
      {pid},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypeMultilist)(implicit session: DBSession = autoSession): ContractParameterTypeMultilist = {
    withSQL {
      update(ContractParameterTypeMultilist).set(
        column.cid -> entity.cid,
        column.pid -> entity.pid,
        column.`val` -> entity.`val`
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypeMultilist)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypeMultilist).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
