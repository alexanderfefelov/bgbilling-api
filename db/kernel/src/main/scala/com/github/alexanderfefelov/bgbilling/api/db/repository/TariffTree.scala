package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TariffTree(
  id: Int,
  parentTree: Int) {

  def save()(implicit session: DBSession = TariffTree.autoSession): TariffTree = TariffTree.save(this)(session)

  def destroy()(implicit session: DBSession = TariffTree.autoSession): Int = TariffTree.destroy(this)(session)

}


object TariffTree extends SQLSyntaxSupport[TariffTree] {

  override val tableName = "tariff_tree"

  override val columns = Seq("id", "parent_tree")

  def apply(tt: SyntaxProvider[TariffTree])(rs: WrappedResultSet): TariffTree = autoConstruct(rs, tt)
  def apply(tt: ResultName[TariffTree])(rs: WrappedResultSet): TariffTree = autoConstruct(rs, tt)

  val tt = TariffTree.syntax("tt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffTree] = {
    withSQL {
      select.from(TariffTree as tt).where.eq(tt.id, id)
    }.map(TariffTree(tt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffTree] = {
    withSQL(select.from(TariffTree as tt)).map(TariffTree(tt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffTree as tt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffTree] = {
    withSQL {
      select.from(TariffTree as tt).where.append(where)
    }.map(TariffTree(tt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffTree] = {
    withSQL {
      select.from(TariffTree as tt).where.append(where)
    }.map(TariffTree(tt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffTree as tt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentTree: Int)(implicit session: DBSession = autoSession): TariffTree = {
    val generatedKey = withSQL {
      insert.into(TariffTree).namedValues(
        column.parentTree -> parentTree
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffTree(
      id = generatedKey.toInt,
      parentTree = parentTree)
  }

  def batchInsert(entities: collection.Seq[TariffTree])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentTree -> entity.parentTree))
    SQL("""insert into tariff_tree(
      parent_tree
    ) values (
      {parentTree}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffTree)(implicit session: DBSession = autoSession): TariffTree = {
    withSQL {
      update(TariffTree).set(
        column.id -> entity.id,
        column.parentTree -> entity.parentTree
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffTree)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffTree).where.eq(column.id, entity.id) }.update.apply()
  }

}
