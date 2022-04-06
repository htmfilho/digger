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

import javax.persistence.*;
import javax.persistence.Column;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "database_table")
public class Table implements Comparable<Table>, DocEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "datasource")
    private Datasource datasource;

    @Column
    private String name;

    @Column(name = "friendly_name")
    private String friendlyName;

    @Column
    private String documentation;

    @Column
    private String type;

    @Column
    private Integer totalColumns;

    public Table() {}

    public Table(Long id) {
        this.id = id;
    }

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(Integer totalColumns) {
        this.totalColumns = totalColumns;
    }

    public String toString() {
        return this.name + " ("+ this.friendlyName +")";
    }

	@Override
	public int compareTo(final Table table) {
        if(this.name != null && table.name != null)
            return this.name.trim().compareTo(table.name.trim());
        else
            return -1;
    }

    @Override
    public boolean equals(final Object object) {
        if(!(object instanceof Table)) {
            return false;
        }

        Table another = (Table) object;
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());
        else
            return Objects.equals(this.id, another.id);
    }
}