package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class QiwiTransaction7(
  id: Int,
  cid: Option[Int] = None,
  createDate: Option[DateTime] = None,
  endDate: Option[DateTime] = None,
  summ: Option[BigDecimal] = None,
  status: Option[String] = None,
  addKey: Option[String] = None,
  refundId: Option[String] = None) {

  def save()(implicit session: DBSession = QiwiTransaction7.autoSession): QiwiTransaction7 = QiwiTransaction7.save(this)(session)

  def destroy()(implicit session: DBSession = QiwiTransaction7.autoSession): Int = QiwiTransaction7.destroy(this)(session)

}


object QiwiTransaction7 extends SQLSyntaxSupport[QiwiTransaction7] with ApiDbConfig {

  override val tableName = s"qiwi_transaction_${bgBillingModuleId("qiwi")}"

  override val columns = Seq("id", "cid", "create_date", "end_date", "summ", "status", "add_key", "refund_id")

  def apply(qt: SyntaxProvider[QiwiTransaction7])(rs: WrappedResultSet): QiwiTransaction7 = autoConstruct(rs, qt)
  def apply(qt: ResultName[QiwiTransaction7])(rs: WrappedResultSet): QiwiTransaction7 = autoConstruct(rs, qt)

  val qt = QiwiTransaction7.syntax("qt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[QiwiTransaction7] = {
    withSQL {
      select.from(QiwiTransaction7 as qt).where.eq(qt.id, id)
    }.map(QiwiTransaction7(qt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[QiwiTransaction7] = {
    withSQL(select.from(QiwiTransaction7 as qt)).map(QiwiTransaction7(qt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(QiwiTransaction7 as qt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[QiwiTransaction7] = {
    withSQL {
      select.from(QiwiTransaction7 as qt).where.append(where)
    }.map(QiwiTransaction7(qt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[QiwiTransaction7] = {
    withSQL {
      select.from(QiwiTransaction7 as qt).where.append(where)
    }.map(QiwiTransaction7(qt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(QiwiTransaction7 as qt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Option[Int] = None,
    createDate: Option[DateTime] = None,
    endDate: Option[DateTime] = None,
    summ: Option[BigDecimal] = None,
    status: Option[String] = None,
    addKey: Option[String] = None,
    refundId: Option[String] = None)(implicit session: DBSession = autoSession): QiwiTransaction7 = {
    val generatedKey = withSQL {
      insert.into(QiwiTransaction7).namedValues(
        column.cid -> cid,
        column.createDate -> createDate,
        column.endDate -> endDate,
        column.summ -> summ,
        column.status -> status,
        column.addKey -> addKey,
        column.refundId -> refundId
      )
    }.updateAndReturnGeneratedKey.apply()

    QiwiTransaction7(
      id = generatedKey.toInt,
      cid = cid,
      createDate = createDate,
      endDate = endDate,
      summ = summ,
      status = status,
      addKey = addKey,
      refundId = refundId)
  }

  def batchInsert(entities: Seq[QiwiTransaction7])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'createDate -> entity.createDate,
        'endDate -> entity.endDate,
        'summ -> entity.summ,
        'status -> entity.status,
        'addKey -> entity.addKey,
        'refundId -> entity.refundId))
    SQL("""insert into qiwi_transaction_7(
      cid,
      create_date,
      end_date,
      summ,
      status,
      add_key,
      refund_id
    ) values (
      {cid},
      {createDate},
      {endDate},
      {summ},
      {status},
      {addKey},
      {refundId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: QiwiTransaction7)(implicit session: DBSession = autoSession): QiwiTransaction7 = {
    withSQL {
      update(QiwiTransaction7).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.createDate -> entity.createDate,
        column.endDate -> entity.endDate,
        column.summ -> entity.summ,
        column.status -> entity.status,
        column.addKey -> entity.addKey,
        column.refundId -> entity.refundId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: QiwiTransaction7)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(QiwiTransaction7).where.eq(column.id, entity.id) }.update.apply()
  }

}
