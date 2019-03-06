package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressCountry(
  id: Int,
  title: String) {

  def save()(implicit session: DBSession = AddressCountry.autoSession): AddressCountry = AddressCountry.save(this)(session)

  def destroy()(implicit session: DBSession = AddressCountry.autoSession): Int = AddressCountry.destroy(this)(session)

}


object AddressCountry extends SQLSyntaxSupport[AddressCountry] {

  override val tableName = "address_country"

  override val columns = Seq("id", "title")

  def apply(ac: SyntaxProvider[AddressCountry])(rs: WrappedResultSet): AddressCountry = autoConstruct(rs, ac)
  def apply(ac: ResultName[AddressCountry])(rs: WrappedResultSet): AddressCountry = autoConstruct(rs, ac)

  val ac = AddressCountry.syntax("ac")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressCountry] = {
    sql"""select ${ac.result.*} from ${AddressCountry as ac} where ${ac.id} = ${id}"""
      .map(AddressCountry(ac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressCountry] = {
    sql"""select ${ac.result.*} from ${AddressCountry as ac}""".map(AddressCountry(ac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressCountry.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressCountry] = {
    sql"""select ${ac.result.*} from ${AddressCountry as ac} where ${where}"""
      .map(AddressCountry(ac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressCountry] = {
    sql"""select ${ac.result.*} from ${AddressCountry as ac} where ${where}"""
      .map(AddressCountry(ac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressCountry as ac} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): AddressCountry = {
    val generatedKey = sql"""
      insert into ${AddressCountry.table} (
        ${column.title}
      ) values (
        ${title}
      )
      """.updateAndReturnGeneratedKey.apply()

    AddressCountry(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: collection.Seq[AddressCountry])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into address_country(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressCountry)(implicit session: DBSession = autoSession): AddressCountry = {
    sql"""
      update
        ${AddressCountry.table}
      set
        ${column.id} = ${entity.id},
        ${column.title} = ${entity.title}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: AddressCountry)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${AddressCountry.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
