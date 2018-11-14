package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ContractParametersPref(
  id: Int,
  pt: Int,
  title: String,
  sort: Int,
  script: String,
  flags: Byte,
  lm: DateTime) {

  def save()(implicit session: DBSession = ContractParametersPref.autoSession): ContractParametersPref = ContractParametersPref.save(this)(session)

  def destroy()(implicit session: DBSession = ContractParametersPref.autoSession): Int = ContractParametersPref.destroy(this)(session)

}


object ContractParametersPref extends SQLSyntaxSupport[ContractParametersPref] {

  override val tableName = "contract_parameters_pref"

  override val columns = Seq("id", "pt", "title", "sort", "script", "flags", "lm")

  def apply(cpp: SyntaxProvider[ContractParametersPref])(rs: WrappedResultSet): ContractParametersPref = autoConstruct(rs, cpp)
  def apply(cpp: ResultName[ContractParametersPref])(rs: WrappedResultSet): ContractParametersPref = autoConstruct(rs, cpp)

  val cpp = ContractParametersPref.syntax("cpp")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ContractParametersPref] = {
    withSQL {
      select.from(ContractParametersPref as cpp).where.eq(cpp.id, id)
    }.map(ContractParametersPref(cpp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ContractParametersPref] = {
    withSQL(select.from(ContractParametersPref as cpp)).map(ContractParametersPref(cpp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ContractParametersPref as cpp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ContractParametersPref] = {
    withSQL {
      select.from(ContractParametersPref as cpp).where.append(where)
    }.map(ContractParametersPref(cpp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ContractParametersPref] = {
    withSQL {
      select.from(ContractParametersPref as cpp).where.append(where)
    }.map(ContractParametersPref(cpp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ContractParametersPref as cpp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pt: Int,
    title: String,
    sort: Int,
    script: String,
    flags: Byte,
    lm: DateTime)(implicit session: DBSession = autoSession): ContractParametersPref = {
    val generatedKey = withSQL {
      insert.into(ContractParametersPref).namedValues(
        column.pt -> pt,
        column.title -> title,
        column.sort -> sort,
        column.script -> script,
        column.flags -> flags,
        column.lm -> lm
      )
    }.updateAndReturnGeneratedKey.apply()

    ContractParametersPref(
      id = generatedKey.toInt,
      pt = pt,
      title = title,
      sort = sort,
      script = script,
      flags = flags,
      lm = lm)
  }

  def batchInsert(entities: collection.Seq[ContractParametersPref])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'pt -> entity.pt,
        'title -> entity.title,
        'sort -> entity.sort,
        'script -> entity.script,
        'flags -> entity.flags,
        'lm -> entity.lm))
    SQL("""insert into contract_parameters_pref(
      pt,
      title,
      sort,
      script,
      flags,
      lm
    ) values (
      {pt},
      {title},
      {sort},
      {script},
      {flags},
      {lm}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ContractParametersPref)(implicit session: DBSession = autoSession): ContractParametersPref = {
    withSQL {
      update(ContractParametersPref).set(
        column.id -> entity.id,
        column.pt -> entity.pt,
        column.title -> entity.title,
        column.sort -> entity.sort,
        column.script -> entity.script,
        column.flags -> entity.flags,
        column.lm -> entity.lm
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ContractParametersPref)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ContractParametersPref).where.eq(column.id, entity.id) }.update.apply()
  }

}
