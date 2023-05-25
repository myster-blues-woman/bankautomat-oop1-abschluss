package ch.hftm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Karte {
    private String kartenId;
    private String vorname;
    private String nachname;
    private String kontoname;
    private String iban;
    private String bankname;
    private int kartennummer;
    private Date ablaufdatum;
    private int pinNr;
    private double kartenstand;
    private int kartenlimit;

    public Karte() {
    }

    public Karte(String kartenId, String vorname, String nachname, String kontoname, String iban, String bankname,
            int kartennummer, String ablaufdatum, int pinNr, double kontostand, int kartenlimit, int blockiert) {
        this.kartenId = kartenId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.kontoname = kontoname;
        this.iban = iban;
        this.bankname = bankname;
        this.kartennummer = kartennummer;
        this.ablaufdatum = this.stringToDate(ablaufdatum);
        this.pinNr = pinNr;
        this.kartenstand = kontostand;
        this.kartenlimit = kartenlimit;
        this.blockiert = blockiert;
    }

    public boolean checkPin(int pin) {
        return this.pinNr == pin;
    }

    public double getKartenstand() {
        return kartenstand;
    }

    public void setKartenstand(double kontostand) {
        this.kartenstand = kontostand;
    }

    public void geldAbheben(int betrag) {
        this.kartenstand = this.kartenstand - betrag;
    }

    public int getKartenlimit() {
        return kartenlimit;
    }

    public void setKartenlimit(int kartenlimit) {
        this.kartenlimit = kartenlimit;
    }

    public String getKarteId() {
        return kartenId;
    }

    public void setKartenId(String kartenId) {
        this.kartenId = kartenId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getKontoname() {
        return kontoname;
    }

    public void setKontoname(String kontoname) {
        this.kontoname = kontoname;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getKartennummer() {
        return kartennummer;
    }

    public void setKartennummer(int kartennummer) {
        this.kartennummer = kartennummer;
    }

    public Date getAblaufdatum() {
        return ablaufdatum;
    }

    public void setAblaufdatum(Date ablaufdatum) {
        this.ablaufdatum = ablaufdatum;
    }

    public int getPinNr() {
        return pinNr;
    }

    public void setPinNr(int pinNr) {
        this.pinNr = pinNr;
    }
}