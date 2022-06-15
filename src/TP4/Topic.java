package TP4;

public class Topic {
    private final String topic;

    public Topic(final String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public String toSQLInsert() {
        return "INSERT IGNORE INTO Topic(topic) VALUES (\"" + topic + "\");";
    }
}
