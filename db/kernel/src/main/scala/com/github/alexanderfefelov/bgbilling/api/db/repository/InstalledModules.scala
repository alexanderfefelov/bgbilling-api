package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class InstalledModules(
  name: Option[String] = None,
  title: Option[String] = None,
  version: Option[String] = None,
  packServer: Option[String] = None,
  packClient: Option[String] = None,
  `type`: String,
  clientZip: Array[Byte],
  init: String,
  id: Int,
  enabled: Boolean,
  uninstall: Option[String] = None) {

  def save()(implicit session: DBSession = InstalledModules.autoSession): InstalledModules = InstalledModules.save(this)(session)

  def destroy()(implicit session: DBSession = InstalledModules.autoSession): Int = InstalledModules.destroy(this)(session)

}


object InstalledModules extends SQLSyntaxSupport[InstalledModules] {

  override val tableName = "installed_modules"

  override val columns = Seq("name", "title", "version", "pack_server", "pack_client", "type", "client_zip", "init", "id", "enabled", "uninstall")

  def apply(im: SyntaxProvider[InstalledModules])(rs: WrappedResultSet): InstalledModules = autoConstruct(rs, im)
  def apply(im: ResultName[InstalledModules])(rs: WrappedResultSet): InstalledModules = autoConstruct(rs, im)

  val im = InstalledModules.syntax("im")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[InstalledModules] = {
    withSQL {
      select.from(InstalledModules as im).where.eq(im.id, id)
    }.map(InstalledModules(im.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[InstalledModules] = {
    withSQL(select.from(InstalledModules as im)).map(InstalledModules(im.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(InstalledModules as im)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[InstalledModules] = {
    withSQL {
      select.from(InstalledModules as im).where.append(where)
    }.map(InstalledModules(im.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[InstalledModules] = {
    withSQL {
      select.from(InstalledModules as im).where.append(where)
    }.map(InstalledModules(im.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(InstalledModules as im).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    title: Option[String] = None,
    version: Option[String] = None,
    packServer: Option[String] = None,
    packClient: Option[String] = None,
    `type`: String,
    clientZip: Array[Byte],
    init: String,
    enabled: Boolean,
    uninstall: Option[String] = None)(implicit session: DBSession = autoSession): InstalledModules = {
    val generatedKey = withSQL {
      insert.into(InstalledModules).namedValues(
        column.name -> name,
        column.title -> title,
        column.version -> version,
        column.packServer -> packServer,
        column.packClient -> packClient,
        column.`type` -> `type`,
        column.clientZip -> clientZip,
        column.init -> init,
        column.enabled -> enabled,
        column.uninstall -> uninstall
      )
    }.updateAndReturnGeneratedKey.apply()

    InstalledModules(
      id = generatedKey.toInt,
      name = name,
      title = title,
      version = version,
      packServer = packServer,
      packClient = packClient,
      `type` = `type`,
      clientZip = clientZip,
      init = init,
      enabled = enabled,
      uninstall = uninstall)
  }

  def batchInsert(entities: Seq[InstalledModules])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'title -> entity.title,
        'version -> entity.version,
        'packServer -> entity.packServer,
        'packClient -> entity.packClient,
        'type -> entity.`type`,
        'clientZip -> entity.clientZip,
        'init -> entity.init,
        'enabled -> entity.enabled,
        'uninstall -> entity.uninstall))
    SQL("""insert into installed_modules(
      name,
      title,
      version,
      pack_server,
      pack_client,
      type,
      client_zip,
      init,
      enabled,
      uninstall
    ) values (
      {name},
      {title},
      {version},
      {packServer},
      {packClient},
      {type},
      {clientZip},
      {init},
      {enabled},
      {uninstall}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: InstalledModules)(implicit session: DBSession = autoSession): InstalledModules = {
    withSQL {
      update(InstalledModules).set(
        column.name -> entity.name,
        column.title -> entity.title,
        column.version -> entity.version,
        column.packServer -> entity.packServer,
        column.packClient -> entity.packClient,
        column.`type` -> entity.`type`,
        column.clientZip -> entity.clientZip,
        column.init -> entity.init,
        column.id -> entity.id,
        column.enabled -> entity.enabled,
        column.uninstall -> entity.uninstall
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: InstalledModules)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(InstalledModules).where.eq(column.id, entity.id) }.update.apply()
  }

}
