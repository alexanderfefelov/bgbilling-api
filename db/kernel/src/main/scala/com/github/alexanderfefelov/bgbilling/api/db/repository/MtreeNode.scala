package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class MtreeNode(
  id: Int,
  parentNode: Int,
  mtreeId: Int,
  `type`: String,
  data: String,
  pos: Int) {

  def save()(implicit session: DBSession = MtreeNode.autoSession): MtreeNode = MtreeNode.save(this)(session)

  def destroy()(implicit session: DBSession = MtreeNode.autoSession): Int = MtreeNode.destroy(this)(session)

}


object MtreeNode extends SQLSyntaxSupport[MtreeNode] {

  override val tableName = "mtree_node"

  override val columns = Seq("id", "parent_node", "mtree_id", "type", "data", "pos")

  def apply(mn: SyntaxProvider[MtreeNode])(rs: WrappedResultSet): MtreeNode = apply(mn.resultName)(rs)
  def apply(mn: ResultName[MtreeNode])(rs: WrappedResultSet): MtreeNode = new MtreeNode(
    id = rs.get(mn.id),
    parentNode = rs.get(mn.parentNode),
    mtreeId = rs.get(mn.mtreeId),
    `type` = rs.get(mn.`type`),
    data = rs.get(mn.data),
    pos = rs.get(mn.pos)
  )

  val mn = MtreeNode.syntax("mn")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MtreeNode] = {
    withSQL {
      select.from(MtreeNode as mn).where.eq(mn.id, id)
    }.map(MtreeNode(mn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[MtreeNode] = {
    withSQL(select.from(MtreeNode as mn)).map(MtreeNode(mn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(MtreeNode as mn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[MtreeNode] = {
    withSQL {
      select.from(MtreeNode as mn).where.append(where)
    }.map(MtreeNode(mn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MtreeNode] = {
    withSQL {
      select.from(MtreeNode as mn).where.append(where)
    }.map(MtreeNode(mn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(MtreeNode as mn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    parentNode: Int,
    mtreeId: Int,
    `type`: String,
    data: String,
    pos: Int)(implicit session: DBSession = autoSession): MtreeNode = {
    val generatedKey = withSQL {
      insert.into(MtreeNode).namedValues(
        column.parentNode -> parentNode,
        column.mtreeId -> mtreeId,
        column.`type` -> `type`,
        column.data -> data,
        column.pos -> pos
      )
    }.updateAndReturnGeneratedKey.apply()

    MtreeNode(
      id = generatedKey.toInt,
      parentNode = parentNode,
      mtreeId = mtreeId,
      `type` = `type`,
      data = data,
      pos = pos)
  }

  def batchInsert(entities: Seq[MtreeNode])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'parentNode -> entity.parentNode,
        'mtreeId -> entity.mtreeId,
        'type -> entity.`type`,
        'data -> entity.data,
        'pos -> entity.pos))
    SQL("""insert into mtree_node(
      parent_node,
      mtree_id,
      type,
      data,
      pos
    ) values (
      {parentNode},
      {mtreeId},
      {type},
      {data},
      {pos}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: MtreeNode)(implicit session: DBSession = autoSession): MtreeNode = {
    withSQL {
      update(MtreeNode).set(
        column.id -> entity.id,
        column.parentNode -> entity.parentNode,
        column.mtreeId -> entity.mtreeId,
        column.`type` -> entity.`type`,
        column.data -> entity.data,
        column.pos -> entity.pos
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: MtreeNode)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(MtreeNode).where.eq(column.id, entity.id) }.update.apply()
  }

}
