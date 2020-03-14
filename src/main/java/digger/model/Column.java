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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public Column getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Column foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String toString() {
        return this.name + " ("+ this.friendlyName +")";
    }

    @Override
    public int compareTo(Column column) {
        if(this.name != null && column.name != null)
            return this.name.trim().compareTo(column.name.trim());

        return -1;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Column)) {
            return false;
        }

        Column another = (Column) object;
        if(this.name != null && another.name != null)
            return this.name.trim().equals(another.name.trim());

        return this.id == another.id;
    }
}