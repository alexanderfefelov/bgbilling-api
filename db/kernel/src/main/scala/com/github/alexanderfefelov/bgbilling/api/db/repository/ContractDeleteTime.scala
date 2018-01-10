package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class ContractDeleteTime(
  id: Int,
  name: String,
  time: Int,
  gr: Long,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  comment: String) {

  def save()(implicit session: DBSession = ContractDeleteTime.autoSession): ContractDeleteTime = ContractDeleteTime.save(this)(session)

  def destroy()(implicit session: DBSession = ContractDeleteTime.autoSession): Int = ContractDeleteTime.destroy(this)(session)

}


object ContractDeleteTime extends SQLSyntaxSupport[ContractDeleteTime] {

  override val tableName = "contract_delete_time"

  override val columns = Seq("id", "name", "time", "gr", "date1", "date2", "comment")

  def apply(cdt: SyntaxProvider[ContractDeleteTime])(rs: WrappedResultSet): ContractDeleteTime = autoConstruct(rs, cdt)
  def apply(cdt: ResultName[ContractDeleteTime])(rs: WrappedResultSet): ContractDeleteTime = autoConstruct(rs, cdt)

  val cdt = ContractDeleteTime.syntax("cdt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractDeleteTime] = {
    withSQL {
      select.from(ContractDeleteTime as cdt).where.eq(cdt.id, id)
    }.map(ContractDeleteTime(cdt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractDeleteTime] = {
    withSQL(select.from(ContractDeleteTime as cdt)).map(ContractDeleteTime(cdt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractDeleteTime as cdt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractDeleteTime] = {
    withSQL {
      select.from(ContractDeleteTime as cdt).where.append(where)
    }.map(ContractDeleteTime(cdt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractDeleteTime] = {
    withSQL {
      select.from(ContractDeleteTime as cdt).where.append(where)
    }.map(ContractDeleteTime(cdt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractDeleteTime as cdt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    time: Int,
    gr: Long,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    comment: String)(implicit session: DBSession = autoSession): ContractDeleteTime = {
    val generatedKey = withSQL {
      insert.into(ContractDeleteTime).namedValues(
        column.name -> name,
        column.time -> time,
        column.gr -> gr,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractDeleteTime(
      id = generatedKey.toInt,
      name = name,
      time = time,
      gr = gr,
      date1 = date1,
      date2 = date2,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractDeleteTime])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'time -> entity.time,
        'gr -> entity.gr,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment))
    SQL("""insert into contract_delete_time(
      name,
      time,
      gr,
      date1,
      date2,
      comment
    ) values (
      {name},
      {time},
      {gr},
      {date1},
      {date2},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractDeleteTime)(implicit session: DBSession = autoSession): ContractDeleteTime = {
    withSQL {
      update(ContractDeleteTime).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.time -> entity.time,
        column.gr -> entity.gr,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractDeleteTime)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractDeleteTime).where.eq(column.id, entity.id) }.update.apply()
  }

}
