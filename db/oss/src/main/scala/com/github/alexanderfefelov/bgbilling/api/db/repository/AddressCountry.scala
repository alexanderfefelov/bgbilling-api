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
    withSQL {
      select.from(AddressCountry as ac).where.eq(ac.id, id)
    }.map(AddressCountry(ac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressCountry] = {
    withSQL(select.from(AddressCountry as ac)).map(AddressCountry(ac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressCountry as ac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressCountry] = {
    withSQL {
      select.from(AddressCountry as ac).where.append(where)
    }.map(AddressCountry(ac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressCountry] = {
    withSQL {
      select.from(AddressCountry as ac).where.append(where)
    }.map(AddressCountry(ac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressCountry as ac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String)(implicit session: DBSession = autoSession): AddressCountry = {
    val generatedKey = withSQL {
      insert.into(AddressCountry).namedValues(
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    AddressCountry(
      id = generatedKey.toInt,
      title = title)
  }

  def batchInsert(entities: Seq[AddressCountry])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title))
    SQL("""insert into address_country(
      title
    ) values (
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressCountry)(implicit session: DBSession = autoSession): AddressCountry = {
    withSQL {
      update(AddressCountry).set(
        column.id -> entity.id,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressCountry)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressCountry).where.eq(column.id, entity.id) }.update.apply()
  }

}
