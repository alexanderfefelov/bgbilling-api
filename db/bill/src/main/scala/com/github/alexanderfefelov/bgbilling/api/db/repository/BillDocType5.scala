package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class BillDocType5(
  id: Int,
  `type`: Byte,
  title: String,
  pattern: String,
  posList: String,
  opt: Byte,
  comment: String,
  setup: Option[String] = None,
  npid: Option[Int] = None,
  xmlconsist: Option[String] = None) {

  def save()(implicit session: DBSession = BillDocType5.autoSession): BillDocType5 = BillDocType5.save(this)(session)

  def destroy()(implicit session: DBSession = BillDocType5.autoSession): Int = BillDocType5.destroy(this)(session)

}


object BillDocType5 extends SQLSyntaxSupport[BillDocType5] with ApiDbConfig {

  override val tableName = s"bill_doc_type_${bgBillingModuleId("bill")}"

  override val columns = Seq("id", "type", "title", "pattern", "pos_list", "opt", "comment", "setup", "npid", "xmlConsist")

  def apply(bdt: SyntaxProvider[BillDocType5])(rs: WrappedResultSet): BillDocType5 = autoConstruct(rs, bdt)
  def apply(bdt: ResultName[BillDocType5])(rs: WrappedResultSet): BillDocType5 = autoConstruct(rs, bdt)

  val bdt = BillDocType5.syntax("bdt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[BillDocType5] = {
    withSQL {
      select.from(BillDocType5 as bdt).where.eq(bdt.id, id)
    }.map(BillDocType5(bdt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[BillDocType5] = {
    withSQL(select.from(BillDocType5 as bdt)).map(BillDocType5(bdt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(BillDocType5 as bdt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[BillDocType5] = {
    withSQL {
      select.from(BillDocType5 as bdt).where.append(where)
    }.map(BillDocType5(bdt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[BillDocType5] = {
    withSQL {
      select.from(BillDocType5 as bdt).where.append(where)
    }.map(BillDocType5(bdt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(BillDocType5 as bdt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    `type`: Byte,
    title: String,
    pattern: String,
    posList: String,
    opt: Byte,
    comment: String,
    setup: Option[String] = None,
    npid: Option[Int] = None,
    xmlconsist: Option[String] = None)(implicit session: DBSession = autoSession): BillDocType5 = {
    val generatedKey = withSQL {
      insert.into(BillDocType5).namedValues(
        column.`type` -> `type`,
        column.title -> title,
        column.pattern -> pattern,
        column.posList -> posList,
        column.opt -> opt,
        column.comment -> comment,
        column.setup -> setup,
        column.npid -> npid,
        column.xmlconsist -> xmlconsist
      )
    }.updateAndReturnGeneratedKey.apply()

    BillDocType5(
      id = generatedKey.toInt,
      `type` = `type`,
      title = title,
      pattern = pattern,
      posList = posList,
      opt = opt,
      comment = comment,
      setup = setup,
      npid = npid,
      xmlconsist = xmlconsist)
  }

  def batchInsert(entities: Seq[BillDocType5])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'type -> entity.`type`,
        'title -> entity.title,
        'pattern -> entity.pattern,
        'posList -> entity.posList,
        'opt -> entity.opt,
        'comment -> entity.comment,
        'setup -> entity.setup,
        'npid -> entity.npid,
        'xmlconsist -> entity.xmlconsist))
    SQL("""insert into bill_doc_type_5(
      type,
      title,
      pattern,
      pos_list,
      opt,
      comment,
      setup,
      npid,
      xmlConsist
    ) values (
      {type},
      {title},
      {pattern},
      {posList},
      {opt},
      {comment},
      {setup},
      {npid},
      {xmlconsist}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: BillDocType5)(implicit session: DBSession = autoSession): BillDocType5 = {
    withSQL {
      update(BillDocType5).set(
        column.id -> entity.id,
        column.`type` -> entity.`type`,
        column.title -> entity.title,
        column.pattern -> entity.pattern,
        column.posList -> entity.posList,
        column.opt -> entity.opt,
        column.comment -> entity.comment,
        column.setup -> entity.setup,
        column.npid -> entity.npid,
        column.xmlconsist -> entity.xmlconsist
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: BillDocType5)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(BillDocType5).where.eq(column.id, entity.id) }.update.apply()
  }

}
