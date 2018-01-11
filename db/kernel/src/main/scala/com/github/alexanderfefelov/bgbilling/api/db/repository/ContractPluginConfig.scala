package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractPluginConfig(
  contractId: Int,
  pluginId: Int,
  key: String,
  value: String) {

  def save()(implicit session: DBSession = ContractPluginConfig.autoSession): ContractPluginConfig = ContractPluginConfig.save(this)(session)

  def destroy()(implicit session: DBSession = ContractPluginConfig.autoSession): Int = ContractPluginConfig.destroy(this)(session)

}


object ContractPluginConfig extends SQLSyntaxSupport[ContractPluginConfig] {

  override val tableName = "contract_plugin_config"

  override val columns = Seq("contract_id", "plugin_id", "key", "value")

  def apply(cpc: SyntaxProvider[ContractPluginConfig])(rs: WrappedResultSet): ContractPluginConfig = autoConstruct(rs, cpc)
  def apply(cpc: ResultName[ContractPluginConfig])(rs: WrappedResultSet): ContractPluginConfig = autoConstruct(rs, cpc)

  val cpc = ContractPluginConfig.syntax("cpc")

  override val autoSession = AutoSession

  def find(contractId: Int, key: String, pluginId: Int)(implicit session: DBSession = autoSession): Option[ContractPluginConfig] = {
    withSQL {
      select.from(ContractPluginConfig as cpc).where.eq(cpc.contractId, contractId).and.eq(cpc.key, key).and.eq(cpc.pluginId, pluginId)
    }.map(ContractPluginConfig(cpc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractPluginConfig] = {
    withSQL(select.from(ContractPluginConfig as cpc)).map(ContractPluginConfig(cpc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractPluginConfig as cpc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractPluginConfig] = {
    withSQL {
      select.from(ContractPluginConfig as cpc).where.append(where)
    }.map(ContractPluginConfig(cpc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractPluginConfig] = {
    withSQL {
      select.from(ContractPluginConfig as cpc).where.append(where)
    }.map(ContractPluginConfig(cpc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractPluginConfig as cpc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    pluginId: Int,
    key: String,
    value: String)(implicit session: DBSession = autoSession): ContractPluginConfig = {
    withSQL {
      insert.into(ContractPluginConfig).namedValues(
        column.contractId -> contractId,
        column.pluginId -> pluginId,
        column.key -> key,
        column.value -> value
      )
    }.update.apply()

    ContractPluginConfig(
      contractId = contractId,
      pluginId = pluginId,
      key = key,
      value = value)
  }

  def batchInsert(entities: Seq[ContractPluginConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'pluginId -> entity.pluginId,
        'key -> entity.key,
        'value -> entity.value))
    SQL("""insert into contract_plugin_config(
      contract_id,
      plugin_id,
      key,
      value
    ) values (
      {contractId},
      {pluginId},
      {key},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractPluginConfig)(implicit session: DBSession = autoSession): ContractPluginConfig = {
    withSQL {
      update(ContractPluginConfig).set(
        column.contractId -> entity.contractId,
        column.pluginId -> entity.pluginId,
        column.key -> entity.key,
        column.value -> entity.value
      ).where.eq(column.contractId, entity.contractId).and.eq(column.key, entity.key).and.eq(column.pluginId, entity.pluginId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractPluginConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractPluginConfig).where.eq(column.contractId, entity.contractId).and.eq(column.key, entity.key).and.eq(column.pluginId, entity.pluginId) }.update.apply()
  }

}
