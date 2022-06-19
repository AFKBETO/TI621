package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Scanner;

public class SQLInitialiser {
    public static boolean run(Statement statement) throws SQLException, FileNotFoundException {
        if (!statement.isClosed()) {
            File file = new File("src/TP4/initialiser.sql");
            Scanner sc = new Scanner(file);
            sc.useDelimiter(";|--|$");
            List<String> queries = new ArrayList<>();
            String query;
            while (sc.hasNext()) {
                query = sc.next().trim() + ";";
                if (query.startsWith("# ") || query.length() == 1) {
                    continue;
                }
                System.out.println("Executer " + query);
                statement.execute(query);
            }
            return true;
        }
        return false;
    }
}
