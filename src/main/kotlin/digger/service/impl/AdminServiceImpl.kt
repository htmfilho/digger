/*
 * Digger
 * Copyright (C) 2019-2022 Hildeberto Mendonca
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A full copy of the GNU General Public License is available at:
 * https://github.com/htmfilho/digger/blob/master/LICENSE
 */
package digger.service.impl

import digger.service.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Service
class AdminServiceImpl(
    private val columnService: ColumnService, 
    private val datasourceService: DatasourceService,
    private val ignoredTableService: IgnoredTableService, 
    private val roleService: RoleService,
    private val tableService: TableService, 
    private val userService: UserService
) : AdminService {
    @PersistenceContext
    private val entityManager: EntityManager? = null
    
    override fun isDatabaseEmpty(): Boolean {
        val total = (userService.countAll()
                + roleService.countAll()
                + datasourceService.countAll()
                + tableService.countAll()
                + ignoredTableService.countAll()
                + columnService.countAll())
        return total == 0L
    }

    override fun exportToSql(): List<String> {
        // This sequence represents the dependencies between database tables.
        val sqlStatements = userService.exportToSql()
        sqlStatements.addAll(roleService.exportToSql())
        sqlStatements.addAll(datasourceService.exportToSql())
        sqlStatements.addAll(tableService.exportToSql())
        sqlStatements.addAll(ignoredTableService.exportToSql())
        sqlStatements.addAll(columnService.exportToSql())
        return sqlStatements
    }

    @Transactional
    override fun restoreBackup(inputStream: InputStream) {
        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            .lines()
            .forEach { sql: String? -> runSql(sql) }
    }

    private fun runSql(sql: String?) {
        if (sql.isNullOrEmpty() || sql.isBlank()) return
        logger.info(sql)
        entityManager!!.createNativeQuery(sql).executeUpdate()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AdminServiceImpl::class.java)
    }
}