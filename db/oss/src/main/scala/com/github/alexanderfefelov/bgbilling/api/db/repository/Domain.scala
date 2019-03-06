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
    sql"""select ${d.result.*} from ${Domain as d} where ${d.id} = ${id}"""
      .map(Domain(d.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Domain] = {
    sql"""select ${d.result.*} from ${Domain as d}""".map(Domain(d.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${Domain.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Domain] = {
    sql"""select ${d.result.*} from ${Domain as d} where ${where}"""
      .map(Domain(d.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Domain] = {
    sql"""select ${d.result.*} from ${Domain as d} where ${where}"""
      .map(Domain(d.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${Domain as d} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String,
    comment: String)(implicit session: DBSession = autoSession): Domain = {
    val generatedKey = sql"""
      insert into ${Domain.table} (
        ${column.parentid},
        ${column.title},
        ${column.comment}
      ) values (
        ${parentid},
        ${title},
        ${comment}
      )
      """.updateAndReturnGeneratedKey.apply()

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
    sql"""
      update
        ${Domain.table}
      set
        ${column.id} = ${entity.id},
        ${column.parentid} = ${entity.parentid},
        ${column.title} = ${entity.title},
        ${column.comment} = ${entity.comment}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: Domain)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${Domain.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
