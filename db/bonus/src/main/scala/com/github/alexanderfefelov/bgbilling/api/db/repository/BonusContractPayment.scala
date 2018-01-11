package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}

case class BonusContractPayment(
  id: Int,
  cid: Int,
  uid: Int,
  typeid: Int,
  date: DateTime,
  sum: BigDecimal,
  datefrom: LocalDate,
  dateto: LocalDate,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = BonusContractPayment.autoSession): BonusContractPayment = BonusContractPayment.save(this)(session)

  def destroy()(implicit session: DBSession = BonusContractPayment.autoSession): Int = BonusContractPayment.destroy(this)(session)

}


object BonusContractPayment extends SQLSyntaxSupport[BonusContractPayment] {

  override val tableName = "bonus_contract_payment"

  override val columns = Seq("id", "cid", "uid", "typeId", "date", "sum", "dateFrom", "dateTo", "comment")

  def apply(bcp: SyntaxProvider[BonusContractPayment])(rs: WrappedResultSet): BonusContractPayment = autoConstruct(rs, bcp)
  def apply(bcp: ResultName[BonusContractPayment])(rs: WrappedResultSet): BonusContractPayment = autoConstruct(rs, bcp)

  val bcp = BonusContractPayment.syntax("bcp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BonusContractPayment] = {
    withSQL {
      select.from(BonusContractPayment as bcp).where.eq(bcp.id, id)
    }.map(BonusContractPayment(bcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusContractPayment] = {
    withSQL(select.from(BonusContractPayment as bcp)).map(BonusContractPayment(bcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusContractPayment as bcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusContractPayment] = {
    withSQL {
      select.from(BonusContractPayment as bcp).where.append(where)
    }.map(BonusContractPayment(bcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusContractPayment] = {
    withSQL {
      select.from(BonusContractPayment as bcp).where.append(where)
    }.map(BonusContractPayment(bcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusContractPayment as bcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    uid: Int,
    typeid: Int,
    date: DateTime,
    sum: BigDecimal,
    datefrom: LocalDate,
    dateto: LocalDate,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): BonusContractPayment = {
    val generatedKey = withSQL {
      insert.into(BonusContractPayment).namedValues(
        column.cid -> cid,
        column.uid -> uid,
        column.typeid -> typeid,
        column.date -> date,
        column.sum -> sum,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    BonusContractPayment(
      id = generatedKey.toInt,
      cid = cid,
      uid = uid,
      typeid = typeid,
      date = date,
      sum = sum,
      datefrom = datefrom,
      dateto = dateto,
      comment = comment)
  }

  def batchInsert(entities: Seq[BonusContractPayment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'uid -> entity.uid,
        'typeid -> entity.typeid,
        'date -> entity.date,
        'sum -> entity.sum,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'comment -> entity.comment))
    SQL("""insert into bonus_contract_payment(
      cid,
      uid,
      typeId,
      date,
      sum,
      dateFrom,
      dateTo,
      comment
    ) values (
      {cid},
      {uid},
      {typeid},
      {date},
      {sum},
      {datefrom},
      {dateto},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusContractPayment)(implicit session: DBSession = autoSession): BonusContractPayment = {
    withSQL {
      update(BonusContractPayment).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.uid -> entity.uid,
        column.typeid -> entity.typeid,
        column.date -> entity.date,
        column.sum -> entity.sum,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusContractPayment)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusContractPayment).where.eq(column.id, entity.id) }.update.apply()
  }

}
