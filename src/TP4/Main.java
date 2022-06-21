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
            //connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, password);
            System.out.println("Base de donnees connectee !");
            DatabaseController.initialize(con);

            // add documents
            System.out.println("\nExo A : ");
            Document doc = new Document("Demande difficile", "2022-06-17", "C:/Users/ArtEfrei/Subvention.pdf");
            doc.setCategory("report");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.sync(con);
            printDocument(con, doc.getDocumentID());

            doc = new Document("Le reve artistique", "2022-06-28", "C:/Users/ArtEfrei/Plaquette.pdf");
            doc.setCategory("report");
            doc.setTopic("Plaquette Partenariat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Etude");
            doc.addTag("Divertissement");
            doc.sync(con);
            printDocument(con, doc.getDocumentID());

            doc = new Document("De beaux pulls", "2022-08-12", "C:/Users/ArtEfrei/Pulls.pdf");
            doc.setCategory("order");
            doc.setTopic("Dossier Sweat ArtEfrei");
            doc.addTag("Association");
            doc.addTag("Divertissement");
            doc.sync(con);
            printDocument(con, doc.getDocumentID());

            doc = new Document("Changement de tableau", "2022-08-22", "C:/Users/ArtEfrei/SubChange.pdf");
            doc.setCategory("receipt");
            doc.setTopic("Subvention ArtEfrei 2022");
            doc.addTag("Association");
            doc.sync(con);
            printDocument(con, doc.getDocumentID());

            doc = new Document("Reglements EFREI", "2021-09-01", "C:/Users/EFREI/Regles.pdf");
            doc.setCategory("policy");
            doc.setTopic("Regles EFREI 2021");
            doc.addTag("Etude");
            doc.addTag("Regle");
            doc.sync(con);
            printDocument(con, doc.getDocumentID());

            String commande = "";
            Statement statement = con.createStatement();
            ResultSet rS;

            while (!commande.equals("q")) {
                System.out.println("\nQue voulez-vous?\n" +
                        "1. Lister les documents par categorie, par sujet ou par tag.\n" +
                        "2. Chercher le sujet le plus frequent.\n" +
                        "3. Recuperer et afficher le nombre d'occurrences de chaque tag.\n" +
                        "4. Creer un nouveau document.\n" +
                        "5. Modifier un document.\n" +
                        "Q. Quitter.");
                commande = sc.nextLine().toLowerCase();
                switch(commande) {
                    case ("q"):
                        break;
                    case ("1"):
                        System.out.println("Lister les documents par :\n" +
                                "1. Categorie\n" +
                                "2. Sujet\n" +
                                "3. Tag");
                        commande = sc.nextLine().toLowerCase();
                        String sql = "select DocumentName,";
                        switch (commande) {
                            case ("1"):
                                System.out.println("Choisissez votre categorie :");
                                sql += "Name as Category from Document join Category using(CategoryID) where Name='";
                                break;
                            case ("2"):
                                System.out.println("Choisissez votre sujet :");
                                sql += "Topic from Document join Topic using(TopicID) where Topic='";
                                break;
                            case ("3"):
                                System.out.println("Choisissez votre tag :");
                                sql += "Tag from Document join Possede using (DocumentId) join Tag using(TagID) where Tag='";
                                break;
                            default:
                                System.out.println("Erreur commande");
                                continue;
                        }
                        commande = sc.nextLine();
                        sql += commande + "';";
                        rS = statement.executeQuery(sql);
                        if (rS.next()) {
                            System.out.println("Resultats :");
                            System.out.println(rS.getString(1));
                            while (rS.next()) {
                                System.out.println(rS.getString(1));
                            }
                        } else {
                            System.out.println("Aucun resultat trouve");
                        }
                        break;
                    case ("2"):
                        rS = statement.executeQuery("select count(*) as NbTopicTimes, Topic from Document " +
                                "join Topic using(TopicID) group by Topic order by NbTopicTimes desc limit 1;");
                        rS.next();
                        System.out.printf("%-30s %-10s\n", "Topic", "NbTopicTimes");
                        System.out.printf("%-30s %-10s\n", rS.getString("Topic"), rS.getInt("NbTopicTimes"));
                        break;
                    case ("3"):
                        rS = statement.executeQuery("select Tag, count(Tag) as NbOccurrenceTag from Possede\n" +
                                "join Tag using(TagID) group by Tag order by NbOccurrenceTag;");
                        System.out.printf("%-30s %-10s\n", "Tag", "NbOccurrenceTag");
                        while (rS.next()) {
                            System.out.printf("%-30s %-10s\n", rS.getString("Tag"), rS.getInt("NbOccurrenceTag"));
                        }
                        break;
                    case ("4"):
                        System.out.println("Saisissez le nom du document :");
                        doc = new Document(sc.nextLine());
                        System.out.println("Saisissez la date du document :");
                        String string = sc.nextLine();
                        if (!string.isEmpty()) {
                            doc.setDocumentDate(string);
                        }
                        System.out.println("Saisissez l'adresse du storage :");
                        string = sc.nextLine();
                        if (!string.isEmpty()) {
                            doc.setStorageAddress(string);
                        }
                        System.out.println("Saisissez la categorie :");
                        string = sc.nextLine();
                        while(string.isEmpty()) {
                            string = sc.nextLine();
                        }
                        doc.setCategory(string);
                        System.out.println("Saisissez le sujet :");
                        string = sc.nextLine();
                        while(string.isEmpty()) {
                            string = sc.nextLine();
                        }
                        doc.setTopic(string);
                        System.out.println("Saisissez les tags (laissez le champ vide pour terminer) :");
                        string = sc.nextLine();
                        while (!string.isEmpty()) {
                            doc.addTag(string);
                            string = sc.nextLine();
                        }
                        doc.sync(con);
                        printDocument(con, doc.getDocumentID());
                        break;
                    case ("5"):
                        System.out.println("Veuillez indiquer l'ID de document a modifier :");
                        try {
                            doc = Document.fetchDocument(con, sc.nextInt());
                            sc.nextLine();
                        } catch (SQLException e) {
                            System.out.println(e);
                            sc.nextLine();
                            break;
                        }
                        System.out.println(doc);
                        System.out.println("Que voulez-vous modifier :\n" +
                                "1. DocumentName\n" +
                                "2. DocumentDate\n" +
                                "3. StorageAddress\n" +
                                "4. Category\n" +
                                "5. Topic\n" +
                                "6. Ajouter Tag\n" +
                                "7. Enlever Tag");
                        commande = sc.nextLine().toLowerCase();
                        switch (commande) {
                            case ("1"):
                                System.out.println("Choisissez un nouveau nom :");
                                doc.setDocumentName(sc.nextLine());
                                break;
                            case ("2"):
                                System.out.println("Choisissez une nouvelle date :");
                                try {
                                    doc.setDocumentDate(sc.nextLine());
                                } catch (Exception e) {
                                    System.out.println(e);
                                    continue;
                                }
                                break;
                            case ("3"):
                                System.out.println("Choisissez un nouveau adresse :");
                                doc.setStorageAddress(sc.nextLine());
                                break;
                            case ("4"):
                                System.out.println("Choisissez une nouvelle catégorie :");
                                string = sc.nextLine();
                                while(string.isEmpty()) {
                                    string = sc.nextLine();
                                }
                                doc.setCategory(string);
                                break;
                            case ("5"):
                                System.out.println("Choisissez un nouveau sujet :");
                                string = sc.nextLine();
                                while(string.isEmpty()) {
                                    string = sc.nextLine();
                                }
                                doc.setTopic(string);
                                break;
                            case ("6"):
                                System.out.println("Choisissez les nouveaux tags (Laissez la ligne vide pour terminer) :");
                                string = sc.nextLine();
                                while (!string.isEmpty()) {
                                    doc.addTag(string);
                                    string = sc.nextLine();
                                }
                                break;
                            case ("7"):
                                System.out.println("Choisissez les tags a supprimer (Laissez la ligne vide pour terminer) :");
                                string = sc.nextLine();
                                while (!string.isEmpty()) {
                                    if(doc.deleteTag(string)){
                                        System.out.println("Tag " + string + " supprime.");
                                    } else {
                                        System.out.println("Tag " + string + " n'existe pas.");
                                    }
                                    string = sc.nextLine();
                                }
                                break;
                            default:
                                System.out.println("Erreur commande");
                                continue;
                        }
                        doc.sync(con);
                        System.out.println(doc);
                        printDocument(con, doc.getDocumentID());
                        break;
                    default:
                        System.out.println("Commande invalide.");
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * printDocument: print the document with the given docId. This is different from printing a Document instance
     * @param con Connection to the database
     * @param docId id of the document
     * @throws SQLException
     */
    public static void printDocument(final Connection con, final int docId) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet rS = stm.executeQuery("SELECT documentName, documentDate, storageAddress, Name AS Category, Topic FROM Document " +
                "JOIN Category USING(categoryId) JOIN Topic USING(topicId) " +
                "WHERE DocumentId = " + docId + ";");
        if(rS.next()){
            System.out.printf("%-25s %-12s %-35s %-10s %-30s %-10s\n", "DocumentName", "Date", "StorageAddress", "Category", "Topic", "Tags");
            System.out.printf("%-25s %-12s %-35s %-10s %-30s", rS.getString("documentName"), rS.getString("documentDate"), rS.getString("storageAddress"), rS.getString("Category"), rS.getString("Topic"));
            rS = stm.executeQuery("SELECT Tag FROM Possede JOIN Tag USING(TagId) WHERE DocumentId = " + docId + ";");
            while (rS.next()) {
                System.out.print(rS.getString(1) + " ");
            }
            System.out.println();
        }
    }
}
