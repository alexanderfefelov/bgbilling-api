package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class BonusContractCharge(
  id: Int,
  cid: Int,
  uid: Int,
  sum: BigDecimal,
  date: DateTime,
  contractchargeid: Int) {

  def save()(implicit session: DBSession = BonusContractCharge.autoSession): BonusContractCharge = BonusContractCharge.save(this)(session)

  def destroy()(implicit session: DBSession = BonusContractCharge.autoSession): Int = BonusContractCharge.destroy(this)(session)

}


object BonusContractCharge extends SQLSyntaxSupport[BonusContractCharge] {

  override val tableName = "bonus_contract_charge"

  override val columns = Seq("id", "cid", "uid", "sum", "date", "contractChargeId")

  def apply(bcc: SyntaxProvider[BonusContractCharge])(rs: WrappedResultSet): BonusContractCharge = autoConstruct(rs, bcc)
  def apply(bcc: ResultName[BonusContractCharge])(rs: WrappedResultSet): BonusContractCharge = autoConstruct(rs, bcc)

  val bcc = BonusContractCharge.syntax("bcc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BonusContractCharge] = {
    withSQL {
      select.from(BonusContractCharge as bcc).where.eq(bcc.id, id)
    }.map(BonusContractCharge(bcc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusContractCharge] = {
    withSQL(select.from(BonusContractCharge as bcc)).map(BonusContractCharge(bcc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusContractCharge as bcc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusContractCharge] = {
    withSQL {
      select.from(BonusContractCharge as bcc).where.append(where)
    }.map(BonusContractCharge(bcc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusContractCharge] = {
    withSQL {
      select.from(BonusContractCharge as bcc).where.append(where)
    }.map(BonusContractCharge(bcc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusContractCharge as bcc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    uid: Int,
    sum: BigDecimal,
    date: DateTime,
    contractchargeid: Int)(implicit session: DBSession = autoSession): BonusContractCharge = {
    val generatedKey = withSQL {
      insert.into(BonusContractCharge).namedValues(
        column.cid -> cid,
        column.uid -> uid,
        column.sum -> sum,
        column.date -> date,
        column.contractchargeid -> contractchargeid
      )
    }.updateAndReturnGeneratedKey.apply()

    BonusContractCharge(
      id = generatedKey.toInt,
      cid = cid,
      uid = uid,
      sum = sum,
      date = date,
      contractchargeid = contractchargeid)
  }

  def batchInsert(entities: Seq[BonusContractCharge])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'uid -> entity.uid,
        'sum -> entity.sum,
        'date -> entity.date,
        'contractchargeid -> entity.contractchargeid))
    SQL("""insert into bonus_contract_charge(
      cid,
      uid,
      sum,
      date,
      contractChargeId
    ) values (
      {cid},
      {uid},
      {sum},
      {date},
      {contractchargeid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusContractCharge)(implicit session: DBSession = autoSession): BonusContractCharge = {
    withSQL {
      update(BonusContractCharge).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.uid -> entity.uid,
        column.sum -> entity.sum,
        column.date -> entity.date,
        column.contractchargeid -> entity.contractchargeid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusContractCharge)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusContractCharge).where.eq(column.id, entity.id) }.update.apply()
  }

}
