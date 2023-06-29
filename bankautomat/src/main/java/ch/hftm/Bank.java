package ch.hftm;

import java.util.ArrayList;

public class Bank {

    private ArrayList<Geld> geldStatus = new ArrayList<>();

    public String[] data = {
            "20:192",
            "50:200",
            "100:199",
            "200:200",
            "1000:199"
    };

    public ArrayList<Geld> getBankStatus() {
        return this.geldStatus;
    }

    public void updateMoneyStatus(int bill, int number) {
        for (Geld geld : this.geldStatus) {
            if (geld.getWert() == bill) {
                geld.setAnzahl(number);
            }
        }
    }

    public void readGeldStatus() {
        geldStatus = new ArrayList<>();

        for (String line : data) {
            String[] parts = line.split(":");
            int bill = Integer.parseInt(parts[0]);
            int number = Integer.parseInt(parts[1]);
            Geld money = new Geld(bill, number);
            geldStatus.add(money);
        }
    }
}
