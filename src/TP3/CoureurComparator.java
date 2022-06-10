package TP3;

import java.util.Comparator;

public class CoureurComparator implements Comparator<Coureur> {

    @Override
    public int compare(Coureur o1, Coureur o2) {
        if (o1.getEtat() == o2.getEtat()) {
            if (o1.getEtat() == Etat.ARRIVEE) {
                if (o1.getTemps() < o2.getTemps()) {
                    return -1;
                } else if (o1.getTemps() > o2.getTemps()) {
                    return 1;
                }
            }
        } else {
            if (o1.getEtat() == Etat.DISQUALIF) {
                return -1;
            } else if (o2.getEtat() == Etat.DISQUALIF) {
                return 1;
            } else if (o1.getEtat() == Etat.ABANDON) {
                return -1;
            } else if (o2.getEtat() == Etat.ABANDON) {
                return 1;
            } else if (o1.getEtat() == Etat.ENCOURS) {
                return -1;
            } else if (o2.getEtat() == Etat.ENCOURS) {
                return 1;
            }
        }
        return 0;
    }
}
