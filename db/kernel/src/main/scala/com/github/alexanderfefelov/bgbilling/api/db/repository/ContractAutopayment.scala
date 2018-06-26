package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractAutopayment(
  id: Int,
  contractId: Int,
  moduleId: Int,
  dateFrom: Option[DateTime] = None,
  dateTo: Option[DateTime] = None,
  userFrom: Int,
  userTo: Int) {

  def save()(implicit session: DBSession = ContractAutopayment.autoSession): ContractAutopayment = ContractAutopayment.save(this)(session)

  def destroy()(implicit session: DBSession = ContractAutopayment.autoSession): Int = ContractAutopayment.destroy(this)(session)

}


object ContractAutopayment extends SQLSyntaxSupport[ContractAutopayment] {

  override val tableName = "contract_autopayment"

  override val columns = Seq("id", "contract_id", "module_id", "date_from", "date_to", "user_from", "user_to")

  def apply(ca: SyntaxProvider[ContractAutopayment])(rs: WrappedResultSet): ContractAutopayment = autoConstruct(rs, ca)
  def apply(ca: ResultName[ContractAutopayment])(rs: WrappedResultSet): ContractAutopayment = autoConstruct(rs, ca)

  val ca = ContractAutopayment.syntax("ca")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractAutopayment] = {
    withSQL {
      select.from(ContractAutopayment as ca).where.eq(ca.id, id)
    }.map(ContractAutopayment(ca.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractAutopayment] = {
    withSQL(select.from(ContractAutopayment as ca)).map(ContractAutopayment(ca.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractAutopayment as ca)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractAutopayment] = {
    withSQL {
      select.from(ContractAutopayment as ca).where.append(where)
    }.map(ContractAutopayment(ca.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractAutopayment] = {
    withSQL {
      select.from(ContractAutopayment as ca).where.append(where)
    }.map(ContractAutopayment(ca.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractAutopayment as ca).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    moduleId: Int,
    dateFrom: Option[DateTime] = None,
    dateTo: Option[DateTime] = None,
    userFrom: Int,
    userTo: Int)(implicit session: DBSession = autoSession): ContractAutopayment = {
    val generatedKey = withSQL {
      insert.into(ContractAutopayment).namedValues(
        column.contractId -> contractId,
        column.moduleId -> moduleId,
        column.dateFrom -> dateFrom,
        column.dateTo -> dateTo,
        column.userFrom -> userFrom,
        column.userTo -> userTo
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractAutopayment(
      id = generatedKey.toInt,
      contractId = contractId,
      moduleId = moduleId,
      dateFrom = dateFrom,
      dateTo = dateTo,
      userFrom = userFrom,
      userTo = userTo)
  }

  def batchInsert(entities: Seq[ContractAutopayment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'moduleId -> entity.moduleId,
        'dateFrom -> entity.dateFrom,
        'dateTo -> entity.dateTo,
        'userFrom -> entity.userFrom,
        'userTo -> entity.userTo))
    SQL("""insert into contract_autopayment(
      contract_id,
      module_id,
      date_from,
      date_to,
      user_from,
      user_to
    ) values (
      {contractId},
      {moduleId},
      {dateFrom},
      {dateTo},
      {userFrom},
      {userTo}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractAutopayment)(implicit session: DBSession = autoSession): ContractAutopayment = {
    withSQL {
      update(ContractAutopayment).set(
        column.id -> entity.id,
        column.contractId -> entity.contractId,
        column.moduleId -> entity.moduleId,
        column.dateFrom -> entity.dateFrom,
        column.dateTo -> entity.dateTo,
        column.userFrom -> entity.userFrom,
        column.userTo -> entity.userTo
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractAutopayment)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractAutopayment).where.eq(column.id, entity.id) }.update.apply()
  }

}
