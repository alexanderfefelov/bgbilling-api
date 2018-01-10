package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class ScriptClassesIfaces(
  name: String,
  iface: String) {

  def save()(implicit session: DBSession = ScriptClassesIfaces.autoSession): ScriptClassesIfaces = ScriptClassesIfaces.save(this)(session)

  def destroy()(implicit session: DBSession = ScriptClassesIfaces.autoSession): Int = ScriptClassesIfaces.destroy(this)(session)

}


object ScriptClassesIfaces extends SQLSyntaxSupport[ScriptClassesIfaces] {

  override val tableName = "script_classes_ifaces"

  override val columns = Seq("name", "iface")

  def apply(sci: SyntaxProvider[ScriptClassesIfaces])(rs: WrappedResultSet): ScriptClassesIfaces = autoConstruct(rs, sci)
  def apply(sci: ResultName[ScriptClassesIfaces])(rs: WrappedResultSet): ScriptClassesIfaces = autoConstruct(rs, sci)

  val sci = ScriptClassesIfaces.syntax("sci")

  override val autoSession = AutoSession

  def find(iface: String, name: String)(implicit session: DBSession = autoSession): Option[ScriptClassesIfaces] = {
    withSQL {
      select.from(ScriptClassesIfaces as sci).where.eq(sci.iface, iface).and.eq(sci.name, name)
    }.map(ScriptClassesIfaces(sci.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ScriptClassesIfaces] = {
    withSQL(select.from(ScriptClassesIfaces as sci)).map(ScriptClassesIfaces(sci.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ScriptClassesIfaces as sci)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ScriptClassesIfaces] = {
    withSQL {
      select.from(ScriptClassesIfaces as sci).where.append(where)
    }.map(ScriptClassesIfaces(sci.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ScriptClassesIfaces] = {
    withSQL {
      select.from(ScriptClassesIfaces as sci).where.append(where)
    }.map(ScriptClassesIfaces(sci.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ScriptClassesIfaces as sci).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    iface: String)(implicit session: DBSession = autoSession): ScriptClassesIfaces = {
    withSQL {
      insert.into(ScriptClassesIfaces).namedValues(
        column.name -> name,
        column.iface -> iface
      )
    }.update.apply()

    ScriptClassesIfaces(
      name = name,
      iface = iface)
  }

  def batchInsert(entities: Seq[ScriptClassesIfaces])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'iface -> entity.iface))
    SQL("""insert into script_classes_ifaces(
      name,
      iface
    ) values (
      {name},
      {iface}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ScriptClassesIfaces)(implicit session: DBSession = autoSession): ScriptClassesIfaces = {
    withSQL {
      update(ScriptClassesIfaces).set(
        column.name -> entity.name,
        column.iface -> entity.iface
      ).where.eq(column.iface, entity.iface).and.eq(column.name, entity.name)
    }.update.apply()
    entity
  }

  def destroy(entity: ScriptClassesIfaces)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ScriptClassesIfaces).where.eq(column.iface, entity.iface).and.eq(column.name, entity.name) }.update.apply()
  }

}
