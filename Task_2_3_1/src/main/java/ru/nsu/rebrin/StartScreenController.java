package ru.nsu.rebrin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScreenController {

    @FXML
    TextField widthField;
    @FXML
    TextField heightField;
    @FXML
    TextField winField;
    @FXML
    TextField initLenField;
    @FXML
    TextField cntAppleField;
    @FXML
    TextField cntStupidSnakes;
    @FXML
    TextField cntSmartSnakes;

    @FXML
    Button startButton;

    public SnakeModel getSettings() {
        int width = SettingsParser.parseOrDefault(widthField.getText(), 20);
        int height = SettingsParser.parseOrDefault(heightField.getText(), 20);
        int win = SettingsParser.parseOrDefault(winField.getText(), 10);
        int initLen = SettingsParser.parseOrDefault(initLenField.getText(), 1);
        int cntApple = SettingsParser.parseOrDefault(cntAppleField.getText(), 3);
        int cntStupid = SettingsParser.parseOrDefault(cntStupidSnakes.getText(), 0);
        int cntSmart = SettingsParser.parseOrDefault(cntSmartSnakes.getText(), 0);

        SnakeModel model = new SnakeModel();
        model.width = width;
        model.height = height;
        model.winn = win;
        model.snakeLength = initLen;
        model.appleCount = cntApple;
        model.smart = cntSmart;
        model.stupid = cntStupid;
        model.initSnake();

        return model;
    }

    // Устанавливаем обработчик кнопки старта
    public void setStartButtonAction(Runnable startAction) {
        startButton.setOnAction(e -> startAction.run());
    }
}
