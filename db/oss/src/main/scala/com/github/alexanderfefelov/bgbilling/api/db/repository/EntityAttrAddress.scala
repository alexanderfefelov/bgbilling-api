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
    withSQL {
      select.from(EntityAttrAddress as eaa).where.eq(eaa.entityid, entityid).and.eq(eaa.entityspecattrid, entityspecattrid)
    }.map(EntityAttrAddress(eaa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[EntityAttrAddress] = {
    withSQL(select.from(EntityAttrAddress as eaa)).map(EntityAttrAddress(eaa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(EntityAttrAddress as eaa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[EntityAttrAddress] = {
    withSQL {
      select.from(EntityAttrAddress as eaa).where.append(where)
    }.map(EntityAttrAddress(eaa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[EntityAttrAddress] = {
    withSQL {
      select.from(EntityAttrAddress as eaa).where.append(where)
    }.map(EntityAttrAddress(eaa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(EntityAttrAddress as eaa).where.append(where)
    }.map(_.long(1)).single.apply().get
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
    withSQL {
      insert.into(EntityAttrAddress).namedValues(
        column.entityid -> entityid,
        column.entityspecattrid -> entityspecattrid,
        column.houseid -> houseid,
        column.flat -> flat,
        column.room -> room,
        column.pod -> pod,
        column.floor -> floor,
        column.value -> value,
        column.comment -> comment,
        column.formatKey -> formatKey
      )
    }.update.apply()

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

  def batchInsert(entities: Seq[EntityAttrAddress])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
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
    withSQL {
      update(EntityAttrAddress).set(
        column.entityid -> entity.entityid,
        column.entityspecattrid -> entity.entityspecattrid,
        column.houseid -> entity.houseid,
        column.flat -> entity.flat,
        column.room -> entity.room,
        column.pod -> entity.pod,
        column.floor -> entity.floor,
        column.value -> entity.value,
        column.comment -> entity.comment,
        column.formatKey -> entity.formatKey
      ).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid)
    }.update.apply()
    entity
  }

  def destroy(entity: EntityAttrAddress)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(EntityAttrAddress).where.eq(column.entityid, entity.entityid).and.eq(column.entityspecattrid, entity.entityspecattrid) }.update.apply()
  }

}
