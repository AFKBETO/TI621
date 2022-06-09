package TP3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        //Entrée de la liste des coureurs
        Scanner scanner = new Scanner(System.in);
        int nombre;
        boolean isInt = false;
        while(!isInt) {
            System.out.println("Veuillez spécifier le nombre de coureurs : ");
            isInt = scanner.hasNextInt();
            if (!isInt) {
                System.out.println("Erreur: Il faut taper un nombre!!");
                scanner.next();
            }
        }
        nombre = scanner.nextInt();

        List<Coureur> coureurs = new ArrayList<>();
        List<Coureur> classement = new ArrayList<>();

        for (int i = 0; i < nombre; i++) {
            System.out.println("Nom du coureur #" + (i + 1) + " : ");
            coureurs.add(new Coureur(scanner.next()));
        }

        //Arrêt Programme
        String com = "";

        while(!com.toLowerCase().equals("q")){
            System.out.println("Que voulez-vous faire ?\n" +
                    "q : quitter le programme\n" +
                    "a : afficher tous les coureurs\n" +
                    "c : afficher le classement\n" +
                    "f : enregister une arrivée\n"
            );
            com = scanner.next().toLowerCase();
            switch (com) {
                case ("a"): // Affichage Coureurs
                    for (Coureur coureur : coureurs) {
                        System.out.println(coureur);
                    }
                    break;
                case ("c"): // Afficher le classement
                    for (int i = 0; i < nombre; i++) {
                        System.out.println("Rang #" + (i+1) + " : " + ((i < classement.size())? classement.get(i) : "Coureur non arrivé"));
                    }
                    break;
                case ("f"): // Enregistrer une arrivée
                    System.out.println("Quel coureur est arrivé ? Précisez son numéro de dossard.");
                    while(!scanner.hasNextInt()) {
                        System.out.println("Veuillez taper un nombre!");
                        scanner.next();
                    }
                    classement.add(coureurs.get(scanner.nextInt()));
                    break;
            }
        }

        

    }
}


