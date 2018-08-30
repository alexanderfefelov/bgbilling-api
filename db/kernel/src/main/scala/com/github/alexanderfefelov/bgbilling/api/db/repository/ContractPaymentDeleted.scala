package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractPaymentDeleted(
  id: Int,
  dt: LocalDate,
  cid: Int,
  pt: Int,
  uid: Int,
  summa: BigDecimal,
  comment: Option[String] = None,
  lm: DateTime) {

  def save()(implicit session: DBSession = ContractPaymentDeleted.autoSession): ContractPaymentDeleted = ContractPaymentDeleted.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPaymentDeleted.autoSession): Int = ContractPaymentDeleted.destroy(this)(session)

}


object ContractPaymentDeleted extends SQLSyntaxSupport[ContractPaymentDeleted] {

  override val tableName = "contract_payment_deleted"

  override val columns = Seq("id", "dt", "cid", "pt", "uid", "summa", "comment", "lm")

  def apply(cpd: SyntaxProvider[ContractPaymentDeleted])(rs: WrappedResultSet): ContractPaymentDeleted = autoConstruct(rs, cpd)
  def apply(cpd: ResultName[ContractPaymentDeleted])(rs: WrappedResultSet): ContractPaymentDeleted = autoConstruct(rs, cpd)

  val cpd = ContractPaymentDeleted.syntax("cpd")

  override val autoSession = AutoSession

  def find(id: Int, dt: LocalDate, cid: Int, pt: Int, uid: Int, summa: BigDecimal, comment: Option[String], lm: DateTime)(implicit session: DBSession = autoSession): Option[ContractPaymentDeleted] = {
    withSQL {
      select.from(ContractPaymentDeleted as cpd).where.eq(cpd.id, id).and.eq(cpd.dt, dt).and.eq(cpd.cid, cid).and.eq(cpd.pt, pt).and.eq(cpd.uid, uid).and.eq(cpd.summa, summa).and.eq(cpd.comment, comment).and.eq(cpd.lm, lm)
    }.map(ContractPaymentDeleted(cpd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPaymentDeleted] = {
    withSQL(select.from(ContractPaymentDeleted as cpd)).map(ContractPaymentDeleted(cpd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPaymentDeleted as cpd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPaymentDeleted] = {
    withSQL {
      select.from(ContractPaymentDeleted as cpd).where.append(where)
    }.map(ContractPaymentDeleted(cpd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPaymentDeleted] = {
    withSQL {
      select.from(ContractPaymentDeleted as cpd).where.append(where)
    }.map(ContractPaymentDeleted(cpd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPaymentDeleted as cpd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    dt: LocalDate,
    cid: Int,
    pt: Int,
    uid: Int,
    summa: BigDecimal,
    comment: Option[String] = None,
    lm: DateTime)(implicit session: DBSession = autoSession): ContractPaymentDeleted = {
    withSQL {
      insert.into(ContractPaymentDeleted).namedValues(
        column.id -> id,
        column.dt -> dt,
        column.cid -> cid,
        column.pt -> pt,
        column.uid -> uid,
        column.summa -> summa,
        column.comment -> comment,
        column.lm -> lm
      )
    }.update.apply()

    ContractPaymentDeleted(
      id = id,
      dt = dt,
      cid = cid,
      pt = pt,
      uid = uid,
      summa = summa,
      comment = comment,
      lm = lm)
  }

  def batchInsert(entities: Seq[ContractPaymentDeleted])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'dt -> entity.dt,
        'cid -> entity.cid,
        'pt -> entity.pt,
        'uid -> entity.uid,
        'summa -> entity.summa,
        'comment -> entity.comment,
        'lm -> entity.lm))
    SQL("""insert into contract_payment_deleted(
      id,
      dt,
      cid,
      pt,
      uid,
      summa,
      comment,
      lm
    ) values (
      {id},
      {dt},
      {cid},
      {pt},
      {uid},
      {summa},
      {comment},
      {lm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPaymentDeleted)(implicit session: DBSession = autoSession): ContractPaymentDeleted = {
    withSQL {
      update(ContractPaymentDeleted).set(
        column.id -> entity.id,
        column.dt -> entity.dt,
        column.cid -> entity.cid,
        column.pt -> entity.pt,
        column.uid -> entity.uid,
        column.summa -> entity.summa,
        column.comment -> entity.comment,
        column.lm -> entity.lm
      ).where.eq(column.id, entity.id).and.eq(column.dt, entity.dt).and.eq(column.cid, entity.cid).and.eq(column.pt, entity.pt).and.eq(column.uid, entity.uid).and.eq(column.summa, entity.summa).and.eq(column.comment, entity.comment).and.eq(column.lm, entity.lm)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPaymentDeleted)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPaymentDeleted).where.eq(column.id, entity.id).and.eq(column.dt, entity.dt).and.eq(column.cid, entity.cid).and.eq(column.pt, entity.pt).and.eq(column.uid, entity.uid).and.eq(column.summa, entity.summa).and.eq(column.comment, entity.comment).and.eq(column.lm, entity.lm) }.update.apply()
  }

}
