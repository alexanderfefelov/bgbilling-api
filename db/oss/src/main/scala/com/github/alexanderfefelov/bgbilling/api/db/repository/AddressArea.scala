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
    sql"""select ${aa.result.*} from ${AddressArea as aa} where ${aa.id} = ${id}"""
      .map(AddressArea(aa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressArea] = {
    sql"""select ${aa.result.*} from ${AddressArea as aa}""".map(AddressArea(aa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressArea.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressArea] = {
    sql"""select ${aa.result.*} from ${AddressArea as aa} where ${where}"""
      .map(AddressArea(aa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressArea] = {
    sql"""select ${aa.result.*} from ${AddressArea as aa} where ${where}"""
      .map(AddressArea(aa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${AddressArea as aa} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    cityid: Int)(implicit session: DBSession = autoSession): AddressArea = {
    val generatedKey = sql"""
      insert into ${AddressArea.table} (
        ${column.title},
        ${column.cityid}
      ) values (
        ${title},
        ${cityid}
      )
      """.updateAndReturnGeneratedKey.apply()

    AddressArea(
      id = generatedKey.toInt,
      title = title,
      cityid = cityid)
  }

  def batchInsert(entities: collection.Seq[AddressArea])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
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
    sql"""
      update
        ${AddressArea.table}
      set
        ${column.id} = ${entity.id},
        ${column.title} = ${entity.title},
        ${column.cityid} = ${entity.cityid}
      where
        ${column.id} = ${entity.id}
      """.update.apply()
    entity
  }

  def destroy(entity: AddressArea)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${AddressArea.table} where ${column.id} = ${entity.id}""".update.apply()
  }

}
