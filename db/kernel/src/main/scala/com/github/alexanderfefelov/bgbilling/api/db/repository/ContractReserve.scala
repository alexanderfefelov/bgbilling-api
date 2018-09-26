package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractReserve(
  id: Int,
  cid: Int,
  typeid: Int,
  sum: BigDecimal,
  datecreate: DateTime,
  dateto: Option[DateTime] = None,
  dateclose: Option[DateTime] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = ContractReserve.autoSession): ContractReserve = ContractReserve.save(this)(session)

  def destroy()(implicit session: DBSession = ContractReserve.autoSession): Int = ContractReserve.destroy(this)(session)

}


object ContractReserve extends SQLSyntaxSupport[ContractReserve] {

  override val tableName = "contract_reserve"

  override val columns = Seq("id", "cid", "typeId", "sum", "dateCreate", "dateTo", "dateClose", "comment")

  def apply(cr: SyntaxProvider[ContractReserve])(rs: WrappedResultSet): ContractReserve = autoConstruct(rs, cr)
  def apply(cr: ResultName[ContractReserve])(rs: WrappedResultSet): ContractReserve = autoConstruct(rs, cr)

  val cr = ContractReserve.syntax("cr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractReserve] = {
    withSQL {
      select.from(ContractReserve as cr).where.eq(cr.id, id)
    }.map(ContractReserve(cr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractReserve] = {
    withSQL(select.from(ContractReserve as cr)).map(ContractReserve(cr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractReserve as cr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractReserve] = {
    withSQL {
      select.from(ContractReserve as cr).where.append(where)
    }.map(ContractReserve(cr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractReserve] = {
    withSQL {
      select.from(ContractReserve as cr).where.append(where)
    }.map(ContractReserve(cr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractReserve as cr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    typeid: Int,
    sum: BigDecimal,
    datecreate: DateTime,
    dateto: Option[DateTime] = None,
    dateclose: Option[DateTime] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): ContractReserve = {
    val generatedKey = withSQL {
      insert.into(ContractReserve).namedValues(
        column.cid -> cid,
        column.typeid -> typeid,
        column.sum -> sum,
        column.datecreate -> datecreate,
        column.dateto -> dateto,
        column.dateclose -> dateclose,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractReserve(
      id = generatedKey.toInt,
      cid = cid,
      typeid = typeid,
      sum = sum,
      datecreate = datecreate,
      dateto = dateto,
      dateclose = dateclose,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[ContractReserve])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'typeid -> entity.typeid,
        'sum -> entity.sum,
        'datecreate -> entity.datecreate,
        'dateto -> entity.dateto,
        'dateclose -> entity.dateclose,
        'comment -> entity.comment))
    SQL("""insert into contract_reserve(
      cid,
      typeId,
      sum,
      dateCreate,
      dateTo,
      dateClose,
      comment
    ) values (
      {cid},
      {typeid},
      {sum},
      {datecreate},
      {dateto},
      {dateclose},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractReserve)(implicit session: DBSession = autoSession): ContractReserve = {
    withSQL {
      update(ContractReserve).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.typeid -> entity.typeid,
        column.sum -> entity.sum,
        column.datecreate -> entity.datecreate,
        column.dateto -> entity.dateto,
        column.dateclose -> entity.dateclose,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractReserve)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractReserve).where.eq(column.id, entity.id) }.update.apply()
  }

}
