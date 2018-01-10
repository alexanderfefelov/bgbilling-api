package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class ModuleConfig(
  id: Int,
  mid: Option[Int] = None,
  dt: DateTime,
  title: String,
  active: Byte,
  uid: Option[Int] = None,
  config: Option[String] = None) {

  def save()(implicit session: DBSession = ModuleConfig.autoSession): ModuleConfig = ModuleConfig.save(this)(session)

  def destroy()(implicit session: DBSession = ModuleConfig.autoSession): Int = ModuleConfig.destroy(this)(session)

}


object ModuleConfig extends SQLSyntaxSupport[ModuleConfig] {

  override val tableName = "module_config"

  override val columns = Seq("id", "mid", "dt", "title", "active", "uid", "config")

  def apply(mc: SyntaxProvider[ModuleConfig])(rs: WrappedResultSet): ModuleConfig = autoConstruct(rs, mc)
  def apply(mc: ResultName[ModuleConfig])(rs: WrappedResultSet): ModuleConfig = autoConstruct(rs, mc)

  val mc = ModuleConfig.syntax("mc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ModuleConfig] = {
    withSQL {
      select.from(ModuleConfig as mc).where.eq(mc.id, id)
    }.map(ModuleConfig(mc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ModuleConfig] = {
    withSQL(select.from(ModuleConfig as mc)).map(ModuleConfig(mc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ModuleConfig as mc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ModuleConfig] = {
    withSQL {
      select.from(ModuleConfig as mc).where.append(where)
    }.map(ModuleConfig(mc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ModuleConfig] = {
    withSQL {
      select.from(ModuleConfig as mc).where.append(where)
    }.map(ModuleConfig(mc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ModuleConfig as mc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: Option[Int] = None,
    dt: DateTime,
    title: String,
    active: Byte,
    uid: Option[Int] = None,
    config: Option[String] = None)(implicit session: DBSession = autoSession): ModuleConfig = {
    val generatedKey = withSQL {
      insert.into(ModuleConfig).namedValues(
        column.mid -> mid,
        column.dt -> dt,
        column.title -> title,
        column.active -> active,
        column.uid -> uid,
        column.config -> config
      )
    }.updateAndReturnGeneratedKey.apply()

    ModuleConfig(
      id = generatedKey.toInt,
      mid = mid,
      dt = dt,
      title = title,
      active = active,
      uid = uid,
      config = config)
  }

  def batchInsert(entities: Seq[ModuleConfig])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'dt -> entity.dt,
        'title -> entity.title,
        'active -> entity.active,
        'uid -> entity.uid,
        'config -> entity.config))
    SQL("""insert into module_config(
      mid,
      dt,
      title,
      active,
      uid,
      config
    ) values (
      {mid},
      {dt},
      {title},
      {active},
      {uid},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ModuleConfig)(implicit session: DBSession = autoSession): ModuleConfig = {
    withSQL {
      update(ModuleConfig).set(
        column.id -> entity.id,
        column.mid -> entity.mid,
        column.dt -> entity.dt,
        column.title -> entity.title,
        column.active -> entity.active,
        column.uid -> entity.uid,
        column.config -> entity.config
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ModuleConfig)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ModuleConfig).where.eq(column.id, entity.id) }.update.apply()
  }

}
