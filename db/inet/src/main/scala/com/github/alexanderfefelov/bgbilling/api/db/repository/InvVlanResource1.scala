package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}

case class InvVlanResource1(
  id: Int,
  title: String,
  vlanfrom: Int,
  vlanto: Int,
  datefrom: Option[LocalDate] = None,
  dateto: Option[LocalDate] = None,
  comment: String,
  categoryid: Int) {

  def save()(implicit session: DBSession = InvVlanResource1.autoSession): InvVlanResource1 = InvVlanResource1.save(this)(session)

  def destroy()(implicit session: DBSession = InvVlanResource1.autoSession): Int = InvVlanResource1.destroy(this)(session)

}


object InvVlanResource1 extends SQLSyntaxSupport[InvVlanResource1] {

  override val tableName = "inv_vlan_resource_1"

  override val columns = Seq("id", "title", "vlanFrom", "vlanTo", "dateFrom", "dateTo", "comment", "categoryId")

  def apply(ivr: SyntaxProvider[InvVlanResource1])(rs: WrappedResultSet): InvVlanResource1 = autoConstruct(rs, ivr)
  def apply(ivr: ResultName[InvVlanResource1])(rs: WrappedResultSet): InvVlanResource1 = autoConstruct(rs, ivr)

  val ivr = InvVlanResource1.syntax("ivr")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvVlanResource1] = {
    withSQL {
      select.from(InvVlanResource1 as ivr).where.eq(ivr.id, id)
    }.map(InvVlanResource1(ivr.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvVlanResource1] = {
    withSQL(select.from(InvVlanResource1 as ivr)).map(InvVlanResource1(ivr.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvVlanResource1 as ivr)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvVlanResource1] = {
    withSQL {
      select.from(InvVlanResource1 as ivr).where.append(where)
    }.map(InvVlanResource1(ivr.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvVlanResource1] = {
    withSQL {
      select.from(InvVlanResource1 as ivr).where.append(where)
    }.map(InvVlanResource1(ivr.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvVlanResource1 as ivr).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    vlanfrom: Int,
    vlanto: Int,
    datefrom: Option[LocalDate] = None,
    dateto: Option[LocalDate] = None,
    comment: String,
    categoryid: Int)(implicit session: DBSession = autoSession): InvVlanResource1 = {
    val generatedKey = withSQL {
      insert.into(InvVlanResource1).namedValues(
        column.title -> title,
        column.vlanfrom -> vlanfrom,
        column.vlanto -> vlanto,
        column.datefrom -> datefrom,
        column.dateto -> dateto,
        column.comment -> comment,
        column.categoryid -> categoryid
      )
    }.updateAndReturnGeneratedKey.apply()

    InvVlanResource1(
      id = generatedKey.toInt,
      title = title,
      vlanfrom = vlanfrom,
      vlanto = vlanto,
      datefrom = datefrom,
      dateto = dateto,
      comment = comment,
      categoryid = categoryid)
  }

  def batchInsert(entities: Seq[InvVlanResource1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'vlanfrom -> entity.vlanfrom,
        'vlanto -> entity.vlanto,
        'datefrom -> entity.datefrom,
        'dateto -> entity.dateto,
        'comment -> entity.comment,
        'categoryid -> entity.categoryid))
    SQL("""insert into inv_vlan_resource_1(
      title,
      vlanFrom,
      vlanTo,
      dateFrom,
      dateTo,
      comment,
      categoryId
    ) values (
      {title},
      {vlanfrom},
      {vlanto},
      {datefrom},
      {dateto},
      {comment},
      {categoryid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvVlanResource1)(implicit session: DBSession = autoSession): InvVlanResource1 = {
    withSQL {
      update(InvVlanResource1).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.vlanfrom -> entity.vlanfrom,
        column.vlanto -> entity.vlanto,
        column.datefrom -> entity.datefrom,
        column.dateto -> entity.dateto,
        column.comment -> entity.comment,
        column.categoryid -> entity.categoryid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvVlanResource1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvVlanResource1).where.eq(column.id, entity.id) }.update.apply()
  }

}
