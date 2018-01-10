package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPaymentTypes(
  id: Int,
  title: String,
  up: Int,
  `type`: Byte,
  flag: Byte) {

  def save()(implicit session: DBSession = ContractPaymentTypes.autoSession): ContractPaymentTypes = ContractPaymentTypes.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPaymentTypes.autoSession): Int = ContractPaymentTypes.destroy(this)(session)

}


object ContractPaymentTypes extends SQLSyntaxSupport[ContractPaymentTypes] {

  override val tableName = "contract_payment_types"

  override val columns = Seq("id", "title", "up", "type", "flag")

  def apply(cpt: SyntaxProvider[ContractPaymentTypes])(rs: WrappedResultSet): ContractPaymentTypes = autoConstruct(rs, cpt)
  def apply(cpt: ResultName[ContractPaymentTypes])(rs: WrappedResultSet): ContractPaymentTypes = autoConstruct(rs, cpt)

  val cpt = ContractPaymentTypes.syntax("cpt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractPaymentTypes] = {
    withSQL {
      select.from(ContractPaymentTypes as cpt).where.eq(cpt.id, id)
    }.map(ContractPaymentTypes(cpt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPaymentTypes] = {
    withSQL(select.from(ContractPaymentTypes as cpt)).map(ContractPaymentTypes(cpt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPaymentTypes as cpt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPaymentTypes] = {
    withSQL {
      select.from(ContractPaymentTypes as cpt).where.append(where)
    }.map(ContractPaymentTypes(cpt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPaymentTypes] = {
    withSQL {
      select.from(ContractPaymentTypes as cpt).where.append(where)
    }.map(ContractPaymentTypes(cpt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPaymentTypes as cpt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    up: Int,
    `type`: Byte,
    flag: Byte)(implicit session: DBSession = autoSession): ContractPaymentTypes = {
    val generatedKey = withSQL {
      insert.into(ContractPaymentTypes).namedValues(
        column.title -> title,
        column.up -> up,
        column.`type` -> `type`,
        column.flag -> flag
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractPaymentTypes(
      id = generatedKey.toInt,
      title = title,
      up = up,
      `type` = `type`,
      flag = flag)
  }

  def batchInsert(entities: Seq[ContractPaymentTypes])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'up -> entity.up,
        'type -> entity.`type`,
        'flag -> entity.flag))
    SQL("""insert into contract_payment_types(
      title,
      up,
      type,
      flag
    ) values (
      {title},
      {up},
      {type},
      {flag}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPaymentTypes)(implicit session: DBSession = autoSession): ContractPaymentTypes = {
    withSQL {
      update(ContractPaymentTypes).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.up -> entity.up,
        column.`type` -> entity.`type`,
        column.flag -> entity.flag
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPaymentTypes)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPaymentTypes).where.eq(column.id, entity.id) }.update.apply()
  }

}
