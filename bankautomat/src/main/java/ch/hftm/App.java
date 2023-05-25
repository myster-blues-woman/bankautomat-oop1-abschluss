package ch.hftm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        // Create instances of Geld class
        Geld geld1 = new Geld(10, 5);
        Geld geld2 = new Geld(20, 3);

        // Print information about Geld objects
        System.out.println("Geld 1:");
        System.out.println("Wert: " + geld1.getWert());
        System.out.println("Anzahl: " + geld1.getAnzahl());
        System.out.println("Gesamt: " + geld1.getGesamt());

        System.out.println();

        System.out.println("Geld 2:");
        System.out.println("Wert: " + geld2.getWert());
        System.out.println("Anzahl: " + geld2.getAnzahl());
        System.out.println("Gesamt: " + geld2.getGesamt());

        System.out.println();

        // Create instances of Karte class
        Karte karte1 = new Karte("1234567890", "John", "Doe", "John Doe", "CH1234567890", "Example Bank", 12345678,
                "2023-12-31", 1234, 1000.0, 5000, 0);
        Karte karte2 = new Karte("0987654321", "Jane", "Smith", "Jane Smith", "CH0987654321", "Sample Bank", 87654321,
                "2024-06-30", 4321, 500.0, 2000, 1);

        // Print information about Karte objects
        System.out.println("Karte 1:");
        System.out.println("KartenID: " + karte1.getKarteId());
        System.out.println("Vorname: " + karte1.getVorname());
        System.out.println("Nachname: " + karte1.getNachname());
        System.out.println("Kontoname: " + karte1.getKontoname());
        System.out.println("IBAN: " + karte1.getIban());
        System.out.println("Bankname: " + karte1.getBankname());
        System.out.println("Kartennummer: " + karte1.getKartennummer());
        System.out.println("Ablaufdatum: " + formatDate(karte1.getAblaufdatum()));
        System.out.println("PIN: " + karte1.getPinNr());
        System.out.println("Kartenstand: " + karte1.getKartenstand());
        System.out.println("Kartenlimit: " + karte1.getKartenlimit());
        System.out.println("Blockiert: " + karte1.getBlockiert());

        System.out.println();

        System.out.println("Karte 2:");
        System.out.println("KartenID: " + karte2.getKarteId());
        System.out.println("Vorname: " + karte2.getVorname());
        System.out.println("Nachname: " + karte2.getNachname());
        System.out.println("Kontoname: " + karte2.getKontoname());
        System.out.println("IBAN: " + karte2.getIban());
        System.out.println("Bankname: " + karte2.getBankname());
        System.out.println("Kartennummer: " + karte2.getKartennummer());
        System.out.println("Ablaufdatum: " + formatDate(karte2.getAblaufdatum()));
        System.out.println("PIN: " + karte2.getPinNr());
        System.out.println("Kartenstand: " + karte2.getKartenstand());
        System.out.println("Kartenlimit: " + karte2.getKartenlimit());
        System.out.println("Blockiert: " + karte2.getBlockiert());
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}