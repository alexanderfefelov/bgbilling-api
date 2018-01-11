package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._
import org.joda.time.{LocalDate}

case class BillData5(
  id: Int,
  cid: Int,
  `type`: Int,
  accountId: Int,
  number: Int,
  numberInMonth: Int,
  numberInYear: Int,
  formatNumber: String,
  uid: Int,
  yy: Int,
  mm: Int,
  createDt: Option[LocalDate] = None,
  payDt: Option[LocalDate] = None,
  status: Byte,
  unloadStatus: Int,
  summ: BigDecimal,
  xml: Array[Byte],
  paymentId: Int,
  npid: Option[Int] = None,
  payUid: Option[Int] = None) {

  def save()(implicit session: DBSession = BillData5.autoSession): BillData5 = BillData5.save(this)(session)

  def destroy()(implicit session: DBSession = BillData5.autoSession): Int = BillData5.destroy(this)(session)

}


object BillData5 extends SQLSyntaxSupport[BillData5] with ApiDbConfig {

  override val tableName = s"bill_data_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "cid", "type", "account_id", "number", "number_in_month", "number_in_year", "format_number", "uid", "yy", "mm", "create_dt", "pay_dt", "status", "unload_status", "summ", "xml", "payment_id", "npid", "pay_uid")

  def apply(bd: SyntaxProvider[BillData5])(rs: WrappedResultSet): BillData5 = autoConstruct(rs, bd)
  def apply(bd: ResultName[BillData5])(rs: WrappedResultSet): BillData5 = autoConstruct(rs, bd)

  val bd = BillData5.syntax("bd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillData5] = {
    withSQL {
      select.from(BillData5 as bd).where.eq(bd.id, id)
    }.map(BillData5(bd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillData5] = {
    withSQL(select.from(BillData5 as bd)).map(BillData5(bd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillData5 as bd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillData5] = {
    withSQL {
      select.from(BillData5 as bd).where.append(where)
    }.map(BillData5(bd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillData5] = {
    withSQL {
      select.from(BillData5 as bd).where.append(where)
    }.map(BillData5(bd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillData5 as bd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    `type`: Int,
    accountId: Int,
    number: Int,
    numberInMonth: Int,
    numberInYear: Int,
    formatNumber: String,
    uid: Int,
    yy: Int,
    mm: Int,
    createDt: Option[LocalDate] = None,
    payDt: Option[LocalDate] = None,
    status: Byte,
    unloadStatus: Int,
    summ: BigDecimal,
    xml: Array[Byte],
    paymentId: Int,
    npid: Option[Int] = None,
    payUid: Option[Int] = None)(implicit session: DBSession = autoSession): BillData5 = {
    val generatedKey = withSQL {
      insert.into(BillData5).namedValues(
        column.cid -> cid,
        column.`type` -> `type`,
        column.accountId -> accountId,
        column.number -> number,
        column.numberInMonth -> numberInMonth,
        column.numberInYear -> numberInYear,
        column.formatNumber -> formatNumber,
        column.uid -> uid,
        column.yy -> yy,
        column.mm -> mm,
        column.createDt -> createDt,
        column.payDt -> payDt,
        column.status -> status,
        column.unloadStatus -> unloadStatus,
        column.summ -> summ,
        column.xml -> xml,
        column.paymentId -> paymentId,
        column.npid -> npid,
        column.payUid -> payUid
      )
    }.updateAndReturnGeneratedKey.apply()

    BillData5(
      id = generatedKey.toInt,
      cid = cid,
      `type` = `type`,
      accountId = accountId,
      number = number,
      numberInMonth = numberInMonth,
      numberInYear = numberInYear,
      formatNumber = formatNumber,
      uid = uid,
      yy = yy,
      mm = mm,
      createDt = createDt,
      payDt = payDt,
      status = status,
      unloadStatus = unloadStatus,
      summ = summ,
      xml = xml,
      paymentId = paymentId,
      npid = npid,
      payUid = payUid)
  }

  def batchInsert(entities: Seq[BillData5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'type -> entity.`type`,
        'accountId -> entity.accountId,
        'number -> entity.number,
        'numberInMonth -> entity.numberInMonth,
        'numberInYear -> entity.numberInYear,
        'formatNumber -> entity.formatNumber,
        'uid -> entity.uid,
        'yy -> entity.yy,
        'mm -> entity.mm,
        'createDt -> entity.createDt,
        'payDt -> entity.payDt,
        'status -> entity.status,
        'unloadStatus -> entity.unloadStatus,
        'summ -> entity.summ,
        'xml -> entity.xml,
        'paymentId -> entity.paymentId,
        'npid -> entity.npid,
        'payUid -> entity.payUid))
    SQL("""insert into bill_data_5(
      cid,
      type,
      account_id,
      number,
      number_in_month,
      number_in_year,
      format_number,
      uid,
      yy,
      mm,
      create_dt,
      pay_dt,
      status,
      unload_status,
      summ,
      xml,
      payment_id,
      npid,
      pay_uid
    ) values (
      {cid},
      {type},
      {accountId},
      {number},
      {numberInMonth},
      {numberInYear},
      {formatNumber},
      {uid},
      {yy},
      {mm},
      {createDt},
      {payDt},
      {status},
      {unloadStatus},
      {summ},
      {xml},
      {paymentId},
      {npid},
      {payUid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillData5)(implicit session: DBSession = autoSession): BillData5 = {
    withSQL {
      update(BillData5).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.`type` -> entity.`type`,
        column.accountId -> entity.accountId,
        column.number -> entity.number,
        column.numberInMonth -> entity.numberInMonth,
        column.numberInYear -> entity.numberInYear,
        column.formatNumber -> entity.formatNumber,
        column.uid -> entity.uid,
        column.yy -> entity.yy,
        column.mm -> entity.mm,
        column.createDt -> entity.createDt,
        column.payDt -> entity.payDt,
        column.status -> entity.status,
        column.unloadStatus -> entity.unloadStatus,
        column.summ -> entity.summ,
        column.xml -> entity.xml,
        column.paymentId -> entity.paymentId,
        column.npid -> entity.npid,
        column.payUid -> entity.payUid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillData5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillData5).where.eq(column.id, entity.id) }.update.apply()
  }

}
