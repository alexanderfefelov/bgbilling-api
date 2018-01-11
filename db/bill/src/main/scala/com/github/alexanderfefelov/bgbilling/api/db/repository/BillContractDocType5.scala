package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class BillContractDocType5(
  id: Int,
  cid: Int,
  docType: Int) {

  def save()(implicit session: DBSession = BillContractDocType5.autoSession): BillContractDocType5 = BillContractDocType5.save(this)(session)

  def destroy()(implicit session: DBSession = BillContractDocType5.autoSession): Int = BillContractDocType5.destroy(this)(session)

}


object BillContractDocType5 extends SQLSyntaxSupport[BillContractDocType5] with ApiDbConfig {

  override val tableName = s"bill_contract_doc_type_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "cid", "doc_type")

  def apply(bcdt: SyntaxProvider[BillContractDocType5])(rs: WrappedResultSet): BillContractDocType5 = autoConstruct(rs, bcdt)
  def apply(bcdt: ResultName[BillContractDocType5])(rs: WrappedResultSet): BillContractDocType5 = autoConstruct(rs, bcdt)

  val bcdt = BillContractDocType5.syntax("bcdt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillContractDocType5] = {
    withSQL {
      select.from(BillContractDocType5 as bcdt).where.eq(bcdt.id, id)
    }.map(BillContractDocType5(bcdt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillContractDocType5] = {
    withSQL(select.from(BillContractDocType5 as bcdt)).map(BillContractDocType5(bcdt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillContractDocType5 as bcdt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillContractDocType5] = {
    withSQL {
      select.from(BillContractDocType5 as bcdt).where.append(where)
    }.map(BillContractDocType5(bcdt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillContractDocType5] = {
    withSQL {
      select.from(BillContractDocType5 as bcdt).where.append(where)
    }.map(BillContractDocType5(bcdt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillContractDocType5 as bcdt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    docType: Int)(implicit session: DBSession = autoSession): BillContractDocType5 = {
    val generatedKey = withSQL {
      insert.into(BillContractDocType5).namedValues(
        column.cid -> cid,
        column.docType -> docType
      )
    }.updateAndReturnGeneratedKey.apply()

    BillContractDocType5(
      id = generatedKey.toInt,
      cid = cid,
      docType = docType)
  }

  def batchInsert(entities: Seq[BillContractDocType5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'docType -> entity.docType))
    SQL("""insert into bill_contract_doc_type_5(
      cid,
      doc_type
    ) values (
      {cid},
      {docType}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillContractDocType5)(implicit session: DBSession = autoSession): BillContractDocType5 = {
    withSQL {
      update(BillContractDocType5).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.docType -> entity.docType
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillContractDocType5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillContractDocType5).where.eq(column.id, entity.id) }.update.apply()
  }

}
