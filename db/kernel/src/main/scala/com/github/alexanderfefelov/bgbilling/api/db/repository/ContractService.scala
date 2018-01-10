package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractService(
  id: Int,
  cid: Int,
  sid: Int,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  comment: String,
  lm: LocalDate,
  emid: Int,
  eid: Int) {

  def save()(implicit session: DBSession = ContractService.autoSession): ContractService = ContractService.save(this)(session)

  def destroy()(implicit session: DBSession = ContractService.autoSession): Int = ContractService.destroy(this)(session)

}


object ContractService extends SQLSyntaxSupport[ContractService] {

  override val tableName = "contract_service"

  override val columns = Seq("id", "cid", "sid", "date1", "date2", "comment", "lm", "emid", "eid")

  def apply(cs: SyntaxProvider[ContractService])(rs: WrappedResultSet): ContractService = autoConstruct(rs, cs)
  def apply(cs: ResultName[ContractService])(rs: WrappedResultSet): ContractService = autoConstruct(rs, cs)

  val cs = ContractService.syntax("cs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractService] = {
    withSQL {
      select.from(ContractService as cs).where.eq(cs.id, id)
    }.map(ContractService(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractService] = {
    withSQL(select.from(ContractService as cs)).map(ContractService(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractService as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractService] = {
    withSQL {
      select.from(ContractService as cs).where.append(where)
    }.map(ContractService(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractService] = {
    withSQL {
      select.from(ContractService as cs).where.append(where)
    }.map(ContractService(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractService as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    sid: Int,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    comment: String,
    lm: LocalDate,
    emid: Int,
    eid: Int)(implicit session: DBSession = autoSession): ContractService = {
    val generatedKey = withSQL {
      insert.into(ContractService).namedValues(
        column.cid -> cid,
        column.sid -> sid,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment,
        column.lm -> lm,
        column.emid -> emid,
        column.eid -> eid
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractService(
      id = generatedKey.toInt,
      cid = cid,
      sid = sid,
      date1 = date1,
      date2 = date2,
      comment = comment,
      lm = lm,
      emid = emid,
      eid = eid)
  }

  def batchInsert(entities: Seq[ContractService])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'sid -> entity.sid,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment,
        'lm -> entity.lm,
        'emid -> entity.emid,
        'eid -> entity.eid))
    SQL("""insert into contract_service(
      cid,
      sid,
      date1,
      date2,
      comment,
      lm,
      emid,
      eid
    ) values (
      {cid},
      {sid},
      {date1},
      {date2},
      {comment},
      {lm},
      {emid},
      {eid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractService)(implicit session: DBSession = autoSession): ContractService = {
    withSQL {
      update(ContractService).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.sid -> entity.sid,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment,
        column.lm -> entity.lm,
        column.emid -> entity.emid,
        column.eid -> entity.eid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractService)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractService).where.eq(column.id, entity.id) }.update.apply()
  }

}
