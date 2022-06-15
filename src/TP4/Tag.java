package TP4;

public class Tag {
    private final String tag;

    public Tag (final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public String toSQLInsert() {
        return "INSERT IGNORE INTO Tag(name) VALUES (\"" + tag + "\");";
    }
}
