package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class InvVlanCategory1(
  id: Int,
  parentid: Int,
  title: String) {

  def save()(implicit session: DBSession = InvVlanCategory1.autoSession): InvVlanCategory1 = InvVlanCategory1.save(this)(session)

  def destroy()(implicit session: DBSession = InvVlanCategory1.autoSession): Int = InvVlanCategory1.destroy(this)(session)

}


object InvVlanCategory1 extends SQLSyntaxSupport[InvVlanCategory1] with ApiDbConfig {

  override val tableName = s"inv_vlan_category_${bgBillingModuleId("inet")}"

  override val columns = Seq("id", "parentId", "title")

  def apply(ivc: SyntaxProvider[InvVlanCategory1])(rs: WrappedResultSet): InvVlanCategory1 = autoConstruct(rs, ivc)
  def apply(ivc: ResultName[InvVlanCategory1])(rs: WrappedResultSet): InvVlanCategory1 = autoConstruct(rs, ivc)

  val ivc = InvVlanCategory1.syntax("ivc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InvVlanCategory1] = {
    withSQL {
      select.from(InvVlanCategory1 as ivc).where.eq(ivc.id, id)
    }.map(InvVlanCategory1(ivc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InvVlanCategory1] = {
    withSQL(select.from(InvVlanCategory1 as ivc)).map(InvVlanCategory1(ivc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InvVlanCategory1 as ivc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InvVlanCategory1] = {
    withSQL {
      select.from(InvVlanCategory1 as ivc).where.append(where)
    }.map(InvVlanCategory1(ivc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InvVlanCategory1] = {
    withSQL {
      select.from(InvVlanCategory1 as ivc).where.append(where)
    }.map(InvVlanCategory1(ivc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InvVlanCategory1 as ivc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentid: Int,
    title: String)(implicit session: DBSession = autoSession): InvVlanCategory1 = {
    val generatedKey = withSQL {
      insert.into(InvVlanCategory1).namedValues(
        column.parentid -> parentid,
        column.title -> title
      )
    }.updateAndReturnGeneratedKey.apply()

    InvVlanCategory1(
      id = generatedKey.toInt,
      parentid = parentid,
      title = title)
  }

  def batchInsert(entities: Seq[InvVlanCategory1])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentid -> entity.parentid,
        'title -> entity.title))
    SQL("""insert into inv_vlan_category_1(
      parentId,
      title
    ) values (
      {parentid},
      {title}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InvVlanCategory1)(implicit session: DBSession = autoSession): InvVlanCategory1 = {
    withSQL {
      update(InvVlanCategory1).set(
        column.id -> entity.id,
        column.parentid -> entity.parentid,
        column.title -> entity.title
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InvVlanCategory1)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InvVlanCategory1).where.eq(column.id, entity.id) }.update.apply()
  }

}
