package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressStreet(
  id: Int,
  title: String,
  pIndex: String,
  cityid: Int) {

  def save()(implicit session: DBSession = AddressStreet.autoSession): AddressStreet = AddressStreet.save(this)(session)

  def destroy()(implicit session: DBSession = AddressStreet.autoSession): Int = AddressStreet.destroy(this)(session)

}


object AddressStreet extends SQLSyntaxSupport[AddressStreet] {

  override val tableName = "address_street"

  override val columns = Seq("id", "title", "p_index", "cityid")

  def apply(as: SyntaxProvider[AddressStreet])(rs: WrappedResultSet): AddressStreet = autoConstruct(rs, as)
  def apply(as: ResultName[AddressStreet])(rs: WrappedResultSet): AddressStreet = autoConstruct(rs, as)

  val as = AddressStreet.syntax("as_")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressStreet] = {
    withSQL {
      select.from(AddressStreet as as).where.eq(as.id, id)
    }.map(AddressStreet(as.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressStreet] = {
    withSQL(select.from(AddressStreet as as)).map(AddressStreet(as.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressStreet as as)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressStreet] = {
    withSQL {
      select.from(AddressStreet as as).where.append(where)
    }.map(AddressStreet(as.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressStreet] = {
    withSQL {
      select.from(AddressStreet as as).where.append(where)
    }.map(AddressStreet(as.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressStreet as as).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    pIndex: String,
    cityid: Int)(implicit session: DBSession = autoSession): AddressStreet = {
    val generatedKey = withSQL {
      insert.into(AddressStreet).namedValues(
        column.title -> title,
        column.pIndex -> pIndex,
        column.cityid -> cityid
      )
    }.updateAndReturnGeneratedKey.apply()

    AddressStreet(
      id = generatedKey.toInt,
      title = title,
      pIndex = pIndex,
      cityid = cityid)
  }

  def batchInsert(entities: Seq[AddressStreet])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'pIndex -> entity.pIndex,
        'cityid -> entity.cityid))
    SQL("""insert into address_street(
      title,
      p_index,
      cityid
    ) values (
      {title},
      {pIndex},
      {cityid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressStreet)(implicit session: DBSession = autoSession): AddressStreet = {
    withSQL {
      update(AddressStreet).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.pIndex -> entity.pIndex,
        column.cityid -> entity.cityid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressStreet)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressStreet).where.eq(column.id, entity.id) }.update.apply()
  }

}
