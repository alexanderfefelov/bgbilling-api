package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class TariffLabelLink(
  tariffId: Int,
  labelId: Int) {

  def save()(implicit session: DBSession = TariffLabelLink.autoSession): TariffLabelLink = TariffLabelLink.save(this)(session)

  def destroy()(implicit session: DBSession = TariffLabelLink.autoSession): Int = TariffLabelLink.destroy(this)(session)

}


object TariffLabelLink extends SQLSyntaxSupport[TariffLabelLink] {

  override val tableName = "tariff_label_link"

  override val columns = Seq("tariff_id", "label_id")

  def apply(tll: SyntaxProvider[TariffLabelLink])(rs: WrappedResultSet): TariffLabelLink = autoConstruct(rs, tll)
  def apply(tll: ResultName[TariffLabelLink])(rs: WrappedResultSet): TariffLabelLink = autoConstruct(rs, tll)

  val tll = TariffLabelLink.syntax("tll")

  override val autoSession = AutoSession

  def find(tariffId: Int, labelId: Int)(implicit session: DBSession = autoSession): Option[TariffLabelLink] = {
    withSQL {
      select.from(TariffLabelLink as tll).where.eq(tll.tariffId, tariffId).and.eq(tll.labelId, labelId)
    }.map(TariffLabelLink(tll.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TariffLabelLink] = {
    withSQL(select.from(TariffLabelLink as tll)).map(TariffLabelLink(tll.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TariffLabelLink as tll)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TariffLabelLink] = {
    withSQL {
      select.from(TariffLabelLink as tll).where.append(where)
    }.map(TariffLabelLink(tll.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TariffLabelLink] = {
    withSQL {
      select.from(TariffLabelLink as tll).where.append(where)
    }.map(TariffLabelLink(tll.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TariffLabelLink as tll).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    tariffId: Int,
    labelId: Int)(implicit session: DBSession = autoSession): TariffLabelLink = {
    withSQL {
      insert.into(TariffLabelLink).namedValues(
        column.tariffId -> tariffId,
        column.labelId -> labelId
      )
    }.update.apply()

    TariffLabelLink(
      tariffId = tariffId,
      labelId = labelId)
  }

  def batchInsert(entities: collection.Seq[TariffLabelLink])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tariffId -> entity.tariffId,
        'labelId -> entity.labelId))
    SQL("""insert into tariff_label_link(
      tariff_id,
      label_id
    ) values (
      {tariffId},
      {labelId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TariffLabelLink)(implicit session: DBSession = autoSession): TariffLabelLink = {
    withSQL {
      update(TariffLabelLink).set(
        column.tariffId -> entity.tariffId,
        column.labelId -> entity.labelId
      ).where.eq(column.tariffId, entity.tariffId).and.eq(column.labelId, entity.labelId)
    }.update.apply()
    entity
  }

  def destroy(entity: TariffLabelLink)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TariffLabelLink).where.eq(column.tariffId, entity.tariffId).and.eq(column.labelId, entity.labelId) }.update.apply()
  }

}
