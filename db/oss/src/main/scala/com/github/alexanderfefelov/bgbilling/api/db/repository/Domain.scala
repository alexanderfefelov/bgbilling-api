package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class Domain(
  id: Int,
  parentid: Int,
  title: String,
  comment: String) {

  def save()(implicit session: DBSession = Domain.autoSession): Domain = Domain.save(this)(session)

  def destroy()(implicit session: DBSession = Domain.autoSession): Int = Domain.destroy(this)(session)

}


object Domain extends SQLSyntaxSupport[Domain] {

  override val tableName = "domain"

  override val columns = Seq("id", "parentId", "title", "comment")

  def apply(d: SyntaxProvider[Domain])(rs: WrappedResultSet): Domain = autoConstruct(rs, d)
  def apply(d: ResultName[Domain])(rs: WrappedResultSet): Domain = autoConstruct(rs, d)

  val d = Domain.syntax("d")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Domain] = {
    withSQL {
      select.from(Domain as d).where.eq(d.id, id)
    }.map(Domain(d.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Domain] = {
    withSQL(select.from(Domain as d)).map(Domain(d.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Domain as d)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Domain] = {
    withSQL {
      select.from(Domain as d).where.append(where)
    }.map(Domain(d.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Domain] = {
    withSQL {
      select.from(Domain as d).where.append(where)
    }.map(Domain(d.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Domain as d).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String,
    comment: String)(implicit session: DBSession = autoSession): Domain = {
    val generatedKey = withSQL {
      insert.into(Domain).namedValues(
        column.parentid -> parentid,
        column.title -> title,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    Domain(
      id = generatedKey.toInt,
      parentid = parentid,
      title = title,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[Domain])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'title -> entity.title,
        'comment -> entity.comment))
    SQL("""insert into domain(
      parentId,
      title,
      comment
    ) values (
      {parentid},
      {title},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Domain)(implicit session: DBSession = autoSession): Domain = {
    withSQL {
      update(Domain).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.title -> entity.title,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Domain)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Domain).where.eq(column.id, entity.id) }.update.apply()
  }

}
