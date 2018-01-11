package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InvIpCategory1(
  id: Int,
  parentid: Int,
  title: String) {

  def save()(implicit session: DBSession = InvIpCategory1.autoSession): InvIpCategory1 = InvIpCategory1.save(this)(session)

  def destroy()(implicit session: DBSession = InvIpCategory1.autoSession): Int = InvIpCategory1.destroy(this)(session)

}


object InvIpCategory1 extends SQLSyntaxSupport[InvIpCategory1] with ApiDbConfig {

  override val tableName = s"inv_ip_category_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "title")

  def apply(iic: SyntaxProvider[InvIpCategory1])(rs: WrappedResultSet): InvIpCategory1 = autoConstruct(rs, iic)
  def apply(iic: ResultName[InvIpCategory1])(rs: WrappedResultSet): InvIpCategory1 = autoConstruct(rs, iic)

  val iic = InvIpCategory1.syntax("iic")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvIpCategory1] = {
    withSQL {
      select.from(InvIpCategory1 as iic).where.eq(iic.id, id)
    }.map(InvIpCategory1(iic.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvIpCategory1] = {
    withSQL(select.from(InvIpCategory1 as iic)).map(InvIpCategory1(iic.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvIpCategory1 as iic)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvIpCategory1] = {
    withSQL {
      select.from(InvIpCategory1 as iic).where.append(where)
    }.map(InvIpCategory1(iic.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvIpCategory1] = {
    withSQL {
      select.from(InvIpCategory1 as iic).where.append(where)
    }.map(InvIpCategory1(iic.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvIpCategory1 as iic).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String)(implicit session: DBSession = autoSession): InvIpCategory1 = {
    val generatedKey = withSQL {
      insert.into(InvIpCategory1).namedValues(
        column.parentid -> parentid,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    InvIpCategory1(
      id = generatedKey.toInt,
      parentid = parentid,
      title = title)
  }

  def batchInsert(entities: Seq[InvIpCategory1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'title -> entity.title))
    SQL("""insert into inv_ip_category_1(
      parentId,
      title
    ) values (
      {parentid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvIpCategory1)(implicit session: DBSession = autoSession): InvIpCategory1 = {
    withSQL {
      update(InvIpCategory1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvIpCategory1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvIpCategory1).where.eq(column.id, entity.id) }.update.apply()
  }

}
