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

package digger.service.impl;

import digger.model.Role;
import digger.repository.RoleRepository;
import digger.service.RoleService;
import digger.utils.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Long countAll() {
        return roleRepository.count();
    }

    public Long countAllByAuthority(String authority) {
        return roleRepository.countAllByAuthority(authority);
    }

    public Role findByUsername(final String username) {
        return this.roleRepository.findByUsername(username);
    }

    public void save(Role role) {
        try {
            this.roleRepository.save(role);
        } catch (DataIntegrityViolationException dive) {
            BigDecimal seq = roleRepository.getSequenceNextVal();
            logger.warn("Role sequence out of sync. Incrementing sequence to: {}", seq);
            save(role);
        }
        logger.info("Assigned role {} to user {}", role.getAuthority(), role.getUsername());
    }

    public List<String> exportToSql() {
        List<String> sqlStatements = new ArrayList<>();
        sqlStatements.add("delete from authorities;");
        List<Role> allRoles = roleRepository.findAll();
        for (Role role: allRoles) {
            String sqlStatement = "insert into authorities (id, username, authority, created) values (" +
                    SqlHelper.fieldToSql(role.getId(), ",") +
                    SqlHelper.fieldToSql(role.getUsername(), ",") +
                    SqlHelper.fieldToSql(role.getAuthority(), ",") +
                    SqlHelper.fieldToSql(role.getCreated(), ");");
            sqlStatements.add(sqlStatement);
        }
        sqlStatements.add("\n");
        return sqlStatements;
    }
}