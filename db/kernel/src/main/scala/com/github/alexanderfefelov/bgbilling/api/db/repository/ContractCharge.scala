package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractCharge(
  id: Int,
  dt: LocalDate,
  cid: Int,
  pt: Int,
  uid: Int,
  summa: BigDecimal,
  comment: String,
  lm: DateTime,
  payback: Boolean) {

  def save()(implicit session: DBSession = ContractCharge.autoSession): ContractCharge = ContractCharge.save(this)(session)

  def destroy()(implicit session: DBSession = ContractCharge.autoSession): Int = ContractCharge.destroy(this)(session)

}


object ContractCharge extends SQLSyntaxSupport[ContractCharge] {

  override val tableName = "contract_charge"

  override val columns = Seq("id", "dt", "cid", "pt", "uid", "summa", "comment", "lm", "payback")

  def apply(cc: SyntaxProvider[ContractCharge])(rs: WrappedResultSet): ContractCharge = autoConstruct(rs, cc)
  def apply(cc: ResultName[ContractCharge])(rs: WrappedResultSet): ContractCharge = autoConstruct(rs, cc)

  val cc = ContractCharge.syntax("cc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractCharge] = {
    withSQL {
      select.from(ContractCharge as cc).where.eq(cc.id, id)
    }.map(ContractCharge(cc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractCharge] = {
    withSQL(select.from(ContractCharge as cc)).map(ContractCharge(cc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractCharge as cc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractCharge] = {
    withSQL {
      select.from(ContractCharge as cc).where.append(where)
    }.map(ContractCharge(cc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractCharge] = {
    withSQL {
      select.from(ContractCharge as cc).where.append(where)
    }.map(ContractCharge(cc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractCharge as cc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dt: LocalDate,
    cid: Int,
    pt: Int,
    uid: Int,
    summa: BigDecimal,
    comment: String,
    lm: DateTime,
    payback: Boolean)(implicit session: DBSession = autoSession): ContractCharge = {
    val generatedKey = withSQL {
      insert.into(ContractCharge).namedValues(
        column.dt -> dt,
        column.cid -> cid,
        column.pt -> pt,
        column.uid -> uid,
        column.summa -> summa,
        column.comment -> comment,
        column.lm -> lm,
        column.payback -> payback
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractCharge(
      id = generatedKey.toInt,
      dt = dt,
      cid = cid,
      pt = pt,
      uid = uid,
      summa = summa,
      comment = comment,
      lm = lm,
      payback = payback)
  }

  def batchInsert(entities: Seq[ContractCharge])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'dt -> entity.dt,
        'cid -> entity.cid,
        'pt -> entity.pt,
        'uid -> entity.uid,
        'summa -> entity.summa,
        'comment -> entity.comment,
        'lm -> entity.lm,
        'payback -> entity.payback))
    SQL("""insert into contract_charge(
      dt,
      cid,
      pt,
      uid,
      summa,
      comment,
      lm,
      payback
    ) values (
      {dt},
      {cid},
      {pt},
      {uid},
      {summa},
      {comment},
      {lm},
      {payback}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractCharge)(implicit session: DBSession = autoSession): ContractCharge = {
    withSQL {
      update(ContractCharge).set(
        column.id -> entity.id,
        column.dt -> entity.dt,
        column.cid -> entity.cid,
        column.pt -> entity.pt,
        column.uid -> entity.uid,
        column.summa -> entity.summa,
        column.comment -> entity.comment,
        column.lm -> entity.lm,
        column.payback -> entity.payback
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractCharge)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractCharge).where.eq(column.id, entity.id) }.update.apply()
  }

}
