package TP4;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nom d'utilisateur : ");
        String user = sc.next();
        sc.nextLine();
        System.out.println("Mot de passe : ");
        String password = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, password);
            Statement statement = con.createStatement();
            System.out.println("Base de données connectée !");
            SQLInitialiser.run(statement);

            //Insert Document
            Document doc = new Document("Demande difficile", "2022-06-17", "C:/Users/ArtEfrei/Subvention.pdf");
            doc.setCategory("report");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.insert(statement);

            doc = new Document("Le rêve artistique", "2022-06-28", "C:/Users/ArtEfrei/Plaquette.pdf");
            doc.setCategory("report");
            doc.setTopic("Plaquette Partenariat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Etude");
            doc.addTag("Divertissement");
            doc.insert(statement);


            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
