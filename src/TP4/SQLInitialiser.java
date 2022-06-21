package TP4;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLInitialiser {
    /**
     * run: initialise the database with the SQL scripts in initialiser.sql
     * @param con Connection to the database
     * @return true if connection is open, else false
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static boolean run(Connection con) throws SQLException, FileNotFoundException {
        if (!con.isClosed()) {
            Statement stm = con.createStatement();
            File file = new File("src/TP4/initialiser.sql");
            Scanner sc = new Scanner(file);
            sc.useDelimiter(";|--|$");

            while (sc.hasNext()) {
                String query = sc.next().trim() + ";";
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
