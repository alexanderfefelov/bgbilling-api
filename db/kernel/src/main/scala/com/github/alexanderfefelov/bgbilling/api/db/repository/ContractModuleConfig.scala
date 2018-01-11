package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractModuleConfig(
  contractId: Int,
  moduleId: Int,
  key: String,
  value: String) {

  def save()(implicit session: DBSession = ContractModuleConfig.autoSession): ContractModuleConfig = ContractModuleConfig.save(this)(session)

  def destroy()(implicit session: DBSession = ContractModuleConfig.autoSession): Int = ContractModuleConfig.destroy(this)(session)

}


object ContractModuleConfig extends SQLSyntaxSupport[ContractModuleConfig] {

  override val tableName = "contract_module_config"

  override val columns = Seq("contract_id", "module_id", "key", "value")

  def apply(cmc: SyntaxProvider[ContractModuleConfig])(rs: WrappedResultSet): ContractModuleConfig = autoConstruct(rs, cmc)
  def apply(cmc: ResultName[ContractModuleConfig])(rs: WrappedResultSet): ContractModuleConfig = autoConstruct(rs, cmc)

  val cmc = ContractModuleConfig.syntax("cmc")

  override val autoSession = AutoSession

  def find(contractId: Int, key: String, moduleId: Int)(implicit session: DBSession = autoSession): Option[ContractModuleConfig] = {
    withSQL {
      select.from(ContractModuleConfig as cmc).where.eq(cmc.contractId, contractId).and.eq(cmc.key, key).and.eq(cmc.moduleId, moduleId)
    }.map(ContractModuleConfig(cmc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractModuleConfig] = {
    withSQL(select.from(ContractModuleConfig as cmc)).map(ContractModuleConfig(cmc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractModuleConfig as cmc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractModuleConfig] = {
    withSQL {
      select.from(ContractModuleConfig as cmc).where.append(where)
    }.map(ContractModuleConfig(cmc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractModuleConfig] = {
    withSQL {
      select.from(ContractModuleConfig as cmc).where.append(where)
    }.map(ContractModuleConfig(cmc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractModuleConfig as cmc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    contractId: Int,
    moduleId: Int,
    key: String,
    value: String)(implicit session: DBSession = autoSession): ContractModuleConfig = {
    withSQL {
      insert.into(ContractModuleConfig).namedValues(
        column.contractId -> contractId,
        column.moduleId -> moduleId,
        column.key -> key,
        column.value -> value
      )
    }.update.apply()

    ContractModuleConfig(
      contractId = contractId,
      moduleId = moduleId,
      key = key,
      value = value)
  }

  def batchInsert(entities: Seq[ContractModuleConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'contractId -> entity.contractId,
        'moduleId -> entity.moduleId,
        'key -> entity.key,
        'value -> entity.value))
    SQL("""insert into contract_module_config(
      contract_id,
      module_id,
      key,
      value
    ) values (
      {contractId},
      {moduleId},
      {key},
      {value}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractModuleConfig)(implicit session: DBSession = autoSession): ContractModuleConfig = {
    withSQL {
      update(ContractModuleConfig).set(
        column.contractId -> entity.contractId,
        column.moduleId -> entity.moduleId,
        column.key -> entity.key,
        column.value -> entity.value
      ).where.eq(column.contractId, entity.contractId).and.eq(column.key, entity.key).and.eq(column.moduleId, entity.moduleId)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractModuleConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractModuleConfig).where.eq(column.contractId, entity.contractId).and.eq(column.key, entity.key).and.eq(column.moduleId, entity.moduleId) }.update.apply()
  }

}
