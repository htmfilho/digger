package digger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ignored_table")
public class IgnoredTable implements Comparable<IgnoredTable> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        if(this.name != null && ignoredTable.name != null)
            return this.name.trim().compareTo(ignoredTable.name.trim());
        else
            return -1;
    }

    @Override
    public boolean equals(Object object) {
        IgnoredTable another = (IgnoredTable) object;
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());
        else
            return this.id == another.id;
    }
}