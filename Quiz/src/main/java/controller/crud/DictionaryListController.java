package controller.crud;

import controller.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.Main;
import model.Dictionary;
import model.Word;

import java.util.ArrayList;
import java.util.Iterator;

public class DictionaryListController {
    public Stage window;
    public ObservableList<String> dictionaryList;
    public ListView<String> list;
    public ArrayList<String> array;

    private Dictionary dictionary;

    @FXML
    public void initialize() {
        this.window = Main.window;
    }

    void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/crud/DictionaryView.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("Menu słownika");
        window.show();
    }

    public void loadList() {
        array = new ArrayList<>();
        Iterator<Word> it = dictionary.iterator();
        Word word;
        while (it.hasNext()) {
            word = it.next();
            array.add(word.getEnglishWord() + "=" + word.getPolishWord());
        }
        dictionaryList = FXCollections.observableArrayList(array);
        list.setItems(dictionaryList);
    }

}