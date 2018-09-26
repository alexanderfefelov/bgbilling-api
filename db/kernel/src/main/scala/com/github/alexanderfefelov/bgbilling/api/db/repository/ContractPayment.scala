package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractPayment(
  id: Int,
  dt: LocalDate,
  cid: Int,
  pt: Int,
  uid: Int,
  summa: BigDecimal,
  comment: Option[String] = None,
  lm: DateTime) {

  def save()(implicit session: DBSession = ContractPayment.autoSession): ContractPayment = ContractPayment.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPayment.autoSession): Int = ContractPayment.destroy(this)(session)

}


object ContractPayment extends SQLSyntaxSupport[ContractPayment] {

  override val tableName = "contract_payment"

  override val columns = Seq("id", "dt", "cid", "pt", "uid", "summa", "comment", "lm")

  def apply(cp: SyntaxProvider[ContractPayment])(rs: WrappedResultSet): ContractPayment = autoConstruct(rs, cp)
  def apply(cp: ResultName[ContractPayment])(rs: WrappedResultSet): ContractPayment = autoConstruct(rs, cp)

  val cp = ContractPayment.syntax("cp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractPayment] = {
    withSQL {
      select.from(ContractPayment as cp).where.eq(cp.id, id)
    }.map(ContractPayment(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPayment] = {
    withSQL(select.from(ContractPayment as cp)).map(ContractPayment(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPayment as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPayment] = {
    withSQL {
      select.from(ContractPayment as cp).where.append(where)
    }.map(ContractPayment(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPayment] = {
    withSQL {
      select.from(ContractPayment as cp).where.append(where)
    }.map(ContractPayment(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPayment as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: LocalDate,
    cid: Int,
    pt: Int,
    uid: Int,
    summa: BigDecimal,
    comment: Option[String] = None,
    lm: DateTime)(implicit session: DBSession = autoSession): ContractPayment = {
    val generatedKey = withSQL {
      insert.into(ContractPayment).namedValues(
        column.dt -> dt,
        column.cid -> cid,
        column.pt -> pt,
        column.uid -> uid,
        column.summa -> summa,
        column.comment -> comment,
        column.lm -> lm
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractPayment(
      id = generatedKey.toInt,
      dt = dt,
      cid = cid,
      pt = pt,
      uid = uid,
      summa = summa,
      comment = comment,
      lm = lm)
  }

  def batchInsert(entities: collection.Seq[ContractPayment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'cid -> entity.cid,
        'pt -> entity.pt,
        'uid -> entity.uid,
        'summa -> entity.summa,
        'comment -> entity.comment,
        'lm -> entity.lm))
    SQL("""insert into contract_payment(
      dt,
      cid,
      pt,
      uid,
      summa,
      comment,
      lm
    ) values (
      {dt},
      {cid},
      {pt},
      {uid},
      {summa},
      {comment},
      {lm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPayment)(implicit session: DBSession = autoSession): ContractPayment = {
    withSQL {
      update(ContractPayment).set(
        column.id -> entity.id,
        column.dt -> entity.dt,
        column.cid -> entity.cid,
        column.pt -> entity.pt,
        column.uid -> entity.uid,
        column.summa -> entity.summa,
        column.comment -> entity.comment,
        column.lm -> entity.lm
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPayment)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPayment).where.eq(column.id, entity.id) }.update.apply()
  }

}
