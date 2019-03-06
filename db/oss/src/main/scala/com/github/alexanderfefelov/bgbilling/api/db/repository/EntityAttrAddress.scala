package com.github.alexanderfefelov.bgbilling.api.db.repository

import scalikejdbc._

case class EntityAttrAddress(
  entityid: Int,
  entityspecattrid: Int,
  houseid: Option[Int] = None,
  flat: Option[String] = None,
  room: String,
  pod: Option[Int] = None,
  floor: Option[Int] = None,
  value: Option[String] = None,
  comment: Option[String] = None,
  formatKey: Option[String] = None) {

  def save()(implicit session: DBSession = EntityAttrAddress.autoSession): EntityAttrAddress = EntityAttrAddress.save(this)(session)

  def destroy()(implicit session: DBSession = EntityAttrAddress.autoSession): Int = EntityAttrAddress.destroy(this)(session)

}


object EntityAttrAddress extends SQLSyntaxSupport[EntityAttrAddress] {

  override val tableName = "entity_attr_address"

  override val columns = Seq("entityId", "entitySpecAttrId", "houseId", "flat", "room", "pod", "floor", "value", "comment", "format_key")

  def apply(eaa: SyntaxProvider[EntityAttrAddress])(rs: WrappedResultSet): EntityAttrAddress = autoConstruct(rs, eaa)
  def apply(eaa: ResultName[EntityAttrAddress])(rs: WrappedResultSet): EntityAttrAddress = autoConstruct(rs, eaa)

  val eaa = EntityAttrAddress.syntax("eaa")

  override val autoSession = AutoSession

  def find(entityid: Int, entityspecattrid: Int)(implicit session: DBSession = autoSession): Option[EntityAttrAddress] = {
    sql"""select ${eaa.result.*} from ${EntityAttrAddress as eaa} where ${eaa.entityid} = ${entityid} and ${eaa.entityspecattrid} = ${entityspecattrid}"""
      .map(EntityAttrAddress(eaa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrAddress] = {
    sql"""select ${eaa.result.*} from ${EntityAttrAddress as eaa}""".map(EntityAttrAddress(eaa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrAddress.table}""".map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrAddress] = {
    sql"""select ${eaa.result.*} from ${EntityAttrAddress as eaa} where ${where}"""
      .map(EntityAttrAddress(eaa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrAddress] = {
    sql"""select ${eaa.result.*} from ${EntityAttrAddress as eaa} where ${where}"""
      .map(EntityAttrAddress(eaa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    sql"""select count(1) from ${EntityAttrAddress as eaa} where ${where}"""
      .map(_.long(1)).single.apply().get
  }

  def create(
    entityid: Int,
    entityspecattrid: Int,
    houseid: Option[Int] = None,
    flat: Option[String] = None,
    room: String,
    pod: Option[Int] = None,
    floor: Option[Int] = None,
    value: Option[String] = None,
    comment: Option[String] = None,
    formatKey: Option[String] = None)(implicit session: DBSession = autoSession): EntityAttrAddress = {
    sql"""
      insert into ${EntityAttrAddress.table} (
        ${column.entityid},
        ${column.entityspecattrid},
        ${column.houseid},
        ${column.flat},
        ${column.room},
        ${column.pod},
        ${column.floor},
        ${column.value},
        ${column.comment},
        ${column.formatKey}
      ) values (
        ${entityid},
        ${entityspecattrid},
        ${houseid},
        ${flat},
        ${room},
        ${pod},
        ${floor},
        ${value},
        ${comment},
        ${formatKey}
      )
      """.update.apply()

    EntityAttrAddress(
      entityid = entityid,
      entityspecattrid = entityspecattrid,
      houseid = houseid,
      flat = flat,
      room = room,
      pod = pod,
      floor = floor,
      value = value,
      comment = comment,
      formatKey = formatKey)
  }

  def batchInsert(entities: collection.Seq[EntityAttrAddress])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'entityid -> entity.entityid,
        'entityspecattrid -> entity.entityspecattrid,
        'houseid -> entity.houseid,
        'flat -> entity.flat,
        'room -> entity.room,
        'pod -> entity.pod,
        'floor -> entity.floor,
        'value -> entity.value,
        'comment -> entity.comment,
        'formatKey -> entity.formatKey))
    SQL("""insert into entity_attr_address(
      entityId,
      entitySpecAttrId,
      houseId,
      flat,
      room,
      pod,
      floor,
      value,
      comment,
      format_key
    ) values (
      {entityid},
      {entityspecattrid},
      {houseid},
      {flat},
      {room},
      {pod},
      {floor},
      {value},
      {comment},
      {formatKey}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: EntityAttrAddress)(implicit session: DBSession = autoSession): EntityAttrAddress = {
    sql"""
      update
        ${EntityAttrAddress.table}
      set
        ${column.entityid} = ${entity.entityid},
        ${column.entityspecattrid} = ${entity.entityspecattrid},
        ${column.houseid} = ${entity.houseid},
        ${column.flat} = ${entity.flat},
        ${column.room} = ${entity.room},
        ${column.pod} = ${entity.pod},
        ${column.floor} = ${entity.floor},
        ${column.value} = ${entity.value},
        ${column.comment} = ${entity.comment},
        ${column.formatKey} = ${entity.formatKey}
      where
        ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}
      """.update.apply()
    entity
  }

  def destroy(entity: EntityAttrAddress)(implicit session: DBSession = autoSession): Int = {
    sql"""delete from ${EntityAttrAddress.table} where ${column.entityid} = ${entity.entityid} and ${column.entityspecattrid} = ${entity.entityspecattrid}""".update.apply()
  }

}
