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

package digger.model;

public class StorageDTO {
    private String tableName;
    private Long numRecords;

    public StorageDTO() {}

    public StorageDTO(String tableName, Long numRecords) {
        this.tableName = tableName;
        this.numRecords = numRecords;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(Long numRecords) {
        this.numRecords = numRecords;
    }
}