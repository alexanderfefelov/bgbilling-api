package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractChargeTypes(
  id: Int,
  title: String,
  flag: Byte,
  `type`: Byte,
  up: Int,
  payback: Boolean) {

  def save()(implicit session: DBSession = ContractChargeTypes.autoSession): ContractChargeTypes = ContractChargeTypes.save(this)(session)

  def destroy()(implicit session: DBSession = ContractChargeTypes.autoSession): Int = ContractChargeTypes.destroy(this)(session)

}


object ContractChargeTypes extends SQLSyntaxSupport[ContractChargeTypes] {

  override val tableName = "contract_charge_types"

  override val columns = Seq("id", "title", "flag", "type", "up", "payback")

  def apply(cct: SyntaxProvider[ContractChargeTypes])(rs: WrappedResultSet): ContractChargeTypes = autoConstruct(rs, cct)
  def apply(cct: ResultName[ContractChargeTypes])(rs: WrappedResultSet): ContractChargeTypes = autoConstruct(rs, cct)

  val cct = ContractChargeTypes.syntax("cct")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractChargeTypes] = {
    withSQL {
      select.from(ContractChargeTypes as cct).where.eq(cct.id, id)
    }.map(ContractChargeTypes(cct.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractChargeTypes] = {
    withSQL(select.from(ContractChargeTypes as cct)).map(ContractChargeTypes(cct.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractChargeTypes as cct)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractChargeTypes] = {
    withSQL {
      select.from(ContractChargeTypes as cct).where.append(where)
    }.map(ContractChargeTypes(cct.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractChargeTypes] = {
    withSQL {
      select.from(ContractChargeTypes as cct).where.append(where)
    }.map(ContractChargeTypes(cct.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractChargeTypes as cct).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    flag: Byte,
    `type`: Byte,
    up: Int,
    payback: Boolean)(implicit session: DBSession = autoSession): ContractChargeTypes = {
    val generatedKey = withSQL {
      insert.into(ContractChargeTypes).namedValues(
        column.title -> title,
        column.flag -> flag,
        column.`type` -> `type`,
        column.up -> up,
        column.payback -> payback
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractChargeTypes(
      id = generatedKey.toInt,
      title = title,
      flag = flag,
      `type` = `type`,
      up = up,
      payback = payback)
  }

  def batchInsert(entities: collection.Seq[ContractChargeTypes])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'flag -> entity.flag,
        'type -> entity.`type`,
        'up -> entity.up,
        'payback -> entity.payback))
    SQL("""insert into contract_charge_types(
      title,
      flag,
      type,
      up,
      payback
    ) values (
      {title},
      {flag},
      {type},
      {up},
      {payback}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractChargeTypes)(implicit session: DBSession = autoSession): ContractChargeTypes = {
    withSQL {
      update(ContractChargeTypes).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.flag -> entity.flag,
        column.`type` -> entity.`type`,
        column.up -> entity.up,
        column.payback -> entity.payback
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractChargeTypes)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractChargeTypes).where.eq(column.id, entity.id) }.update.apply()
  }

}
