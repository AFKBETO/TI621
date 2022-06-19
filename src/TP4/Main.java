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
            Document doc1 = new Document("DocumentTest", "2022-06-21", "izguehf");
            doc1.setCategory(new Category("A new category"));
            doc1.setTopic(new Topic("A new topic"));
            doc1.addTag(new Tag("Tag1"));
            doc1.addTag(new Tag("Tag2"));
            doc1.insert(statement);
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
