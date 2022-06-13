package TP4;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom d'utilisateur : ");
        String user = sc.next();
        System.out.println("Mot de passe : ");
        String password = sc.next();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp4", user, password);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from document;");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
