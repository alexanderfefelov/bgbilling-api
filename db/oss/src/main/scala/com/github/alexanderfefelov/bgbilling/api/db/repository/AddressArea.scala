package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressArea(
  id: Int,
  title: String,
  cityid: Int) {

  def save()(implicit session: DBSession = AddressArea.autoSession): AddressArea = AddressArea.save(this)(session)

  def destroy()(implicit session: DBSession = AddressArea.autoSession): Int = AddressArea.destroy(this)(session)

}


object AddressArea extends SQLSyntaxSupport[AddressArea] {

  override val tableName = "address_area"

  override val columns = Seq("id", "title", "cityid")

  def apply(aa: SyntaxProvider[AddressArea])(rs: WrappedResultSet): AddressArea = autoConstruct(rs, aa)
  def apply(aa: ResultName[AddressArea])(rs: WrappedResultSet): AddressArea = autoConstruct(rs, aa)

  val aa = AddressArea.syntax("aa")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[AddressArea] = {
    withSQL {
      select.from(AddressArea as aa).where.eq(aa.id, id)
    }.map(AddressArea(aa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressArea] = {
    withSQL(select.from(AddressArea as aa)).map(AddressArea(aa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressArea as aa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressArea] = {
    withSQL {
      select.from(AddressArea as aa).where.append(where)
    }.map(AddressArea(aa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressArea] = {
    withSQL {
      select.from(AddressArea as aa).where.append(where)
    }.map(AddressArea(aa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressArea as aa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    cityid: Int)(implicit session: DBSession = autoSession): AddressArea = {
    val generatedKey = withSQL {
      insert.into(AddressArea).namedValues(
        column.title -> title,
        column.cityid -> cityid
      )
    }.updateAndReturnGeneratedKey.apply()

    AddressArea(
      id = generatedKey.toInt,
      title = title,
      cityid = cityid)
  }

  def batchInsert(entities: Seq[AddressArea])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'cityid -> entity.cityid))
    SQL("""insert into address_area(
      title,
      cityid
    ) values (
      {title},
      {cityid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressArea)(implicit session: DBSession = autoSession): AddressArea = {
    withSQL {
      update(AddressArea).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.cityid -> entity.cityid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressArea)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressArea).where.eq(column.id, entity.id) }.update.apply()
  }

}
