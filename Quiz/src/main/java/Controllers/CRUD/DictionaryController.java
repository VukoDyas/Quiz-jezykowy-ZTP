package Controllers.CRUD;

import MainPackage.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Dictionary;
import models.Level;

import java.util.Map;

public class DictionaryController {
    public Stage window;

    private String selectedLevel = "A1"; //TODO: unhardcode
    private Dictionary dictionary;

    @FXML
    public void initialize() {
        this.window = Main.window;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
    public static Map<String, String> oldDict;
    public static void loadDictionary(String selectedLevel) {
    }

    public void addWord(ActionEvent actionEvent) throws Exception {
        this.dictionary = new Dictionary(new Level(this.selectedLevel));
        AddWordController addWordController = new AddWordController(this);
    }

    public void editWord(ActionEvent actionEvent) throws Exception {
        this.dictionary = new Dictionary(new Level(this.selectedLevel));
        EditWordController editWordController = new EditWordController(this);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CRUD/EditWordView.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("Edycja słowa");
        window.show();
    }

    public void removeWord(ActionEvent actionEvent) throws Exception {
        this.dictionary = new Dictionary(new Level(this.selectedLevel));
        RemoveWordController removeWordController = new RemoveWordController(this);

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CRUD/RemoveWordView.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("Usuwanie słowa");
        window.show();
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuView.fxml"));
        window.setScene(new Scene(root));
        window.show();
    }
}
