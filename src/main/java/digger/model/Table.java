package digger.model;

import javax.persistence.*;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Entity
@javax.persistence.Table(name = "database_table")
public class Table implements Comparable<Table> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "datasource")
    private @Getter @Setter Datasource datasource;

    @Column
    private @Getter @Setter String name;

    @Column(name = "friendly_name")
    private @Getter @Setter String friendlyName;

    @Column
    private @Getter @Setter String documentation;

    @Column
    private @Getter @Setter String type;

    @Column
    private @Getter @Setter Integer totalColumns;

    public Table() {}

    public Table(Long id) {
        this.id = id;
    }

    public Table(String name) {
        this.name = name;
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
    
    @Override
    public boolean equals(Object object) {
        Table table = (Table) object;
        if(this.name != null && table.name != null)
            return this.name.trim().equals(table.name.trim());
        else
            return this.id == table.id;
    }
}