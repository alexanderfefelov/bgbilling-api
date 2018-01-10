package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractParameterType4Directory(
  id: Int,
  title: Option[String] = None) {

  def save()(implicit session: DBSession = ContractParameterType4Directory.autoSession): ContractParameterType4Directory = ContractParameterType4Directory.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParameterType4Directory.autoSession): Int = ContractParameterType4Directory.destroy(this)(session)

}


object ContractParameterType4Directory extends SQLSyntaxSupport[ContractParameterType4Directory] {

  override val tableName = "contract_parameter_type_4_directory"

  override val columns = Seq("id", "title")

  def apply(cptd: SyntaxProvider[ContractParameterType4Directory])(rs: WrappedResultSet): ContractParameterType4Directory = autoConstruct(rs, cptd)
  def apply(cptd: ResultName[ContractParameterType4Directory])(rs: WrappedResultSet): ContractParameterType4Directory = autoConstruct(rs, cptd)

  val cptd = ContractParameterType4Directory.syntax("cptd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParameterType4Directory] = {
    withSQL {
      select.from(ContractParameterType4Directory as cptd).where.eq(cptd.id, id)
    }.map(ContractParameterType4Directory(cptd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParameterType4Directory] = {
    withSQL(select.from(ContractParameterType4Directory as cptd)).map(ContractParameterType4Directory(cptd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParameterType4Directory as cptd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParameterType4Directory] = {
    withSQL {
      select.from(ContractParameterType4Directory as cptd).where.append(where)
    }.map(ContractParameterType4Directory(cptd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParameterType4Directory] = {
    withSQL {
      select.from(ContractParameterType4Directory as cptd).where.append(where)
    }.map(ContractParameterType4Directory(cptd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParameterType4Directory as cptd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None)(implicit session: DBSession = autoSession): ContractParameterType4Directory = {
    val generatedKey = withSQL {
      insert.into(ContractParameterType4Directory).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParameterType4Directory(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: Seq[ContractParameterType4Directory])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into contract_parameter_type_4_directory(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParameterType4Directory)(implicit session: DBSession = autoSession): ContractParameterType4Directory = {
    withSQL {
      update(ContractParameterType4Directory).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParameterType4Directory)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParameterType4Directory).where.eq(column.id, entity.id) }.update.apply()
  }

}
