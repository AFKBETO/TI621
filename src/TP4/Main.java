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
            System.out.println("Base de donnees connectee !");
            SQLInitialiser.run(statement);

            //Insert Document
            Document doc = new Document("Demande difficile", "2022-06-17", "C:/Users/ArtEfrei/Subvention.pdf");
            doc.setCategory("report");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.insert(statement);

            doc = new Document("Le reve artistique", "2022-06-28", "C:/Users/ArtEfrei/Plaquette.pdf");
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

            doc = new Document("Changement de tableau", "2022-08-22", "C:/Users/ArtEfrei/SubChange.pdf");
            doc.setCategory("receipt");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.insert(statement);

            doc = new Document("Reglements EFREI", "2021-09-01", "C:/Users/EFREI/Regles.pdf");
            doc.setCategory("policy");
            doc.setTopic("Regles EFREI 2021");
            doc.addTag("Etude");
            doc.addTag("Regle");
            doc.insert(statement);


            System.out.println("\nExo B.i : ");
            ResultSet rS = statement.executeQuery("select DocumentName, Name as Category, Topic, Tag from Document \n" +
                    "join Category using(CategoryID)\n" +
                    "join Topic using(TopicID)\n" +
                    "join Possede using(DocumentID)\n" +
                    "join Tag using(TagID)\n" +
                    "group by DocumentName;");

            while (rS.next()) {
                System.out.println(rS.getString(1) + " : Category='" + rS.getString(2) + "', Topic='" + rS.getString(3) + "', Tag='" + rS.getString(4) + "'");
            }

            System.out.println("\nExo B.ii : ");
            rS = statement.executeQuery("select count(*) as NbTopicTimes, Topic from Document \n" +
                    "join Topic using(TopicID)\n" +
                    "group by Topic\n" +
                    "order by NbTopicTimes desc\n" +
                    "limit 1;");
            rS.next();
            System.out.println("Topic = '" + rS.getString(2) + "', NbTopicTimes = " + rS.getInt(1));

            System.out.println("\nExo B.iii : ");
            rS = statement.executeQuery("select Tag, count(Tag) as NbOccurrenceTag from Possede\n" +
                    "join Tag using(TagID)\n" +
                    "group by Tag\n" +
                    "order by NbOccurrenceTag;");
            while (rS.next()) {
                System.out.println(rS.getString(1) + " : compteur=" + rS.getInt(2));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}
