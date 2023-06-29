package ch.hftm;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LoginController {

    @FXML
    private TextField pinTextField;
    @FXML
    private Button einloggenButton;
    @FXML
    private Button neuKarteButton;
    @FXML
    private Label labelMainLogin;
    @FXML
    private ListView<String> objekteListView;
    @FXML
    private ComboBox<Karte> kartenAuswahlComboBox;

    public Karte eingeloggteKarte = null;
    boolean authorized = false;
    int invalidPwCounter = 0;

    public Karte getEingeloggteKarte() {
        return eingeloggteKarte;
    }

    @FXML
    private void initialize() {
        kartenAuswahlComboBox.setItems(App.getKartenList());
        labelMainLogin.setVisible(false);
        kartenAuswahlComboBox.setCellFactory(
                (Callback<ListView<Karte>, ListCell<Karte>>) new Callback<ListView<Karte>, ListCell<Karte>>() {
                    @Override
                    public ListCell<Karte> call(ListView<Karte> listView) {
                        return new ListCell<Karte>() {
                            @Override
                            protected void updateItem(Karte karte, boolean empty) {
                                super.updateItem(karte, empty);

                                if (empty || karte == null) {
                                    setText(null);
                                } else {
                                    setText(karte.toString());
                                }
                            }
                        };
                    }
                });
    }

    @FXML
    private void einloggenButtonClicked() throws IOException {
        eingeloggteKarte = kartenAuswahlComboBox.getSelectionModel().getSelectedItem();
        String pin = pinTextField.getText();

        boolean loginSuccessful = false;
        int number = Integer.parseInt(pin);
        loginSuccessful = eingeloggteKarte.checkPin(number);
        if (loginSuccessful) {
            authorized = true;
            try {
                Karte eingeloggteKarte = getEingeloggteKarte();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/bankautomat.fxml"));
                Parent root = loader.load();
                BankautomatController controller = loader.getController();
                controller.setKarte(eingeloggteKarte);
                controller.setAuthorized(authorized);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage currentStage = (Stage) einloggenButton.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Einloggen fehlgeschlagen
            eingeloggteKarte = null;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Einloggen fehlgeschlagen");
            alert.setContentText("Die eingegebene Kartennummer oder PIN ist ungültig.");
            alert.showAndWait();
            tryLoggin();
        }
    }

    private void pullInCard() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Einloggen fehlgeschlagen");
        alert.setContentText("Karte wurde eingezogen, wenden Sie sich an ihre Bank");
    }

    private void tryLoggin() {
        if (!this.authorized) {
            this.invalidPwCounter++;
            switch (this.invalidPwCounter) {
                case 1:
                    labelMainLogin.setVisible(true);
                    labelMainLogin
                            .setText("Pin ist falsch,Sie haben noch zwei versuche!");
                    break;
                case 2:
                    labelMainLogin.setVisible(true);
                    labelMainLogin
                            .setText("Pin ist falsch,Sie haben noch einen versuch!");
                    break;
                case 3:
                    this.pullInCard();
                    break;
            }
        }
    }

    @FXML
    private void neuKarteButtonClicked() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/karte.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage) neuKarteButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void löschenKarteButtonClicked() {
        Karte löschKarte = kartenAuswahlComboBox.getSelectionModel().getSelectedItem();
        String pin = pinTextField.getText();
        boolean pinSuccessful = false;
        int number = Integer.parseInt(pin);
        pinSuccessful = löschKarte.checkPin(number);

        if (löschKarte != null && pinSuccessful) {
            showDeleteConfirmationDialog(löschKarte);
        }
    }

    private void showDeleteConfirmationDialog(Karte karte) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Karte löschen");
        alert.setHeaderText(null);
        alert.setContentText("Sind Sie sicher, dass Sie die Karte löschen möchten?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteKarte(karte);
        }
    }

    private void deleteKarte(Karte karte) {
        pinTextField.setText(null);
        App.getKartenList().remove(karte);
    }

}
