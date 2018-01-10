package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime, LocalDate}

case class ContractStatusLog(
  id: Int,
  status: Byte,
  uid: Int,
  date: DateTime,
  comment: String,
  cid: Int,
  date1: LocalDate,
  date2: Option[LocalDate] = None) {

  def save()(implicit session: DBSession = ContractStatusLog.autoSession): ContractStatusLog = ContractStatusLog.save(this)(session)

  def destroy()(implicit session: DBSession = ContractStatusLog.autoSession): Int = ContractStatusLog.destroy(this)(session)

}


object ContractStatusLog extends SQLSyntaxSupport[ContractStatusLog] {

  override val tableName = "contract_status_log"

  override val columns = Seq("id", "status", "uid", "date", "comment", "cid", "date1", "date2")

  def apply(csl: SyntaxProvider[ContractStatusLog])(rs: WrappedResultSet): ContractStatusLog = autoConstruct(rs, csl)
  def apply(csl: ResultName[ContractStatusLog])(rs: WrappedResultSet): ContractStatusLog = autoConstruct(rs, csl)

  val csl = ContractStatusLog.syntax("csl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractStatusLog] = {
    withSQL {
      select.from(ContractStatusLog as csl).where.eq(csl.id, id)
    }.map(ContractStatusLog(csl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractStatusLog] = {
    withSQL(select.from(ContractStatusLog as csl)).map(ContractStatusLog(csl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractStatusLog as csl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractStatusLog] = {
    withSQL {
      select.from(ContractStatusLog as csl).where.append(where)
    }.map(ContractStatusLog(csl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractStatusLog] = {
    withSQL {
      select.from(ContractStatusLog as csl).where.append(where)
    }.map(ContractStatusLog(csl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractStatusLog as csl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    status: Byte,
    uid: Int,
    date: DateTime,
    comment: String,
    cid: Int,
    date1: LocalDate,
    date2: Option[LocalDate] = None)(implicit session: DBSession = autoSession): ContractStatusLog = {
    val generatedKey = withSQL {
      insert.into(ContractStatusLog).namedValues(
        column.status -> status,
        column.uid -> uid,
        column.date -> date,
        column.comment -> comment,
        column.cid -> cid,
        column.date1 -> date1,
        column.date2 -> date2
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractStatusLog(
      id = generatedKey.toInt,
      status = status,
      uid = uid,
      date = date,
      comment = comment,
      cid = cid,
      date1 = date1,
      date2 = date2)
  }

  def batchInsert(entities: Seq[ContractStatusLog])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'status -> entity.status,
        'uid -> entity.uid,
        'date -> entity.date,
        'comment -> entity.comment,
        'cid -> entity.cid,
        'date1 -> entity.date1,
        'date2 -> entity.date2))
    SQL("""insert into contract_status_log(
      status,
      uid,
      date,
      comment,
      cid,
      date1,
      date2
    ) values (
      {status},
      {uid},
      {date},
      {comment},
      {cid},
      {date1},
      {date2}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractStatusLog)(implicit session: DBSession = autoSession): ContractStatusLog = {
    withSQL {
      update(ContractStatusLog).set(
        column.id -> entity.id,
        column.status -> entity.status,
        column.uid -> entity.uid,
        column.date -> entity.date,
        column.comment -> entity.comment,
        column.cid -> entity.cid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractStatusLog)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractStatusLog).where.eq(column.id, entity.id) }.update.apply()
  }

}
