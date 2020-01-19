package controller.crud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import main.MainLauncher;
import model.proxy.DictionaryProxy;
import model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class EditWordController {
    public Stage window;
    private DictionaryProxy dictionaryProxy;

    @FXML
    public TextField polishTextField, englishTextField;
    public Label resultLabel;

    @FXML
    public void initialize() {
        this.window = Main.window;
    }

    public void editWord(ActionEvent actionEvent) throws IOException {
        String polishWord = polishTextField.getText();
        String englishWord = englishTextField.getText();
        if (polishTextField.getText().equals("") || englishTextField.getText().equals("")) {
            resultLabel.setText("Pola nie mogą być puste!");
            return;
        }
        Iterator<Word> it = dictionaryProxy.iterator();
        Word word;
        while (it.hasNext()) {
            word = it.next();
            if (word.getPolishWord().equals(polishWord) && word.getEnglishWord().equals(englishWord)) {
                resultLabel.setText("Dane słowa już istnieją.");
                return;
            } else if (word.getPolishWord().equals(polishWord) && !word.getEnglishWord().equals(englishWord)) {
                word.setEnglishWord(englishWord);
                saveEditedDictionary();
                resultLabel.setText("Zedytowano słowo.");
                return;
            } else if (word.getEnglishWord().equals(englishWord) && !word.getPolishWord().equals(polishWord)) {
                word.setPolishWord(polishWord);
                saveEditedDictionary();
                resultLabel.setText("Zedytowano słowo.");
                return;
            }
        }
        resultLabel.setText("Nie znaleziono słowa.");
    }

    private void saveEditedDictionary() throws IOException {
        FileWriter writer = new FileWriter(new File(Objects.requireNonNull(MainLauncher.class.getClassLoader().getResource(dictionaryProxy.getLevel().getName() + "dictionaryProxy")).getFile()), false);
        for (int i = 0; i < dictionaryProxy.getWordList().size(); i++) {
            writer.write(dictionaryProxy.getWordList().get(i).getEnglishWord() + "=" + dictionaryProxy.getWordList().get(i).getPolishWord() + "\n");
        }
        writer.close();
        dictionaryProxy = new DictionaryProxy(dictionaryProxy.getLevel());
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/crud/DictionaryView.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("Menu słownika");
        window.show();
    }

    public void setDictionaryProxy(DictionaryProxy dictionaryProxy) {
        this.dictionaryProxy = dictionaryProxy;
    }
}
