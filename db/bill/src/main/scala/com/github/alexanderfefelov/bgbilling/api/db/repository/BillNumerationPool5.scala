package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class BillNumerationPool5(
  id: Int,
  title: Option[String] = None,
  pattern: Option[String] = None) {

  def save()(implicit session: DBSession = BillNumerationPool5.autoSession): BillNumerationPool5 = BillNumerationPool5.save(this)(session)

  def destroy()(implicit session: DBSession = BillNumerationPool5.autoSession): Int = BillNumerationPool5.destroy(this)(session)

}


object BillNumerationPool5 extends SQLSyntaxSupport[BillNumerationPool5] with ApiDbConfig {

  override val tableName = s"bill_numeration_pool_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "title", "pattern")

  def apply(bnp: SyntaxProvider[BillNumerationPool5])(rs: WrappedResultSet): BillNumerationPool5 = autoConstruct(rs, bnp)
  def apply(bnp: ResultName[BillNumerationPool5])(rs: WrappedResultSet): BillNumerationPool5 = autoConstruct(rs, bnp)

  val bnp = BillNumerationPool5.syntax("bnp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillNumerationPool5] = {
    withSQL {
      select.from(BillNumerationPool5 as bnp).where.eq(bnp.id, id)
    }.map(BillNumerationPool5(bnp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillNumerationPool5] = {
    withSQL(select.from(BillNumerationPool5 as bnp)).map(BillNumerationPool5(bnp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillNumerationPool5 as bnp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillNumerationPool5] = {
    withSQL {
      select.from(BillNumerationPool5 as bnp).where.append(where)
    }.map(BillNumerationPool5(bnp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillNumerationPool5] = {
    withSQL {
      select.from(BillNumerationPool5 as bnp).where.append(where)
    }.map(BillNumerationPool5(bnp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillNumerationPool5 as bnp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: Option[String] = None,
    pattern: Option[String] = None)(implicit session: DBSession = autoSession): BillNumerationPool5 = {
    val generatedKey = withSQL {
      insert.into(BillNumerationPool5).namedValues(
        column.title -> title,
        column.pattern -> pattern
      )
    }.updateAndReturnGeneratedKey.apply()

    BillNumerationPool5(
      id = generatedKey.toInt,
      title = title,
      pattern = pattern)
  }

  def batchInsert(entities: Seq[BillNumerationPool5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'pattern -> entity.pattern))
    SQL("""insert into bill_numeration_pool_5(
      title,
      pattern
    ) values (
      {title},
      {pattern}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillNumerationPool5)(implicit session: DBSession = autoSession): BillNumerationPool5 = {
    withSQL {
      update(BillNumerationPool5).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.pattern -> entity.pattern
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillNumerationPool5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillNumerationPool5).where.eq(column.id, entity.id) }.update.apply()
  }

}
