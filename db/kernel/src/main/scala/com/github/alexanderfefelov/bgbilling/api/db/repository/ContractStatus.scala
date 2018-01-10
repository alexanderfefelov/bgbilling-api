package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractStatus(
  id: Int,
  cid: Int,
  status: Byte,
  date1: LocalDate,
  date2: Option[LocalDate] = None,
  comment: String) {

  def save()(implicit session: DBSession = ContractStatus.autoSession): ContractStatus = ContractStatus.save(this)(session)

  def destroy()(implicit session: DBSession = ContractStatus.autoSession): Int = ContractStatus.destroy(this)(session)

}


object ContractStatus extends SQLSyntaxSupport[ContractStatus] {

  override val tableName = "contract_status"

  override val columns = Seq("id", "cid", "status", "date1", "date2", "comment")

  def apply(cs: SyntaxProvider[ContractStatus])(rs: WrappedResultSet): ContractStatus = autoConstruct(rs, cs)
  def apply(cs: ResultName[ContractStatus])(rs: WrappedResultSet): ContractStatus = autoConstruct(rs, cs)

  val cs = ContractStatus.syntax("cs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractStatus] = {
    withSQL {
      select.from(ContractStatus as cs).where.eq(cs.id, id)
    }.map(ContractStatus(cs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractStatus] = {
    withSQL(select.from(ContractStatus as cs)).map(ContractStatus(cs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractStatus as cs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractStatus] = {
    withSQL {
      select.from(ContractStatus as cs).where.append(where)
    }.map(ContractStatus(cs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractStatus] = {
    withSQL {
      select.from(ContractStatus as cs).where.append(where)
    }.map(ContractStatus(cs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractStatus as cs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    status: Byte,
    date1: LocalDate,
    date2: Option[LocalDate] = None,
    comment: String)(implicit session: DBSession = autoSession): ContractStatus = {
    val generatedKey = withSQL {
      insert.into(ContractStatus).namedValues(
        column.cid -> cid,
        column.status -> status,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractStatus(
      id = generatedKey.toInt,
      cid = cid,
      status = status,
      date1 = date1,
      date2 = date2,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractStatus])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'status -> entity.status,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment))
    SQL("""insert into contract_status(
      cid,
      status,
      date1,
      date2,
      comment
    ) values (
      {cid},
      {status},
      {date1},
      {date2},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractStatus)(implicit session: DBSession = autoSession): ContractStatus = {
    withSQL {
      update(ContractStatus).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.status -> entity.status,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractStatus)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractStatus).where.eq(column.id, entity.id) }.update.apply()
  }

}
