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

    public static int scanUntilInt(Scanner scanner) {
        while(!scanner.hasNextInt()) {
            System.out.println("Veuillez taper un nombre!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        //Entrée de la liste des coureurs
        Scanner scanner = new Scanner(System.in);
        int nombre = 0;
        System.out.println("Veuillez spécifier le nombre de coureurs : ");
        while(nombre == 0) {
            nombre = scanUntilInt(scanner);
            if (nombre == 0) {
                System.out.println("Le nombre doit être positif !");
            }
        }

        List<Coureur> coureurs = new ArrayList<>();
        Coureur recherche;
        int compteur = 0;

        for (int i = 0; i < nombre; i++) {
            System.out.println("Nom du coureur #" + (i + 1) + " : ");
            coureurs.add(new Coureur(scanner.next()));
        }

        //Arrêt Programme
        String com = "";
        int start = LocalTime.now().toSecondOfDay();

        while(!com.toLowerCase().equals("q")){
            if (compteur == nombre) {
                System.out.println("La course est terminée ! Voici le classement final :");
                coureurs.sort(new CoureurComparator());
                for (Coureur coureur : coureurs) {
                    System.out.println("Rang #" + coureur.getNumDossard() + " : " + coureur);
                }
                break;
            }
            System.out.println("Que voulez-vous faire ?\n" +
                    "q : quitter le programme\n" +
                    "a : afficher tous les coureurs\n" +
                    "c : afficher le classement provisoire\n" +
                    "f : enregistrer une arrivée\n" +
                    "d : enregistrer un abandon\n" +
                    "i : enregistrer une disqualification\n" +
                    "m : enregistrer le temps d'arrivée d'un coureur\n" +
                    "t : afficher le temps d'arrivée d'un coureur\n" +
                    "e : afficher l'écart entre deux coureurs"
            );
            com = scanner.next().toLowerCase();
            switch (com) {
                case ("a"): // Affichage Coureurs
                    coureurs.sort(new DossardComparator());
                    for (Coureur coureur : coureurs) {
                        System.out.println(coureur);
                    }
                    break;
                case ("c"): // Afficher le classement provisoire
                    coureurs.sort(new CoureurComparator());
                    for (Coureur coureur : coureurs) {
                        System.out.println("Rang #" + coureur.getNumDossard() + " : " + coureur);
                    }
                    break;
                case ("f"): // Enregistrer une arrivée
                    int tempsArrive = LocalTime.now().toSecondOfDay() - start;
                    if (tempsArrive < 0) {
                        tempsArrive += 24 * 60* 60;
                    }
                    System.out.println("Quel coureur est arrivé ? Précisez son numéro de dossard.");

                    recherche = search(coureurs, scanUntilInt(scanner));

                    if (recherche != null) {
                        switch (recherche.getEtat()){
                            case ENCOURS:
                                recherche.setTemps(tempsArrive);
                                compteur++;
                                break;
                            case ABANDON:
                                System.out.println("Le coureur a abandonné la course !");
                                break;
                            case ARRIVEE:
                                System.out.println("Le coureur est déjà arrivé !");
                                break;
                            case DISQUALIFICATION:
                                System.out.println("Le coureur est disqualifié de cette course !");
                                break;
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case ("i"): // Enregistrer une disqualification
                    System.out.println("Quel coureur est disqualifié ? Précisez son numéro de dossard.");

                    recherche = search(coureurs, scanUntilInt(scanner));

                    if (recherche != null) {
                        switch (recherche.getEtat()){
                            case ABANDON:
                                System.out.println("Le coureur a déjà abandonné la course !");
                                break;
                            case DISQUALIFICATION:
                                System.out.println("Le coureur a déjà été disqualifié de cette course !");
                                break;
                            default:
                                recherche.disqualifier();
                                compteur++;
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case "m":
                    System.out.println("De quel coureur souhaitez-vous modifier le temps d'arrivée ? Précisez son numéro de dossard.");

                    recherche = search(coureurs, scanUntilInt(scanner));

                    if (recherche != null) {
                        switch (recherche.getEtat()){
                            case ABANDON:
                                System.out.println("Le coureur a déjà abandonné la course !");
                                break;
                            case DISQUALIFICATION:
                                System.out.println("Le coureur est disqualifié de cette course !");
                                break;
                            case ENCOURS:
                                compteur++;
                            case ARRIVEE:
                                System.out.println("Veuillez préciser le temps du coureur #" + recherche.getNumDossard() + " en seconde :");
                                recherche.setTemps(scanUntilInt(scanner));
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case ("d"): // Enregistrer une abandon
                    System.out.println("Quel coureur souhaite abandonner ? Précisez son numéro de dossard.");

                    recherche = search(coureurs, scanUntilInt(scanner));

                    if (recherche != null) {
                        switch (recherche.getEtat()){
                            case ENCOURS:
                                recherche.abandonner();
                                compteur++;
                                break;
                            case ABANDON:
                                System.out.println("Le coureur a abandonné la course !");
                                break;
                            case ARRIVEE:
                                System.out.println("Le coureur est déjà arrivé !");
                                break;
                            case DISQUALIFICATION:
                                System.out.println("Le coureur est disqualifié de cette course !");
                                break;
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case "t":
                    System.out.println("De quel coureur souhaitez-vous afficher le temps d'arrivée ? Précisez son numéro de dossard.");

                    recherche = search(coureurs, scanUntilInt(scanner));

                    if (recherche != null) {
                        switch (recherche.getEtat()){
                            case ENCOURS:
                                int actuel = LocalTime.now().toSecondOfDay() - start;
                                if (actuel < 0) {
                                    actuel += 24 * 3600;
                                }
                                System.out.println("Le coureur est encore dans la course. Le temps actuel : " + LocalTime.ofSecondOfDay(actuel));
                                break;
                            case ABANDON:
                                System.out.println("Le coureur a déjà abandonné la course !");
                                break;
                            case DISQUALIFICATION:
                                System.out.println("Le coureur est disqualifié de cette course !");
                                break;
                            case ARRIVEE:
                                System.out.println("Le temps du coureur#" + recherche.getNumDossard() + " : " + recherche.getTempsString());
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
                case "e":
                    System.out.println("Précisez le numéro de dossard du coureur 1 :");
                    Coureur coureur1 = search(coureurs, scanUntilInt(scanner));

                    System.out.println("Précisez le numéro de dossard du coureur 2 :");
                    Coureur coureur2 = search(coureurs, scanUntilInt(scanner));

                    if (coureur1 != null && coureur2 != null) {
                        if (coureur1.getEtat() == Etat.DISQUALIFICATION || coureur2.getEtat() == Etat.DISQUALIFICATION || coureur1.getEtat() == Etat.ABANDON || coureur2.getEtat() == Etat.ABANDON) {
                            System.out.println("Au moins un coureur a quitté la course !");
                        } else if (coureur1.getEtat() == coureur2.getEtat() && coureur1.getEtat() == Etat.ENCOURS) {
                            System.out.println("Les deux coureurs sont encore en course !");
                        } else if (coureur1.getEtat() == Etat.ENCOURS) {
                            System.out.println("L'écart entre coureur#" +
                                    coureur1.getNumDossard() +
                                    " et coureur#" +
                                    coureur2.getNumDossard() +
                                    "est " + LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay() - start - coureur2.getTemps()));
                        } else if (coureur2.getEtat() == Etat.ENCOURS) {
                            System.out.println("L'écart entre coureur#" +
                                    coureur1.getNumDossard() +
                                    " et coureur#" +
                                    coureur2.getNumDossard() +
                                    "est " + LocalTime.ofSecondOfDay(LocalTime.now().toSecondOfDay() - start - coureur1.getTemps()));
                        } else {
                            System.out.println("L'écart entre coureur#" +
                                    coureur1.getNumDossard() +
                                    " et coureur#" +
                                    coureur2.getNumDossard() +
                                    "est " + LocalTime.ofSecondOfDay(Math.abs(coureur1.getTemps() - coureur2.getTemps())));
                        }
                    } else {
                        System.out.println("Le nombre de dossard est invalide.");
                    }
                    break;
            }
        }



    }
}


