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

            doc = new Document("De beaux pulls", "2022-08-12", "C:/Users/ArtEfrei/Pulls.pdf");
            doc.setCategory("order");
            doc.setTopic("Dossier Sweat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Divertissement");
            doc.insert(statement);

            doc = new Document("Demande facile", "2022-06-10", "C:/Users/ArtEfrei/Subvention2.pdf");
            doc.setCategory("report");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.insert(statement);

            doc = new Document("Reglements EFREI", "2021-09-01", "C:/Users/EFREI/Regles.pdf");
            doc.setCategory("policy");
            doc.setTopic("Regles EFREI 2021");
            doc.addTag("Etude");
            doc.addTag("Regle");
            doc.insert(statement);

            ResultSet resultSet = statement.executeQuery("select count(*) as NbTopicTimes, Topic from Document \n" +
                    "join Topic using(TopicID)\n" +
                    "group by Topic\n" +
                    "order by NbTopicTimes desc\n" +
                    "limit 1;");
            resultSet.next();
            System.out.println("Topic = " + resultSet.getString(2) + ", NbTopicTimes = " + resultSet.getInt(1));


            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
