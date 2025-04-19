package ru.nsu.rebrin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScreenController {

    @FXML
    private TextField widthField, heightField, winField, initLenField, cntAppleField;

    @FXML
    private Button startButton;

    public SnakeModel getSettings() {
        int width = parseOrDefault(widthField.getText(), 50);
        int height = parseOrDefault(heightField.getText(), 40);
        int win = parseOrDefault(winField.getText(), 10);
        int initLen = parseOrDefault(initLenField.getText(), 1);
        int cntApple = parseOrDefault(cntAppleField.getText(), 3);

        SnakeModel model = new SnakeModel();
        model.width = width;
        model.height = height;
        model.winn = win;
        model.snakeLength = initLen;
        model.appleCount = cntApple;

        return model;
    }
    private int parseOrDefault(String text, int defaultValue) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Устанавливаем обработчик кнопки старта
    public void setStartButtonAction(Runnable startAction) {
        startButton.setOnAction(e -> startAction.run());
    }
}
