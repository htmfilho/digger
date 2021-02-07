/*
 * Digger
 * Copyright (C) 2019-2021 Hildeberto Mendonca
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

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "table_column")
public class Column implements Comparable<Column> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "database_table")
    private Table table;

    @javax.persistence.Column
    private String name;

    @javax.persistence.Column(name = "friendly_name")
    private String friendlyName;

    @javax.persistence.Column
    private String type;

    @javax.persistence.Column
    private Integer size;

    @javax.persistence.Column
    private Boolean nullable;

    @javax.persistence.Column
    private Boolean primaryKey;

    @javax.persistence.Column
    private Boolean sensitive;

    @javax.persistence.Column(name = "default_value")
    private String defaultValue;

    @javax.persistence.Column
    private String documentation;

    @ManyToOne
    @JoinColumn(name = "foreign_key")
    private Column foreignKey;

    public Column() {}

    public Column(Long id) {
        this.id = id;
    }

    public Column(String name) {
        this.name = name;
    }

    public Column(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public Column(Long id, String name, Boolean primaryKey) {
        this.id = id;
        this.name = name;
        this.primaryKey = primaryKey;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getPrimaryKey() {
        if(this.primaryKey == null)
            return false;

        return this.primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Boolean getSensitive() {
        if (this.sensitive == null)
            return false;

        return this.sensitive;
    }

    public void setSensitive(Boolean sensitive) {
        this.sensitive = sensitive;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public Column getForeignKey() {
        return this.foreignKey;
    }

    public void setForeignKey(Column foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String toString() {
        return this.name + " ("+ this.friendlyName +")";
    }

    /**
     * Two columns are considered the same if they have the same physical name.
     * */
    @Override
    public int compareTo(final Column column) {
        if(this.name != null && column.name != null)
            return this.name.trim().compareTo(column.name.trim());

        return -1;
    }

    @Override
    public boolean equals(final Object object) {
        if(!(object instanceof Column)) {
            return false;
        }

        Column another = (Column) object;
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());

        return this.id == another.id;
    }
}