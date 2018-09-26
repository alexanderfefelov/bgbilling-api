package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractCommentPatterns(
  id: Int,
  title: String,
  pat: String) {

  def save()(implicit session: DBSession = ContractCommentPatterns.autoSession): ContractCommentPatterns = ContractCommentPatterns.save(this)(session)

  def destroy()(implicit session: DBSession = ContractCommentPatterns.autoSession): Int = ContractCommentPatterns.destroy(this)(session)

}


object ContractCommentPatterns extends SQLSyntaxSupport[ContractCommentPatterns] {

  override val tableName = "contract_comment_patterns"

  override val columns = Seq("id", "title", "pat")

  def apply(ccp: SyntaxProvider[ContractCommentPatterns])(rs: WrappedResultSet): ContractCommentPatterns = autoConstruct(rs, ccp)
  def apply(ccp: ResultName[ContractCommentPatterns])(rs: WrappedResultSet): ContractCommentPatterns = autoConstruct(rs, ccp)

  val ccp = ContractCommentPatterns.syntax("ccp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractCommentPatterns] = {
    withSQL {
      select.from(ContractCommentPatterns as ccp).where.eq(ccp.id, id)
    }.map(ContractCommentPatterns(ccp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractCommentPatterns] = {
    withSQL(select.from(ContractCommentPatterns as ccp)).map(ContractCommentPatterns(ccp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractCommentPatterns as ccp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractCommentPatterns] = {
    withSQL {
      select.from(ContractCommentPatterns as ccp).where.append(where)
    }.map(ContractCommentPatterns(ccp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractCommentPatterns] = {
    withSQL {
      select.from(ContractCommentPatterns as ccp).where.append(where)
    }.map(ContractCommentPatterns(ccp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractCommentPatterns as ccp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    pat: String)(implicit session: DBSession = autoSession): ContractCommentPatterns = {
    val generatedKey = withSQL {
      insert.into(ContractCommentPatterns).namedValues(
        column.title -> title,
        column.pat -> pat
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractCommentPatterns(
      id = generatedKey.toInt,
      title = title,
      pat = pat)
  }

  def batchInsert(entities: collection.Seq[ContractCommentPatterns])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'pat -> entity.pat))
    SQL("""insert into contract_comment_patterns(
      title,
      pat
    ) values (
      {title},
      {pat}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractCommentPatterns)(implicit session: DBSession = autoSession): ContractCommentPatterns = {
    withSQL {
      update(ContractCommentPatterns).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.pat -> entity.pat
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractCommentPatterns)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractCommentPatterns).where.eq(column.id, entity.id) }.update.apply()
  }

}
