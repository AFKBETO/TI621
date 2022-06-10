package TP3;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static Coureur search (List<Coureur> coureurs, int nomDossard) {
        for (Coureur coureur : coureurs) {
            if (coureur.getNumDossard() == nomDossard) {
                return coureur;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //Entrée de la liste des coureurs
        Scanner scanner = new Scanner(System.in);
        int nombre = 0;
        boolean isInt = false;
        while(!isInt) {
            System.out.println("Veuillez spécifier le nombre de coureurs : ");
            isInt = scanner.hasNextInt();
            if (!isInt) {
                System.out.println("Erreur: Il faut taper un nombre!!");
                scanner.next();
            } else {
                nombre = scanner.nextInt();
                isInt = nombre != 0;
                if (!isInt) {
                    System.out.println("Le nombre doit être positif !");
                }
            }
        }

        List<Coureur> coureurs = new ArrayList<>();
        int nombreArrivees = 0;

        for (int i = 0; i < nombre; i++) {
            System.out.println("Nom du coureur #" + (i + 1) + " : ");
            coureurs.add(new Coureur(scanner.next()));
        }

        //Arrêt Programme
        String com = "";
        int start = LocalTime.now().toSecondOfDay();

        while(!com.toLowerCase().equals("q")){
            System.out.println("Que voulez-vous faire ?\n" +
                    "q : quitter le programme\n" +
                    "a : afficher tous les coureurs\n" +
                    "c : afficher le classement\n" +
                    "f : enregister une arrivée\n" +
                    "i : enregister une disqualification\n" +
                    "t : enregister une disqualification\n"
            );
            com = scanner.next().toLowerCase();
            switch (com) {
                case ("a"): // Affichage Coureurs
                    for (Coureur coureur : coureurs) {
                        System.out.println(coureur);
                    }
                    break;
                case ("c"): // Afficher le classement
                    coureurs.sort(new CoureurComparator());
                    for (int i = 0; i < nombre; i++) {
                        System.out.println("Rang #" + (i+1) + " : " + coureurs.get(i));
                    }
                    break;
                case ("f"): // Enregistrer une arrivée
                    int tempsArrive = LocalTime.now().toSecondOfDay() - start;
                    if (tempsArrive < 0) {
                        tempsArrive += 24 * 60* 60;
                    }
                    System.out.println("Quel coureur est arrivé ? Précisez son numéro de dossard.");
                    while(!scanner.hasNextInt()) {
                        System.out.println("Veuillez taper un nombre!");
                        scanner.next();
                    }
                    int dossard = scanner.nextInt();
                    Coureur arrivee = search(coureurs, dossard);
                    if (arrivee != null) {
                        switch (arrivee.getEtat()){
                            case ENCOURS:
                                arrivee.setTemps(tempsArrive);
                                nombreArrivees++;
                                break;
                            case ABANDON:
                                System.out.println("Le coureur a abandonné la course !");
                                break;
                            case ARRIVEE:
                                System.out.println("Le coureur est déjà arrivé !");
                                break;
                            case DISQUALIF:
                                System.out.println("Le coureur est disqualifié de cette course !");
                                break;
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case ("i"): // Enregistrer une disqualification
                    System.out.println("Quel coureur est disqualifié ? Précisez son numéro de dossard.");
                    while(!scanner.hasNextInt()) {
                        System.out.println("Veuillez taper un nombre!");
                        scanner.next();
                    }
                    Coureur disqualif = search(coureurs, scanner.nextInt());
                    if (disqualif != null) {
                        switch (disqualif.getEtat()){
                            case ABANDON:
                                System.out.println("Le coureur a déjà abandonné la course !");
                                break;
                            case DISQUALIF:
                                System.out.println("Le coureur a déjà été disqualifié de cette course !");
                                break;
                            default:
                                disqualif.disqualifier();
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
            }
        }



    }
}


