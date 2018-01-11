package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BonusPaymentType(
  id: Int,
  name: String,
  used: Int) {

  def save()(implicit session: DBSession = BonusPaymentType.autoSession): BonusPaymentType = BonusPaymentType.save(this)(session)

  def destroy()(implicit session: DBSession = BonusPaymentType.autoSession): Int = BonusPaymentType.destroy(this)(session)

}


object BonusPaymentType extends SQLSyntaxSupport[BonusPaymentType] {

  override val tableName = "bonus_payment_type"

  override val columns = Seq("id", "name", "used")

  def apply(bpt: SyntaxProvider[BonusPaymentType])(rs: WrappedResultSet): BonusPaymentType = autoConstruct(rs, bpt)
  def apply(bpt: ResultName[BonusPaymentType])(rs: WrappedResultSet): BonusPaymentType = autoConstruct(rs, bpt)

  val bpt = BonusPaymentType.syntax("bpt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BonusPaymentType] = {
    withSQL {
      select.from(BonusPaymentType as bpt).where.eq(bpt.id, id)
    }.map(BonusPaymentType(bpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BonusPaymentType] = {
    withSQL(select.from(BonusPaymentType as bpt)).map(BonusPaymentType(bpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BonusPaymentType as bpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BonusPaymentType] = {
    withSQL {
      select.from(BonusPaymentType as bpt).where.append(where)
    }.map(BonusPaymentType(bpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BonusPaymentType] = {
    withSQL {
      select.from(BonusPaymentType as bpt).where.append(where)
    }.map(BonusPaymentType(bpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BonusPaymentType as bpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    used: Int)(implicit session: DBSession = autoSession): BonusPaymentType = {
    val generatedKey = withSQL {
      insert.into(BonusPaymentType).namedValues(
        column.name -> name,
        column.used -> used
      )
    }.updateAndReturnGeneratedKey.apply()

    BonusPaymentType(
      id = generatedKey.toInt,
      name = name,
      used = used)
  }

  def batchInsert(entities: Seq[BonusPaymentType])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'used -> entity.used))
    SQL("""insert into bonus_payment_type(
      name,
      used
    ) values (
      {name},
      {used}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BonusPaymentType)(implicit session: DBSession = autoSession): BonusPaymentType = {
    withSQL {
      update(BonusPaymentType).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.used -> entity.used
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BonusPaymentType)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BonusPaymentType).where.eq(column.id, entity.id) }.update.apply()
  }

}
