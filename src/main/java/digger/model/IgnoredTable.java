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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ignored_table")
public class IgnoredTable implements Comparable<IgnoredTable> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "datasource")
    private Datasource datasource;

    @Column
    private String name;

    public IgnoredTable() {}

    public IgnoredTable(Long id) {
        this.id = id;
    }

    public IgnoredTable(String name) {
        this.name = name;
    }

    public IgnoredTable(String name, Datasource datasource) {
        this.name = name;
        this.datasource = datasource;
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

    public digger.model.Table toTable() {
        return new digger.model.Table(this.name);
    }

    public String toString() {
        return this.name;
    }

	@Override
	public int compareTo(IgnoredTable ignoredTable) {
        if(this.equals(ignoredTable))
            return 0;

        if(this.name != null && ignoredTable.name != null)
            return this.name.trim().compareTo(ignoredTable.name.trim());
        else
            return -1;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof IgnoredTable)) {
            return false;
        }

        IgnoredTable another = (IgnoredTable) object;
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());
        else
            return Objects.equals(this.id, another.id);
    }
}