package ch.hftm;

import java.util.Comparator;

public class GeldAbsteigendComparator implements Comparator<Geld> {
    @Override
    public int compare(Geld geld1, Geld geld2) {
        return Integer.compare(geld2.getWert(), geld1.getWert());
    }
}