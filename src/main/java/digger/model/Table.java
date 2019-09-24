package digger.model;

import javax.persistence.*;
import javax.persistence.Column;

@Entity
@javax.persistence.Table(name = "database_table")
public class Table implements Comparable<Table> {

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

    public Table(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Datasource getDatasource() {
        return this.datasource;
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
        return this.friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getDocumentation() {
        return this.documentation;
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
        return this.totalColumns;
    }

    public void setTotalColumns(Integer totalColumns) {
        this.totalColumns = totalColumns;
    }

    public String toString() {
        return this.name + " ("+ this.friendlyName +")";
    }

	@Override
	public int compareTo(Table table) {
        return this.name.compareTo(table.name);
	}
}