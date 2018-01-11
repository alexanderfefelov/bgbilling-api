package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class BillAttributeType5(
  id: Int,
  name: String,
  title: String,
  check: Option[String] = None) {

  def save()(implicit session: DBSession = BillAttributeType5.autoSession): BillAttributeType5 = BillAttributeType5.save(this)(session)

  def destroy()(implicit session: DBSession = BillAttributeType5.autoSession): Int = BillAttributeType5.destroy(this)(session)

}


object BillAttributeType5 extends SQLSyntaxSupport[BillAttributeType5] with ApiDbConfig {

  override val tableName = s"bill_attribute_type_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "name", "title", "check")

  def apply(bat: SyntaxProvider[BillAttributeType5])(rs: WrappedResultSet): BillAttributeType5 = autoConstruct(rs, bat)
  def apply(bat: ResultName[BillAttributeType5])(rs: WrappedResultSet): BillAttributeType5 = autoConstruct(rs, bat)

  val bat = BillAttributeType5.syntax("bat")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillAttributeType5] = {
    withSQL {
      select.from(BillAttributeType5 as bat).where.eq(bat.id, id)
    }.map(BillAttributeType5(bat.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillAttributeType5] = {
    withSQL(select.from(BillAttributeType5 as bat)).map(BillAttributeType5(bat.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillAttributeType5 as bat)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillAttributeType5] = {
    withSQL {
      select.from(BillAttributeType5 as bat).where.append(where)
    }.map(BillAttributeType5(bat.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillAttributeType5] = {
    withSQL {
      select.from(BillAttributeType5 as bat).where.append(where)
    }.map(BillAttributeType5(bat.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillAttributeType5 as bat).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    title: String,
    check: Option[String] = None)(implicit session: DBSession = autoSession): BillAttributeType5 = {
    val generatedKey = withSQL {
      insert.into(BillAttributeType5).namedValues(
        column.name -> name,
        column.title -> title,
        column.check -> check
      )
    }.updateAndReturnGeneratedKey.apply()

    BillAttributeType5(
      id = generatedKey.toInt,
      name = name,
      title = title,
      check = check)
  }

  def batchInsert(entities: Seq[BillAttributeType5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'title -> entity.title,
        'check -> entity.check))
    SQL("""insert into bill_attribute_type_5(
      name,
      title,
      check
    ) values (
      {name},
      {title},
      {check}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillAttributeType5)(implicit session: DBSession = autoSession): BillAttributeType5 = {
    withSQL {
      update(BillAttributeType5).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.title -> entity.title,
        column.check -> entity.check
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillAttributeType5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillAttributeType5).where.eq(column.id, entity.id) }.update.apply()
  }

}
