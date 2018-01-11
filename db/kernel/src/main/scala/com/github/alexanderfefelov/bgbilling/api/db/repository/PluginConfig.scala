package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class PluginConfig(
  pid: Int,
  config: Option[String] = None) {

  def save()(implicit session: DBSession = PluginConfig.autoSession): PluginConfig = PluginConfig.save(this)(session)

  def destroy()(implicit session: DBSession = PluginConfig.autoSession): Int = PluginConfig.destroy(this)(session)

}


object PluginConfig extends SQLSyntaxSupport[PluginConfig] {

  override val tableName = "plugin_config"

  override val columns = Seq("pid", "config")

  def apply(pc: SyntaxProvider[PluginConfig])(rs: WrappedResultSet): PluginConfig = autoConstruct(rs, pc)
  def apply(pc: ResultName[PluginConfig])(rs: WrappedResultSet): PluginConfig = autoConstruct(rs, pc)

  val pc = PluginConfig.syntax("pc")

  override val autoSession = AutoSession

  def find(pid: Int)(implicit session: DBSession = autoSession): Option[PluginConfig] = {
    withSQL {
      select.from(PluginConfig as pc).where.eq(pc.pid, pid)
    }.map(PluginConfig(pc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[PluginConfig] = {
    withSQL(select.from(PluginConfig as pc)).map(PluginConfig(pc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(PluginConfig as pc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[PluginConfig] = {
    withSQL {
      select.from(PluginConfig as pc).where.append(where)
    }.map(PluginConfig(pc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[PluginConfig] = {
    withSQL {
      select.from(PluginConfig as pc).where.append(where)
    }.map(PluginConfig(pc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PluginConfig as pc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pid: Int,
    config: Option[String] = None)(implicit session: DBSession = autoSession): PluginConfig = {
    withSQL {
      insert.into(PluginConfig).namedValues(
        column.pid -> pid,
        column.config -> config
      )
    }.update.apply()

    PluginConfig(
      pid = pid,
      config = config)
  }

  def batchInsert(entities: Seq[PluginConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pid -> entity.pid,
        'config -> entity.config))
    SQL("""insert into plugin_config(
      pid,
      config
    ) values (
      {pid},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: PluginConfig)(implicit session: DBSession = autoSession): PluginConfig = {
    withSQL {
      update(PluginConfig).set(
        column.pid -> entity.pid,
        column.config -> entity.config
      ).where.eq(column.pid, entity.pid)
    }.update.apply()
    entity
  }

  def destroy(entity: PluginConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(PluginConfig).where.eq(column.pid, entity.pid) }.update.apply()
  }

}
