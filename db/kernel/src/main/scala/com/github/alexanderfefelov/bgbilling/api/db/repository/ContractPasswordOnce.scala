package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ContractPasswordOnce(
  lu: DateTime,
  dt: DateTime,
  contractTitle: String,
  password: String) {

  def save()(implicit session: DBSession = ContractPasswordOnce.autoSession): ContractPasswordOnce = ContractPasswordOnce.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPasswordOnce.autoSession): Int = ContractPasswordOnce.destroy(this)(session)

}


object ContractPasswordOnce extends SQLSyntaxSupport[ContractPasswordOnce] {

  override val tableName = "contract_password_once"

  override val columns = Seq("lu", "dt", "contract_title", "password")

  def apply(cpo: SyntaxProvider[ContractPasswordOnce])(rs: WrappedResultSet): ContractPasswordOnce = autoConstruct(rs, cpo)
  def apply(cpo: ResultName[ContractPasswordOnce])(rs: WrappedResultSet): ContractPasswordOnce = autoConstruct(rs, cpo)

  val cpo = ContractPasswordOnce.syntax("cpo")

  override val autoSession = AutoSession

  def find(lu: DateTime, dt: DateTime, contractTitle: String, password: String)(implicit session: DBSession = autoSession): Option[ContractPasswordOnce] = {
    withSQL {
      select.from(ContractPasswordOnce as cpo).where.eq(cpo.lu, lu).and.eq(cpo.dt, dt).and.eq(cpo.contractTitle, contractTitle).and.eq(cpo.password, password)
    }.map(ContractPasswordOnce(cpo.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPasswordOnce] = {
    withSQL(select.from(ContractPasswordOnce as cpo)).map(ContractPasswordOnce(cpo.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPasswordOnce as cpo)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPasswordOnce] = {
    withSQL {
      select.from(ContractPasswordOnce as cpo).where.append(where)
    }.map(ContractPasswordOnce(cpo.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPasswordOnce] = {
    withSQL {
      select.from(ContractPasswordOnce as cpo).where.append(where)
    }.map(ContractPasswordOnce(cpo.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPasswordOnce as cpo).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    lu: DateTime,
    dt: DateTime,
    contractTitle: String,
    password: String)(implicit session: DBSession = autoSession): ContractPasswordOnce = {
    withSQL {
      insert.into(ContractPasswordOnce).namedValues(
        column.lu -> lu,
        column.dt -> dt,
        column.contractTitle -> contractTitle,
        column.password -> password
      )
    }.update.apply()

    ContractPasswordOnce(
      lu = lu,
      dt = dt,
      contractTitle = contractTitle,
      password = password)
  }

  def batchInsert(entities: Seq[ContractPasswordOnce])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'lu -> entity.lu,
        'dt -> entity.dt,
        'contractTitle -> entity.contractTitle,
        'password -> entity.password))
    SQL("""insert into contract_password_once(
      lu,
      dt,
      contract_title,
      password
    ) values (
      {lu},
      {dt},
      {contractTitle},
      {password}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPasswordOnce)(implicit session: DBSession = autoSession): ContractPasswordOnce = {
    withSQL {
      update(ContractPasswordOnce).set(
        column.lu -> entity.lu,
        column.dt -> entity.dt,
        column.contractTitle -> entity.contractTitle,
        column.password -> entity.password
      ).where.eq(column.lu, entity.lu).and.eq(column.dt, entity.dt).and.eq(column.contractTitle, entity.contractTitle).and.eq(column.password, entity.password)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPasswordOnce)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPasswordOnce).where.eq(column.lu, entity.lu).and.eq(column.dt, entity.dt).and.eq(column.contractTitle, entity.contractTitle).and.eq(column.password, entity.password) }.update.apply()
  }

}
