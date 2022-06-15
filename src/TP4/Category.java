package TP4;

public class Category {
    private final String name;

    public Category(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toSQLInsert() {
        return "INSERT IGNORE INTO Category(name) VALUES (\"" + name + "\");";
    }
}
