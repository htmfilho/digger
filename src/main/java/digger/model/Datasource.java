package digger.model;

import javax.persistence.*;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Datasource {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column
    private @Getter @Setter String name;

    @Column
    private @Getter @Setter String description;

    @Column
    private @Getter @Setter String driver;

    @Column
    private @Getter @Setter String url;

    @Column
    private @Getter @Setter String username;

    @Column
    private @Getter @Setter String password;

    @Column
    private @Getter @Setter Integer totalTables;

    @Transient
    private @Getter @Setter Boolean status;

    public Datasource() {}

    public Datasource(Long id) {
        this.id = id;
    }
}