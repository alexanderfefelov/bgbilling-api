package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterGroupName(
  id: Int,
  title: String) {

  def save()(implicit session: DBSession = ContractParameterGroupName.autoSession): ContractParameterGroupName = ContractParameterGroupName.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterGroupName.autoSession): Int = ContractParameterGroupName.destroy(this)(session)

}


object ContractParameterGroupName extends SQLSyntaxSupport[ContractParameterGroupName] {

  override val tableName = "contract_parameter_group_name"

  override val columns = Seq("id", "title")

  def apply(cpgn: SyntaxProvider[ContractParameterGroupName])(rs: WrappedResultSet): ContractParameterGroupName = autoConstruct(rs, cpgn)
  def apply(cpgn: ResultName[ContractParameterGroupName])(rs: WrappedResultSet): ContractParameterGroupName = autoConstruct(rs, cpgn)

  val cpgn = ContractParameterGroupName.syntax("cpgn")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParameterGroupName] = {
    withSQL {
      select.from(ContractParameterGroupName as cpgn).where.eq(cpgn.id, id)
    }.map(ContractParameterGroupName(cpgn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterGroupName] = {
    withSQL(select.from(ContractParameterGroupName as cpgn)).map(ContractParameterGroupName(cpgn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterGroupName as cpgn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterGroupName] = {
    withSQL {
      select.from(ContractParameterGroupName as cpgn).where.append(where)
    }.map(ContractParameterGroupName(cpgn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterGroupName] = {
    withSQL {
      select.from(ContractParameterGroupName as cpgn).where.append(where)
    }.map(ContractParameterGroupName(cpgn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterGroupName as cpgn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): ContractParameterGroupName = {
    val generatedKey = withSQL {
      insert.into(ContractParameterGroupName).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParameterGroupName(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: Seq[ContractParameterGroupName])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into contract_parameter_group_name(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterGroupName)(implicit session: DBSession = autoSession): ContractParameterGroupName = {
    withSQL {
      update(ContractParameterGroupName).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterGroupName)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterGroupName).where.eq(column.id, entity.id) }.update.apply()
  }

}
