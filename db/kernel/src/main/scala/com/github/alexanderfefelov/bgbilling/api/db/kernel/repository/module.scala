package com.github.alexanderfefelov.bgbilling.api.db.kernel.repository

import com.github.alexanderfefelov.bgbilling.api.db.kernel.model._
import scalikejdbc._

object ModuleRepository {

  def findAll: List[Module] = {
    DB readOnly { implicit session =>
      sql"select * from module".map(Module.*).list().apply()
    }
  }

}

object ModuleConfigRepository {

  def findAll: List[ModuleConfig] = {
    DB readOnly { implicit session =>
      sql"select * from module_config".map(ModuleConfig.*).list().apply()
    }
  }

}

object ModuleTariffTreeRepository {

  def findAll: List[ModuleTariffTree] = {
    DB readOnly { implicit session =>
      sql"select * from module_tariff_tree".map(ModuleTariffTree.*).list().apply()
    }
  }

}

object MTreeNodeRepository {

  def findAll: List[MTreeNode] = {
    DB readOnly { implicit session =>
      sql"select * from mtree_node".map(MTreeNode.*).list().apply()
    }
  }

}
