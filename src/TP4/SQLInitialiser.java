package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLInitialiser {
    public static boolean run(Connection con) throws SQLException, FileNotFoundException {
        if (!con.isClosed()) {
            Statement stm = con.createStatement();
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
                stm.execute(query);
            }
            return true;
        }
        return false;
    }
}
