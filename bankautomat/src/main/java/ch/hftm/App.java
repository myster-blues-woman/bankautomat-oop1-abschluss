package ch.hftm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ObservableList<Karte> kartenList = FXCollections.observableArrayList();

    public App() {
        kartenList
                .add(new Karte("3389dsd8d9", "Adriana", "Faraci", "Sparkonto", "CH123456789", "BKBE", 12345678, "06/25",
                        1234, 5000.0, 10000, 0));
        kartenList
                .add(new Karte("3389dsd8d2", "Max", "Mustermann", "Girokonto", "CH987654321", "UBS", 87654321, "09/23",
                        4321, 10000.0, 15000, 0));
        kartenList.add(
                new Karte("3389dsd8d5", "Laura", "Musterfrau", "Sparkonto", "CH456789123", "Beispiel Bank", 65432198,
                        "12/24", 9876, 20000.0, 25000, 0));
    }

    public static void main(String[] args) {
        launch();
    }

    public static ObservableList<Karte> getKartenList() {
        return kartenList;
    }

    public static void addKarte(Karte karte) {
        kartenList.add(karte);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 644, 332);
        stage.setScene(scene);
        stage.show();
    }

    static void setSceneRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}