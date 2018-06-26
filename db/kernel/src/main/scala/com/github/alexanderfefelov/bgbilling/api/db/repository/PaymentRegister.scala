package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate, DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class PaymentRegister(
  id: Int,
  date: Option[LocalDate] = None,
  title: String,
  pt: Int,
  loadTime: Option[DateTime] = None,
  count: Int,
  errorcount: Int,
  summa: BigDecimal,
  processed: Byte,
  processTime: Option[DateTime] = None,
  loadLog: String,
  ptitle: String,
  regtype: Int) {

  def save()(implicit session: DBSession = PaymentRegister.autoSession): PaymentRegister = PaymentRegister.save(this)(session)

  def destroy()(implicit session: DBSession = PaymentRegister.autoSession): Int = PaymentRegister.destroy(this)(session)

}


object PaymentRegister extends SQLSyntaxSupport[PaymentRegister] {

  override val tableName = "payment_register"

  override val columns = Seq("id", "date", "title", "pt", "load_time", "count", "errorCount", "summa", "processed", "process_time", "load_log", "ptitle", "regtype")

  def apply(pr: SyntaxProvider[PaymentRegister])(rs: WrappedResultSet): PaymentRegister = autoConstruct(rs, pr)
  def apply(pr: ResultName[PaymentRegister])(rs: WrappedResultSet): PaymentRegister = autoConstruct(rs, pr)

  val pr = PaymentRegister.syntax("pr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[PaymentRegister] = {
    withSQL {
      select.from(PaymentRegister as pr).where.eq(pr.id, id)
    }.map(PaymentRegister(pr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PaymentRegister] = {
    withSQL(select.from(PaymentRegister as pr)).map(PaymentRegister(pr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PaymentRegister as pr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PaymentRegister] = {
    withSQL {
      select.from(PaymentRegister as pr).where.append(where)
    }.map(PaymentRegister(pr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PaymentRegister] = {
    withSQL {
      select.from(PaymentRegister as pr).where.append(where)
    }.map(PaymentRegister(pr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PaymentRegister as pr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    date: Option[LocalDate] = None,
    title: String,
    pt: Int,
    loadTime: Option[DateTime] = None,
    count: Int,
    errorcount: Int,
    summa: BigDecimal,
    processed: Byte,
    processTime: Option[DateTime] = None,
    loadLog: String,
    ptitle: String,
    regtype: Int)(implicit session: DBSession = autoSession): PaymentRegister = {
    val generatedKey = withSQL {
      insert.into(PaymentRegister).namedValues(
        column.date -> date,
        column.title -> title,
        column.pt -> pt,
        column.loadTime -> loadTime,
        column.count -> count,
        column.errorcount -> errorcount,
        column.summa -> summa,
        column.processed -> processed,
        column.processTime -> processTime,
        column.loadLog -> loadLog,
        column.ptitle -> ptitle,
        column.regtype -> regtype
      )
    }.updateAndReturnGeneratedKey.apply()

    PaymentRegister(
      id = generatedKey.toInt,
      date = date,
      title = title,
      pt = pt,
      loadTime = loadTime,
      count = count,
      errorcount = errorcount,
      summa = summa,
      processed = processed,
      processTime = processTime,
      loadLog = loadLog,
      ptitle = ptitle,
      regtype = regtype)
  }

  def batchInsert(entities: Seq[PaymentRegister])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'date -> entity.date,
        'title -> entity.title,
        'pt -> entity.pt,
        'loadTime -> entity.loadTime,
        'count -> entity.count,
        'errorcount -> entity.errorcount,
        'summa -> entity.summa,
        'processed -> entity.processed,
        'processTime -> entity.processTime,
        'loadLog -> entity.loadLog,
        'ptitle -> entity.ptitle,
        'regtype -> entity.regtype))
    SQL("""insert into payment_register(
      date,
      title,
      pt,
      load_time,
      count,
      errorCount,
      summa,
      processed,
      process_time,
      load_log,
      ptitle,
      regtype
    ) values (
      {date},
      {title},
      {pt},
      {loadTime},
      {count},
      {errorcount},
      {summa},
      {processed},
      {processTime},
      {loadLog},
      {ptitle},
      {regtype}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PaymentRegister)(implicit session: DBSession = autoSession): PaymentRegister = {
    withSQL {
      update(PaymentRegister).set(
        column.id -> entity.id,
        column.date -> entity.date,
        column.title -> entity.title,
        column.pt -> entity.pt,
        column.loadTime -> entity.loadTime,
        column.count -> entity.count,
        column.errorcount -> entity.errorcount,
        column.summa -> entity.summa,
        column.processed -> entity.processed,
        column.processTime -> entity.processTime,
        column.loadLog -> entity.loadLog,
        column.ptitle -> entity.ptitle,
        column.regtype -> entity.regtype
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: PaymentRegister)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PaymentRegister).where.eq(column.id, entity.id) }.update.apply()
  }

}
