package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractReserveTypes(
  id: Int,
  title: String,
  used: Option[Boolean] = None) {

  def save()(implicit session: DBSession = ContractReserveTypes.autoSession): ContractReserveTypes = ContractReserveTypes.save(this)(session)

  def destroy()(implicit session: DBSession = ContractReserveTypes.autoSession): Int = ContractReserveTypes.destroy(this)(session)

}


object ContractReserveTypes extends SQLSyntaxSupport[ContractReserveTypes] {

  override val tableName = "contract_reserve_types"

  override val columns = Seq("id", "title", "used")

  def apply(crt: SyntaxProvider[ContractReserveTypes])(rs: WrappedResultSet): ContractReserveTypes = autoConstruct(rs, crt)
  def apply(crt: ResultName[ContractReserveTypes])(rs: WrappedResultSet): ContractReserveTypes = autoConstruct(rs, crt)

  val crt = ContractReserveTypes.syntax("crt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractReserveTypes] = {
    withSQL {
      select.from(ContractReserveTypes as crt).where.eq(crt.id, id)
    }.map(ContractReserveTypes(crt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractReserveTypes] = {
    withSQL(select.from(ContractReserveTypes as crt)).map(ContractReserveTypes(crt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractReserveTypes as crt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractReserveTypes] = {
    withSQL {
      select.from(ContractReserveTypes as crt).where.append(where)
    }.map(ContractReserveTypes(crt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractReserveTypes] = {
    withSQL {
      select.from(ContractReserveTypes as crt).where.append(where)
    }.map(ContractReserveTypes(crt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractReserveTypes as crt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    used: Option[Boolean] = None)(implicit session: DBSession = autoSession): ContractReserveTypes = {
    val generatedKey = withSQL {
      insert.into(ContractReserveTypes).namedValues(
        column.title -> title,
        column.used -> used
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractReserveTypes(
      id = generatedKey.toInt,
      title = title,
      used = used)
  }

  def batchInsert(entities: collection.Seq[ContractReserveTypes])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'used -> entity.used))
    SQL("""insert into contract_reserve_types(
      title,
      used
    ) values (
      {title},
      {used}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractReserveTypes)(implicit session: DBSession = autoSession): ContractReserveTypes = {
    withSQL {
      update(ContractReserveTypes).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.used -> entity.used
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractReserveTypes)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractReserveTypes).where.eq(column.id, entity.id) }.update.apply()
  }

}
