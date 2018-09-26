package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPatternNamedNumbers(
  id: Int,
  title: String,
  ln: Int,
  countNumber: Option[Int] = None,
  comment: Option[String] = None) {

  def save()(implicit session: DBSession = ContractPatternNamedNumbers.autoSession): ContractPatternNamedNumbers = ContractPatternNamedNumbers.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPatternNamedNumbers.autoSession): Int = ContractPatternNamedNumbers.destroy(this)(session)

}


object ContractPatternNamedNumbers extends SQLSyntaxSupport[ContractPatternNamedNumbers] {

  override val tableName = "contract_pattern_named_numbers"

  override val columns = Seq("id", "title", "ln", "count_number", "comment")

  def apply(cpnn: SyntaxProvider[ContractPatternNamedNumbers])(rs: WrappedResultSet): ContractPatternNamedNumbers = autoConstruct(rs, cpnn)
  def apply(cpnn: ResultName[ContractPatternNamedNumbers])(rs: WrappedResultSet): ContractPatternNamedNumbers = autoConstruct(rs, cpnn)

  val cpnn = ContractPatternNamedNumbers.syntax("cpnn")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractPatternNamedNumbers] = {
    withSQL {
      select.from(ContractPatternNamedNumbers as cpnn).where.eq(cpnn.id, id)
    }.map(ContractPatternNamedNumbers(cpnn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPatternNamedNumbers] = {
    withSQL(select.from(ContractPatternNamedNumbers as cpnn)).map(ContractPatternNamedNumbers(cpnn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPatternNamedNumbers as cpnn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPatternNamedNumbers] = {
    withSQL {
      select.from(ContractPatternNamedNumbers as cpnn).where.append(where)
    }.map(ContractPatternNamedNumbers(cpnn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPatternNamedNumbers] = {
    withSQL {
      select.from(ContractPatternNamedNumbers as cpnn).where.append(where)
    }.map(ContractPatternNamedNumbers(cpnn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPatternNamedNumbers as cpnn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    ln: Int,
    countNumber: Option[Int] = None,
    comment: Option[String] = None)(implicit session: DBSession = autoSession): ContractPatternNamedNumbers = {
    val generatedKey = withSQL {
      insert.into(ContractPatternNamedNumbers).namedValues(
        column.title -> title,
        column.ln -> ln,
        column.countNumber -> countNumber,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractPatternNamedNumbers(
      id = generatedKey.toInt,
      title = title,
      ln = ln,
      countNumber = countNumber,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[ContractPatternNamedNumbers])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'ln -> entity.ln,
        'countNumber -> entity.countNumber,
        'comment -> entity.comment))
    SQL("""insert into contract_pattern_named_numbers(
      title,
      ln,
      count_number,
      comment
    ) values (
      {title},
      {ln},
      {countNumber},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPatternNamedNumbers)(implicit session: DBSession = autoSession): ContractPatternNamedNumbers = {
    withSQL {
      update(ContractPatternNamedNumbers).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.ln -> entity.ln,
        column.countNumber -> entity.countNumber,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPatternNamedNumbers)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPatternNamedNumbers).where.eq(column.id, entity.id) }.update.apply()
  }

}
