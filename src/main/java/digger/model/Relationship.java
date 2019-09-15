package digger.model;

public class Relationship {

    private String name;
    private Table target;
    private Table origin;
    private Column foreignKey;

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
