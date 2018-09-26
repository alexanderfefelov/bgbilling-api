package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterTypePhone(
  pid: Int,
  cid: Int,
  value: String) {

  def save()(implicit session: DBSession = ContractParameterTypePhone.autoSession): ContractParameterTypePhone = ContractParameterTypePhone.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypePhone.autoSession): Int = ContractParameterTypePhone.destroy(this)(session)

}


object ContractParameterTypePhone extends SQLSyntaxSupport[ContractParameterTypePhone] {

  override val tableName = "contract_parameter_type_phone"

  override val columns = Seq("pid", "cid", "value")

  def apply(cptp: SyntaxProvider[ContractParameterTypePhone])(rs: WrappedResultSet): ContractParameterTypePhone = autoConstruct(rs, cptp)
  def apply(cptp: ResultName[ContractParameterTypePhone])(rs: WrappedResultSet): ContractParameterTypePhone = autoConstruct(rs, cptp)

  val cptp = ContractParameterTypePhone.syntax("cptp")

  override val autoSession = AutoSession

  def find(cid: Int, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhone] = {
    withSQL {
      select.from(ContractParameterTypePhone as cptp).where.eq(cptp.cid, cid).and.eq(cptp.pid, pid)
    }.map(ContractParameterTypePhone(cptp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypePhone] = {
    withSQL(select.from(ContractParameterTypePhone as cptp)).map(ContractParameterTypePhone(cptp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypePhone as cptp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhone] = {
    withSQL {
      select.from(ContractParameterTypePhone as cptp).where.append(where)
    }.map(ContractParameterTypePhone(cptp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypePhone] = {
    withSQL {
      select.from(ContractParameterTypePhone as cptp).where.append(where)
    }.map(ContractParameterTypePhone(cptp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypePhone as cptp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    cid: Int,
    value: String)(implicit session: DBSession = autoSession): ContractParameterTypePhone = {
    withSQL {
      insert.into(ContractParameterTypePhone).namedValues(
        column.pid -> pid,
        column.cid -> cid,
        column.value -> value
      )
    }.update.apply()

    ContractParameterTypePhone(
      pid = pid,
      cid = cid,
      value = value)
  }

  def batchInsert(entities: collection.Seq[ContractParameterTypePhone])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'cid -> entity.cid,
        'value -> entity.value))
    SQL("""insert into contract_parameter_type_phone(
      pid,
      cid,
      value
    ) values (
      {pid},
      {cid},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypePhone)(implicit session: DBSession = autoSession): ContractParameterTypePhone = {
    withSQL {
      update(ContractParameterTypePhone).set(
        column.pid -> entity.pid,
        column.cid -> entity.cid,
        column.value -> entity.value
      ).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypePhone)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypePhone).where.eq(column.cid, entity.cid).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
