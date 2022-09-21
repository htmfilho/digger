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

package digger.repository;

import digger.model.Datasource;
import digger.model.Table;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.util.List;

public interface TableRepository extends Repository<Table, Long> {
    Long count();

    Table findById(Long id);

    List<Table> findAll();
    List<Table> findByDatasourceOrderByNameAsc(Datasource datasource);

    @Query(value = "select database_table_id_seq.nextval from dual", nativeQuery = true)
    BigDecimal getNextVal();
    
    void save(Table table);
    void deleteById(Long id);
}
