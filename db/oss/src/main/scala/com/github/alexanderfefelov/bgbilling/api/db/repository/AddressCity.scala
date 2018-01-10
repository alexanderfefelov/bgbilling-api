package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressCity(
  id: Int,
  countryId: Int,
  title: String,
  pos: Option[Int] = None) {

  def save()(implicit session: DBSession = AddressCity.autoSession): AddressCity = AddressCity.save(this)(session)

  def destroy()(implicit session: DBSession = AddressCity.autoSession): Int = AddressCity.destroy(this)(session)

}


object AddressCity extends SQLSyntaxSupport[AddressCity] {

  override val tableName = "address_city"

  override val columns = Seq("id", "country_id", "title", "pos")

  def apply(ac: SyntaxProvider[AddressCity])(rs: WrappedResultSet): AddressCity = autoConstruct(rs, ac)
  def apply(ac: ResultName[AddressCity])(rs: WrappedResultSet): AddressCity = autoConstruct(rs, ac)

  val ac = AddressCity.syntax("ac")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressCity] = {
    withSQL {
      select.from(AddressCity as ac).where.eq(ac.id, id)
    }.map(AddressCity(ac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressCity] = {
    withSQL(select.from(AddressCity as ac)).map(AddressCity(ac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressCity as ac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressCity] = {
    withSQL {
      select.from(AddressCity as ac).where.append(where)
    }.map(AddressCity(ac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressCity] = {
    withSQL {
      select.from(AddressCity as ac).where.append(where)
    }.map(AddressCity(ac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressCity as ac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    countryId: Int,
    title: String,
    pos: Option[Int] = None)(implicit session: DBSession = autoSession): AddressCity = {
    val generatedKey = withSQL {
      insert.into(AddressCity).namedValues(
        column.countryId -> countryId,
        column.title -> title,
        column.pos -> pos
      )
    }.updateAndReturnGeneratedKey.apply()

    AddressCity(
      id = generatedKey.toInt,
      countryId = countryId,
      title = title,
      pos = pos)
  }

  def batchInsert(entities: Seq[AddressCity])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'countryId -> entity.countryId,
        'title -> entity.title,
        'pos -> entity.pos))
    SQL("""insert into address_city(
      country_id,
      title,
      pos
    ) values (
      {countryId},
      {title},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressCity)(implicit session: DBSession = autoSession): AddressCity = {
    withSQL {
      update(AddressCity).set(
        column.id -> entity.id,
        column.countryId -> entity.countryId,
        column.title -> entity.title,
        column.pos -> entity.pos
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressCity)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressCity).where.eq(column.id, entity.id) }.update.apply()
  }

}
