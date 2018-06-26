package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class BillInvoiceData5(
  id: Int,
  cid: Int,
  `type`: Int,
  number: Int,
  numberInMonth: Int,
  numberInYear: Int,
  formatNumber: String,
  uid: Int,
  yy: Int,
  mm: Int,
  createDt: Option[LocalDate] = None,
  unloadStatus: Int,
  summ: BigDecimal,
  showReady: Byte,
  xml: Array[Byte],
  npid: Option[Int] = None) {

  def save()(implicit session: DBSession = BillInvoiceData5.autoSession): BillInvoiceData5 = BillInvoiceData5.save(this)(session)

  def destroy()(implicit session: DBSession = BillInvoiceData5.autoSession): Int = BillInvoiceData5.destroy(this)(session)

}


object BillInvoiceData5 extends SQLSyntaxSupport[BillInvoiceData5] with ApiDbConfig {

  override val tableName = s"bill_invoice_data_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "cid", "type", "number", "number_in_month", "number_in_year", "format_number", "uid", "yy", "mm", "create_dt", "unload_status", "summ", "show_ready", "xml", "npid")

  def apply(bid: SyntaxProvider[BillInvoiceData5])(rs: WrappedResultSet): BillInvoiceData5 = autoConstruct(rs, bid)
  def apply(bid: ResultName[BillInvoiceData5])(rs: WrappedResultSet): BillInvoiceData5 = autoConstruct(rs, bid)

  val bid = BillInvoiceData5.syntax("bid")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillInvoiceData5] = {
    withSQL {
      select.from(BillInvoiceData5 as bid).where.eq(bid.id, id)
    }.map(BillInvoiceData5(bid.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillInvoiceData5] = {
    withSQL(select.from(BillInvoiceData5 as bid)).map(BillInvoiceData5(bid.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillInvoiceData5 as bid)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillInvoiceData5] = {
    withSQL {
      select.from(BillInvoiceData5 as bid).where.append(where)
    }.map(BillInvoiceData5(bid.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillInvoiceData5] = {
    withSQL {
      select.from(BillInvoiceData5 as bid).where.append(where)
    }.map(BillInvoiceData5(bid.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillInvoiceData5 as bid).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    `type`: Int,
    number: Int,
    numberInMonth: Int,
    numberInYear: Int,
    formatNumber: String,
    uid: Int,
    yy: Int,
    mm: Int,
    createDt: Option[LocalDate] = None,
    unloadStatus: Int,
    summ: BigDecimal,
    showReady: Byte,
    xml: Array[Byte],
    npid: Option[Int] = None)(implicit session: DBSession = autoSession): BillInvoiceData5 = {
    val generatedKey = withSQL {
      insert.into(BillInvoiceData5).namedValues(
        column.cid -> cid,
        column.`type` -> `type`,
        column.number -> number,
        column.numberInMonth -> numberInMonth,
        column.numberInYear -> numberInYear,
        column.formatNumber -> formatNumber,
        column.uid -> uid,
        column.yy -> yy,
        column.mm -> mm,
        column.createDt -> createDt,
        column.unloadStatus -> unloadStatus,
        column.summ -> summ,
        column.showReady -> showReady,
        column.xml -> xml,
        column.npid -> npid
      )
    }.updateAndReturnGeneratedKey.apply()

    BillInvoiceData5(
      id = generatedKey.toInt,
      cid = cid,
      `type` = `type`,
      number = number,
      numberInMonth = numberInMonth,
      numberInYear = numberInYear,
      formatNumber = formatNumber,
      uid = uid,
      yy = yy,
      mm = mm,
      createDt = createDt,
      unloadStatus = unloadStatus,
      summ = summ,
      showReady = showReady,
      xml = xml,
      npid = npid)
  }

  def batchInsert(entities: Seq[BillInvoiceData5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'type -> entity.`type`,
        'number -> entity.number,
        'numberInMonth -> entity.numberInMonth,
        'numberInYear -> entity.numberInYear,
        'formatNumber -> entity.formatNumber,
        'uid -> entity.uid,
        'yy -> entity.yy,
        'mm -> entity.mm,
        'createDt -> entity.createDt,
        'unloadStatus -> entity.unloadStatus,
        'summ -> entity.summ,
        'showReady -> entity.showReady,
        'xml -> entity.xml,
        'npid -> entity.npid))
    SQL("""insert into bill_invoice_data_5(
      cid,
      type,
      number,
      number_in_month,
      number_in_year,
      format_number,
      uid,
      yy,
      mm,
      create_dt,
      unload_status,
      summ,
      show_ready,
      xml,
      npid
    ) values (
      {cid},
      {type},
      {number},
      {numberInMonth},
      {numberInYear},
      {formatNumber},
      {uid},
      {yy},
      {mm},
      {createDt},
      {unloadStatus},
      {summ},
      {showReady},
      {xml},
      {npid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillInvoiceData5)(implicit session: DBSession = autoSession): BillInvoiceData5 = {
    withSQL {
      update(BillInvoiceData5).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.`type` -> entity.`type`,
        column.number -> entity.number,
        column.numberInMonth -> entity.numberInMonth,
        column.numberInYear -> entity.numberInYear,
        column.formatNumber -> entity.formatNumber,
        column.uid -> entity.uid,
        column.yy -> entity.yy,
        column.mm -> entity.mm,
        column.createDt -> entity.createDt,
        column.unloadStatus -> entity.unloadStatus,
        column.summ -> entity.summ,
        column.showReady -> entity.showReady,
        column.xml -> entity.xml,
        column.npid -> entity.npid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillInvoiceData5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillInvoiceData5).where.eq(column.id, entity.id) }.update.apply()
  }

}
