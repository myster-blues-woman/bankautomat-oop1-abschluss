package ch.hftm;

import java.io.IOException;
import java.util.UUID;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KarteController {
    @FXML
    private TextField kartenIdField;
    @FXML
    private TextField vornameField;
    @FXML
    private TextField nachnameField;
    @FXML
    private TextField kontonameField;
    @FXML
    private TextField ibanField;
    @FXML
    private TextField banknameField;
    @FXML
    private TextField kartennummerField;
    @FXML
    private TextField ablaufdatumField;
    @FXML
    private TextField pinNrField;
    @FXML
    private TextField kontostandField;
    @FXML
    private TextField kartenlimitField;
    @FXML
    private Button speichernButton;
    @FXML
    private Button abbrechenButton;
    @FXML
    private Label karteGespeichertLabel;

    @FXML
    private void initialize() {
        karteGespeichertLabel.setVisible(false);
    }

    @FXML
    private void handleSpeichernButton() {
        String kartenId = UUID.randomUUID().toString();
        String vorname = vornameField.getText();
        String nachname = nachnameField.getText();
        String kontoname = kontonameField.getText();
        String iban = ibanField.getText();
        String bankname = banknameField.getText();
        int kartennummer = Integer.parseInt(kartennummerField.getText());
        String ablaufdatum = ablaufdatumField.getText();
        int pinNr = Integer.parseInt(pinNrField.getText());
        double kontostand = Double.parseDouble(kontostandField.getText());
        int kartenlimit = Integer.parseInt(kartenlimitField.getText());

        Karte neueKarte = new Karte(kartenId, vorname, nachname, kontoname, iban, bankname, kartennummer, ablaufdatum,
                pinNr, kontostand, kartenlimit, 0);

        App.addKarte(neueKarte);
        karteGespeichertLabel.setVisible(true);

        // Zurücksetzen der Eingabefelder
        vornameField.clear();
        nachnameField.clear();
        kontonameField.clear();
        ibanField.clear();
        banknameField.clear();
        kartennummerField.clear();
        ablaufdatumField.clear();
        pinNrField.clear();
        kontostandField.clear();
        kartenlimitField.clear();
    }

    @FXML
    private void handleAbbrechenButton() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) abbrechenButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
