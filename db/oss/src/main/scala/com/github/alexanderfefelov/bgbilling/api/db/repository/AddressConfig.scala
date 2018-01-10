package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class AddressConfig(
  tableId: String,
  recordId: Int,
  key: String,
  value: String) {

  def save()(implicit session: DBSession = AddressConfig.autoSession): AddressConfig = AddressConfig.save(this)(session)

  def destroy()(implicit session: DBSession = AddressConfig.autoSession): Int = AddressConfig.destroy(this)(session)

}


object AddressConfig extends SQLSyntaxSupport[AddressConfig] {

  override val tableName = "address_config"

  override val columns = Seq("table_id", "record_id", "key", "value")

  def apply(ac: SyntaxProvider[AddressConfig])(rs: WrappedResultSet): AddressConfig = autoConstruct(rs, ac)
  def apply(ac: ResultName[AddressConfig])(rs: WrappedResultSet): AddressConfig = autoConstruct(rs, ac)

  val ac = AddressConfig.syntax("ac")

  override val autoSession = AutoSession

  def find(key: String, recordId: Int, tableId: String)(implicit session: DBSession = autoSession): Option[AddressConfig] = {
    withSQL {
      select.from(AddressConfig as ac).where.eq(ac.key, key).and.eq(ac.recordId, recordId).and.eq(ac.tableId, tableId)
    }.map(AddressConfig(ac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[AddressConfig] = {
    withSQL(select.from(AddressConfig as ac)).map(AddressConfig(ac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(AddressConfig as ac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[AddressConfig] = {
    withSQL {
      select.from(AddressConfig as ac).where.append(where)
    }.map(AddressConfig(ac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[AddressConfig] = {
    withSQL {
      select.from(AddressConfig as ac).where.append(where)
    }.map(AddressConfig(ac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(AddressConfig as ac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tableId: String,
    recordId: Int,
    key: String,
    value: String)(implicit session: DBSession = autoSession): AddressConfig = {
    withSQL {
      insert.into(AddressConfig).namedValues(
        column.tableId -> tableId,
        column.recordId -> recordId,
        column.key -> key,
        column.value -> value
      )
    }.update.apply()

    AddressConfig(
      tableId = tableId,
      recordId = recordId,
      key = key,
      value = value)
  }

  def batchInsert(entities: Seq[AddressConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tableId -> entity.tableId,
        'recordId -> entity.recordId,
        'key -> entity.key,
        'value -> entity.value))
    SQL("""insert into address_config(
      table_id,
      record_id,
      key,
      value
    ) values (
      {tableId},
      {recordId},
      {key},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: AddressConfig)(implicit session: DBSession = autoSession): AddressConfig = {
    withSQL {
      update(AddressConfig).set(
        column.tableId -> entity.tableId,
        column.recordId -> entity.recordId,
        column.key -> entity.key,
        column.value -> entity.value
      ).where.eq(column.key, entity.key).and.eq(column.recordId, entity.recordId).and.eq(column.tableId, entity.tableId)
    }.update.apply()
    entity
  }

  def destroy(entity: AddressConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(AddressConfig).where.eq(column.key, entity.key).and.eq(column.recordId, entity.recordId).and.eq(column.tableId, entity.tableId) }.update.apply()
  }

}
