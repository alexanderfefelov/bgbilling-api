package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{LocalDate}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractTreeLink(
  id: Int,
  cid: Int,
  treeId: Int,
  title: String,
  date1: Option[LocalDate] = None,
  date2: Option[LocalDate] = None,
  pos: Byte,
  emid: Int,
  eid: Int) {

  def save()(implicit session: DBSession = ContractTreeLink.autoSession): ContractTreeLink = ContractTreeLink.save(this)(session)

  def destroy()(implicit session: DBSession = ContractTreeLink.autoSession): Int = ContractTreeLink.destroy(this)(session)

}


object ContractTreeLink extends SQLSyntaxSupport[ContractTreeLink] {

  override val tableName = "contract_tree_link"

  override val columns = Seq("id", "cid", "tree_id", "title", "date1", "date2", "pos", "emid", "eid")

  def apply(ctl: SyntaxProvider[ContractTreeLink])(rs: WrappedResultSet): ContractTreeLink = autoConstruct(rs, ctl)
  def apply(ctl: ResultName[ContractTreeLink])(rs: WrappedResultSet): ContractTreeLink = autoConstruct(rs, ctl)

  val ctl = ContractTreeLink.syntax("ctl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractTreeLink] = {
    withSQL {
      select.from(ContractTreeLink as ctl).where.eq(ctl.id, id)
    }.map(ContractTreeLink(ctl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractTreeLink] = {
    withSQL(select.from(ContractTreeLink as ctl)).map(ContractTreeLink(ctl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractTreeLink as ctl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractTreeLink] = {
    withSQL {
      select.from(ContractTreeLink as ctl).where.append(where)
    }.map(ContractTreeLink(ctl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractTreeLink] = {
    withSQL {
      select.from(ContractTreeLink as ctl).where.append(where)
    }.map(ContractTreeLink(ctl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractTreeLink as ctl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cid: Int,
    treeId: Int,
    title: String,
    date1: Option[LocalDate] = None,
    date2: Option[LocalDate] = None,
    pos: Byte,
    emid: Int,
    eid: Int)(implicit session: DBSession = autoSession): ContractTreeLink = {
    val generatedKey = withSQL {
      insert.into(ContractTreeLink).namedValues(
        column.cid -> cid,
        column.treeId -> treeId,
        column.title -> title,
        column.date1 -> date1,
        column.date2 -> date2,
        column.pos -> pos,
        column.emid -> emid,
        column.eid -> eid
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractTreeLink(
      id = generatedKey.toInt,
      cid = cid,
      treeId = treeId,
      title = title,
      date1 = date1,
      date2 = date2,
      pos = pos,
      emid = emid,
      eid = eid)
  }

  def batchInsert(entities: collection.Seq[ContractTreeLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'cid -> entity.cid,
        'treeId -> entity.treeId,
        'title -> entity.title,
        'date1 -> entity.date1,
        'date2 -> entity.date2,
        'pos -> entity.pos,
        'emid -> entity.emid,
        'eid -> entity.eid))
    SQL("""insert into contract_tree_link(
      cid,
      tree_id,
      title,
      date1,
      date2,
      pos,
      emid,
      eid
    ) values (
      {cid},
      {treeId},
      {title},
      {date1},
      {date2},
      {pos},
      {emid},
      {eid}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractTreeLink)(implicit session: DBSession = autoSession): ContractTreeLink = {
    withSQL {
      update(ContractTreeLink).set(
        column.id -> entity.id,
        column.cid -> entity.cid,
        column.treeId -> entity.treeId,
        column.title -> entity.title,
        column.date1 -> entity.date1,
        column.date2 -> entity.date2,
        column.pos -> entity.pos,
        column.emid -> entity.emid,
        column.eid -> entity.eid
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractTreeLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractTreeLink).where.eq(column.id, entity.id) }.update.apply()
  }

}
