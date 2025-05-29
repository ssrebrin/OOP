package ru.nsu.rebrin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller for the start screen UI.
 * Handles user input fields and starts the game with configured settings.
 */
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

    /**
     * Reads and parses settings from input fields,
     * creates and initializes a SnakeModel with these settings.
     *
     * @return initialized SnakeModel based on user input or default values
     */
    public SnakeModel getSettings() {

        SnakeModel model = new SnakeModel();

        model.width = parseOrDefault(widthField.getText(), 20);
        model.height = parseOrDefault(heightField.getText(), 20);
        model.winn = parseOrDefault(winField.getText(), 10);
        model.snakeLength = parseOrDefault(initLenField.getText(), 1);
        model.appleCount = parseOrDefault(cntAppleField.getText(), 3);
        model.smart = parseOrDefault(cntStupidSnakes.getText(), 0);
        model.stupid = parseOrDefault(cntSmartSnakes.getText(), 0);
        model.initSnake();

        return model;
    }

    /**
     * Parses integer from string or returns default value if parsing fails.
     *
     * @param text input string to parse
     * @param defaultValue value to return if parsing fails
     * @return parsed integer or defaultValue
     */
    static int parseOrDefault(String text, int defaultValue) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Checks if a string is a palindrome, ignoring case and non-alphanumeric characters.
     *
     * @param input string to check
     * @return true if input is palindrome, false otherwise
     */
    public static boolean isPalindrome(String input) {
        if (input == null) {
            return false;
        }

        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int len = cleaned.length();

        for (int i = 0; i < len / 2; i++) {
            if (cleaned.charAt(i) != cleaned.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the action to be executed when the start button is pressed.
     *
     * @param startAction Runnable action to run on button click
     */
    public void setStartButtonAction(Runnable startAction) {
        startButton.setOnAction(e -> startAction.run());
    }
}