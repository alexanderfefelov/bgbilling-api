package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ContractGroup(
  id: Byte,
  title: String,
  enable: Byte,
  editable: Byte,
  comment: String) {

  def save()(implicit session: DBSession = ContractGroup.autoSession): ContractGroup = ContractGroup.save(this)(session)

  def destroy()(implicit session: DBSession = ContractGroup.autoSession): Int = ContractGroup.destroy(this)(session)

}


object ContractGroup extends SQLSyntaxSupport[ContractGroup] {

  override val tableName = "contract_group"

  override val columns = Seq("id", "title", "enable", "editable", "comment")

  def apply(cg: SyntaxProvider[ContractGroup])(rs: WrappedResultSet): ContractGroup = autoConstruct(rs, cg)
  def apply(cg: ResultName[ContractGroup])(rs: WrappedResultSet): ContractGroup = autoConstruct(rs, cg)

  val cg = ContractGroup.syntax("cg")

  override val autoSession = AutoSession

  def find(id: Byte)(implicit session: DBSession = autoSession): Option[ContractGroup] = {
    withSQL {
      select.from(ContractGroup as cg).where.eq(cg.id, id)
    }.map(ContractGroup(cg.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractGroup] = {
    withSQL(select.from(ContractGroup as cg)).map(ContractGroup(cg.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractGroup as cg)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractGroup] = {
    withSQL {
      select.from(ContractGroup as cg).where.append(where)
    }.map(ContractGroup(cg.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractGroup] = {
    withSQL {
      select.from(ContractGroup as cg).where.append(where)
    }.map(ContractGroup(cg.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractGroup as cg).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Byte,
    title: String,
    enable: Byte,
    editable: Byte,
    comment: String)(implicit session: DBSession = autoSession): ContractGroup = {
    withSQL {
      insert.into(ContractGroup).namedValues(
        column.id -> id,
        column.title -> title,
        column.enable -> enable,
        column.editable -> editable,
        column.comment -> comment
      )
    }.update.apply()

    ContractGroup(
      id = id,
      title = title,
      enable = enable,
      editable = editable,
      comment = comment)
  }

  def batchInsert(entities: collection.Seq[ContractGroup])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'title -> entity.title,
        'enable -> entity.enable,
        'editable -> entity.editable,
        'comment -> entity.comment))
    SQL("""insert into contract_group(
      id,
      title,
      enable,
      editable,
      comment
    ) values (
      {id},
      {title},
      {enable},
      {editable},
      {comment}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractGroup)(implicit session: DBSession = autoSession): ContractGroup = {
    withSQL {
      update(ContractGroup).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.enable -> entity.enable,
        column.editable -> entity.editable,
        column.comment -> entity.comment
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractGroup)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractGroup).where.eq(column.id, entity.id) }.update.apply()
  }

}
