package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InvDeviceGroup1(
  id: Int,
  parentid: Int,
  title: String,
  cityid: Int,
  comment: String) {

  def save()(implicit session: DBSession = InvDeviceGroup1.autoSession): InvDeviceGroup1 = InvDeviceGroup1.save(this)(session)

  def destroy()(implicit session: DBSession = InvDeviceGroup1.autoSession): Int = InvDeviceGroup1.destroy(this)(session)

}


object InvDeviceGroup1 extends SQLSyntaxSupport[InvDeviceGroup1] {

  override val tableName = "inv_device_group_1"

  override val columns = Seq("id", "parentId", "title", "cityId", "comment")

  def apply(idg: SyntaxProvider[InvDeviceGroup1])(rs: WrappedResultSet): InvDeviceGroup1 = autoConstruct(rs, idg)
  def apply(idg: ResultName[InvDeviceGroup1])(rs: WrappedResultSet): InvDeviceGroup1 = autoConstruct(rs, idg)

  val idg = InvDeviceGroup1.syntax("idg")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvDeviceGroup1] = {
    withSQL {
      select.from(InvDeviceGroup1 as idg).where.eq(idg.id, id)
    }.map(InvDeviceGroup1(idg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvDeviceGroup1] = {
    withSQL(select.from(InvDeviceGroup1 as idg)).map(InvDeviceGroup1(idg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvDeviceGroup1 as idg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvDeviceGroup1] = {
    withSQL {
      select.from(InvDeviceGroup1 as idg).where.append(where)
    }.map(InvDeviceGroup1(idg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvDeviceGroup1] = {
    withSQL {
      select.from(InvDeviceGroup1 as idg).where.append(where)
    }.map(InvDeviceGroup1(idg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvDeviceGroup1 as idg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String,
    cityid: Int,
    comment: String)(implicit session: DBSession = autoSession): InvDeviceGroup1 = {
    val generatedKey = withSQL {
      insert.into(InvDeviceGroup1).namedValues(
        column.parentid -> parentid,
        column.title -> title,
        column.cityid -> cityid,
        column.comment -> comment
      )
    }.updateAndReturnGeneratedKey.apply()

    InvDeviceGroup1(
      id = generatedKey.toInt,
      parentid = parentid,
      title = title,
      cityid = cityid,
      comment = comment)
  }

  def batchInsert(entities: Seq[InvDeviceGroup1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'title -> entity.title,
        'cityid -> entity.cityid,
        'comment -> entity.comment))
    SQL("""insert into inv_device_group_1(
      parentId,
      title,
      cityId,
      comment
    ) values (
      {parentid},
      {title},
      {cityid},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvDeviceGroup1)(implicit session: DBSession = autoSession): InvDeviceGroup1 = {
    withSQL {
      update(InvDeviceGroup1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.title -> entity.title,
        column.cityid -> entity.cityid,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvDeviceGroup1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvDeviceGroup1).where.eq(column.id, entity.id) }.update.apply()
  }

}
