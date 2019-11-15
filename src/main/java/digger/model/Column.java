package digger.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@javax.persistence.Table(name = "table_column")
public class Column implements Comparable<Column> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private @Getter @Setter Long id;
    
    @ManyToOne
    @JoinColumn(name = "database_table")
    private @Getter @Setter Table table;

    @javax.persistence.Column
    private @Getter @Setter String name;

    @javax.persistence.Column(name = "friendly_name")
    private @Getter @Setter String friendlyName;

    @javax.persistence.Column
    private @Getter @Setter String type;

    @javax.persistence.Column
    private @Getter @Setter Integer size;

    @javax.persistence.Column
    private @Getter @Setter Boolean nullable;

    @javax.persistence.Column(name = "default_value")
    private @Getter @Setter String defaultValue;

    @javax.persistence.Column
    private @Getter @Setter String documentation;

    @ManyToOne
    @JoinColumn(name = "foreign_key")
    private @Getter @Setter Column foreignKey;

    public Column() {}

    public Column(Long id) {
        this.id = id;
    }

    public Column(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name + " ("+ this.friendlyName +")";
    }

    @Override
    public int compareTo(Column column) {
        if(this.name != null && column.name != null)
            return this.name.trim().compareTo(column.name.trim());
        else
            return -1;
    }

    @Override
    public boolean equals(Object object) {
        Column column = (Column) object;
        if(this.name != null && column.name != null)
            return this.name.trim().equals(column.name.trim());
        else
            return this.id == column.id;
    }
}