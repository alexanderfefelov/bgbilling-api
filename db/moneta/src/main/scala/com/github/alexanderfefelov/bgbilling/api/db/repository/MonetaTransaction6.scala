package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}

case class MonetaTransaction6(
  id: Int,
  contractId: Int,
  paymentId: Int,
  createDate: DateTime,
  transactionDate: Option[DateTime] = None,
  transactionId: Option[String] = None,
  status: Option[String] = None,
  params: Option[String] = None,
  sum: BigDecimal) {

  def save()(implicit session: DBSession = MonetaTransaction6.autoSession): MonetaTransaction6 = MonetaTransaction6.save(this)(session)

  def destroy()(implicit session: DBSession = MonetaTransaction6.autoSession): Int = MonetaTransaction6.destroy(this)(session)

}


object MonetaTransaction6 extends SQLSyntaxSupport[MonetaTransaction6] with ApiDbConfig {

  override val tableName = s"moneta_transaction_${bgBillingModuleId("moneta")}"

  override val columns = Seq("id", "contract_id", "payment_id", "create_date", "transaction_date", "transaction_id", "status", "params", "sum")

  def apply(mt: SyntaxProvider[MonetaTransaction6])(rs: WrappedResultSet): MonetaTransaction6 = autoConstruct(rs, mt)
  def apply(mt: ResultName[MonetaTransaction6])(rs: WrappedResultSet): MonetaTransaction6 = autoConstruct(rs, mt)

  val mt = MonetaTransaction6.syntax("mt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MonetaTransaction6] = {
    withSQL {
      select.from(MonetaTransaction6 as mt).where.eq(mt.id, id)
    }.map(MonetaTransaction6(mt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MonetaTransaction6] = {
    withSQL(select.from(MonetaTransaction6 as mt)).map(MonetaTransaction6(mt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MonetaTransaction6 as mt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MonetaTransaction6] = {
    withSQL {
      select.from(MonetaTransaction6 as mt).where.append(where)
    }.map(MonetaTransaction6(mt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MonetaTransaction6] = {
    withSQL {
      select.from(MonetaTransaction6 as mt).where.append(where)
    }.map(MonetaTransaction6(mt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MonetaTransaction6 as mt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    paymentId: Int,
    createDate: DateTime,
    transactionDate: Option[DateTime] = None,
    transactionId: Option[String] = None,
    status: Option[String] = None,
    params: Option[String] = None,
    sum: BigDecimal)(implicit session: DBSession = autoSession): MonetaTransaction6 = {
    val generatedKey = withSQL {
      insert.into(MonetaTransaction6).namedValues(
        column.contractId -> contractId,
        column.paymentId -> paymentId,
        column.createDate -> createDate,
        column.transactionDate -> transactionDate,
        column.transactionId -> transactionId,
        column.status -> status,
        column.params -> params,
        column.sum -> sum
      )
    }.updateAndReturnGeneratedKey.apply()

    MonetaTransaction6(
      id = generatedKey.toInt,
      contractId = contractId,
      paymentId = paymentId,
      createDate = createDate,
      transactionDate = transactionDate,
      transactionId = transactionId,
      status = status,
      params = params,
      sum = sum)
  }

  def batchInsert(entities: Seq[MonetaTransaction6])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'paymentId -> entity.paymentId,
        'createDate -> entity.createDate,
        'transactionDate -> entity.transactionDate,
        'transactionId -> entity.transactionId,
        'status -> entity.status,
        'params -> entity.params,
        'sum -> entity.sum))
    SQL("""insert into moneta_transaction_6(
      contract_id,
      payment_id,
      create_date,
      transaction_date,
      transaction_id,
      status,
      params,
      sum
    ) values (
      {contractId},
      {paymentId},
      {createDate},
      {transactionDate},
      {transactionId},
      {status},
      {params},
      {sum}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MonetaTransaction6)(implicit session: DBSession = autoSession): MonetaTransaction6 = {
    withSQL {
      update(MonetaTransaction6).set(
        column.id -> entity.id,
        column.contractId -> entity.contractId,
        column.paymentId -> entity.paymentId,
        column.createDate -> entity.createDate,
        column.transactionDate -> entity.transactionDate,
        column.transactionId -> entity.transactionId,
        column.status -> entity.status,
        column.params -> entity.params,
        column.sum -> entity.sum
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MonetaTransaction6)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MonetaTransaction6).where.eq(column.id, entity.id) }.update.apply()
  }

}
