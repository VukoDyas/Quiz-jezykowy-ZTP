package controller.level;

import controller.QuizSelectController;
import controller.question.TestClosedQestionController;
import controller.question.TestOpenQuestionController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;
import model.Dictionary;
import model.progress.Level;
import model.question.OpenedQuestion;
import model.quiz.TestQuiz;

import java.io.IOException;

public class TestQuizLevelSelectController {
    public Stage window;
    private String selectedLevel;
    private Dictionary dictionary;

    public TestQuizLevelSelectController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestQuiz/TestQuizLevelSelectView.fxml"));
        loader.setController(this);
        this.window = Main.window;
        this.window.setScene(new Scene(loader.load()));
        this.window.setTitle("Wybór poziomu testu");
        this.window.show();
    }

    @FXML
    public void initialize() {
        this.window = Main.window;
        selectedLevel = null;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void startTestQuiz(ActionEvent actionEvent) throws Exception {
        this.selectedLevel = ((Button) actionEvent.getSource()).getText();
        this.dictionary = new Dictionary(new Level(this.selectedLevel));

        TestQuiz testQuiz = new TestQuiz(dictionary, 10);
        if (testQuiz.getCurrentQuestion() instanceof OpenedQuestion) {
            new TestOpenQuestionController(testQuiz);
        } else {
            new TestClosedQestionController(testQuiz);
        }
    }

    public void back() throws Exception {
        new QuizSelectController();
    }
}
