package TP3;

import java.time.LocalTime;

public class Coureur {
    private static int COMPTEUR = 0;
    private final int numDossard;
    private final String nom;

    private Etat etat = Etat.ENCOURS;
    private int temps = 0;

    public Coureur(final String nom) {
        this.numDossard = ++COMPTEUR;
        this.nom = nom;
    }

    public int getNumDossard() {
        return numDossard;
    }

    public int getTemps() {
        return temps;
    }

    public String getTempsString() {
        LocalTime temps = LocalTime.ofSecondOfDay(this.temps);
        return temps.toString();
    }

    public void setTemps(int temps) {
        this.temps = temps;
        if (etat == Etat.ENCOURS) {
            arriver();
        }
    }

    public String getNom() {
        return nom;
    }

    public void abandonner() {
        etat = Etat.ABANDON;
    }

    public void arriver() {
        etat = Etat.ARRIVE;
    }

    public void disqualifier() {
        etat = Etat.DISQUALIFIE;
    }

    public Etat getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        return "Coureur #" + numDossard +
                " : " + nom + ", statut : " +
                etat + (etat == Etat.ARRIVE ? (", temps : " + getTempsString()): "");
    }
}
