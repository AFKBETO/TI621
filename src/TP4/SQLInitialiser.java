package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Scanner;

public class SQLInitialiser {
    private List<String> queries = new ArrayList<>();
    private static SQLInitialiser instance;

    static {
        try {
            instance = new SQLInitialiser();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private SQLInitialiser() throws FileNotFoundException {
        File file = new File("src/TP4/queries.sql");
        Scanner sc = new Scanner(file);
        sc.useDelimiter(";|--|$");
        String query;
        while (sc.hasNext()) {
            query = sc.next().trim();
            if (query.startsWith("# ") || query.length() == 0) {
                continue;
            }
            queries.add(query + ";");
        }
    }

    public static boolean run(Statement statement) throws SQLException {
        if (!statement.isClosed()) {
            for (String query : instance.queries) {
                System.out.println("Executer " + query);
                statement.execute(query);
            }
            return true;
        }
        return false;
    }

    public static void println() {
        for (String query : instance.queries) {
            System.out.println(query);
        }
    }

    public static void main(String[] args) {
        println();
    }
}
