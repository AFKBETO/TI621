package TP4;

import java.sql.*;
import java.util.Objects;

public class Tag implements Comparable {
    private final String tag;

    public Tag (final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public int getKey(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO tag(tag) VALUES (\"" + tag + "\");");
            resultSet = statement.executeQuery("SELECT tagID FROM tag WHERE tag = '" + tag + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

    @Override
    public int compareTo(Object o) {
        Tag tag1 = (Tag) o;
        return getTag().compareTo(tag1.getTag());
    }
}
