package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TariffTreeConfig(
  module: String,
  data: String) {

  def save()(implicit session: DBSession = TariffTreeConfig.autoSession): TariffTreeConfig = TariffTreeConfig.save(this)(session)

  def destroy()(implicit session: DBSession = TariffTreeConfig.autoSession): Int = TariffTreeConfig.destroy(this)(session)

}


object TariffTreeConfig extends SQLSyntaxSupport[TariffTreeConfig] {

  override val tableName = "tariff_tree_config"

  override val columns = Seq("module", "data")

  def apply(ttc: SyntaxProvider[TariffTreeConfig])(rs: WrappedResultSet): TariffTreeConfig = autoConstruct(rs, ttc)
  def apply(ttc: ResultName[TariffTreeConfig])(rs: WrappedResultSet): TariffTreeConfig = autoConstruct(rs, ttc)

  val ttc = TariffTreeConfig.syntax("ttc")

  override val autoSession = AutoSession

  def find(module: String)(implicit session: DBSession = autoSession): Option[TariffTreeConfig] = {
    withSQL {
      select.from(TariffTreeConfig as ttc).where.eq(ttc.module, module)
    }.map(TariffTreeConfig(ttc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffTreeConfig] = {
    withSQL(select.from(TariffTreeConfig as ttc)).map(TariffTreeConfig(ttc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffTreeConfig as ttc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffTreeConfig] = {
    withSQL {
      select.from(TariffTreeConfig as ttc).where.append(where)
    }.map(TariffTreeConfig(ttc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffTreeConfig] = {
    withSQL {
      select.from(TariffTreeConfig as ttc).where.append(where)
    }.map(TariffTreeConfig(ttc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffTreeConfig as ttc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    module: String,
    data: String)(implicit session: DBSession = autoSession): TariffTreeConfig = {
    withSQL {
      insert.into(TariffTreeConfig).namedValues(
        column.module -> module,
        column.data -> data
      )
    }.update.apply()

    TariffTreeConfig(
      module = module,
      data = data)
  }

  def batchInsert(entities: Seq[TariffTreeConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'module -> entity.module,
        'data -> entity.data))
    SQL("""insert into tariff_tree_config(
      module,
      data
    ) values (
      {module},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffTreeConfig)(implicit session: DBSession = autoSession): TariffTreeConfig = {
    withSQL {
      update(TariffTreeConfig).set(
        column.module -> entity.module,
        column.data -> entity.data
      ).where.eq(column.module, entity.module)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffTreeConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffTreeConfig).where.eq(column.module, entity.module) }.update.apply()
  }

}
