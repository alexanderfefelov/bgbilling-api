package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ModuleTariffTree(
  id: Int,
  mid: Int,
  treeId: Int,
  parentTree: Int,
  lm: Long) {

  def save()(implicit session: DBSession = ModuleTariffTree.autoSession): ModuleTariffTree = ModuleTariffTree.save(this)(session)

  def destroy()(implicit session: DBSession = ModuleTariffTree.autoSession): Int = ModuleTariffTree.destroy(this)(session)

}


object ModuleTariffTree extends SQLSyntaxSupport[ModuleTariffTree] {

  override val tableName = "module_tariff_tree"

  override val columns = Seq("id", "mid", "tree_id", "parent_tree", "lm")

  def apply(mtt: SyntaxProvider[ModuleTariffTree])(rs: WrappedResultSet): ModuleTariffTree = autoConstruct(rs, mtt)
  def apply(mtt: ResultName[ModuleTariffTree])(rs: WrappedResultSet): ModuleTariffTree = autoConstruct(rs, mtt)

  val mtt = ModuleTariffTree.syntax("mtt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ModuleTariffTree] = {
    withSQL {
      select.from(ModuleTariffTree as mtt).where.eq(mtt.id, id)
    }.map(ModuleTariffTree(mtt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ModuleTariffTree] = {
    withSQL(select.from(ModuleTariffTree as mtt)).map(ModuleTariffTree(mtt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ModuleTariffTree as mtt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ModuleTariffTree] = {
    withSQL {
      select.from(ModuleTariffTree as mtt).where.append(where)
    }.map(ModuleTariffTree(mtt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ModuleTariffTree] = {
    withSQL {
      select.from(ModuleTariffTree as mtt).where.append(where)
    }.map(ModuleTariffTree(mtt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ModuleTariffTree as mtt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    mid: Int,
    treeId: Int,
    parentTree: Int,
    lm: Long)(implicit session: DBSession = autoSession): ModuleTariffTree = {
    val generatedKey = withSQL {
      insert.into(ModuleTariffTree).namedValues(
        column.mid -> mid,
        column.treeId -> treeId,
        column.parentTree -> parentTree,
        column.lm -> lm
      )
    }.updateAndReturnGeneratedKey.apply()

    ModuleTariffTree(
      id = generatedKey.toInt,
      mid = mid,
      treeId = treeId,
      parentTree = parentTree,
      lm = lm)
  }

  def batchInsert(entities: collection.Seq[ModuleTariffTree])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'mid -> entity.mid,
        'treeId -> entity.treeId,
        'parentTree -> entity.parentTree,
        'lm -> entity.lm))
    SQL("""insert into module_tariff_tree(
      mid,
      tree_id,
      parent_tree,
      lm
    ) values (
      {mid},
      {treeId},
      {parentTree},
      {lm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ModuleTariffTree)(implicit session: DBSession = autoSession): ModuleTariffTree = {
    withSQL {
      update(ModuleTariffTree).set(
        column.id -> entity.id,
        column.mid -> entity.mid,
        column.treeId -> entity.treeId,
        column.parentTree -> entity.parentTree,
        column.lm -> entity.lm
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ModuleTariffTree)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ModuleTariffTree).where.eq(column.id, entity.id) }.update.apply()
  }

}
