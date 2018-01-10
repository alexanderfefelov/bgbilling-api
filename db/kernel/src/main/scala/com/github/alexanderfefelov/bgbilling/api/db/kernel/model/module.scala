package com.github.alexanderfefelov.bgbilling.api.db.kernel.model

import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet

case class Module (

  id: Long,
  title: String,
  name: String,
  configId: Option[Long]

)

object Module {

  val * = (rs: WrappedResultSet) =>
    Module(
      rs.long("id"),
      rs.string("title"),
      rs.string("name"),
      rs.longOpt("config_id")
    )

}

case class ModuleConfig (

  id: Long,
  mid: Option[Int],
  dt: DateTime,
  title: String,
  active: Boolean,
  uid: Option[Long],
  config: Option[String]

)

object ModuleConfig {

  val * = (rs: WrappedResultSet) =>
    ModuleConfig(
      rs.long("id"),
      rs.intOpt("mid"),
      rs.jodaDateTime("dt"),
      rs.string("title"),
      rs.int("active") != 0,
      rs.longOpt("uid"),
      rs.stringOpt("config")
    )

}

case class ModuleTariffTree (

  id: Long,
  mid: Long,
  treeId: Long,
  parentTree: Long,
  lm: DateTime

)

object ModuleTariffTree {

  val * = (rs: WrappedResultSet) =>
    ModuleTariffTree(
      rs.long("id"),
      rs.long("mid"),
      rs.long("tree_id"),
      rs.long("parent_tree"),
      new DateTime(rs.long("lm"))
    )

}

case class MTreeNode (

  id: Long,
  parentNode: Long,
  mTreeId: Long,
  typ: String,
  data: String,
  pos: Int

)

object MTreeNode {

  val * = (rs: WrappedResultSet) =>
    MTreeNode(
      rs.long("id"),
      rs.long("parent_node"),
      rs.long("mtree_id"),
      rs.string("type"),
      rs.string("data"),
      rs.int("pos")
    )

}
