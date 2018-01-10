package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressQuarter(
  id: Int,
  title: String,
  gid: Option[Int] = None,
  cityid: Int) {

  def save()(implicit session: DBSession = AddressQuarter.autoSession): AddressQuarter = AddressQuarter.save(this)(session)

  def destroy()(implicit session: DBSession = AddressQuarter.autoSession): Int = AddressQuarter.destroy(this)(session)

}


object AddressQuarter extends SQLSyntaxSupport[AddressQuarter] {

  override val tableName = "address_quarter"

  override val columns = Seq("id", "title", "gid", "cityid")

  def apply(aq: SyntaxProvider[AddressQuarter])(rs: WrappedResultSet): AddressQuarter = autoConstruct(rs, aq)
  def apply(aq: ResultName[AddressQuarter])(rs: WrappedResultSet): AddressQuarter = autoConstruct(rs, aq)

  val aq = AddressQuarter.syntax("aq")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressQuarter] = {
    withSQL {
      select.from(AddressQuarter as aq).where.eq(aq.id, id)
    }.map(AddressQuarter(aq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressQuarter] = {
    withSQL(select.from(AddressQuarter as aq)).map(AddressQuarter(aq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressQuarter as aq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressQuarter] = {
    withSQL {
      select.from(AddressQuarter as aq).where.append(where)
    }.map(AddressQuarter(aq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressQuarter] = {
    withSQL {
      select.from(AddressQuarter as aq).where.append(where)
    }.map(AddressQuarter(aq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressQuarter as aq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    gid: Option[Int] = None,
    cityid: Int)(implicit session: DBSession = autoSession): AddressQuarter = {
    val generatedKey = withSQL {
      insert.into(AddressQuarter).namedValues(
        column.title -> title,
        column.gid -> gid,
        column.cityid -> cityid
      )
    }.updateAndReturnGeneratedKey.apply()

    AddressQuarter(
      id = generatedKey.toInt,
      title = title,
      gid = gid,
      cityid = cityid)
  }

  def batchInsert(entities: Seq[AddressQuarter])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'gid -> entity.gid,
        'cityid -> entity.cityid))
    SQL("""insert into address_quarter(
      title,
      gid,
      cityid
    ) values (
      {title},
      {gid},
      {cityid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressQuarter)(implicit session: DBSession = autoSession): AddressQuarter = {
    withSQL {
      update(AddressQuarter).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.gid -> entity.gid,
        column.cityid -> entity.cityid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressQuarter)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressQuarter).where.eq(column.id, entity.id) }.update.apply()
  }

}
