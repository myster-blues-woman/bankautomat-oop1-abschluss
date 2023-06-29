package ch.hftm;

import java.util.Comparator;

public class GeldComparator implements Comparator<Geld> {
    @Override
    public int compare(Geld geld1, Geld geld2) {
        return Integer.compare(geld1.getWert(), geld2.getWert());
    }
}
