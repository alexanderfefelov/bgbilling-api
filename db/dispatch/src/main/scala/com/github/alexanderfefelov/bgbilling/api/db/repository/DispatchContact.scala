package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class DispatchContact(
  id: Int,
  contractId: Int,
  typeId: Int,
  `val`: String) {

  def save()(implicit session: DBSession = DispatchContact.autoSession): DispatchContact = DispatchContact.save(this)(session)

  def destroy()(implicit session: DBSession = DispatchContact.autoSession): Int = DispatchContact.destroy(this)(session)

}


object DispatchContact extends SQLSyntaxSupport[DispatchContact] {

  override val tableName = "dispatch_contact"

  override val columns = Seq("id", "contract_id", "type_id", "val")

  def apply(dc: SyntaxProvider[DispatchContact])(rs: WrappedResultSet): DispatchContact = autoConstruct(rs, dc)
  def apply(dc: ResultName[DispatchContact])(rs: WrappedResultSet): DispatchContact = autoConstruct(rs, dc)

  val dc = DispatchContact.syntax("dc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DispatchContact] = {
    withSQL {
      select.from(DispatchContact as dc).where.eq(dc.id, id)
    }.map(DispatchContact(dc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DispatchContact] = {
    withSQL(select.from(DispatchContact as dc)).map(DispatchContact(dc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DispatchContact as dc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DispatchContact] = {
    withSQL {
      select.from(DispatchContact as dc).where.append(where)
    }.map(DispatchContact(dc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DispatchContact] = {
    withSQL {
      select.from(DispatchContact as dc).where.append(where)
    }.map(DispatchContact(dc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DispatchContact as dc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    typeId: Int,
    `val`: String)(implicit session: DBSession = autoSession): DispatchContact = {
    val generatedKey = withSQL {
      insert.into(DispatchContact).namedValues(
        column.contractId -> contractId,
        column.typeId -> typeId,
        column.`val` -> `val`
      )
    }.updateAndReturnGeneratedKey.apply()

    DispatchContact(
      id = generatedKey.toInt,
      contractId = contractId,
      typeId = typeId,
      `val` = `val`)
  }

  def batchInsert(entities: Seq[DispatchContact])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'typeId -> entity.typeId,
        'val -> entity.`val`))
    SQL("""insert into dispatch_contact(
      contract_id,
      type_id,
      val
    ) values (
      {contractId},
      {typeId},
      {val}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: DispatchContact)(implicit session: DBSession = autoSession): DispatchContact = {
    withSQL {
      update(DispatchContact).set(
        column.id -> entity.id,
        column.contractId -> entity.contractId,
        column.typeId -> entity.typeId,
        column.`val` -> entity.`val`
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DispatchContact)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(DispatchContact).where.eq(column.id, entity.id) }.update.apply()
  }

}
