package TP3;

public class Coureur {
    private final int numDossard;
    private final String nom;

    public Coureur(final int numDossard, final String nom) {
        if (numDossard < 1) {
            throw new IllegalArgumentException("Numéro de dossard doit être positif!");
        }
        this.numDossard = numDossard;
        this.nom = nom;
    }
}
