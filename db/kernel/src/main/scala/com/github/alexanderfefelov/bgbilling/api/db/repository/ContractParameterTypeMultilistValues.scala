package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterTypeMultilistValues(
  id: Int,
  pid: Int,
  title: String) {

  def save()(implicit session: DBSession = ContractParameterTypeMultilistValues.autoSession): ContractParameterTypeMultilistValues = ContractParameterTypeMultilistValues.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterTypeMultilistValues.autoSession): Int = ContractParameterTypeMultilistValues.destroy(this)(session)

}


object ContractParameterTypeMultilistValues extends SQLSyntaxSupport[ContractParameterTypeMultilistValues] {

  override val tableName = "contract_parameter_type_multilist_values"

  override val columns = Seq("id", "pid", "title")

  def apply(cptmv: SyntaxProvider[ContractParameterTypeMultilistValues])(rs: WrappedResultSet): ContractParameterTypeMultilistValues = autoConstruct(rs, cptmv)
  def apply(cptmv: ResultName[ContractParameterTypeMultilistValues])(rs: WrappedResultSet): ContractParameterTypeMultilistValues = autoConstruct(rs, cptmv)

  val cptmv = ContractParameterTypeMultilistValues.syntax("cptmv")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistValues] = {
    withSQL {
      select.from(ContractParameterTypeMultilistValues as cptmv).where.eq(cptmv.id, id)
    }.map(ContractParameterTypeMultilistValues(cptmv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistValues] = {
    withSQL(select.from(ContractParameterTypeMultilistValues as cptmv)).map(ContractParameterTypeMultilistValues(cptmv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterTypeMultilistValues as cptmv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterTypeMultilistValues] = {
    withSQL {
      select.from(ContractParameterTypeMultilistValues as cptmv).where.append(where)
    }.map(ContractParameterTypeMultilistValues(cptmv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterTypeMultilistValues] = {
    withSQL {
      select.from(ContractParameterTypeMultilistValues as cptmv).where.append(where)
    }.map(ContractParameterTypeMultilistValues(cptmv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterTypeMultilistValues as cptmv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    title: String)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistValues = {
    val generatedKey = withSQL {
      insert.into(ContractParameterTypeMultilistValues).namedValues(
        column.pid -> pid,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParameterTypeMultilistValues(
      id = generatedKey.toInt,
      pid = pid,
      title = title)
  }

  def batchInsert(entities: Seq[ContractParameterTypeMultilistValues])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'title -> entity.title))
    SQL("""insert into contract_parameter_type_multilist_values(
      pid,
      title
    ) values (
      {pid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterTypeMultilistValues)(implicit session: DBSession = autoSession): ContractParameterTypeMultilistValues = {
    withSQL {
      update(ContractParameterTypeMultilistValues).set(
        column.id -> entity.id,
        column.pid -> entity.pid,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterTypeMultilistValues)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterTypeMultilistValues).where.eq(column.id, entity.id) }.update.apply()
  }

}
