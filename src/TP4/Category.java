package TP4;

import java.sql.*;
import java.util.Objects;

public class Category implements Comparable {
    private final String name;

    public Category(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getKey(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO Category(name) VALUES (\"" + name + "\");");
            resultSet = statement.executeQuery("SELECT CategoryId FROM category WHERE Name = '" + name + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

    @Override
    public int compareTo(Object o) {
        Category category1 = (Category) o;
        return getName().compareTo(category1.getName());
    }
}
