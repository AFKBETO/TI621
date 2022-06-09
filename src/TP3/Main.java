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

        List coureurs = new ArrayList<Coureur>();

        for (int i = 0; i < nombre; i++) {
            System.out.println("Nom du coureur #" + (i + 1) + " : ");
            coureurs.add(scanner.next());
        }


        boolean course = true;

        while(course){

        }

    }
}


