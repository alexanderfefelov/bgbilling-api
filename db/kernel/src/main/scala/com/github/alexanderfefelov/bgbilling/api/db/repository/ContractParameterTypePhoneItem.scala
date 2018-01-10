package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterTypePhoneItem(
  pid: Int,
  cid: Int,
  n: Byte,
  phone: Option[String] = None,
  format: Option[String] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = ContractParameterTypePhoneItem.autoSession): ContractParameterTypePhoneItem = ContractParameterTypePhoneItem.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypePhoneItem.autoSession): Int = ContractParameterTypePhoneItem.destroy(this)(session)

}


object ContractParameterTypePhoneItem extends SQLSyntaxSupport[ContractParameterTypePhoneItem] {

  override val tableName = "contract_parameter_type_phone_item"

  override val columns = Seq("pid", "cid", "n", "phone", "format", "comment")

  def apply(cptpi: SyntaxProvider[ContractParameterTypePhoneItem])(rs: WrappedResultSet): ContractParameterTypePhoneItem = autoConstruct(rs, cptpi)
  def apply(cptpi: ResultName[ContractParameterTypePhoneItem])(rs: WrappedResultSet): ContractParameterTypePhoneItem = autoConstruct(rs, cptpi)

  val cptpi = ContractParameterTypePhoneItem.syntax("cptpi")

  override val autoSession = AutoSession

  def find(cid: Int, n: Byte, pid: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhoneItem] = {
    withSQL {
      select.from(ContractParameterTypePhoneItem as cptpi).where.eq(cptpi.cid, cid).and.eq(cptpi.n, n).and.eq(cptpi.pid, pid)
    }.map(ContractParameterTypePhoneItem(cptpi.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypePhoneItem] = {
    withSQL(select.from(ContractParameterTypePhoneItem as cptpi)).map(ContractParameterTypePhoneItem(cptpi.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypePhoneItem as cptpi)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypePhoneItem] = {
    withSQL {
      select.from(ContractParameterTypePhoneItem as cptpi).where.append(where)
    }.map(ContractParameterTypePhoneItem(cptpi.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypePhoneItem] = {
    withSQL {
      select.from(ContractParameterTypePhoneItem as cptpi).where.append(where)
    }.map(ContractParameterTypePhoneItem(cptpi.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypePhoneItem as cptpi).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    cid: Int,
    n: Byte,
    phone: Option[String] = None,
    format: Option[String] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): ContractParameterTypePhoneItem = {
    withSQL {
      insert.into(ContractParameterTypePhoneItem).namedValues(
        column.pid -> pid,
        column.cid -> cid,
        column.n -> n,
        column.phone -> phone,
        column.format -> format,
        column.comment -> comment
      )
    }.update.apply()

    ContractParameterTypePhoneItem(
      pid = pid,
      cid = cid,
      n = n,
      phone = phone,
      format = format,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractParameterTypePhoneItem])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'cid -> entity.cid,
        'n -> entity.n,
        'phone -> entity.phone,
        'format -> entity.format,
        'comment -> entity.comment))
    SQL("""insert into contract_parameter_type_phone_item(
      pid,
      cid,
      n,
      phone,
      format,
      comment
    ) values (
      {pid},
      {cid},
      {n},
      {phone},
      {format},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypePhoneItem)(implicit session: DBSession = autoSession): ContractParameterTypePhoneItem = {
    withSQL {
      update(ContractParameterTypePhoneItem).set(
        column.pid -> entity.pid,
        column.cid -> entity.cid,
        column.n -> entity.n,
        column.phone -> entity.phone,
        column.format -> entity.format,
        column.comment -> entity.comment
      ).where.eq(column.cid, entity.cid).and.eq(column.n, entity.n).and.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypePhoneItem)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypePhoneItem).where.eq(column.cid, entity.cid).and.eq(column.n, entity.n).and.eq(column.pid, entity.pid) }.update.apply()
  }

}
