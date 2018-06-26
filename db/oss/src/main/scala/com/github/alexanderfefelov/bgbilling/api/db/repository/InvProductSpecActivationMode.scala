package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class InvProductSpecActivationMode(
  id: Int,
  productspecid: Int,
  title: Option[String] = None,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  periodmode: Int,
  periodamount: Int,
  deactivationmode: Int,
  reactivationmode: Int,
  chargetypeid: Int,
  chargeamount: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = InvProductSpecActivationMode.autoSession): InvProductSpecActivationMode = InvProductSpecActivationMode.save(this)(session)

  def destroy()(implicit session: DBSession = InvProductSpecActivationMode.autoSession): Int = InvProductSpecActivationMode.destroy(this)(session)

}


object InvProductSpecActivationMode extends SQLSyntaxSupport[InvProductSpecActivationMode] {

  override val tableName = "inv_product_spec_activation_mode"

  override val columns = Seq("id", "productSpecId", "title", "dateFrom", "dateTo", "periodMode", "periodAmount", "deactivationMode", "reactivationMode", "chargeTypeId", "chargeAmount")

  def apply(ipsam: SyntaxProvider[InvProductSpecActivationMode])(rs: WrappedResultSet): InvProductSpecActivationMode = autoConstruct(rs, ipsam)
  def apply(ipsam: ResultName[InvProductSpecActivationMode])(rs: WrappedResultSet): InvProductSpecActivationMode = autoConstruct(rs, ipsam)

  val ipsam = InvProductSpecActivationMode.syntax("ipsam")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvProductSpecActivationMode] = {
    withSQL {
      select.from(InvProductSpecActivationMode as ipsam).where.eq(ipsam.id, id)
    }.map(InvProductSpecActivationMode(ipsam.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProductSpecActivationMode] = {
    withSQL(select.from(InvProductSpecActivationMode as ipsam)).map(InvProductSpecActivationMode(ipsam.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvProductSpecActivationMode as ipsam)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProductSpecActivationMode] = {
    withSQL {
      select.from(InvProductSpecActivationMode as ipsam).where.append(where)
    }.map(InvProductSpecActivationMode(ipsam.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProductSpecActivationMode] = {
    withSQL {
      select.from(InvProductSpecActivationMode as ipsam).where.append(where)
    }.map(InvProductSpecActivationMode(ipsam.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvProductSpecActivationMode as ipsam).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    productspecid: Int,
    title: Option[String] = None,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    periodmode: Int,
    periodamount: Int,
    deactivationmode: Int,
    reactivationmode: Int,
    chargetypeid: Int,
    chargeamount: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): InvProductSpecActivationMode = {
    val generatedKey = withSQL {
      insert.into(InvProductSpecActivationMode).namedValues(
        column.productspecid -> productspecid,
        column.title -> title,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.periodmode -> periodmode,
        column.periodamount -> periodamount,
        column.deactivationmode -> deactivationmode,
        column.reactivationmode -> reactivationmode,
        column.chargetypeid -> chargetypeid,
        column.chargeamount -> chargeamount
      )
    }.updateAndReturnGeneratedKey.apply()

    InvProductSpecActivationMode(
      id = generatedKey.toInt,
      productspecid = productspecid,
      title = title,
      datefrom = datefrom,
      dateto = dateto,
      periodmode = periodmode,
      periodamount = periodamount,
      deactivationmode = deactivationmode,
      reactivationmode = reactivationmode,
      chargetypeid = chargetypeid,
      chargeamount = chargeamount)
  }

  def batchInsert(entities: Seq[InvProductSpecActivationMode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'productspecid -> entity.productspecid,
        'title -> entity.title,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'periodmode -> entity.periodmode,
        'periodamount -> entity.periodamount,
        'deactivationmode -> entity.deactivationmode,
        'reactivationmode -> entity.reactivationmode,
        'chargetypeid -> entity.chargetypeid,
        'chargeamount -> entity.chargeamount))
    SQL("""insert into inv_product_spec_activation_mode(
      productSpecId,
      title,
      dateFrom,
      dateTo,
      periodMode,
      periodAmount,
      deactivationMode,
      reactivationMode,
      chargeTypeId,
      chargeAmount
    ) values (
      {productspecid},
      {title},
      {datefrom},
      {dateto},
      {periodmode},
      {periodamount},
      {deactivationmode},
      {reactivationmode},
      {chargetypeid},
      {chargeamount}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvProductSpecActivationMode)(implicit session: DBSession = autoSession): InvProductSpecActivationMode = {
    withSQL {
      update(InvProductSpecActivationMode).set(
        column.id -> entity.id,
        column.productspecid -> entity.productspecid,
        column.title -> entity.title,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.periodmode -> entity.periodmode,
        column.periodamount -> entity.periodamount,
        column.deactivationmode -> entity.deactivationmode,
        column.reactivationmode -> entity.reactivationmode,
        column.chargetypeid -> entity.chargetypeid,
        column.chargeamount -> entity.chargeamount
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvProductSpecActivationMode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvProductSpecActivationMode).where.eq(column.id, entity.id) }.update.apply()
  }

}
