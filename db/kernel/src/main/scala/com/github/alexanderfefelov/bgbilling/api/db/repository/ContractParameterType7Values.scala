package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType7Values(
  id: Int,
  pid: Int,
  title: String) {

  def save()(implicit session: DBSession = ContractParameterType7Values.autoSession): ContractParameterType7Values = ContractParameterType7Values.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType7Values.autoSession): Int = ContractParameterType7Values.destroy(this)(session)

}


object ContractParameterType7Values extends SQLSyntaxSupport[ContractParameterType7Values] {

  override val tableName = "contract_parameter_type_7_values"

  override val columns = Seq("id", "pid", "title")

  def apply(cptv: SyntaxProvider[ContractParameterType7Values])(rs: WrappedResultSet): ContractParameterType7Values = autoConstruct(rs, cptv)
  def apply(cptv: ResultName[ContractParameterType7Values])(rs: WrappedResultSet): ContractParameterType7Values = autoConstruct(rs, cptv)

  val cptv = ContractParameterType7Values.syntax("cptv")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType7Values] = {
    withSQL {
      select.from(ContractParameterType7Values as cptv).where.eq(cptv.id, id)
    }.map(ContractParameterType7Values(cptv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType7Values] = {
    withSQL(select.from(ContractParameterType7Values as cptv)).map(ContractParameterType7Values(cptv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType7Values as cptv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType7Values] = {
    withSQL {
      select.from(ContractParameterType7Values as cptv).where.append(where)
    }.map(ContractParameterType7Values(cptv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType7Values] = {
    withSQL {
      select.from(ContractParameterType7Values as cptv).where.append(where)
    }.map(ContractParameterType7Values(cptv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType7Values as cptv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    title: String)(implicit session: DBSession = autoSession): ContractParameterType7Values = {
    val generatedKey = withSQL {
      insert.into(ContractParameterType7Values).namedValues(
        column.pid -> pid,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParameterType7Values(
      id = generatedKey.toInt,
      pid = pid,
      title = title)
  }

  def batchInsert(entities: Seq[ContractParameterType7Values])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'title -> entity.title))
    SQL("""insert into contract_parameter_type_7_values(
      pid,
      title
    ) values (
      {pid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType7Values)(implicit session: DBSession = autoSession): ContractParameterType7Values = {
    withSQL {
      update(ContractParameterType7Values).set(
        column.id -> entity.id,
        column.pid -> entity.pid,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType7Values)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType7Values).where.eq(column.id, entity.id) }.update.apply()
  }

}
