package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class InvProductSpecServiceLink(
  id: Int,
  productspecid: Int,
  servicespecid: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = InvProductSpecServiceLink.autoSession): InvProductSpecServiceLink = InvProductSpecServiceLink.save(this)(session)

  def destroy()(implicit session: DBSession = InvProductSpecServiceLink.autoSession): Int = InvProductSpecServiceLink.destroy(this)(session)

}


object InvProductSpecServiceLink extends SQLSyntaxSupport[InvProductSpecServiceLink] {

  override val tableName = "inv_product_spec_service_link"

  override val columns = Seq("id", "productSpecId", "serviceSpecId", "dateFrom", "dateTo", "comment")

  def apply(ipssl: SyntaxProvider[InvProductSpecServiceLink])(rs: WrappedResultSet): InvProductSpecServiceLink = autoConstruct(rs, ipssl)
  def apply(ipssl: ResultName[InvProductSpecServiceLink])(rs: WrappedResultSet): InvProductSpecServiceLink = autoConstruct(rs, ipssl)

  val ipssl = InvProductSpecServiceLink.syntax("ipssl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvProductSpecServiceLink] = {
    withSQL {
      select.from(InvProductSpecServiceLink as ipssl).where.eq(ipssl.id, id)
    }.map(InvProductSpecServiceLink(ipssl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvProductSpecServiceLink] = {
    withSQL(select.from(InvProductSpecServiceLink as ipssl)).map(InvProductSpecServiceLink(ipssl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvProductSpecServiceLink as ipssl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvProductSpecServiceLink] = {
    withSQL {
      select.from(InvProductSpecServiceLink as ipssl).where.append(where)
    }.map(InvProductSpecServiceLink(ipssl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvProductSpecServiceLink] = {
    withSQL {
      select.from(InvProductSpecServiceLink as ipssl).where.append(where)
    }.map(InvProductSpecServiceLink(ipssl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvProductSpecServiceLink as ipssl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    productspecid: Int,
    servicespecid: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): InvProductSpecServiceLink = {
    val generatedKey = withSQL {
      insert.into(InvProductSpecServiceLink).namedValues(
        column.productspecid -> productspecid,
        column.servicespecid -> servicespecid,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InvProductSpecServiceLink(
      id = generatedKey.toInt,
      productspecid = productspecid,
      servicespecid = servicespecid,
      datefrom = datefrom,
      dateto = dateto,
      comment = comment)
  }

  def batchInsert(entities: Seq[InvProductSpecServiceLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'productspecid -> entity.productspecid,
        'servicespecid -> entity.servicespecid,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'comment -> entity.comment))
    SQL("""insert into inv_product_spec_service_link(
      productSpecId,
      serviceSpecId,
      dateFrom,
      dateTo,
      comment
    ) values (
      {productspecid},
      {servicespecid},
      {datefrom},
      {dateto},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvProductSpecServiceLink)(implicit session: DBSession = autoSession): InvProductSpecServiceLink = {
    withSQL {
      update(InvProductSpecServiceLink).set(
        column.id -> entity.id,
        column.productspecid -> entity.productspecid,
        column.servicespecid -> entity.servicespecid,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvProductSpecServiceLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvProductSpecServiceLink).where.eq(column.id, entity.id) }.update.apply()
  }

}
