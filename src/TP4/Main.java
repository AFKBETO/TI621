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

            System.out.println("\nExo A : ");
            Document doc = new Document("Demande difficile", "2022-06-17", "C:/Users/ArtEfrei/Subvention.pdf");
            doc.setCategory("report");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.sync(statement);
            printDocument(statement, doc);

            doc = new Document("Le reve artistique", "2022-06-28", "C:/Users/ArtEfrei/Plaquette.pdf");
            doc.setCategory("report");
            doc.setTopic("Plaquette Partenariat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Etude");
            doc.addTag("Divertissement");
            doc.sync(statement);
            printDocument(statement, doc);

            doc = new Document("De beaux pulls", "2022-08-12", "C:/Users/ArtEfrei/Pulls.pdf");
            doc.setCategory("order");
            doc.setTopic("Dossier Sweat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Divertissement");
            doc.sync(statement);
            printDocument(statement, doc);

            doc = new Document("Changement de tableau", "2022-08-22", "C:/Users/ArtEfrei/SubChange.pdf");
            doc.setCategory("receipt");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.sync(statement);
            printDocument(statement, doc);

            doc = new Document("Reglements EFREI", "2021-09-01", "C:/Users/EFREI/Regles.pdf");
            doc.setCategory("policy");
            doc.setTopic("Regles EFREI 2021");
            doc.addTag("Etude");
            doc.addTag("Regle");
            doc.sync(statement);
            printDocument(statement, doc);

            System.out.println("\nExo B.i : ");
            ResultSet rS = statement.executeQuery("select DocumentName, Name as Category, Topic, Tag from Document \n" +
                    "join Category using(CategoryID)\n" +
                    "join Topic using(TopicID)\n" +
                    "join Possede using(DocumentID)\n" +
                    "join Tag using(TagID)\n" +
                    "group by DocumentName;");

            while (rS.next()) {
                System.out.println(rS.getString("DocumentName") + " : Category='" + rS.getString("Category") + "', Topic='" + rS.getString("Topic") + "', Tag='" + rS.getString("Tag") + "'");
            }

            System.out.println("\nExo B.ii : ");
            rS = statement.executeQuery("select count(*) as NbTopicTimes, Topic from Document \n" +
                    "join Topic using(TopicID)\n" +
                    "group by Topic\n" +
                    "order by NbTopicTimes desc\n" +
                    "limit 1;");
            rS.next();
            System.out.println("Topic = '" + rS.getString("Topic") + "', NbTopicTimes = " + rS.getInt("NbTopicTimes"));

            System.out.println("\nExo B.iii : ");
            rS = statement.executeQuery("select Tag, count(Tag) as NbOccurrenceTag from Possede\n" +
                    "join Tag using(TagID)\n" +
                    "group by Tag\n" +
                    "order by NbOccurrenceTag;");
            while (rS.next()) {
                System.out.println(rS.getString("Tag") + " : compteur=" + rS.getInt("NbOccurrenceTag"));
            }

            System.out.println("\nExo C.i : ");
            doc = new Document("Tableau dexamen 2022", "C:/Users/EFREI/Examen2022.pdf");
            doc.setCategory("policy");
            doc.setTopic("Dossier Examen EFREI 2022");
            doc.addTag("Etude");
            doc.addTag("Important");
            doc.sync(statement);
            printDocument(statement, doc);

            System.out.println("\nExo C.ii : ");
            doc.setDocumentDate("2022-01-01");
            doc.sync(statement);
            printDocument(statement, doc);

            System.out.println("\nExo C.iii : ");
            doc.addTag("Examen");
            doc.deleteTag("Etude");
            doc.sync(statement);
            printDocument(statement, doc);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void printDocument(Statement statement, Document doc) throws SQLException{
        ResultSet rS = statement.executeQuery("SELECT documentName, documentDate, storageAddress, Name AS Category, Topic FROM Document " +
                "JOIN Category USING(categoryId) JOIN Topic USING(topicId) " +
                "WHERE DocumentId = " + doc.getDocumentID() + ";");
        rS.next();
        System.out.println("Document " + rS.getString("documentName") + " - date : " + rS.getString("documentDate") + ", storageAddress : " + rS.getString("storageAddress") + ", Category : " + rS.getString("Category") + ", Topic : " + rS.getString("Topic"));
        rS = statement.executeQuery("SELECT Tag FROM Possede JOIN Tag USING(TagId) WHERE DocumentId = " + doc.getDocumentID() + ";");
        System.out.print("Liste des tags : ");
        while (rS.next()) {
            System.out.print(rS.getString(1) + " ");
        }
        System.out.println();
    }
}
