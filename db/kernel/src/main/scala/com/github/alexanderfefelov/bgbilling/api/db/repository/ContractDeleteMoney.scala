package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractDeleteMoney(
  id: Int,
  name: String,
  money: Int,
  time: Int,
  gr: Long,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  comment: String) {

  def save()(implicit session: DBSession = ContractDeleteMoney.autoSession): ContractDeleteMoney = ContractDeleteMoney.save(this)(session)

  def destroy()(implicit session: DBSession = ContractDeleteMoney.autoSession): Int = ContractDeleteMoney.destroy(this)(session)

}


object ContractDeleteMoney extends SQLSyntaxSupport[ContractDeleteMoney] {

  override val tableName = "contract_delete_money"

  override val columns = Seq("id", "name", "money", "time", "gr", "date1", "date2", "comment")

  def apply(cdm: SyntaxProvider[ContractDeleteMoney])(rs: WrappedResultSet): ContractDeleteMoney = autoConstruct(rs, cdm)
  def apply(cdm: ResultName[ContractDeleteMoney])(rs: WrappedResultSet): ContractDeleteMoney = autoConstruct(rs, cdm)

  val cdm = ContractDeleteMoney.syntax("cdm")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractDeleteMoney] = {
    withSQL {
      select.from(ContractDeleteMoney as cdm).where.eq(cdm.id, id)
    }.map(ContractDeleteMoney(cdm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractDeleteMoney] = {
    withSQL(select.from(ContractDeleteMoney as cdm)).map(ContractDeleteMoney(cdm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractDeleteMoney as cdm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractDeleteMoney] = {
    withSQL {
      select.from(ContractDeleteMoney as cdm).where.append(where)
    }.map(ContractDeleteMoney(cdm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractDeleteMoney] = {
    withSQL {
      select.from(ContractDeleteMoney as cdm).where.append(where)
    }.map(ContractDeleteMoney(cdm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractDeleteMoney as cdm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    money: Int,
    time: Int,
    gr: Long,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    comment: String)(implicit session: DBSession = autoSession): ContractDeleteMoney = {
    val generatedKey = withSQL {
      insert.into(ContractDeleteMoney).namedValues(
        column.name -> name,
        column.money -> money,
        column.time -> time,
        column.gr -> gr,
        column.date1 -> date1,
        column.date2 -> date2,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractDeleteMoney(
      id = generatedKey.toInt,
      name = name,
      money = money,
      time = time,
      gr = gr,
      date1 = date1,
      date2 = date2,
      comment = comment)
  }

  def batchInsert(entities: Seq[ContractDeleteMoney])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'money -> entity.money,
        'time -> entity.time,
        'gr -> entity.gr,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'comment -> entity.comment))
    SQL("""insert into contract_delete_money(
      name,
      money,
      time,
      gr,
      date1,
      date2,
      comment
    ) values (
      {name},
      {money},
      {time},
      {gr},
      {date1},
      {date2},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractDeleteMoney)(implicit session: DBSession = autoSession): ContractDeleteMoney = {
    withSQL {
      update(ContractDeleteMoney).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.money -> entity.money,
        column.time -> entity.time,
        column.gr -> entity.gr,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractDeleteMoney)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractDeleteMoney).where.eq(column.id, entity.id) }.update.apply()
  }

}
