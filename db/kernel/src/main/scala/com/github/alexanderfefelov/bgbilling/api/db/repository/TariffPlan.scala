package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}

case class TariffPlan(
  id: Int,
  title: String,
  titleWeb: Option[String] = None,
  lm: DateTime,
  actual: Int,
  gr: Long,
  pattern: Option[String] = None,
  face: Option[Byte] = None,
  treeId: Int,
  config: Option[String] = None) {

  def save()(implicit session: DBSession = TariffPlan.autoSession): TariffPlan = TariffPlan.save(this)(session)

  def destroy()(implicit session: DBSession = TariffPlan.autoSession): Int = TariffPlan.destroy(this)(session)

}


object TariffPlan extends SQLSyntaxSupport[TariffPlan] {

  override val tableName = "tariff_plan"

  override val columns = Seq("id", "title", "title_web", "lm", "actual", "gr", "pattern", "face", "tree_id", "config")

  def apply(tp: SyntaxProvider[TariffPlan])(rs: WrappedResultSet): TariffPlan = autoConstruct(rs, tp)
  def apply(tp: ResultName[TariffPlan])(rs: WrappedResultSet): TariffPlan = autoConstruct(rs, tp)

  val tp = TariffPlan.syntax("tp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TariffPlan] = {
    withSQL {
      select.from(TariffPlan as tp).where.eq(tp.id, id)
    }.map(TariffPlan(tp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffPlan] = {
    withSQL(select.from(TariffPlan as tp)).map(TariffPlan(tp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffPlan as tp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffPlan] = {
    withSQL {
      select.from(TariffPlan as tp).where.append(where)
    }.map(TariffPlan(tp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffPlan] = {
    withSQL {
      select.from(TariffPlan as tp).where.append(where)
    }.map(TariffPlan(tp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffPlan as tp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    title: String,
    titleWeb: Option[String] = None,
    lm: DateTime,
    actual: Int,
    gr: Long,
    pattern: Option[String] = None,
    face: Option[Byte] = None,
    treeId: Int,
    config: Option[String] = None)(implicit session: DBSession = autoSession): TariffPlan = {
    val generatedKey = withSQL {
      insert.into(TariffPlan).namedValues(
        column.title -> title,
        column.titleWeb -> titleWeb,
        column.lm -> lm,
        column.actual -> actual,
        column.gr -> gr,
        column.pattern -> pattern,
        column.face -> face,
        column.treeId -> treeId,
        column.config -> config
      )
    }.updateAndReturnGeneratedKey.apply()

    TariffPlan(
      id = generatedKey.toInt,
      title = title,
      titleWeb = titleWeb,
      lm = lm,
      actual = actual,
      gr = gr,
      pattern = pattern,
      face = face,
      treeId = treeId,
      config = config)
  }

  def batchInsert(entities: Seq[TariffPlan])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'title -> entity.title,
        'titleWeb -> entity.titleWeb,
        'lm -> entity.lm,
        'actual -> entity.actual,
        'gr -> entity.gr,
        'pattern -> entity.pattern,
        'face -> entity.face,
        'treeId -> entity.treeId,
        'config -> entity.config))
    SQL("""insert into tariff_plan(
      title,
      title_web,
      lm,
      actual,
      gr,
      pattern,
      face,
      tree_id,
      config
    ) values (
      {title},
      {titleWeb},
      {lm},
      {actual},
      {gr},
      {pattern},
      {face},
      {treeId},
      {config}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffPlan)(implicit session: DBSession = autoSession): TariffPlan = {
    withSQL {
      update(TariffPlan).set(
        column.id -> entity.id,
        column.title -> entity.title,
        column.titleWeb -> entity.titleWeb,
        column.lm -> entity.lm,
        column.actual -> entity.actual,
        column.gr -> entity.gr,
        column.pattern -> entity.pattern,
        column.face -> entity.face,
        column.treeId -> entity.treeId,
        column.config -> entity.config
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffPlan)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffPlan).where.eq(column.id, entity.id) }.update.apply()
  }

}
