package ch.hftm;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BankautomatController {
    enum OperationLevel {
        START,
        AUTHENTICATION,
        OPERATION,
        OLDPIN,
        NEWPIN,
        GETCHF,
        RECEIPT,
        BILLSIZE,
        SUGGESTION,
        SALDO,
        BLOCKCARD,
        SALDOOPTION
    }

    OperationLevel step = OperationLevel.START;
    @FXML
    private ListView<String> objekteListView;
    @FXML
    private Button erfassenButton;
    @FXML
    private Button aendernButton;
    @FXML
    private Button loeschenButton;
    @FXML
    private Button btnOptionL1;
    @FXML
    private Button btnOptionL2;
    @FXML
    private Button btnOptionL3;
    @FXML
    private Button btnOptionR2;
    @FXML
    private Button btnOptionR3;
    @FXML
    private Button btnOptionR1;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn0;
    @FXML
    private ComboBox<String> cardComboBox;
    @FXML
    private Label labelMain;
    @FXML
    private Label labelOptR1;
    @FXML
    private Label labelOptR2;
    @FXML
    private Label labelOptR3;
    @FXML
    private Label labelOptL1;
    @FXML
    private Label labelOptL2;
    @FXML
    private Label labelOptL3;
    @FXML
    private Pane quittungPane;
    @FXML
    private Button btnAbbruch;

    private StringBuilder pinBuilder;
    private Karte eingeloggteKarte;
    private int enteredPinNumber = 0;
    boolean grosseBanknoten = true;
    boolean withReceipt = true;
    int suggLess = 0;
    int suggMore = 0;
    boolean authorized = false;
    GeldService geldService = new GeldService();

    public void setKarte(Karte selectedKarte) {
        this.eingeloggteKarte = selectedKarte;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.authorized = isAuthorized;
    }

    public void initialize() {
        this.setPinBtnDisabled(true);
        pinBuilder = new StringBuilder();
        quittungPane.setVisible(false);

        btn1.setOnAction(event -> handleDigitButton("1"));
        btn2.setOnAction(event -> handleDigitButton("2"));
        btn3.setOnAction(event -> handleDigitButton("3"));
        btn4.setOnAction(event -> handleDigitButton("4"));
        btn5.setOnAction(event -> handleDigitButton("5"));
        btn6.setOnAction(event -> handleDigitButton("6"));
        btn7.setOnAction(event -> handleDigitButton("7"));
        btn8.setOnAction(event -> handleDigitButton("8"));
        btn9.setOnAction(event -> handleDigitButton("9"));
        btn0.setOnAction(event -> handleDigitButton("0"));
    }

    private void handleDigitButton(String digit) {
        pinBuilder.append(digit);
        switch (this.step) {
            case NEWPIN:
                labelMain.setText("Neuer PIN: " + pinBuilder.toString());
                break;
            case OLDPIN:
                labelMain.setText("Alter PIN: " + pinBuilder.toString());
                break;
            case GETCHF:
                labelMain.setText(
                        "Wähle einen Wert den Sie abheben möchten oder geben Sie einen eigenen ein: "
                                + pinBuilder.toString());
            default:
                break;
        }
    }

    @FXML
    private void btnEnterClicked() {
        switch (this.step) {
            case START:
                break;
            case AUTHENTICATION:
                break;
            case GETCHF:
                String number = pinBuilder.toString();
                int customAmount = Integer.parseInt(number);
                this.doMoneyCheck(customAmount);
                break;
            case OLDPIN:
                if (pinBuilder.length() > 0) {
                    String enteredPIN = pinBuilder.toString();
                    this.enteredPinNumber = Integer.parseInt(enteredPIN);

                    if (this.enteredPinNumber != this.eingeloggteKarte.getPinNr()) {
                        labelMain.setText("Falscher PIN. Bitte geben Sie den alten PIN erneut ein:");
                        pinBuilder.setLength(0);
                    } else {
                        pinBuilder.setLength(0);
                        finishPinChange();
                    }
                } else {
                    labelMain.setText("Bitte geben sie einegültige zahl ein");
                }
                break;
            case NEWPIN:
                if (pinBuilder.length() > 0) {
                    String newPIN = pinBuilder.toString();
                    int pinNumber = Integer.parseInt(newPIN);
                    eingeloggteKarte.setPinNr(pinNumber);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Erfolg");
                    alert.setHeaderText(null);
                    alert.setContentText("PIN erfolgreich geändert. Klicken Sie auf OK, um fortzufahren.");
                    alert.showAndWait();
                    pinBuilder.setLength(0);
                    this.setPinBtnDisabled(true);
                    this.startOption();

                    break;
                }
            default:
                break;
        }

    }

    @FXML
    private void btnAbbrechenClicked() {
        rejectCard();
    }

    @FXML
    private void btnOptionR1Clicked() {
        switch (this.step) {
            case RECEIPT:
                System.out.println("Ohne Beleg");
                this.withReceipt = false;
                this.getNotes();
                break;
            default:
                this.startPinChange();
                break;
        }
    }

    @FXML
    private void pinÄndernClicked() {
        switch (this.step) {
            case NEWPIN:
                this.startPinChange();
                break;
            case RECEIPT:
                System.out.println("Ohne Beleg");
                this.withReceipt = false;
                this.getNotes();
                // eject card and get money
                break;
            case BILLSIZE:
                System.out.println("Kleine Noten");
                this.grosseBanknoten = false;
                askForReceipt();
                break;
            case GETCHF:
            case SUGGESTION:
                int amount = Integer.parseInt(labelOptR1.getText());
                this.doMoneyCheck(amount);
                break;
            default:
                this.startPinChange();
                break;
        }
    }

    @FXML
    private void kontostandCLicked() {
        switch (this.step) {
            case SALDO:
                this.showSaldo();
                break;
            case RECEIPT:
                System.out.println("mit beleg!");
                this.withReceipt = true;
                this.getNotes();
                break;
            case GETCHF:
            case SUGGESTION:
                int amount = Integer.parseInt(labelOptR3.getText());
                this.doMoneyCheck(amount);
                break;
            default:
                this.showSaldo();
                break;
        }

    }

    @FXML
    private void karteBlockierenClicked() {
        switch (this.step) {
            case BLOCKCARD:
                showConfirmationBlockDialog("Karte blockieren", eingeloggteKarte.toString(),
                        "Möchten Sie die Karte wirklich blockieren?");
                break;
            case GETCHF:
            case SUGGESTION:
                int amount = Integer.parseInt(labelOptR2.getText());
                this.doMoneyCheck(amount);
                break;
            default:
                showConfirmationBlockDialog("Karte blockieren", eingeloggteKarte.toString(),
                        "Möchten Sie diese Karte wirklich blockieren?");
                break;
        }
    }

    @FXML
    private void handleKorrekturButton() {
        System.out.println("clicked" + pinBuilder.length());
        if (pinBuilder.length() > 0) {
            pinBuilder.deleteCharAt(pinBuilder.length() - 1);
            updatePinDisplay();
        }
    }

    private void updatePinDisplay() {
        switch (this.step) {
            case NEWPIN:
                labelMain.setText("Neuer PIN: " + pinBuilder.toString());
                break;
            case OLDPIN:
                labelMain.setText("Alter PIN: " + pinBuilder.toString());
                break;
            case GETCHF:
                labelMain.setText(
                        "Wähle einen Wert den Sie abheben möchten oder geben Sie einen eigenen ein: "
                                + pinBuilder.toString());
            default:
                break;
        }
    }

    @FXML
    private void getMoneyClicked() {
        if (eingeloggteKarte != null) {
            startGettingChf();
        }
    }

    private void startGettingChf() {
        this.step = OperationLevel.GETCHF;
        labelMain.setText(
                "Wähle einen Wert den Sie abheben möchten oder geben Sie einen eigenen ein:");
        labelMain.setWrapText(true);
        this.disableOptions();
        setOption(labelOptR1, "50", true);
        setOption(labelOptR2, "100", true);
        setOption(labelOptR3, "200", true);
        setOption(labelOptL1, "1000", true);
        this.setPinBtnDisabled(false);
    }

    private void startPinChange() {
        this.step = OperationLevel.OLDPIN;
        this.disableOptions();
        labelMain.setText("Alter Pin");
        this.setPinBtnDisabled(false);
    }

    private void finishPinChange() {
        if (eingeloggteKarte != null) {
            labelMain.setText(eingeloggteKarte.getNachname());
            this.step = OperationLevel.NEWPIN;
            if (pinBuilder.length() > 4) {
                labelMain.setText("Bitte drücken Sie 'Enter', um die PIN zu bestätigen.");
            } else {
                labelMain.setText("Bitte geben Sie eine neue PIN ein.");
            }
        }
    }

    private void blockCard() {
        eingeloggteKarte.setBlockiert();
        labelMain.setText("Ihre Karte wurde erfolgreich blockiert.");
        this.setPinBtnDisabled(true);
        this.disableOptions();

    }

    private void showConfirmationBlockDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                blockCard();
            }
        });
    }

    private void askForBillSize() {
        this.step = OperationLevel.BILLSIZE;
        this.setPinBtnDisabled(false);
        this.disableOptions();
        labelMain.setText("");
        setOption(labelOptR1, "Kleine Noten", true);
        setOption(labelOptR3, "Grosse Noten", true);
    }

    private void askForReceipt() {
        this.step = OperationLevel.RECEIPT;
        this.setPinBtnDisabled(false);
        this.disableOptions();
        labelMain.setText("");
        setOption(labelOptL3, "Ohne Beleg", true);
        setOption(labelOptR3, "Mit Beleg", true);
    }

    private void newSuggestion(int suggLess, int suggMore) {
        this.step = OperationLevel.SUGGESTION;
        if (suggLess >= 20) {
            this.setOption(labelOptL3, String.valueOf(suggLess), true);
        }
        this.setOption(labelOptR3, String.valueOf(suggMore), true);
        this.labelMain.setText("Ausgabe nicht möglich, vorschlag übernehmen?");
    }

    private void showSaldo() {
        this.step = OperationLevel.SALDO;
        this.disableOptions();
        this.setOption(labelOptR2, "Zurück", true);
        this.labelMain.setText("Saldo: \"" + eingeloggteKarte.getKartenstand() + "" + "Fr.\"Limit: \""
                + eingeloggteKarte.getKartenlimit() + "Fr.\"");
    }

    private void disableOptions() {
        setOption(labelOptL1, "", false);
        setOption(labelOptL2, "", false);
        setOption(labelOptR1, "", false);
        setOption(labelOptR2, "", false);
        setOption(labelOptR3, "", false);
    }

    private void setOption(Label label, String text, boolean visible) {
        label.setText(text);
        label.setVisible(visible);
    }

    private void setPinBtnDisabled(boolean status) {
        btn1.setDisable(status);
        btn2.setDisable(status);
        btn3.setDisable(status);
        btn4.setDisable(status);
        btn5.setDisable(status);
        btn6.setDisable(status);
        btn7.setDisable(status);
        btn8.setDisable(status);
        btn9.setDisable(status);
        btn0.setDisable(status);
    }

    private void startOption() {
        if (this.authorized && !this.eingeloggteKarte.isBlockiert()) {
            this.step = OperationLevel.OPERATION;
            labelMain.setText("Wähle eine Option");
            this.disableOptions();
            setOption(labelOptR3, "Kontostand", true);
            setOption(labelOptR1, "Pin Ändern", true);
            setOption(labelOptL1, "CHF", true);
            setOption(labelOptR2, "Karte blockieren", true);
        } else if (this.eingeloggteKarte.isBlockiert()) {
            this.authorized = false;
            rejectBlockedCard();
        }
    }

    private void rejectBlockedCard() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Karte blockiert");
        alert.setHeaderText(null);
        alert.setContentText("Karte ist blockiert, bitte wenden Sie sich an Ihre Bank.");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setPrefWidth(400);

        Stage dialogStage = (Stage) dialogPane.getScene().getWindow();
        dialogStage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                    Parent root = loader.load();
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                    dialogStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void rejectCard() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Karte auswerfen");
        alert.setHeaderText(null);
        alert.setContentText("Karte ausgeworfen, bitte Karte neu einfügen");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
        dialogPane.setPrefWidth(400);

        Stage dialogStage = (Stage) dialogPane.getScene().getWindow();
        dialogStage.setAlwaysOnTop(true);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                    Parent root = loader.load();
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                    dialogStage.close();
                    Stage currentStage = (Stage) btnAbbruch.getScene().getWindow();
                    currentStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doMoneyCheck(int amount) {
        this.geldService = new GeldService();
        boolean valid = checkAmountValidity(amount);
        if (valid) {
            this.geldService.setBetrag(amount);
            String available = this.geldService.pruefeVerfuegbaresGeld(eingeloggteKarte);
            if (available == null) {
                askForBillSize();
            } else {
                this.startGettingChf();
                labelMain.setText(available + " " + "Wähle einen anderen Betrag:");
                labelMain.setWrapText(true);
            }
        } else {
            this.labelMain.setText(
                    "Wert ungültig, bitte wähle ein Wert der in Noten ausgegeben werden kann.");
        }
    }

    private boolean checkAmountValidity(int amount) {
        return !(amount % 10 != 0 || amount < 20);
    }

    private void ejectAndGetMoney(ArrayList<Geld> noteList) {
        if (this.withReceipt) {
            quittungPane.setVisible(true);
        }
        labelMain.setText("Karte ausgeworfen,bitte geld entnehmen");
        System.out.println(noteList);

    }

    private void getNotes() {
        int grosseScheine = this.geldService.grosseScheine();
        int kleineScheine = this.geldService.kleineScheine();
        ArrayList<Geld> noteList = null;

        if (this.grosseBanknoten && grosseScheine == 0) {
            noteList = this.geldService.getGeldBetrag(this.grosseBanknoten);
        } else if (!this.grosseBanknoten && kleineScheine == 0) {
            noteList = this.geldService.getGeldBetrag(this.grosseBanknoten);
        } else if (this.grosseBanknoten && this.geldService.grosseScheine() != 0) {
            if (this.geldService.kleineScheine() == 0) {
                noteList = this.geldService.getGeldBetrag(false);
            } else {
                System.out.println("augabe nicht möglich: " + grosseScheine);
                System.out.println("vorschlag: " + (grosseScheine + geldService.getBetrag()) + ", oder: "
                        + (geldService.getBetrag() - grosseScheine));
                this.newSuggestion((geldService.getBetrag() - grosseScheine),
                        (grosseScheine + geldService.getBetrag()));
            }
        } else if (!this.grosseBanknoten && kleineScheine != 0) {
            if (grosseScheine == 0) {
                noteList = this.geldService.getGeldBetrag(true);
            } else {
                System.out.println("augabe nicht möglich: " + kleineScheine);
                System.out.println("vorschlag: " + (kleineScheine + geldService.getBetrag()) + ", oder: "
                        + (geldService.getBetrag() - kleineScheine));
                this.newSuggestion((geldService.getBetrag() - kleineScheine),
                        (kleineScheine + geldService.getBetrag()));
            }
        } else {
            this.startGettingChf();
            this.labelMain.setText(
                    ">Es ist ein fehler aufgetreten, wähle einen Betrag den Sie abheben möchten.");
        }
        if (noteList != null) {
            if (this.geldService.scheineVerfuegbar(noteList)) {
                this.ejectAndGetMoney(noteList);
            } else {
                this.startGettingChf();
                labelMain.setText("Noten nicht verfügbar");
            }
        }
    }
}