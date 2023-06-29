package ch.hftm;

import java.util.ArrayList;
import java.util.Collections;

public class GeldService {
    private ArrayList<Geld> bankStatus = null;
    private int maxBankgeld = 0;
    private int betrag = 0;
    private int tmpBetrag = 0;
    private ArrayList<Geld> geldBetraege = new ArrayList<>();
    private Bank bank = new Bank();

    public GeldService() {
        bank.readGeldStatus();
        bankStatus = bank.getBankStatus();
    }

    public int getBetrag() {
        return this.betrag;
    }

    public void setBetrag(int betrag) {
        this.betrag = this.tmpBetrag = betrag;
    }

    public String pruefeVerfuegbaresGeld(Karte karte) {
        bank.readGeldStatus();
        bankStatus = bank.getBankStatus();
        geldBetraege = new ArrayList<>();

        for (Geld Geld : bankStatus) {
            maxBankgeld += Geld.getGesamt();
            geldBetraege.add(new Geld(Geld.getWert(), 0));
        }
        int maxKartengeld = (int) karte.getKartenstand();
        int limit = karte.getKartenlimit();

        System.out.println("Max. Bankbetrag: " + maxBankgeld);
        System.out.println("Max. Kartenguthaben: " + maxKartengeld);
        System.out.println("Kartenlimit: " + limit);

        System.out.println("Abhebungsbetrag: " + betrag);
        if (betrag > limit) {
            return "Der Betrag übersteigt das Limit";
        }
        if (betrag > maxKartengeld) {
            return "Der Betrag übersteigt das verfügbare Guthaben";
        }
        return null;
    }

    public boolean scheineVerfuegbar(ArrayList<Geld> scheinListe) {
        for (Geld geld : scheinListe) {
            for (Geld geldSchein : bankStatus) {
                if (geld.getWert() == geldSchein.getWert()) {
                    if (geldSchein.getAnzahl() < geld.getAnzahl()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<Geld> getGeldBetrag(boolean grosseScheine) {
        geldBetraege = new ArrayList<>();
        for (Geld schein : bankStatus) {
            geldBetraege.add(new Geld(schein.getWert(), 0));
        }
        if (grosseScheine) {
            grosseScheine();
        } else {
            kleineScheine();
        }
        if (tmpBetrag != 0) {
            return null;
        } else {
            return geldBetraege;
        }
    }

    public int grosseScheine() {
        tmpBetrag = betrag;
        Collections.sort(geldBetraege, new GeldAbsteigendComparator());
        for (Geld geld : geldBetraege) {
            geld.setAnzahl(tmpBetrag / geld.getWert());
            tmpBetrag = tmpBetrag % geld.getWert();
        }
        return tmpBetrag;
    }

    public int kleineScheine() {
        tmpBetrag = betrag;
        Collections.sort(geldBetraege, new GeldComparator());
        for (Geld geld : geldBetraege) {
            geld.setAnzahl(tmpBetrag / geld.getWert());
            tmpBetrag = tmpBetrag % geld.getWert();
        }
        return tmpBetrag;
    }
}