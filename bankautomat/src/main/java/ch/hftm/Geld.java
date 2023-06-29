package ch.hftm;

import java.util.Comparator;

public class Geld {

    private int wert;
    private int anzahl;
    private int gesamt;

    public Geld(int wert, int anzahl) {
        this.wert = wert;
        this.anzahl = anzahl;
        this.gesamt = wert * anzahl;
    }

    public int getGesamt() {
        return gesamt;
    }

    public int getWert() {
        return wert;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
        this.gesamt = this.wert * this.anzahl;
    }

    public int compareTo(Geld Geld) {
        return this.wert - Geld.wert;
    }

    public static Comparator sortAufsteigend() {
        return new VergleicheBetragAufsteigend();
    }

    public static Comparator sortAbsteigend() {
        return new VergleicheBetragAbsteigend();
    }

    @Override
    public String toString() {
        return "Geld{" + "wert=" + wert + ", anzahl=" + anzahl + ", gesamt=" + gesamt + '}';
    }
}

class VergleicheBetragAbsteigend implements Comparator<Geld> {
    @Override
    public int compare(Geld r1, Geld r2) {
        return r2.getWert() - r1.getWert();
    }
}

class VergleicheBetragAufsteigend implements Comparator<Geld> {
    @Override
    public int compare(Geld r1, Geld r2) {
        return r1.getWert() - r2.getWert();
    }
}
