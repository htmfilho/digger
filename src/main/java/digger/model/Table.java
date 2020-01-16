package digger.model;

import javax.persistence.*;
import javax.persistence.Column;

@Entity
@javax.persistence.Table(name = "database_table")
public class Table implements Comparable<Table> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

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
	public int compareTo(Table table) {
        if(this.name != null && table.name != null)
            return this.name.trim().compareTo(table.name.trim());
        else
            return -1;
    }

    public boolean equals(Table another) {
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());
        else
            return this.id == another.id;
    }
}