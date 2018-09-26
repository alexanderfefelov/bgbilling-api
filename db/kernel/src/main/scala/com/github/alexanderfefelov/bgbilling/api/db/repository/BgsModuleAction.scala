package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class BgsModuleAction(
  module: String,
  data: String) {

  def save()(implicit session: DBSession = BgsModuleAction.autoSession): BgsModuleAction = BgsModuleAction.save(this)(session)

  def destroy()(implicit session: DBSession = BgsModuleAction.autoSession): Int = BgsModuleAction.destroy(this)(session)

}


object BgsModuleAction extends SQLSyntaxSupport[BgsModuleAction] {

  override val tableName = "bgs_module_action"

  override val columns = Seq("module", "data")

  def apply(bma: SyntaxProvider[BgsModuleAction])(rs: WrappedResultSet): BgsModuleAction = autoConstruct(rs, bma)
  def apply(bma: ResultName[BgsModuleAction])(rs: WrappedResultSet): BgsModuleAction = autoConstruct(rs, bma)

  val bma = BgsModuleAction.syntax("bma")

  override val autoSession = AutoSession

  def find(module: String)(implicit session: DBSession = autoSession): Option[BgsModuleAction] = {
    withSQL {
      select.from(BgsModuleAction as bma).where.eq(bma.module, module)
    }.map(BgsModuleAction(bma.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BgsModuleAction] = {
    withSQL(select.from(BgsModuleAction as bma)).map(BgsModuleAction(bma.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BgsModuleAction as bma)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BgsModuleAction] = {
    withSQL {
      select.from(BgsModuleAction as bma).where.append(where)
    }.map(BgsModuleAction(bma.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BgsModuleAction] = {
    withSQL {
      select.from(BgsModuleAction as bma).where.append(where)
    }.map(BgsModuleAction(bma.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BgsModuleAction as bma).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    module: String,
    data: String)(implicit session: DBSession = autoSession): BgsModuleAction = {
    withSQL {
      insert.into(BgsModuleAction).namedValues(
        column.module -> module,
        column.data -> data
      )
    }.update.apply()

    BgsModuleAction(
      module = module,
      data = data)
  }

  def batchInsert(entities: collection.Seq[BgsModuleAction])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'module -> entity.module,
        'data -> entity.data))
    SQL("""insert into bgs_module_action(
      module,
      data
    ) values (
      {module},
      {data}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BgsModuleAction)(implicit session: DBSession = autoSession): BgsModuleAction = {
    withSQL {
      update(BgsModuleAction).set(
        column.module -> entity.module,
        column.data -> entity.data
      ).where.eq(column.module, entity.module)
    }.update.apply()
    entity
  }

  def destroy(entity: BgsModuleAction)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BgsModuleAction).where.eq(column.module, entity.module) }.update.apply()
  }

}
