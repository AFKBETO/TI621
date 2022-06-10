package TP3;

import java.util.Comparator;

public class DossardComparator implements Comparator<Coureur> {

    @Override
    public int compare(Coureur o1, Coureur o2) {
        if (o1.getNumDossard() < o2.getNumDossard()) {
            return -1;
        }
        if (o1.getNumDossard() > o2.getNumDossard()) {
            return 1;
        }
        return 0;
    }
}
