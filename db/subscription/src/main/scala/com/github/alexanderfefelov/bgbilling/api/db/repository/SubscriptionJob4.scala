package com.github.alexanderfefelov.bgbilling.api.db.repository

import com.github.alexanderfefelov.bgbilling.api.db.util._
import scalikejdbc._

case class SubscriptionJob4(
  id: Long,
  group: Option[String] = None,
  name: Option[String] = None,
  classname: String,
  persistent: Byte,
  concurrent: Byte,
  params: Option[String] = None,
  version: Long) {

  def save()(implicit session: DBSession = SubscriptionJob4.autoSession): SubscriptionJob4 = SubscriptionJob4.save(this)(session)

  def destroy()(implicit session: DBSession = SubscriptionJob4.autoSession): Int = SubscriptionJob4.destroy(this)(session)

}


object SubscriptionJob4 extends SQLSyntaxSupport[SubscriptionJob4] with ApiDbConfig {

  override val tableName = s"subscription_job_${bgBillingModuleId("subscription")}"

  override val columns = Seq("id", "group", "name", "className", "persistent", "concurrent", "params", "version")

  def apply(sj: SyntaxProvider[SubscriptionJob4])(rs: WrappedResultSet): SubscriptionJob4 = autoConstruct(rs, sj)
  def apply(sj: ResultName[SubscriptionJob4])(rs: WrappedResultSet): SubscriptionJob4 = autoConstruct(rs, sj)

  val sj = SubscriptionJob4.syntax("sj")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[SubscriptionJob4] = {
    withSQL {
      select.from(SubscriptionJob4 as sj).where.eq(sj.id, id)
    }.map(SubscriptionJob4(sj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SubscriptionJob4] = {
    withSQL(select.from(SubscriptionJob4 as sj)).map(SubscriptionJob4(sj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SubscriptionJob4 as sj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SubscriptionJob4] = {
    withSQL {
      select.from(SubscriptionJob4 as sj).where.append(where)
    }.map(SubscriptionJob4(sj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SubscriptionJob4] = {
    withSQL {
      select.from(SubscriptionJob4 as sj).where.append(where)
    }.map(SubscriptionJob4(sj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SubscriptionJob4 as sj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    group: Option[String] = None,
    name: Option[String] = None,
    classname: String,
    persistent: Byte,
    concurrent: Byte,
    params: Option[String] = None,
    version: Long)(implicit session: DBSession = autoSession): SubscriptionJob4 = {
    val generatedKey = withSQL {
      insert.into(SubscriptionJob4).namedValues(
        column.group -> group,
        column.name -> name,
        column.classname -> classname,
        column.persistent -> persistent,
        column.concurrent -> concurrent,
        column.params -> params,
        column.version -> version
      )
    }.updateAndReturnGeneratedKey.apply()

    SubscriptionJob4(
      id = generatedKey,
      group = group,
      name = name,
      classname = classname,
      persistent = persistent,
      concurrent = concurrent,
      params = params,
      version = version)
  }

  def batchInsert(entities: Seq[SubscriptionJob4])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'group -> entity.group,
        'name -> entity.name,
        'classname -> entity.classname,
        'persistent -> entity.persistent,
        'concurrent -> entity.concurrent,
        'params -> entity.params,
        'version -> entity.version))
    SQL("""insert into subscription_job_4(
      group,
      name,
      className,
      persistent,
      concurrent,
      params,
      version
    ) values (
      {group},
      {name},
      {classname},
      {persistent},
      {concurrent},
      {params},
      {version}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: SubscriptionJob4)(implicit session: DBSession = autoSession): SubscriptionJob4 = {
    withSQL {
      update(SubscriptionJob4).set(
        column.id -> entity.id,
        column.group -> entity.group,
        column.name -> entity.name,
        column.classname -> entity.classname,
        column.persistent -> entity.persistent,
        column.concurrent -> entity.concurrent,
        column.params -> entity.params,
        column.version -> entity.version
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: SubscriptionJob4)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(SubscriptionJob4).where.eq(column.id, entity.id) }.update.apply()
  }

}
