package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType3Mail(
  mailid: Int,
  eid: Int) {

  def save()(implicit session: DBSession = ContractParameterType3Mail.autoSession): ContractParameterType3Mail = ContractParameterType3Mail.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType3Mail.autoSession): Int = ContractParameterType3Mail.destroy(this)(session)

}


object ContractParameterType3Mail extends SQLSyntaxSupport[ContractParameterType3Mail] {

  override val tableName = "contract_parameter_type_3_mail"

  override val columns = Seq("mailid", "eid")

  def apply(cptm: SyntaxProvider[ContractParameterType3Mail])(rs: WrappedResultSet): ContractParameterType3Mail = autoConstruct(rs, cptm)
  def apply(cptm: ResultName[ContractParameterType3Mail])(rs: WrappedResultSet): ContractParameterType3Mail = autoConstruct(rs, cptm)

  val cptm = ContractParameterType3Mail.syntax("cptm")

  override val autoSession = AutoSession

  def find(mailid: Int, eid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType3Mail] = {
    withSQL {
      select.from(ContractParameterType3Mail as cptm).where.eq(cptm.mailid, mailid).and.eq(cptm.eid, eid)
    }.map(ContractParameterType3Mail(cptm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType3Mail] = {
    withSQL(select.from(ContractParameterType3Mail as cptm)).map(ContractParameterType3Mail(cptm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType3Mail as cptm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType3Mail] = {
    withSQL {
      select.from(ContractParameterType3Mail as cptm).where.append(where)
    }.map(ContractParameterType3Mail(cptm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType3Mail] = {
    withSQL {
      select.from(ContractParameterType3Mail as cptm).where.append(where)
    }.map(ContractParameterType3Mail(cptm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType3Mail as cptm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mailid: Int,
    eid: Int)(implicit session: DBSession = autoSession): ContractParameterType3Mail = {
    withSQL {
      insert.into(ContractParameterType3Mail).namedValues(
        column.mailid -> mailid,
        column.eid -> eid
      )
    }.update.apply()

    ContractParameterType3Mail(
      mailid = mailid,
      eid = eid)
  }

  def batchInsert(entities: collection.Seq[ContractParameterType3Mail])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mailid -> entity.mailid,
        'eid -> entity.eid))
    SQL("""insert into contract_parameter_type_3_mail(
      mailid,
      eid
    ) values (
      {mailid},
      {eid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType3Mail)(implicit session: DBSession = autoSession): ContractParameterType3Mail = {
    withSQL {
      update(ContractParameterType3Mail).set(
        column.mailid -> entity.mailid,
        column.eid -> entity.eid
      ).where.eq(column.mailid, entity.mailid).and.eq(column.eid, entity.eid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType3Mail)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType3Mail).where.eq(column.mailid, entity.mailid).and.eq(column.eid, entity.eid) }.update.apply()
  }

}
