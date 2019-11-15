package digger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ignored_table")
public class IgnoredTable implements Comparable<IgnoredTable> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "datasource")
    private @Getter @Setter Datasource datasource;

    @Column
    private @Getter @Setter String name;

    public IgnoredTable() {}

    public IgnoredTable(Long id) {
        this.id = id;
    }

    public IgnoredTable(String name) {
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
        IgnoredTable ignoredTable = (IgnoredTable) object;
        if(this.name != null && ignoredTable.name != null)
            return this.name.trim().equals(ignoredTable.name.trim());
        else
            return this.id == ignoredTable.id;
    }
}