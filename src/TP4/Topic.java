package TP4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Topic implements Comparable {
    private final String topic;

    public Topic(final String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public int getKey(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
        if (!resultSet.next()) {
            statement.execute("INSERT INTO topic(topic) VALUES (\"" + topic + "\");");
            resultSet = statement.executeQuery("SELECT topicId FROM topic WHERE topic = '" + topic + "' LIMIT 1");
            resultSet.next();
        }
        return resultSet.getInt(1);
    }

    @Override
    public int compareTo(Object o) {
        Topic topic1 = (Topic) o;
        return getTopic().compareTo(topic1.getTopic());
    }
}
