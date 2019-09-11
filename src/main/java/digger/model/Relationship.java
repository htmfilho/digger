package digger.model;

import javax.persistence.*;

@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @javax.persistence.Column
    private String name;

    @ManyToOne
    private Table target;

    @ManyToOne
    private Table origin;

    @ManyToOne
    private Column foreignKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getTarget() {
        return target;
    }

    public void setTarget(Table target) {
        this.target = target;
    }

    public Table getOrigin() {
        return origin;
    }

    public void setOrigin(Table origin) {
        this.origin = origin;
    }

    public Column getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(Column foreignKey) {
        this.foreignKey = foreignKey;
    }
}
